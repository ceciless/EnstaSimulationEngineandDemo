/**
* Classe MoniteurTempsReelTextuel.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.entities.demo.DemoSimEntity;

public class MoniteurTempsReelTextuel extends TimerTask{
	
	protected HashMap<String,HashMap<String,Object>>  loggersNames;
	SimEngine engine;
	public MoniteurTempsReelTextuel(long rate) {
		

		
		engine = new SimEngine();		
		loggersNames = new HashMap<String,HashMap<String,Object>>();
		
		loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());
		Logger.Init(engine,loggersNames,true);		
		
		Logger.Information(this, "constructeur", "test");
		this.rate=rate;
		ldRate=LogicalDuration.ofSeconds(rate/1000);
		
		DemoSimEntity e1 = new DemoSimEntity("e1",10, 0.1);
		DemoSimEntity e2 = new DemoSimEntity("e2",20, 0.1);
		engine.addSimEntity(e1);
		engine.addSimEntity(e2);
		
		
		e1.watchState();
		e2.watchState();
	}
	double rate;
	public static void main(String[] args) {
		Timer t = new Timer();
		long rate = 1000;
		t.scheduleAtFixedRate(new MoniteurTempsReelTextuel(rate), 0, rate);
	}
	

	LogicalDuration ldRate;
	@Override
	public void run() {
		
		Logger.Information(this, "run", "New Date pour Moniteur");
		engine.simulateFor(ldRate);
		
	}
}

