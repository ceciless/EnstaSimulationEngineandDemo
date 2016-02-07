/**
* Classe StatutCoiffeur.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur;

public enum StatutCoiffeur {

	Patron("Patron"),
	Employe("Employe");
	
	private final String statut;
	private StatutCoiffeur(String s) {
		statut=s;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return statut;
	}
}

