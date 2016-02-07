/**
* Classe SimEntityDoubleControlerFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler;

import enstabretagne.simulation.components.SimFeatures;

public class SimEntityDoubleControlerFeatures extends SimFeatures {

	double radiansParIncrement;
	public SimEntityDoubleControlerFeatures(String id,double radiansParIncrement) {
		super(id);
		this.radiansParIncrement = radiansParIncrement;
	}
	public double getRadiansParIncrement() {
		return radiansParIncrement;
	}
	public static final SimEntityDoubleControlerFeatures basicFeature=new SimEntityDoubleControlerFeatures("BasicFeature",5*Math.PI/180);

}

