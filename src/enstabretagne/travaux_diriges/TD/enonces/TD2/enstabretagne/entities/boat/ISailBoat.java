/**
* Classe ISailBoat.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat;

import java.util.List;

import enstabretagne.entities.IMovable;

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

