/**
* Classe ClientFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;

public class ClientFeatures extends SimFeatures {

	CoiffeursNames coiffeurPrefere;
	CoiffeursNames coiffeurDeteste;
	
	public CoiffeursNames getCoiffeurPrefere() {
		return coiffeurPrefere;
	}

	public CoiffeursNames getCoiffeurDeteste() {
		return coiffeurDeteste;
	}

	public int getPatience() {
		return patience;
	}
	
	//nombre de personnes en salle d'attente
	int patience;

	public ClientFeatures(String id, CoiffeursNames coiffeurPrefere,
			CoiffeursNames coiffeurDeteste, int patience) {
		super(id);
		this.coiffeurPrefere = coiffeurPrefere;
		this.coiffeurDeteste = coiffeurDeteste;
		this.patience = patience;
	}

}

