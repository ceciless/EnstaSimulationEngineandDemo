/**
* Classe SailBoatContinuous.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.expertise.cinematique.KineticContinuous;
import enstabretagne.simulation.core.IContinuousState;

public abstract class SailBoatContinuous extends KineticContinuous {

	public SailBoatContinuous (IContinuousState state, LogicalDuration step) {
		super(state, step);
	}
	
	public double getPhi() {
		return ((SailBoatState) getUntypedROState()).getPhi();
	}

	public double getPhi_point() {
		return ((SailBoatState) getUntypedROState()).getPhi_point();		
	}

	public double getPhi_point_point() {
		return ((SailBoatRate) getUntypedRORate()).getPhi_point_rate();
	}

	public double getTheta() {
		return ((SailBoatState) getUntypedROState()).getTheta();
	}
	public double getTheta_point() {
		return ((SailBoatState) getUntypedROState()).getTheta_point();
	}
	public double getTheta_point_point() {
		return ((SailBoatRate) getUntypedRORate()).getTheta_point_rate();
	}
}
	

