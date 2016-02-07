/**
* Classe GouvernailInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail;

import enstabretagne.simulation.components.SimInitParameters;

public class GouvernailInit extends SimInitParameters {

	double alpha_gouvernail;

	public double getAlpha_gouvernail() {
		return alpha_gouvernail;
	}

	public GouvernailInit(double alpha_gouvernail) {
		super();
		this.alpha_gouvernail = alpha_gouvernail;
	}
	
}

