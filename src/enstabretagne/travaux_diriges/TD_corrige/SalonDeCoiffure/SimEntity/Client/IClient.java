/**
* Classe IClient.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.Salon;

public interface IClient {
	public Salon allerSalonCoiffure();
	public void quitterSalonCoiffure(RaisonsDepart raison);
	
	public CoiffeursNames getCoiffeurPrefere();
	public CoiffeursNames getCoiffeurDeteste();
	public int getPatience();
	public EtatClient getEtatClient();
	public void setEtatClient( EtatClient etat);
}

