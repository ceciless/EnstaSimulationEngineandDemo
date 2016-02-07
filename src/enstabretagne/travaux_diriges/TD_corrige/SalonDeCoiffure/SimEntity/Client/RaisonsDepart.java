/**
* Classe RaisonsDepart.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

public enum RaisonsDepart {
	SalonFerme("Salon Fermé"),
	CoiffeurPrefereAbsent("Coiffeur Préféré Absent"),
	TropDeMondePourPrefere("Trop de monde pour mon coiffeur preféré"),
	TropDeMonde("Trop de monde"),
	PlusDePlace("PlusDePlace"),
	CheveuxCoupes("Cheuveux Coupés");

	String raisonDepart;
	private RaisonsDepart(String RaisonsDepart) {
		this.raisonDepart=RaisonsDepart;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return raisonDepart;
	}
}

