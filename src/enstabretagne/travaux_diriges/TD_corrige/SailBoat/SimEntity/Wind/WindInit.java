/**
* Classe WindInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.Wind;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.SimInitParameters;

public class WindInit extends SimInitParameters {

	Vector3D windSpeed;
	/**
	 * 
	 * @param psi = cap
	 * @param theta = angle azimutale
	 */
	public WindInit(double psi,double theta,double windSpeed) {
		this.windSpeed = new Vector3D(psi, theta).scalarMultiply(windSpeed);		
	}

	public Vector3D getWindSpeedInit() {
		return windSpeed;
	}

}

