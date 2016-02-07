/**
* Classe BoueeFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee;

import enstabretagne.simulation.components.SimFeatures;

public class BoueeFeatures extends SimFeatures {

	public BoueeFeatures(String id) {
		super(id);
	}

	public static final BoueeFeatures basicBouee = new BoueeFeatures("Bouee Basique");
}

