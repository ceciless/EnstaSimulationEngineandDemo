/**
* Classe CoiffeurFeature.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur;

import enstabretagne.simulation.components.SimFeatures;

public class CoiffeurFeature extends SimFeatures {

	CoiffeursNames coiffeurName;
	StatutCoiffeur coiffeurStatut;
	double vitesseCoupe;
	double popularite;

	
	
	public CoiffeursNames getCoiffeurName() {
		return coiffeurName;
	}

	public StatutCoiffeur getCoiffeurStatut(){
		return coiffeurStatut;
	}
	
	public double getVitesseCoupe() {
		return vitesseCoupe;
	}
	public double getPopularite() {
		return popularite;
	}
	
	
	public CoiffeurFeature(CoiffeursNames coiffeurName,double vitesseCoupe,StatutCoiffeur statut,double popularite) {
		super(coiffeurName.toString());
		this.coiffeurName=coiffeurName;
		this.vitesseCoupe=vitesseCoupe;
		this.coiffeurStatut=statut;
		this.popularite=popularite;
	}

	
}

