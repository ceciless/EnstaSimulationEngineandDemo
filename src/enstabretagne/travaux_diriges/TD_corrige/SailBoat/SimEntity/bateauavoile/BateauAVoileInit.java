/**
* Classe BateauAVoileInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail.GouvernailInit;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile.VoileInit;

public class BateauAVoileInit extends SimInitParameters {

	public BateauAVoileInit(Vector3D bateauInitPosition,double theta_init,double phi_init,VoileInit voileInit,GouvernailInit gouvernailInit) {
		super();
		this.bateauInitPosition = bateauInitPosition;
		this.gouvernailInit=gouvernailInit;
		this.voileInit=voileInit;
		this.theta_init=theta_init;
		this.phi_init=phi_init;

	}
	
	public VoileInit getVoileInit() {
		return voileInit;
	}
	public GouvernailInit getGouvernailInit() {
		return gouvernailInit;
	}

	Vector3D bateauInitPosition;
	public Vector3D getBateauInitPosition() {
		return bateauInitPosition;
	}
	
	public double getTheta_init() {
		return theta_init;
	}

	public double getPhi_init() {
		return phi_init;
	}

	VoileInit voileInit;
	GouvernailInit gouvernailInit;
	
	double theta_init;
	double phi_init;

}

