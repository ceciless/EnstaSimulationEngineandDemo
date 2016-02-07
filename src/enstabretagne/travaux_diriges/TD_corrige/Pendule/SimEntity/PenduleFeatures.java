/**
* Classe PenduleFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity;

import enstabretagne.simulation.components.SimFeatures;

public class PenduleFeatures extends SimFeatures {
	
	double m;
	double r;
	double Cx;
	
	public PenduleFeatures(String id,double m,double r,double Cx) {
		super(id);
		this.m=m;
		this.r=r;
		this.Cx=Cx;
	}

	public double getCx() {
		return Cx;
	}
	
	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

}

