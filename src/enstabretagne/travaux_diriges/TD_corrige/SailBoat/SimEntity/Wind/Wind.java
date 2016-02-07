/**
* Classe Wind.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.IWind;

public class Wind extends SimEntity implements IWind{
	

	public Wind(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		WindInit wi = (WindInit) init;
		windSpeed=wi.getWindSpeedInit();
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}


	Vector3D windSpeed;
	public Vector3D getWindSpeed() {
		return windSpeed;
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

