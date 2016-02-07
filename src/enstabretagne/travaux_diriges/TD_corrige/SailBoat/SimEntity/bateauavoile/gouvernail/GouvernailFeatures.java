/**
* Classe GouvernailFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.utility.Settings;
import enstabretagne.simulation.components.SimFeatures;

public class GouvernailFeatures extends SimFeatures{

	/**
	 * Vocabulaire
	 * 
	 * Rc = point d'ancrage du gouvernail sur la coque
	 * R = point d'application des forces sur le gouvernail
	 * 
	 * 
	 * @param id
	 * @param alphaG
	 */
	public GouvernailFeatures(String id, double alphaG,double surfaceGouvernail,double hG, double pG) {
		super(id);
		controleurName = id+Settings.controleurSuffixe;
		this.alphaG=alphaG;
		positionCg=new Vector3D(-pG,0,hG);
		this.surfaceGouvernail=surfaceGouvernail;
	}
	
	String controleurName;
	public String getControlerName() {
		return controleurName;
	}
	
	private double surfaceGouvernail;
	
	public double getSurfaceGouvernail() {
		return surfaceGouvernail;
	}

	//Centre gouvernail
	private Vector3D positionCg;
	public Vector3D getPositionCg(){
		return positionCg;
	}
	
	public double getAlphaG() {
		return alphaG;
	}

	/**
	 * Portance du gouvernail
	 */
	private double alphaG;
	

	public static final GouvernailFeatures BasicGouvernailFeatures;
	static {
		/*
		 * Gouvernail : alphaG = 6000
		 * Gouvernail : surfaceSafran = 0.2
		 * Gouvernail : estSafranVersBabord = false
		 * Gouvernail : position du centre des forces : 0,0 (approximation)
		 */

		BasicGouvernailFeatures = new GouvernailFeatures("Gouvernail Basique", 6000.0,0.2, 0, 0);
	}
}

