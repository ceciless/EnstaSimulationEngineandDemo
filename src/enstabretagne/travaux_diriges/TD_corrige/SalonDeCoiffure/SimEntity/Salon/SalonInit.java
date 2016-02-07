/**
* Classe SalonInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon;

import java.util.HashMap;

import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;

public class SalonInit extends SimInitParameters {

	HashMap<CoiffeursNames, CoiffeurInit> coiffeursInits;
	public SalonInit(HashMap<CoiffeursNames, CoiffeurInit> coiffeursInits) {
		this.coiffeursInits=coiffeursInits;
	}
	public HashMap<CoiffeursNames, CoiffeurInit> getCoiffeursInits() {
		return coiffeursInits;
	}
	
	

}

