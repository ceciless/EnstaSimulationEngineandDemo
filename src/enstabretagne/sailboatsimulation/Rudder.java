/**
 * 
 */
package enstabretagne.sailboatsimulation;

/**
 * La classe <code>Rudder</code> decrit le comportement du gouvernail
 * d'un voilier
 * @author Bruno Aizier
 * @author Jean-Philippe Schneider
 * @version 1.0
 * @version 2.0 Portage du code C++ en Java.
 *
 */
public class Rudder {
	/**
	 * Portance du gouvernail
	 */
	private double alphaG;
	/**
	 * Angle de barre
	 */
	private double rudderAng;
	/**
	 * Surface du safran
	 */
	private double surfaceSafran;
	/**
	 * Safran est vers babord
	 */
	private boolean estSafranVersBabord;
	/**
	 * Force du safran = alphaG * v
	 */
	private double fg;
	
	public Rudder(double alphaG, double rudderAng, double surfaceSafran, 
			boolean estSafranVersBabord) {
		this.alphaG = alphaG;
		this.rudderAng = rudderAng;
		this.surfaceSafran = surfaceSafran;
		this.estSafranVersBabord = estSafranVersBabord;
		this.fg = 0;
	}

	public double getAlphaG() {
		return alphaG;
	}

	public void setAlphaG(double alphaG) {
		this.alphaG = alphaG;
	}

	public double getRudderAng() {
		return rudderAng;
	}

	public void setRudderAng(double rudderAng) {
		this.rudderAng = rudderAng;
	}

	public double getSurfaceSafran() {
		return surfaceSafran;
	}

	public void setSurfaceSafran(double surfaceSafran) {
		this.surfaceSafran = surfaceSafran;
	}

	public boolean isEstSafranVersBabord() {
		return estSafranVersBabord;
	}

	public void setEstSafranVersBabord(boolean estSafranVersBabord) {
		this.estSafranVersBabord = estSafranVersBabord;
	}

	public double getFg() {
		return fg;
	}

	public void setFg(double fg) {
		this.fg = fg;
	}
	
	public void update(double v, double deltag) {
		this.rudderAng = deltag;
		this.fg = this.alphaG * v * Math.sin(this.rudderAng);
	}
}
