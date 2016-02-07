/**
* Classe LoggerAndProba.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import enstabretagne.base.math.MoreRandom;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.ExcelDataloggerImpl;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;

public class LoggerAndProba implements IRecordable{

	public static void main(String[] args) {
		//besoin d'un provider de temps logique pour le logger => nécessité d'un ISimulationDateProvider
		BasicSimEngine bse = new BasicSimEngine("12/11/2014 10:00");
		

		//Deux types de loggers sont déja implémentés
		
		//Premier d'entre eux: le logger qui écrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());
		
		//Premier d'entre eux: le logger qui écrit dans un fichier excel
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(), "LoggerAndProba.xls");
		loggersNames.put(ExcelDataloggerImpl.class.getCanonicalName(),params);
		
		//Initialisation de l'ensemble des loggers
		Logger.Init(bse, loggersNames, true);

		//LoggerAndProba est un moniteur
		LoggerAndProba lap=new LoggerAndProba();
		lap.run();
		
		//Cloture des logger et qui procède notammnt à l'écriture du fichier Excel
		Logger.Terminate();
		
	}
	
	private double etatGaussien;
	private Map<String,Integer> stats;
	private MoreRandom random;
	
	public LoggerAndProba() {

		Logger.Information(this, "Contructeur", "Construit!");
				
		stats = new TreeMap<String, Integer>(new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {
				return Integer.compare(Integer.parseInt(arg0),Integer.parseInt(arg1));
			}
			
		});
		
		random= new MoreRandom();
	}
	
	public void run() {
		int i = 0;
		
		for(i=0;i<10000;i++) {
			etatGaussien = random.nextGaussian();
			long m = Math.round(etatGaussien);
			int u=0;
			if(stats.containsKey(Long.toString(m))){
				u = stats.get(Long.toString(m))+1;
				stats.put(Long.toString(m), u);
			}
			else
				stats.put(Long.toString(m), 1);
		}

		Logger.Data(new IRecordable() {
			
			@Override
			public String[] getTitles() {
				String[] titles=Arrays.copyOf(stats.keySet().toArray(),stats.size(),String[].class);
				return titles;
			}
			
			@Override
			public String[] getRecords() {
				String[] records=stats.values().parallelStream().map(k->Integer.toString(k)).toArray(String[]::new);
				return records;
			}
			
			@Override
			public String getClassement() {
				return "Stats";
			}
		});
	}


	@Override
	public String[] getTitles() {
		String[] s={"Gauss"};
		return s;
	}


	@Override
	public String[] getRecords() {
		String[] s = {Double.toString(etatGaussien)};
		return s;
	}


	@Override
	public String getClassement() {
		return "LoggerAndProba";
	}
	
	
	

}

