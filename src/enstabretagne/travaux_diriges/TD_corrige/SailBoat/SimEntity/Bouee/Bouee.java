/**
* Classe Bouee.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.IBuoy;

public class Bouee extends SimEntity implements IBuoy{

	public Bouee(SimEngine engine, String name, SimFeatures features) {
		super(engine,name, features);
	}

	@Override
	public void onParentSet() {

	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {

	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {

	}


	@Override
	public Vector3D getPosition() {
		BoueeInit b = (BoueeInit) getInitParameters();
				
		
		return b.getPositionInit();
	}

	@Override
	public Vector3D getVitesse() {
		BoueeInit b = (BoueeInit) getInitParameters();
		return b.getVitesseInit();
	}

	@Override
	public Vector3D getAcceleration() {
		BoueeInit b = (BoueeInit) getInitParameters();	
		return b.getAccelerationInit();
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

}

