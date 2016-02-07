/**
* Classe PenduleScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.Pendule;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.PenduleFeatures;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.PenduleInit;

public class PenduleScenario extends SimScenario{
	public PenduleScenario(SimEngine engine,ScenarioId id,SimFeatures features,LogicalDateTime start, LogicalDateTime end) {
		super(engine,id, features,start,end);
		Add(new Action_EntityCreation(Pendule.class, "Pendule", new PenduleFeatures("Pendule1",3,0.1,0.5), new PenduleInit(new Vector3D(0, 0, 2),new Vector3D(-1, 0, 1))));
		
	}

}

