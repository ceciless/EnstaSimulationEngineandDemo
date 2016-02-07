/**
 * 
 */
package enstabretagne.sailboatsimulation;

/**
 * La classe <code>Hull</code> decrit le comportement de la coque d'un
 * voilier
 * @author Bruno Aizier
 * @author Jean-Philippe Schneider
 * @version 1.0
 * @version 2.0 portage du code C++ en Java
 *
 */
public class Hull {
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
	
	
	public Hull(double m, double Jx, double Jz, double alphaF, double alphaTheta,
			double rG, double rV, double beta) {
		this.m = m;
		this.Jx = Jx;
		this.Jz = Jz;
		this.alphaF = alphaF;
		this.alphaTheta = alphaTheta;
		this.rG = rG;
		this.rV = rV;
		this.beta = beta;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getJx() {
		return Jx;
	}

	public void setJx(double jx) {
		Jx = jx;
	}

	public double getJz() {
		return Jz;
	}

	public void setJz(double jz) {
		Jz = jz;
	}

	public double getAlphaF() {
		return alphaF;
	}

	public void setAlphaF(double alphaF) {
		this.alphaF = alphaF;
	}

	public double getAlphaTheta() {
		return alphaTheta;
	}

	public void setAlphaTheta(double alphaTheta) {
		this.alphaTheta = alphaTheta;
	}

	public double getrG() {
		return rG;
	}

	public void setrG(double rG) {
		this.rG = rG;
	}

	public double getrV() {
		return rV;
	}

	public void setrV(double rV) {
		this.rV = rV;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	
}
