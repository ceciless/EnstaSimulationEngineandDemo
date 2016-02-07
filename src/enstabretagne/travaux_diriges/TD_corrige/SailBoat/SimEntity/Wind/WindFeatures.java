/**
* Classe WindFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind;

import enstabretagne.simulation.components.SimFeatures;

public class WindFeatures extends SimFeatures {

	public static final SimFeatures BasicWindFeatures = new WindFeatures("Vent Basique");

	public WindFeatures(String id) {
		super(id);
	}

}

