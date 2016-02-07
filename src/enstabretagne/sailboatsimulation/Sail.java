/**
 * 
 */
package enstabretagne.sailboatsimulation;

/**
 * La classe <code>Sail</code> decrit le comportement des voiles d'un voilier
 * @author Bruno Aizier
 * @author Jean-Philippe Schneider
 * @version 1.0
 * @version 2.0 Portage du code C++ en Java
 *
 */
public class Sail {
	/**
	 * Masse volumique de l'air
	 */
	public static double RHO_AIR = 1.293;
	/**
	 * Coefficient de portance de la voile
	 */
	public static double C_SAIL = 0.9;
	/**
	 * Portance de la voile
	 */
	private double alphaV;
	/**
	 * Angle de la voile
	 */
	private double deltaV;
	/**
	 * Force velique = alphaV * vitesse
	 */
	private double fV;
	/**
	 * Surface de la voile
	 */
	private double surfaceVoile;
	/**
	 * Hauteur du centre de poussee de la voile
	 */
	private double hV;
	/**
	 * Distance centre de poussee de la voile - mat
	 */
	private double l;
	
	public Sail(double alphaV, double deltaV, double surfaceVoile,
			double hV, double l) {
		this.alphaV = alphaV;
		this.deltaV = deltaV;
		this.surfaceVoile = surfaceVoile;
		this.fV = 0.;
		this.hV = hV;
		this.l = l;
	}

	public double getAlphaV() {
		return alphaV;
	}

	public void setAlphaV(double alphaV) {
		this.alphaV = alphaV;
	}

	public double getDeltaV() {
		return deltaV;
	}

	public void setDeltaV(double deltaV) {
		this.deltaV = deltaV;
	}

	public double getfV() {
		return fV;
	}

	public void setfV(double fV) {
		this.fV = fV;
	}

	public double getSurfaceVoile() {
		return surfaceVoile;
	}

	public void setSurfaceVoile(double surfaceVoile) {
		this.surfaceVoile = surfaceVoile;
	}
	
	public double gethV() {
		return hV;
	}

	public void sethV(double hV) {
		this.hV = hV;
	}

	public double getL() {
		return l;
	}

	public void setL(double l) {
		this.l = l;
	}

	public void borderVoile(double angleVoile) {
		//TODO
	}
	
	public void choquerVoile(double angleVoile) {
		//TODO
	}

	public void update(double psi_ap, double deltavmax, double a_ap) {
		double gamma = Math.cos(psi_ap) + Math.cos(deltavmax);
		if (gamma < 0) {
			this.deltaV = Math.PI + psi_ap;
		} else if (Math.sin(-psi_ap) > 0) {
			this.deltaV = deltavmax;
		} else {
			this.deltaV = -deltavmax;
		}
		this.fV = (1.0/2.0) * Sail.RHO_AIR * this.surfaceVoile * a_ap * a_ap * Sail.C_SAIL;;
	}
}
