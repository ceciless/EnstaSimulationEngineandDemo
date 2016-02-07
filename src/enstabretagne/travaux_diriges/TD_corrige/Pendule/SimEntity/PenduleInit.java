/**
* Classe PenduleInit.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.SimInitParameters;

public class PenduleInit extends SimInitParameters {

	public PenduleInit(Vector3D pointAccroche, Vector3D penduleInitPosition) {
		super();
		this.pointAccroche = pointAccroche;
		this.penduleInitPosition = penduleInitPosition;
		this.lFil = getPenduleInitPosition().subtract(getPointAccroche()).getNorm();
	}
	
	public Vector3D getPointAccroche() {
		return pointAccroche;
	}

	public void setPointAccroche(Vector3D pointAccroche) {
		this.pointAccroche = pointAccroche;
	}

	Vector3D pointAccroche;
	Vector3D penduleInitPosition;

	public Vector3D getPenduleInitPosition() {
		return penduleInitPosition;
	}
	
	private double lFil;
	public double getLongueurFil()
	{
		return lFil;
	}
}

