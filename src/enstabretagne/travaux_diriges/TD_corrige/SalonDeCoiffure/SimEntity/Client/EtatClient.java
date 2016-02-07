/**
* Classe EtatClient.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

public enum EtatClient {

	RechercheSalon("Recherche Salon"),
	AttenteCoiffeur("Attente du coiffeur"),
	CheuveuxEnCoursDeCoupe("Cheuveux en cours de Coupe"),
	CheuveuxCoupes("Cheuveux Coupés"),
	PartiCheuveuxCoupes("Parti cheveux coupés"),
	PartiCheuveuxNonCoupes("Parti cheveux non coupés");
	
	private String etatClient;
	private EtatClient(String etat){
		etatClient=etat;
	}
	
	@Override
	public String toString() {
		return etatClient;
	}
}

