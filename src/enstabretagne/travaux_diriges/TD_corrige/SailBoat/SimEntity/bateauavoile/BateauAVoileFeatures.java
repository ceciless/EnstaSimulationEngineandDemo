/**
* Classe BateauAVoileFeatures.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile;

import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail.GouvernailFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile.VoileFeatures;

public class BateauAVoileFeatures extends SimFeatures{

	/***
	 * 
	 * Vocabulaire:
	 * G = Centre de Gravité
	 * Rc= Point d'ancrage du gouvernail  sur la coque
	 * Vc= Point d'ancrage de la voile sur la coque
	 * 
	 * @param id
	 * @param m
	 * @param Jx
	 * @param Jz
	 * @param alphaF
	 * @param alphaTheta
	 * @param rG
	 * @param rV
	 * @param beta
	 * @param hV
	 * @param l
	 * @param gouvF
	 * @param voileF
	 */
	public BateauAVoileFeatures(String id,double m, double Jx, double Jz, double alphaF, double alphaTheta,
			 double beta,double Cg_CC,GouvernailFeatures gouvF,double rG,  VoileFeatures voileF,double rV) {		
		super(id);
		this.m = m;
		this.Jx = Jx;
		this.Jz = Jz;
		this.alphaF = alphaF;
		this.alphaTheta = alphaTheta;
		this.rG = rG;
		this.rV = rV;
		this.beta = beta;
		this.gouvernailFeatures = gouvF;
		this.voileFeatures = voileF;
		this.Cg_CC_Max=Cg_CC;
	}
	
	
	
	public GouvernailFeatures getGouvernailFeatures() {
		return gouvernailFeatures;
	}



	public VoileFeatures getVoileFeatures() {
		return voileFeatures;
	}


	double Cg_CC_Max;
	public double getCg_CC_Max() {
		return Cg_CC_Max;
	}
	public double getM() {
		return m;
	}
	public double getJx() {
		return Jx;
	}
	public double getJz() {
		return Jz;
	}
	public double getAlphaF() {
		return alphaF;
	}
	public double getAlphaTheta() {
		return alphaTheta;
	}
	public double getrG() {
		return rG;
	}
	public double getrV() {
		return rV;
	}
	public double getBeta() {
		return beta;
	}
	public double gethV() {
		return hV;
	}
	
	/**
	 * Features du gouvernail
	 */
	private GouvernailFeatures gouvernailFeatures;
	
	/**
	 * Features de la voile
	 */
	private VoileFeatures voileFeatures;

	/**
	 * Masse ou deplacement
	 */
	private double m;
	/**
	 * Moment d'inertie Jx
	 */
	private double Jx;
	/**
	 * Moment d'inertie Jz
	 */
	private double Jz;
	/**
	 * Coefficient de la force de frottement
	 */
	private double alphaF;
	/**
	 * Coefficient du couple de frottement
	 */
	private double alphaTheta;
	/**
	 * Distance centre de gravite - axe du gouvernail
	 */
	private double rG;
	/**
	 * Distance centre de gravite - mat
	 */
	private double rV;
	/**
	 * Coefficient de derive
	 */
	private double beta;
	/**
	 * Hauteur du centre de poussee de la voile
	 */
	private double hV;
	/**
	 * Distance centre de poussee de la voile - mat
	 */
	private double l;
	
	
	public static final BateauAVoileFeatures BasicSailBoatFeatures;
	static{
		/*
		 * Coque : masse = 500
		 * Coque : Jx = 10000
		 * Coque : Jz = 10000
		 * Coque : alphaf = 1
		 * Coque : alphatheta = 6000
		 * Coque : rG = 2.0
		 * Coque : rV = 1.0
		 * Coque : beta = 0.1 //coef de derive
		 */

		BasicSailBoatFeatures = new BateauAVoileFeatures("Bateau A Voile Simple", 500, 10000, 10000, 1, 6000, 0.1, 0.8,GouvernailFeatures.BasicGouvernailFeatures, 1.0, VoileFeatures.BasicVoileF,2.0);
	}

}

