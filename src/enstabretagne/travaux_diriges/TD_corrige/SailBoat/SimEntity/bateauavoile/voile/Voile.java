/**
* Classe Voile.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.models.environment.Environment;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimObject;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind.Wind;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.BateauAVoile;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler.DoubleGetter;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler.DoubleSetter;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControlerFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControlerInit;

public class Voile extends SimEntity implements SimEntityDoubleControler.IRequired{

	double alpha_voile;
	
	Wind w;
	
	public double getAlpha_voile() {
		return alpha_voile;
	}

	private void setAlpha_voile(double a) {
		alpha_voile=a;
	}

	SimEntityDoubleControler controler;
	public Voile(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
		getAlphaVoile=this::getAlpha_voile;
		setAlphaVoile=this::setAlpha_voile;
		VoileFeatures vf = (VoileFeatures) features;

		controler=(SimEntityDoubleControler) createChild(engine,SimEntityDoubleControler.class, vf.getControlerName(), SimEntityDoubleControlerFeatures.basicFeature);

		
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		List<SimObject> l = getEngine().requestSimObject(new SimObjectRequest() {
			
			@Override
			public boolean filter(SimObject o) {
				return o instanceof Wind;
			}
		});
		if(l.size()>0) w=(Wind) l.get(0);

		VoileInit i = (VoileInit) init;
		alpha_voile=i.getRotationInitVoile();
		controler.Initialize(SimEntityDoubleControlerInit.basicInit);
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		controler.activate();		
	}


	public Vector3D getFv() {
		BateauAVoile bav = (BateauAVoile) getParent();
		
		VoileFeatures vf = (VoileFeatures) getFeatures();
		Vector3D fvent;
		if(w!=null)
		{
			Vector3D vap = w.getWindSpeed().subtract(bav.getVitesse());
			fvent = vap.scalarMultiply((1.0/2.0) * Environment.rho_air_25deg * vf.getSurfaceVoile() * vap.getNorm() * vf.getPortance());			
		}
		else
			fvent = Vector3D.ZERO;
		
		//identification des axes du bateau
		Rotation rz=new Rotation(Vector3D.PLUS_K,bav.getTheta());
		Vector3D axe_x=rz.applyTo(Vector3D.PLUS_I);

		Rotation r_axe_x=new Rotation(axe_x,bav.getPhi());
		Vector3D axe_z=r_axe_x.applyTo(Vector3D.PLUS_K);
		
		Rotation r_voile=new Rotation(axe_z,getAlpha_voile()); 
		
		//conversion de la force du vent en force de voile (orientation de la force liée à la normale à la voile)
		Vector3D fvoile;
		Vector3D normaleVoile = r_voile.applyTo(r_axe_x.applyTo(rz.applyTo(Vector3D.PLUS_J)));
		
		//la force est dans le sens de la force du vent
		fvoile = normaleVoile.scalarMultiply(normaleVoile.dotProduct(fvent));
		return fvoile;
	}
	
	public Vector3D getCV() {
		VoileFeatures vf = (VoileFeatures) getFeatures();
		BateauAVoile bav = (BateauAVoile) getParent();
		
		//identification des axes du bateau
		Rotation rz=new Rotation(Vector3D.PLUS_K,bav.getTheta());
		Vector3D axe_x=rz.applyTo(Vector3D.PLUS_I);

		Rotation r_axe_x=new Rotation(axe_x,bav.getPhi());
		Vector3D axe_z=r_axe_x.applyTo(Vector3D.PLUS_K);
		
		Rotation r_voile=new Rotation(axe_z,getAlpha_voile()); 

		Vector3D positionCV = r_voile.applyTo(r_axe_x.applyTo(rz.applyTo(vf.getPositionCV())));
		
		return positionCV;
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}
	
	DoubleGetter getAlphaVoile;
	DoubleSetter setAlphaVoile;


	@Override
	public DoubleGetter getter() {
		return getAlphaVoile;
	}

	@Override
	public DoubleSetter setter() {
		return setAlphaVoile;
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		controler.deactivate();				

		
	}

}

