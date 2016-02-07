/**
* Classe Pendule.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.models.environment.Environment;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.expertise.cinematique.KineticContinuous;
import enstabretagne.expertise.cinematique.KineticRate;
import enstabretagne.expertise.cinematique.KineticState;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.IContinuousRate;
import enstabretagne.simulation.core.IContinuousState;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.GL3DRepresentations.IPendule;

public class Pendule extends SimEntity implements IPendule,IRecordable {

	public Pendule(SimEngine engine,String name, SimFeatures features) {
		super(engine, name, features);
	}

	private class EtatsContinus extends KineticContinuous{

		public EtatsContinus(IContinuousState state, LogicalDuration step) {
			super(state, step);
		}

		@Override
		protected void ComputeUntypedRate(IContinuousState ROstate,
				IContinuousRate WOrate) {
			KineticState etat = (KineticState) ROstate;
			KineticRate taux = (KineticRate) WOrate;
			
			PenduleInit init = (PenduleInit) getInitParameters();
			PenduleFeatures tno = (PenduleFeatures) getFeatures();

			double l = init.getLongueurFil();
			
			//vecteur unitaire colinéaire au fil
			Vector3D OM_u = etat.getPos().subtract(init.getPointAccroche()).normalize();
					
			//force exercée par le fil sur le pendule
			Vector3D r=OM_u.scalarMultiply(-(OM_u.dotProduct(Environment.g))*tno.getM()).add(OM_u.scalarMultiply(-tno.getM()*etat.getVel().getNorm()*etat.getVel().getNorm()/l));
			
			//force de frottements de l'air
			double resAir_25deg=0.5*Environment.rho_air_25deg*tno.getCx()*tno.r*tno.r*Math.PI;
			Vector3D frottements = etat.getVel().scalarMultiply(-etat.getVel().getNorm()*resAir_25deg);

			//calcul de l'acceleration
			Vector3D a= r.scalarMultiply(1/tno.getM()).add(Environment.g).add(frottements.scalarMultiply(1/tno.getM()));
			
			//affectation des grandeurs aux vecteur taux pour l'algorithme de runge kunta
			taux.setCurvRate(etat.getVel().getNorm());
			taux.setPosRate(etat.getVel());
			taux.setVelRate(a);

		}
		
		/**
		 * Cette méthode sert par exemple à logger les états stabilisés à l'issue des passes de l'algo Runge Kunta
		 */
		@Override
		public void WatchState() {
			// TODO Auto-generated method stub
			super.WatchState();

			Logger.Data((IRecordable)getOwner());
		}
		
		
	}

	@Override
	public Vector3D getPosition() {
		return ((EtatsContinus) getContinuous()).getPosition();
	}

	@Override
	public Vector3D getVitesse() {
		return ((EtatsContinus) getContinuous()).getVitesse();
	}

	@Override
	public Vector3D getAcceleration() {
		return ((EtatsContinus) getContinuous()).getAcceleration();
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		this.setContinuous(new EtatsContinus(new KineticState(((PenduleInit) getInitParameters()).getPenduleInitPosition()),LogicalDuration.ofSeconds(0.2)));

	}

	@Override
	public String[] getTitles() {
		String[] titles={"x","z"};
		return titles;
	}
	@Override
	public String[] getRecords() {
		String[] records={Double.toString(getPosition().getX()),Double.toString(getPosition().getZ())};
		return records;
	}
	@Override
	public String getClassement() {
		// TODO Auto-generated method stub
		return "Pendule";
	}
	@Override
	public double getRadius() {
		return ((PenduleFeatures) getFeatures()).r;
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

}

