/**
* Classe DemoEtat.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.demo;

import enstabretagne.simulation.core.IEtat;

public class DemoEtat implements IEtat {
	double z;
	double vz;
	
	public DemoEtat(double z0,double vz0) {
		z=z0;
		vz=vz0;
	}

}

