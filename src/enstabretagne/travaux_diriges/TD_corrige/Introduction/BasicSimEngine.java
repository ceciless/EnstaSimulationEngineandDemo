/**
* Classe BasicSimEngine.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.ISimulationDateProvider;

public class BasicSimEngine implements ISimulationDateProvider{

	LogicalDateTime d;
	
	public BasicSimEngine(String date) {
		d = new LogicalDateTime(date);
	}

	@Override
	public LogicalDateTime SimulationDate() {
		return d;
	}
	

}

