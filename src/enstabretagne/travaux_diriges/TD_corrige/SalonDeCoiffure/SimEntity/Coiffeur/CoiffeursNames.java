/**
* Classe CoiffeursNames.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur;

public enum CoiffeursNames {
	Petunia("Petunia"),
	Lumpy("Lumpy"),
	Flaky("Flacky"),
	EmployeSupplementaire("EmployeSupplementaire"),
	Indifferent("Indifférent");
	
	private final String name;
	
	private CoiffeursNames(String name){
		this.name=name;
	}
		
	@Override
	public String toString() {
		return name;
	}
}

