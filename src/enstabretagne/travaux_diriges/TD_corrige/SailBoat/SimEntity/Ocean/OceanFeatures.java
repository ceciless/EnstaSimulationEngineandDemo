/**
* Classe OceanFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Ocean;

import enstabretagne.simulation.components.SimFeatures;

public class OceanFeatures extends SimFeatures{

	public OceanFeatures(String id) {
		super(id);
	}

	public static final OceanFeatures basicOcean = new OceanFeatures("Basic Ocean");

}

