/**
* Classe Client.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client;

import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimObject;
import enstabretagne.simulation.core.SimObjectRequest;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.Salon;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.base.messages.Messages;

public class Client extends SimEntity implements IClient,IRecordable {

	public Client(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
		
		setEtatClient(EtatClient.RechercheSalon);
		openedSalonRequest = new SimObjectRequest() {
			
		
			@Override
			public boolean filter(SimObject o) {
				if(Salon.class.isAssignableFrom(o.getClass()))
				{
					Salon s = (Salon) o;
					if(s.isOpened())
						return true;
				}
				return false;
			}
		};
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		allerSalonCoiffure();
	}

	
	SimObjectRequest openedSalonRequest;
	private Salon findOpenedSalon(){
		List<SimObject> s = getEngine().requestSimObject(openedSalonRequest);
		if(s.size()>0)
			return (Salon) s.get(0);//à faire évoluer 
		else
			return null;
	}

	@Override
	public Salon allerSalonCoiffure() {
		Salon s=findOpenedSalon();
		if(s !=null) {
			setEtatClient(EtatClient.AttenteCoiffeur);
			s.Accueillir(this);
			return s;
		}
		else
		{
			quitterSalonCoiffure(RaisonsDepart.SalonFerme);
			return null;
		}
	}

	@Override
	public CoiffeursNames getCoiffeurPrefere() {
		return ((ClientFeatures) getFeatures()).getCoiffeurPrefere();
	}

	@Override
	public int getPatience() {
		return ((ClientFeatures) getFeatures()).getPatience();
	}

	@Override
	public CoiffeursNames getCoiffeurDeteste() {
		return ((ClientFeatures) getFeatures()).getCoiffeurDeteste();
	}

	RaisonsDepart raison;
	@Override
	public void quitterSalonCoiffure(RaisonsDepart raison) {
		this.raison=raison;
		if(raison.equals(RaisonsDepart.CheveuxCoupes)) {
			setEtatClient(EtatClient.PartiCheuveuxCoupes);
			Logger.Information(this,"quitterSalon",Messages.QuitterSalonCheuveuxCoupes,this.getName());
		}
		else {
			setEtatClient(EtatClient.PartiCheuveuxNonCoupes);
			Logger.Information(this,"quitterSalon",Messages.QuitterSalonCheuveuxNonCoupes,this.getName());
		}
		
		Logger.Data(this);
		deactivate();
	}

	EtatClient etatClient;
	@Override
	public EtatClient getEtatClient() {
		return etatClient;
	}

	LogicalDateTime heureEntreeFileAttente;
	LogicalDateTime heureDebutCoupe;
	LogicalDateTime heureFinCoupe;

	LogicalDuration dureeAttente;
	LogicalDuration dureeCoupe;
	@Override
	public void setEtatClient(EtatClient  etatClient) {
		if(etatClient.equals(EtatClient.AttenteCoiffeur)) heureEntreeFileAttente=(LogicalDateTime) getCurrentLogicalDate().getCopy();
		if(etatClient.equals(EtatClient.CheuveuxEnCoursDeCoupe)) {
			heureDebutCoupe=(LogicalDateTime) getCurrentLogicalDate().getCopy();
			dureeAttente=LogicalDuration.soustract(heureDebutCoupe, heureEntreeFileAttente);					
		}
		if(etatClient.equals(EtatClient.PartiCheuveuxCoupes)) {
			heureFinCoupe=(LogicalDateTime) getCurrentLogicalDate().getCopy();
			dureeCoupe=LogicalDuration.soustract(heureFinCoupe, heureDebutCoupe);
		}
		this.etatClient=etatClient;
	}

	@Override
	public String[] getTitles() {
		String[] titles={"Raison du départ","Durée d'Attente","Durée de Coupe","Classe Attente"};
		return titles;
	}

	@Override
	public String[] getRecords() {
		String[] rec;
		if(raison.equals(RaisonsDepart.CheveuxCoupes))
			rec=new String[]{raison.toString(),Integer.toString(dureeAttente.getMinutes()),Integer.toString(dureeCoupe.getMinutes()),((ClientInit)getInitParameters()).tempsAttenteGenerator.getSegmentOf(dureeAttente.getMinutes()).toString()};
		else
			rec=new String[]{raison.toString(),"","",""};
		return rec;
	}

	@Override
	public String getClassement() {
		return "Client";
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

}

