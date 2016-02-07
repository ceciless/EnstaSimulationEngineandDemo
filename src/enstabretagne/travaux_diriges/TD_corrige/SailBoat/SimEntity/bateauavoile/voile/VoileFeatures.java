/**
* Classe VoileFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.utility.Settings;
import enstabretagne.simulation.components.SimFeatures;

public class VoileFeatures extends SimFeatures{

	/**
	 * 
	 * Vocabulaire
	 * 
	 * Vc= Point d'ancrage de la voile sur la coque 
	 * V = point d'application des forces véliques
	 * 
	 * @param id
	 * @param c_sail
	 * @param surfaceVoile
	 */
	public VoileFeatures(String id,double c_sail,double surfaceVoile,double hv, double pv) {
		super(id);
		this.c_sail=c_sail;
		this.surfaceVoile=surfaceVoile;

		controlerName = id+Settings.controleurSuffixe;
		positionCV = new Vector3D(-pv, 0, hv);
	}
	
	private String controlerName;
	public String getControlerName() {
		return controlerName;
	}

	/**
	 * Coefficient de portance de la voile
	 */
	private double c_sail;
	public double getPortance() {
		return c_sail;
	}

	Vector3D positionCV;
	public Vector3D getPositionCV()
	{
		return positionCV;
	}
	
	/**
	 * Surface de la voile
	 */
	private double surfaceVoile;
	public double getSurfaceVoile() {
		return surfaceVoile;
	}
	
	public static final VoileFeatures BasicVoileF;
	static
	{
		/*
		 * Voile : alphaV = 1500.0
		 * Voile : surfaceVoile = 12
		 * Voile : hV = 6
		 * Voile : l = 1
		 */
		BasicVoileF = new VoileFeatures("Voile basique", 0.9, 12, 6, 1);
	}
}

