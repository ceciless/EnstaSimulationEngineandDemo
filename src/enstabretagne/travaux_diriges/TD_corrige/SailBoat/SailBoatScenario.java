/**
* Classe SailBoatScenario.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee.Bouee;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee.BoueeFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee.BoueeInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Ocean.Ocean;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Ocean.OceanFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Ocean.OceanInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind.Wind;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind.WindFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind.WindInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.BateauAVoile;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.BateauAVoileFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.BateauAVoileInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail.GouvernailInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile.VoileInit;

public class SailBoatScenario extends SimScenario{

	protected SailBoatScenario(SimEngine engine,ScenarioId id, SimFeatures features,
			LogicalDateTime start, LogicalDateTime end) {
		super(engine,id, features, start, end);
		
		GouvernailInit gouvernailInit = new GouvernailInit(0*Math.PI/180);
		VoileInit voileInit = new VoileInit(Math.PI/4);
		BateauAVoileInit bavi;

		bavi= new BateauAVoileInit(Vector3D.ZERO, 0,20*Math.PI/180, voileInit, gouvernailInit);
		Add(new Action_EntityCreation(BateauAVoile.class, "Bateau à voile 1", BateauAVoileFeatures.BasicSailBoatFeatures, bavi,true));
		bavi= new BateauAVoileInit(new Vector3D(10,10,0), 20*Math.PI/180,-20*Math.PI/180, voileInit, gouvernailInit);
		Add(new Action_EntityCreation(BateauAVoile.class, "Bateau à voile 2", BateauAVoileFeatures.BasicSailBoatFeatures, bavi));

		Add(new Action_EntityCreation(Wind.class, "Vent", WindFeatures.BasicWindFeatures, new WindInit(0,0,10)));
		Add(new Action_EntityCreation(Ocean.class, "Ocean", OceanFeatures.basicOcean, OceanInit.basicOcean));
		
		//création d'un circuit de bouees
		Vector3D p_0=new Vector3D(0,0,0);
		Vector3D p_delta=new Vector3D(20,0,0);
		Vector3D p_i=p_0;
		
		for(int i = 0;i<8;i++) {
			p_i = p_i.add(p_delta);
			Add(new Action_EntityCreation(Bouee.class, "Bouee", BoueeFeatures.basicBouee, new BoueeInit(p_i)));
		}
		
	}

}

