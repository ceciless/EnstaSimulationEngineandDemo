/**
* Classe Coiffeur.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur;

import java.util.LinkedList;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.Client;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.EtatClient;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.IClient;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.RaisonsDepart;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon.Salon;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.base.messages.Messages;

public class Coiffeur extends SimEntity implements ICoiffeur,IRecordable {

	MoreRandom random;
	double lambda_temps_coupe;
	CoiffeursNames coiffeurName;
	
	public CoiffeursNames getCoiffeurName() {
		return coiffeurName;
	}

	public Coiffeur(SimEngine engine, String name, SimFeatures features) {
		super(engine, name, features);
		
		CoiffeurFeature cf = (CoiffeurFeature) features;
		random = new MoreRandom(MoreRandom.globalSeed);
		lambda_temps_coupe = 60.0/cf.getVitesseCoupe();
		Logger.Information(this, "Coiffeur constructor", name + " est créé !");
		
		coiffeurName=cf.getCoiffeurName();
		setEnCoursDeCoupe(false);
		estAbsent=false;
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Logger.Information(this, "Coiffeur constructor", getName() + " est embauché !");
		
	}

	boolean estAbsent;
	@Override
	public boolean estAbsent() {
		return estAbsent;
	}

	LogicalDateTime dateDeFinDeCoupe;
	LogicalDuration dureeInactive;
	boolean enCoursDeCoupe;
	
	private void setEnCoursDeCoupe(boolean enCoursDeCoupe) {
		if(!estAbsent() ) {
			if(enCoursDeCoupe == false) {
				dateDeFinDeCoupe = CurrentDate();
				dureeInactive = LogicalDuration.ZERO;
			}
			else {
				dureeInactive = CurrentDate().soustract(dateDeFinDeCoupe);
				Logger.Data(this);
			}
		}
		this.enCoursDeCoupe = enCoursDeCoupe;
	}
	@Override
	public boolean enCoursDeCoupe() {
		return enCoursDeCoupe;
	}
	
	public double getPopularite() {
		return((CoiffeurFeature) getFeatures()).getVitesseCoupe();
	}

	IClient c;
	@Override
	public boolean assignerClient(IClient c) {
		if(this.c!=null){
			Logger.Information(this, "assignerClient", "déja pris avec %s", ((Client) c).getName());
			return false;
		}
		
		if(!estAbsent) {
			this.c=c;
			
			Logger.Information(this, "assignerClient", "%s prend en charge %s dont le coiffeur prefere est %s.", getName(),((Client)c).getName(),c.getCoiffeurPrefere().toString());
			setEnCoursDeCoupe(true);
			c.setEtatClient(EtatClient.CheuveuxEnCoursDeCoupe);
			double d=random.nextExp(lambda_temps_coupe)*3600;
			if(d<5*60)
				d=5*60;
			LogicalDuration t = LogicalDuration.ofSeconds(d);
			Post(new FinCoupeCheveux(),getCurrentLogicalDate().add(t));
			return true;
		}
		
		return false;
		
	}

	class FinCoupeCheveux extends SimEvent {

		@Override
		public void Process() {
	
			if(((Client) c).getName().compareTo("Client_1135")==0)
			{
				System.out.println("ici---------------------");
			}
			Logger.Information(Owner(), "FinCoupeCheveux", Messages.FinDeCoupeDeCheveux, c.toString());
			setEnCoursDeCoupe(false);
			c.setEtatClient(EtatClient.CheuveuxCoupes);
			c.quitterSalonCoiffure(RaisonsDepart.CheveuxCoupes);
			c=null;
			
			prendreClientSuivant();
		}		
	}
	
	private void prendreClientSuivant(){
		Salon s = (Salon) getParent();
		LinkedList<Client> listeClient = s.getFileAttente();
		
		for(int i=0;i<listeClient.size();i++)
		{
			//on ne prend que le prochain client qui est indifférent ou me prefere.
			if(listeClient.get(i).getCoiffeurPrefere().equals(CoiffeursNames.Indifferent)||listeClient.get(i).getCoiffeurPrefere().equals(getCoiffeurName()))
			{
				assignerClient(listeClient.remove(i));
				return;
			}
		}
	}
	
	//proba de 1 chance sur 20 d'être absent
	@Override
	public boolean notifierSalonOuvert() {
		if(((CoiffeurFeature) getFeatures()).getCoiffeurStatut().equals(StatutCoiffeur.Patron))
		{
			estAbsent=false;//le patron n'est jamais absent
			setEnCoursDeCoupe(false);
			Logger.Information(this, "notifierSalonOuvert", this.getCoiffeurName().toString()+" est disponible.");
		}
		else {
			
			double probaPresence = random.nextDouble()*20;
			if(probaPresence<=1) {
				estAbsent=true;
				Logger.Information(this, "notifierSalonOuvert", this.getCoiffeurName().toString()+" est absent.");
			}
			else {
				estAbsent=false;
				setEnCoursDeCoupe(false);
				Logger.Information(this, "notifierSalonOuvert", this.getCoiffeurName().toString()+" est disponible.");
			}
		}
	
		return estAbsent;
	}

	@Override
	public void onParentSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getTitles() {
		return new String[]{"Nom","Temps non travaillé"};
	}

	@Override
	public String[] getRecords() {
		return new String[]{getName(),Integer.toString(dureeInactive.getMinutes())};
	}

	@Override
	public String getClassement() {
		return "Coiffeurs";
	}
}

