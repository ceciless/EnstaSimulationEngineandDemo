/**
* Classe ISimEntity.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.entities.demo.DemoEtatDerive;

public interface ISimEntity {
	void update(IEtatDerive e,LogicalDuration dt);

	DemoEtatDerive getTaux();
	LogicalDuration getStep();
	void calculTaux();
	
	void watchState();
}

