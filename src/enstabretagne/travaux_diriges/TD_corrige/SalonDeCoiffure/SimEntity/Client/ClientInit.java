/**
* Classe ClientInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimInitParameters;

public class ClientInit extends SimInitParameters {
	
	CategoriesGenerator tempsAttenteGenerator;

	public ClientInit(CategoriesGenerator tempsAttenteGenerator) {
		super();
		this.tempsAttenteGenerator = tempsAttenteGenerator;
	}
	

}

