/**
* Classe Logger.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.utility;

import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.components.IScenarioIdProvider;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.ISimulationDateProvider;
import enstabretagne.simulation.core.SimObject;

public class Logger {
	
	private static Logger log = new Logger();
	
	private ISimulationDateProvider engine;
	 
	//--------------------------- Journaling -----------------------------------
    /// <summary>
    /// Link between simulation application overridable settings for trace level and the 'old' TraceSwitch
    /// </summary>
    private List<ILogger> loggers;
    
    public static void Data(IRecordable obj) {
    	log.log(LogLevels.data, obj, "", "");
    }
    
    public static void Fatal(Object obj,String function,String message,Object... args)
    {
    	log.log(LogLevels.fatal, obj, function, message, args);
    }

    public static void Error(Object obj,String function,String message,Object... args)
    {
    	log.log(LogLevels.error, obj, function, message, args);
    }

    public static void Detail(Object obj,String function,String message,Object... args)
    {
    	log.log(LogLevels.detail, obj, function, message, args);    	
    }

    public static void Information(Object obj,String function,String message,Object... args)
    {
    	log.log(LogLevels.information, obj, function, message, args);    	
    }
    
    public static void Warning(Object obj,String function,String message,Object... args)
    {
    	log.log(LogLevels.warning, obj, function, message, args);    	
    }
    
    private void log(LogLevels level, Object obj,String function,String message,Object... args) {
    	if(engine!=null) {
    		if(engine instanceof IScenarioIdProvider)
    			log(((IScenarioIdProvider)engine).getScenarioId(),Instant.now(),engine.SimulationDate(),level,obj,function, message,args);
    		else
    			log(ScenarioId.ScenarioID_NULL,Instant.now(),engine.SimulationDate(),level,obj,function, message,args);
    	}
    	else
    		log(ScenarioId.ScenarioID_NULL,Instant.now(),LogicalDateTime.UNDEFINED,level,obj,function, message,args);
    		
	}
	protected void log(ScenarioId scenarioId, Temporal t, LogicalDateTime d, LogLevels level, Object obj,String function,String message,Object... args){
		boolean toBeLogged=true;
		if(Settings.filterEngineLogs())
		{
			StackTraceElement[] st= Thread.currentThread().getStackTrace();
			if(st.length>=5){
			StackTraceElement el= Thread.currentThread().getStackTrace()[4];
			String s = el.getClassName();
			Package p1 = SimObject.class.getPackage();
			Package p2 = SimScenario.class.getPackage();

			if(s.startsWith(p1.getName())||s.startsWith(p2.getName()))
				if(level.equals(LogLevels.detail)||level.equals(LogLevels.information))
					toBeLogged=false;
			}
		}
		
		if(toBeLogged) loggers.forEach((log) -> log.log(scenarioId,t, d, level, obj, function, message,args));
	}
	
	public void addLogger(ILogger log) {
		loggers.add(log);
	}
	
	public void clearLoggers(){
		loggers.clear();
	}
	
	public Logger() {
		loggers = new ArrayList<ILogger>();
	}
	
	public static boolean isInitialized()
	{
		return log.engine!=null & log.loggers.size()>0;
	}
	@Override
	public void finalize() throws Throwable {
		super.finalize();
		loggers.forEach((log) -> log.close());
	}
	
	public static void setDateProvider(ISimulationDateProvider e){
		log.engine = e;
	}
	
	public static void Init(HashMap<String,HashMap<String,Object>> loggers,boolean clear)
	{
		log.clearLoggers();
		for(Map.Entry<String,HashMap<String,Object>> m:loggers.entrySet()){
			
			String s = m.getKey();
			HashMap<String,Object> params = m.getValue();
			try {
				Class c = Class.forName(s);
				if(ILogger.class.isAssignableFrom(c))
				{
					try {
						ILogger logger=(ILogger) c.newInstance();
						boolean success=logger.open(params);
						if(success)
							log.addLogger(logger);
						
						if(clear) logger.clear(params);
					} catch (InstantiationException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	
	public static void Init(ISimulationDateProvider e,HashMap<String,HashMap<String,Object>> loggers,boolean clear)
	{
		log.engine = e;
		Init(loggers,clear);
		
	}

	public static void Terminate() {
		// TODO Auto-generated method stub
		try {
			log.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

