/**
* Classe Salon.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Salon;

import java.util.HashMap;
import java.util.LinkedList;

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
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Client.RaisonsDepart;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.Coiffeur;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurFeature;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeurInit;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.SimEntity.Coiffeur.CoiffeursNames;
import enstabretagne.travaux_diriges.TD_corrige.SalonDeCoiffure.base.messages.Messages;

public class Salon extends SimEntity implements IRecordable{

	LogicalDuration salonOpened;
	LogicalDuration salonClosed;
	HashMap<CoiffeursNames, Coiffeur> listeCoiffeurs;
	LinkedList<Coiffeur> coiffeursParOrdreCroissantPopularite=new LinkedList<Coiffeur>();
	
	LinkedList<Client> fileAttente;
	
	boolean isOpened;
	

	public boolean isOpened() {
		return isOpened;
	}

	public Salon(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
		SalonFeatures sf = (SalonFeatures) features;
		salonOpened = LogicalDuration.fromString(sf.getHeureOuverture());
		salonClosed = LogicalDuration.fromString(sf.getHeureFermeture());
		
		fileAttente = new LinkedList<>();
		
		listeCoiffeurs = new HashMap<>();
		for(CoiffeurFeature cf: sf.getCoiffeursFeatures()) {
			Coiffeur c = (Coiffeur)createChild(engine,Coiffeur.class, cf.getCoiffeurName().toString(), cf);
			listeCoiffeurs.put(c.getCoiffeurName(), c);
		}

		//Optimisation : on privilégie que le moins populaire soit celui qui est choisi en priorité par un client n'ayant pas de préférences
		coiffeursParOrdreCroissantPopularite=new LinkedList<Coiffeur>();
		coiffeursParOrdreCroissantPopularite.addAll(listeCoiffeurs.values());
		coiffeursParOrdreCroissantPopularite.sort((Coiffeur c1,Coiffeur c2)->Double.compare(c1.getPopularite(), c2.getPopularite()));
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		for(IEntity s : getChildren())
		{
			SalonInit si = (SalonInit) init;
			CoiffeurInit ci = si.getCoiffeursInits().get(s.getName());
			if(Coiffeur.class.isAssignableFrom(s.getClass()))
			{
				Coiffeur c = (Coiffeur) s;
				c.Initialize(ci);
			}
		}
	}
	
	public int nbClientEnAttenteDe(CoiffeursNames name)
	{
		int i=0;
		for(Client cl : fileAttente){
			if(cl.getCoiffeurPrefere().equals(name)) i++;
		}
		return i;
	}
	
	public void Accueillir(Client cl) {
		if(cl.getName().compareTo("Client_1135")==0)
		{
			System.out.println("");
		}
		CoiffeursNames pref=cl.getCoiffeurPrefere();
		if(!pref.equals(CoiffeursNames.Indifferent)) {
			Coiffeur c = listeCoiffeurs.get(pref);
			if(!c.estAbsent())
			{
				if(fileAttente.size()<((SalonFeatures) getFeatures()).nbClientMaxSalleAttente)
				{				
					int i = nbClientEnAttenteDe(cl.getCoiffeurPrefere());
					if(i<=cl.getPatience()) {
						fileAttente.add(cl);
						Logger.Information(this, "Accueillir", "%s entre en file d'attente. %d attendent déja et %d attendent %s", cl.getName(),fileAttente.size(),i,cl.getCoiffeurPrefere().toString());
						prevenirCoiffeurs(c, cl);
					}
					else {
						cl.quitterSalonCoiffure(RaisonsDepart.TropDeMondePourPrefere);
					}
				}
				else
					cl.quitterSalonCoiffure(RaisonsDepart.PlusDePlace);
			}
			else
			{
				cl.quitterSalonCoiffure(RaisonsDepart.CoiffeurPrefereAbsent);
			}
		}
		else
		{
			if(fileAttente.size()<((SalonFeatures) getFeatures()).nbClientMaxSalleAttente && fileAttente.size()<=cl.getPatience())
			{
				fileAttente.add(cl);
				Logger.Information(this, "Accueillir", "%s entre en file d'attente. %d attendent déja.", cl.getName(),fileAttente.size());
				prevenirCoiffeurs(cl);
			}		
			else
			{
				cl.quitterSalonCoiffure(RaisonsDepart.PlusDePlace);
			}
		}
		
	}
	
	private void prevenirCoiffeurs(Client cl){
		for(Coiffeur c:coiffeursParOrdreCroissantPopularite)
		{
			if(!c.enCoursDeCoupe()) {

				if(c.assignerClient(cl))
					fileAttente.remove(cl);
				return;
			}
		}
		
	}

	private void prevenirCoiffeurs(Coiffeur c,Client cl){
		if(!c.enCoursDeCoupe()){
			if(c.assignerClient(cl))
				fileAttente.remove(cl);
		}
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		Post(new FermetureSalon());
		Post(new ObservationTailleFileAttente());
		for(SimEntity a:getChildren())
			a.activate();
			
	}

	class OuvertureSalon extends SimEvent {
		@Override
		public void Process() {			
			isOpened = true;
			Post(new FermetureSalon(),getCurrentLogicalDate().truncateToDays().add(salonClosed));

			for(Coiffeur c : listeCoiffeurs.values())
				c.notifierSalonOuvert();
			
			Logger.Information(this.Owner(), "OuvertureSalon", Messages.OuvertureSalon);
		}		
	}

	class FermetureSalon extends SimEvent {
		@Override
		public void Process() {			
			isOpened = false;
			
			//Boucle pour sauter les jours de fermeture de l'établissement
			LogicalDateTime tomorrow=getCurrentLogicalDate().truncateToDays().add(LogicalDuration.ofDay(1).add(salonOpened));
			while(((SalonFeatures)getFeatures()).getJourFermeture().contains(tomorrow.getDayOfWeek())){
				tomorrow=tomorrow.truncateToDays().add(LogicalDuration.ofDay(1).add(salonOpened));
			}
			

			
			Post(new OuvertureSalon(),tomorrow);			
			Logger.Information(this.Owner(), "FermetureSalon", Messages.FermetureSalon);
		}		
	}
	
	class ObservationTailleFileAttente extends SimEvent{

		@Override
		public void Process() {			
			if(isOpened()) Logger.Data((IRecordable)this.Owner());
			Post(this,getCurrentLogicalDate().add(LogicalDuration.ofMinutes(((SalonFeatures) getFeatures()).getFrequenceObservationTailleFileAtente())));
		}
		
	}

	public LinkedList<Client> getFileAttente() {
		return fileAttente;
	}

	@Override
	public String[] getTitles() {
		String[] titles={"Taille fileAttente"};
		return titles;
	}

	@Override
	public String[] getRecords() {
		String[] records={Integer.toString(getFileAttente().size())};
		return records;
	}

	@Override
	public String getClassement() {
		return "Salon";
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

