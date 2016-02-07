/**
* Classe SalonCoiffureScenarioFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.Scenarios;

import java.util.HashMap;
import java.util.List;

import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.SalonFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.SalonInit;

public class SalonCoiffureScenarioFeatures extends SimFeatures {

	double frequenceArriveeClientParHeure;
	String debutArriveeClient;
	String finArriveeClient;
	SalonFeatures salonFeatures;
	
	SalonInit salonInit;
	private CategoriesGenerator delaiAttenteRecordingCatGen;
	
	public SalonCoiffureScenarioFeatures(String id,
			double frequenceArriveeClientParHeure, 
			String debutArriveeClient,String finArriveeClient, 
			SalonFeatures salonFeatures,
			SalonInit salonInit, CategoriesGenerator arrivalDelayRecordingCatGen,CategoriesGenerator delaiAttenteRecordingCatGen) {
		super(id);
		this.frequenceArriveeClientParHeure = frequenceArriveeClientParHeure;
		this.debutArriveeClient = debutArriveeClient;
		this.finArriveeClient = finArriveeClient;
		this.salonFeatures = salonFeatures;
		this.salonInit = salonInit;
		this.ArrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}

	public CategoriesGenerator getDelaiAttenteRecordingCatGen() {
		return delaiAttenteRecordingCatGen;
	}

	public void setDelaiAttenteRecordingCatGen(
			CategoriesGenerator delaiAttenteRecordingCatGen) {
		this.delaiAttenteRecordingCatGen = delaiAttenteRecordingCatGen;
	}

	CategoriesGenerator ArrivalDelayRecordingCatGen;
	
	public SalonFeatures getSalonFeatures() {
		return salonFeatures;
	}

	public void setSalonFeatures(SalonFeatures salonFeatures) {
		this.salonFeatures = salonFeatures;
	}

	public SalonInit getSalonInit() {
		return salonInit;
	}

	public void setSalonInit(SalonInit salonInit) {
		this.salonInit = salonInit;
	}

	public CategoriesGenerator getArrivalDelayRecordingCatGen() {
		return ArrivalDelayRecordingCatGen;
	}

	public void setArrivalDelayRecordingCatGen(
			CategoriesGenerator arrivalDelayRecordingCatGen) {
		ArrivalDelayRecordingCatGen = arrivalDelayRecordingCatGen;
	}



	public void setFrequenceArriveeClientParHeure(
			double frequenceArriveeClientParHeure) {
		this.frequenceArriveeClientParHeure = frequenceArriveeClientParHeure;
	}

	public void setDebutArriveeClient(String debutArriveeClient) {
		this.debutArriveeClient = debutArriveeClient;
	}

	public void setFinArriveeClient(String finArriveeClient) {
		this.finArriveeClient = finArriveeClient;
	}

	public String getDebutArriveeClient() {
		return debutArriveeClient;
	}

	public String getFinArriveeClient() {
		return finArriveeClient;
	}

	public double getFrequenceArriveeClientParHeure() {
		return frequenceArriveeClientParHeure;
	}


}

