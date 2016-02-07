/**
* Classe ISimEngine.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDuration;

public interface ISimEngine {

	public abstract void addSimEntity(ISimEntity entity);

	public abstract void simulateFor(LogicalDuration d);

}
