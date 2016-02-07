/**
* Classe ICoiffeur.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur;

import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.IClient;

public interface ICoiffeur {
	boolean estAbsent();
	boolean enCoursDeCoupe();
	boolean assignerClient(IClient c);
	boolean notifierSalonOuvert();
	
}

