package enstabretagne.travaux_diriges.TD_corrige;

import java.time.Instant;
import java.util.HashMap;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SXLSXExcelDataloggerImpl;
import enstabretagne.travaux_diriges.TD_corrige.Introduction.BasicSimEngine;
import enstabretagne.travaux_diriges.TD_corrige.Introduction.LoggerAndProba;

public class DebugLogger {
	
	public void test() {
		Logger.Detail(this, "test", "ceci est un test",null);
	}
	
	public static void main(String[] args) {
		BasicSimEngine bse = new BasicSimEngine("12/11/2014 10:00");
		
		//Premier d'entre eux: le logger qui écrit dans la sortie standard
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
//		loggersNames.put(SysOutLogger.class.getCanonicalName(), new HashMap<String,Object>());

		//Premier d'entre eux: le logger qui écrit dans la sortie standard
//		loggersNames.put("sdqsd", new HashMap<String,Object>());

		//Premier d'entre eux: le logger qui écrit dans un fichier excel
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir"));
		params.put(LoggerParamsNames.FileName.toString(), "LoggerAndProba.xlsx");
		loggersNames.put(SXLSXExcelDataloggerImpl.class.getCanonicalName(),params);
		
		//Initialisation de l'ensemble des loggers
		Logger.Init(loggersNames, true);

		//LoggerAndProba est un moniteur
		LoggerAndProba lap=new LoggerAndProba();
		lap.run();
		for(long i = 0;i<999990;i++)
		{
			if(i%100000==0)
				System.out.println(Instant.now() + "="+ i);
			Logger.Information(null, "main", "olivier");
		}
		
		DebugLogger dl = new DebugLogger();
		dl.test();
		
		//Cloture des logger et qui procède notammnt à l'écriture du fichier Excel
		Logger.Terminate();
	
	}
}
