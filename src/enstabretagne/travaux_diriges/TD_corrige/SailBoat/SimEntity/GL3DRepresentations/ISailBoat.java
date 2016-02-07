/**
* Classe ISailBoat.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations;

import java.util.List;

import enstabretagne.base.models.environment.Force;
import enstabretagne.monitors.IMovable;

public interface ISailBoat extends IMovable{

	double getTheta();

	double getPhi();

	double getDeltag();

	double getDeltav();
	double getPositionRelativeAncrageVoileSurCoque();
	
	double getFv();
	double getPositionRelativeGouvernail();
	
	List<Force> getForces();

}

