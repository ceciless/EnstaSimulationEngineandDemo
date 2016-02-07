/**
* Classe SalonFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon;

import java.time.DayOfWeek;
import java.util.List;

import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;

public class SalonFeatures extends SimFeatures {

	public SalonFeatures(String id,
			int frequenceObservationTailleFileAtente, int nbPosteCoiffure,
			int nbClientMaxSalleAttente, String heureOuverture,
			String heureFermeture, List<DayOfWeek> jourFermeture,List<CoiffeurFeature> coiffeursFeatures) {
		super(id);
		this.frequenceObservationTailleFileAtente = frequenceObservationTailleFileAtente;
		this.nbPosteCoiffure = nbPosteCoiffure;
		this.nbClientMaxSalleAttente = nbClientMaxSalleAttente;
		this.heureOuverture = heureOuverture;
		this.heureFermeture = heureFermeture;
		this.coiffeursFeatures = coiffeursFeatures;
		this.jourFermeture=jourFermeture;
	}

	//duree en minutes entieres
	int frequenceObservationTailleFileAtente;
	public int getFrequenceObservationTailleFileAtente() {
		return frequenceObservationTailleFileAtente;
	}

	List<DayOfWeek> jourFermeture;
	public List<DayOfWeek> getJourFermeture() {
		return jourFermeture;
	}

	int nbPosteCoiffure;
	int nbClientMaxSalleAttente;
	String heureOuverture;
	String heureFermeture;
	List<CoiffeurFeature> coiffeursFeatures;
	public List<CoiffeurFeature> getCoiffeursFeatures() {
		return coiffeursFeatures;
	}
	public int getNbPosteCoiffure() {
		return nbPosteCoiffure;
	}
	public int getNbClientMaxSalleAttente() {
		return nbClientMaxSalleAttente;
	}
	public String getHeureOuverture() {
		return heureOuverture;
	}
	public String getHeureFermeture() {
		return heureFermeture;
	}

}

