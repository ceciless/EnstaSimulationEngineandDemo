/**
* Classe BateauAVoile.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.models.environment.Environment;
import enstabretagne.base.models.environment.Force;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.IContinuousRate;
import enstabretagne.simulation.core.IContinuousState;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.ISailBoat;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics.SailBoatContinuous;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics.SailBoatRate;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics.SailBoatState;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail.Gouvernail;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile.Voile;

public class BateauAVoile extends SimEntity implements ISailBoat,IRecordable {

	Gouvernail gouvernail;
	Voile voile;
	
	List<Force> forces;
	
	public BateauAVoile(SimEngine engine, String name, SimFeatures features) {
		super(engine,name, features);
		forces = new ArrayList<Force>();
		BateauAVoileFeatures bavFeatures = (BateauAVoileFeatures) features;
		gouvernail=(Gouvernail) createChild(engine,Gouvernail.class, "Gouvernail", bavFeatures.getGouvernailFeatures());
		voile=(Voile) createChild(engine,Voile.class,"Voile",bavFeatures.getVoileFeatures());
	}


	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		BateauAVoileInit bavInit = (BateauAVoileInit) init;
		voile.Initialize(bavInit.voileInit);
		gouvernail.Initialize(bavInit.gouvernailInit);
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterActivate", "Activation du bateau à voile");
		BateauAVoileInit init= (BateauAVoileInit) getInitParameters();
		this.setContinuous(new EtatsContinus(new SailBoatState(init.getBateauInitPosition(),init.getTheta_init(),init.getPhi_init()),LogicalDuration.ofSeconds((0.2))));
		voile.activate();
		gouvernail.activate();
	}

	private class EtatsContinus extends SailBoatContinuous{

		public EtatsContinus(IContinuousState state, LogicalDuration step) {
			super(state, step);
		}

		@Override
		protected void ComputeUntypedRate(IContinuousState ROstate,
				IContinuousRate WOrate) {
			SailBoatState etat = (SailBoatState) ROstate;
			SailBoatRate taux = (SailBoatRate) WOrate;
			
			BateauAVoileInit init = (BateauAVoileInit) getInitParameters();
			BateauAVoileFeatures tno = (BateauAVoileFeatures) getFeatures();
			
//			if(CurrentDate().toString().compareTo("01/09/2014 06:00:00.62")==0)
//				System.out.println("ici");
			
			//identification des axes du bateau
			Rotation rz=new Rotation(Vector3D.PLUS_K,etat.getTheta());
			Vector3D axe_x=rz.applyTo(Vector3D.PLUS_I);

			Vector3D axe_y_horizontal=rz.applyTo(Vector3D.PLUS_J);
			
			Rotation r_axe_x=new Rotation(axe_x,etat.getPhi());
			Vector3D axe_y=r_axe_x.applyTo(axe_y_horizontal);
			Vector3D axe_z=r_axe_x.applyTo(Vector3D.PLUS_K);
			
			//Points application des forces
			Vector3D CcG = axe_y_horizontal.scalarMultiply(tno.getCg_CC_Max()*Math.sin(etat.getPhi()));
			Vector3D CcVc = axe_x.scalarMultiply(tno.getrV());
			Vector3D CcRg = axe_x.scalarMultiply(-tno.getrG());
			Vector3D CcCV = CcVc.add(voile.getCV());
			
			//bilan des forces
			Vector3D poids = Environment.g.scalarMultiply(tno.getM());
			Vector3D fv = voile.getFv();
			Vector3D fg = gouvernail.getFg();
			Vector3D f_friction = etat.getVel().scalarMultiply(-tno.getAlphaF());
			Vector3D fAntiDerive = axe_y_horizontal.scalarMultiply(-Math.signum(etat.getVel().dotProduct(axe_y_horizontal))*0.5 * 1000 * (0.4 * Math.pow(etat.getVel().dotProduct(axe_y_horizontal) * 1.5,2)));
			//archimede compense les composantes selon z du gouvernail et de la voile 
			Vector3D archimede = poids.scalarMultiply(-1).add(Vector3D.PLUS_K.scalarMultiply(-Vector3D.PLUS_K.dotProduct(fg.add(fv))));
			
			forces.clear();
//			forces.add(new Force(CcG,poids));
//			forces.add(new Force(Vector3D.ZERO,archimede));
			forces.add(new Force(CcCV,fv));
			forces.add(new Force(CcRg,fg));
			forces.add(new Force(CcG,etat.getVel()));
			
			//bilan des moments en Cc suivant l'axe x du bateau
			//contribution de la force vélique
			M1 = axe_z.scalarMultiply(Math.abs(CcCV.dotProduct(axe_z))).crossProduct(axe_y.scalarMultiply(fv.dotProduct(axe_y))).dotProduct(axe_x);
			//contribution du poids
			M2 = CcG.crossProduct(poids).dotProduct(axe_x);
			//antifriction selon
			M3 = -tno.getAlphaTheta()*etat.getPhi_point();
			
			//bilan des moments en Cc suivant l'axe z du bateau
			//contribution de la force vélique
			M4 = CcVc.crossProduct(axe_y.scalarMultiply(fv.dotProduct(axe_y))).dotProduct(axe_z);
			//contribution du gouvernail
			M5 = CcRg.crossProduct(axe_y.scalarMultiply(fg.dotProduct(axe_y))).dotProduct(axe_z);
			//contribution du couple anti friction
			M6 = -tno.getAlphaTheta()*etat.getTheta_point();
			
			taux.setCurvRate(etat.getVel().getNorm());//abscisse curviligne 
			taux.setPosRate(etat.getVel());
			taux.setVelRate(poids.add(archimede).add(fv).add(fg).add(f_friction).scalarMultiply(1/tno.getM()).add(fAntiDerive));

			taux.setPhi_rate(etat.getPhi_point());
			taux.setPhi_point_rate((M1+M2+M3)*1.0/tno.getJx());
			taux.setTheta_rate(etat.getTheta_point());
			taux.setTheta_point_rate((M4+M5+M6)*1.0/tno.getJz());
			
		}
		
		@Override
		public void WatchState() {
			super.WatchState();
			Logger.Data((IRecordable)getOwner());
		}
	}

	@Override
	public double getTheta() {
		return ((EtatsContinus) getContinuous()).getTheta();
	}


	@Override
	public double getPhi() {
		return ((EtatsContinus) getContinuous()).getPhi();
		}


	@Override
	public double getDeltag() {
		return gouvernail.getAlpha_gouvernail();
	}


	@Override
	public double getDeltav() {
		return voile.getAlpha_voile();
	}
	


	@Override
	public double getFv() {
		return voile.getFv().getNorm();
	}


	@Override
	public Vector3D getPosition() {
		return((EtatsContinus) getContinuous()).getPosition();
	}


	@Override
	public Vector3D getVitesse() {
		return((EtatsContinus) getContinuous()).getVitesse();
	}


	@Override
	public Vector3D getAcceleration() {
		return((EtatsContinus) getContinuous()).getAcceleration();
	}


	double M1;
	double M2;
	double M3;
	double M4;
	double M5;
	double M6;
	double fv_n;
	double fg_n;
	@Override
	public String[] getTitles() {
		String[] s = {"Phi","Theta","Pos","Vit","M1","M2","M3","M4","M5","M6","fv","fg"};
		return s;
	}


	@Override
	public String[] getRecords() {
		String[] r={Double.toString(getPhi()),Double.toString(getTheta()),getPosition().toString(),Double.toString(getVitesse().getNorm()),Double.toString(M1),Double.toString(M2),Double.toString(M3),Double.toString(M4),Double.toString(M5),Double.toString(M6),Double.toString(fv_n),Double.toString(fg_n)};
		return r;
	}


	@Override
	public String getClassement() {
		return "BaV";
	}


	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Force> getForces() {
		return forces;
	}


	@Override
	public double getPositionRelativeAncrageVoileSurCoque() {
		BateauAVoileFeatures bavf = (BateauAVoileFeatures) getFeatures();
		return bavf.getrV();
	}


	@Override
	public double getPositionRelativeGouvernail() {
		BateauAVoileFeatures bavf = (BateauAVoileFeatures) getFeatures();
		return bavf.getrG();
	}


	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		Logger.Information(this, "AfterActivate", "Désactivation du bateau à voile");
		voile.deactivate();
		gouvernail.deactivate();
		
	}

}

