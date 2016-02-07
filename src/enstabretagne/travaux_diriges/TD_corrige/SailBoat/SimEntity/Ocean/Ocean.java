/**
* Classe Ocean.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Ocean;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.ISea;

public class Ocean extends SimEntity implements ISea{

	public Ocean(SimEngine engine, String name, SimFeatures features) {
		super(engine,name, features);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}
}

