/**
* Classe BoueeInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Bouee;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.SimInitParameters;

public class BoueeInit extends SimInitParameters {

	private Vector3D positionInit;
	private Vector3D vitesseInit;	
	private Vector3D accelerationInit;
	
	public Vector3D getVitesseInit() {
		return vitesseInit;
	}
	public Vector3D getAccelerationInit() {
		return accelerationInit;
	}
	public BoueeInit(Vector3D positionInit) {
		this.positionInit=positionInit;
		this.vitesseInit = Vector3D.ZERO;
		this.accelerationInit = Vector3D.ZERO;
	}
	public Vector3D getPositionInit() {
		return positionInit;
	}
	

}

