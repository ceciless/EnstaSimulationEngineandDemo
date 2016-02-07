/**
* Classe VoileInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile;

import enstabretagne.simulation.components.SimInitParameters;

public class VoileInit extends SimInitParameters {

	private double angleInitVoile;

	public double getRotationInitVoile() {
		return angleInitVoile;
	}

	public VoileInit(double angleInitVoile) {
		super();
		this.angleInitVoile= angleInitVoile;
	}
	
	
}

