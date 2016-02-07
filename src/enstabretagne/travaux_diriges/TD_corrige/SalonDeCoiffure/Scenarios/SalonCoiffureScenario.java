/**
* Classe SalonCoiffureScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.Scenarios;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.CategoriesGenerator;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.Client;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.ClientFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.ClientInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.StatutCoiffeur;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.*;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.base.messages.Messages;

public class SalonCoiffureScenario extends SimScenario {
	

	MoreRandom random;
	double lambda_arrivee_client; 
	
	LogicalDuration startClientArrival;
	LogicalDuration endClientArrival;

	CategoriesGenerator arrivalDelayRecordingCatGen;
	CategoriesGenerator delaiAttenteRecordingCatGen;
	
	public SalonCoiffureScenario(SimEngine engine,
			ScenarioId scenarioId, 
			SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(engine,scenarioId, features,start,end);
		SalonCoiffureScenarioFeatures scsf = (SalonCoiffureScenarioFeatures) features;

		arrivalDelayRecordingCatGen = scsf.getArrivalDelayRecordingCatGen();
		delaiAttenteRecordingCatGen=scsf.getDelaiAttenteRecordingCatGen();

		random = new MoreRandom(MoreRandom.globalSeed);
		lambda_arrivee_client = scsf.getFrequenceArriveeClientParHeure()/3600;
		startClientArrival = LogicalDuration.fromString(scsf.getDebutArriveeClient());
		endClientArrival = LogicalDuration.fromString(scsf.getFinArriveeClient());
		
		Add(new Action_EntityCreation(Salon.class, scsf.getSalonFeatures().getId(), scsf.getSalonFeatures(), scsf.getSalonInit()));
		
	}
	
	int nbClient;

	class DebutArriveeClient extends SimEvent {

		@Override
		public void Process() {
			LogicalDateTime d = getNextTimeForClient();
			if(d!=null) Post(new NouveauClientEvent(),d);
			
			Post(new FinArriveeClient(),getCurrentLogicalDate().truncateToDays().add(endClientArrival));			
			Logger.Information(this.Owner(), "DebutArriveeClient", Messages.OuverturePeriodeNouveauxClients);
		}


	}

	class FinArriveeClient extends SimEvent {
		@Override
		public void Process() {			
			Post(new DebutArriveeClient(),getCurrentLogicalDate().truncateToDays().add(LogicalDuration.ofDay(1).add(startClientArrival)));			
			Logger.Information(this.Owner(), "FinArriveeClient", Messages.FinPeriodeNouveauxClients);
		}		
	}

	class NouveauClientEvent extends SimEvent {


		int dureeNextClient;
		@Override
		public void Process() {
			double clientAvecFavoriProba=random.nextDouble()*10;
			ClientFeatures cf ;

			if(clientAvecFavoriProba<=4){
				double typeClientProba=random.nextDouble()*4;
				if(typeClientProba<=3)
					cf=new ClientFeatures("Client_"+nbClient++,CoiffeursNames.Lumpy,CoiffeursNames.Petunia,3);
				else
					cf=new ClientFeatures("Client_"+nbClient++,CoiffeursNames.Flaky,CoiffeursNames.Petunia,3);
			}
			else
			{
				cf=new ClientFeatures("Client_"+nbClient++,CoiffeursNames.Indifferent,CoiffeursNames.Petunia,6);
			}
			Client c=(Client)createChild(getEngine(),Client.class, cf.getId(), cf);
			
			c.Initialize(new ClientInit(delaiAttenteRecordingCatGen));
			Logger.Information(this.Owner(), "NouveauClient", Messages.NouveauClientPotentiel,cf.getId());
			LogicalDateTime d = getNextTimeForClient();
			if(d!=null) Post(new NouveauClientEvent(),d);

			c.activate();
			
		}
		
		
	}

	
	private class VerifDistribRecord implements IRecordable{
		double d;
		public VerifDistribRecord(double d){
		this.d=d;
	}
		@Override
		public String[] getTitles() {
			return new String[]{"delta","Categorie"};
		}
		
		@Override
		public String[] getRecords() {
			arrivalDelayRecordingCatGen.getSegmentOf(d).toString();
			return new String[]{Double.toString(d/60),arrivalDelayRecordingCatGen.getSegmentOf(d/60).toString()};
		}
		
		@Override
		public String getClassement() {
			return "NextClient";
		}
		
	}
	
	LogicalDateTime getNextTimeForClient() {
		
		double d=random.nextExp(lambda_arrivee_client);
		Logger.Data(new VerifDistribRecord(d));

		LogicalDuration t = LogicalDuration.ofSeconds(d);
		LogicalDateTime nextEndOfClientsArrival =getCurrentLogicalDate().truncateToDays().add(endClientArrival); 

		LogicalDateTime possibleClientArrival = getCurrentLogicalDate().add(t);
		if(possibleClientArrival.compareTo(nextEndOfClientsArrival)<0)
			return possibleClientArrival;
		else
			return null;

	}
	
	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		super.AfterActivate(sender, starting);
		
		this.Post(new FinArriveeClient());//même si contradictoire, le fait de poster la fin d'arrivee client déclenche l'arrivée des nouveaux clients
	}
}

