/**
* Classe SailBoatRate.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics;

import enstabretagne.expertise.cinematique.KineticRate;
import enstabretagne.simulation.core.IContinuousRate;

public class SailBoatRate extends KineticRate {

	private double theta_rate;
	private double theta_point_rate;
	
	public double getTheta_rate() {
		return theta_rate;
	}

	public void setTheta_rate(double theta_rate) {
		this.theta_rate = theta_rate;
	}

	public double getTheta_point_rate() {
		return theta_point_rate;
	}

	public void setTheta_point_rate(double theta_point_rate) {
		this.theta_point_rate = theta_point_rate;
	}

	public double getPhi_rate() {
		return phi_rate;
	}

	public void setPhi_rate(double phi_rate) {
		this.phi_rate = phi_rate;
	}

	public double getPhi_point_rate() {
		return phi_point_rate;
	}

	public void setPhi_point_rate(double phi_point_rate) {
		this.phi_point_rate = phi_point_rate;
	}

	private double phi_rate;
	private double phi_point_rate;



	public SailBoatRate() {
		super();
		theta_rate=0;
		theta_point_rate=0;
		phi_rate=0;
		phi_point_rate=0;

	}

	@Override
	public void Affine(IContinuousRate rate, double k) {
		super.Affine(rate, k);
		SailBoatRate sbr = (SailBoatRate) rate;
		theta_rate=theta_rate+k*sbr.getTheta_rate();
		theta_point_rate=theta_point_rate+k*sbr.getTheta_point_rate();
		phi_rate=phi_rate+k*sbr.getPhi_rate()*k;
		phi_point_rate=phi_point_rate+k*sbr.getPhi_point_rate();
	}
	
	@Override
	public Object clone() {
		SailBoatRate skr = new SailBoatRate();
		skr.CopyFrom(this);
		return skr;
	}
	@Override
	public void CopyFrom(Object item) {
		SailBoatRate skr = (SailBoatRate) item;
		super.CopyFrom(item);
		
		theta_point_rate = skr.getTheta_point_rate();
		theta_rate = skr.getTheta_rate();
		phi_point_rate = skr.getPhi_point_rate();
		phi_rate = skr.getPhi_rate();

	}
	@Override
	public void Mult(double k) {
		super.Mult(k);
		theta_point_rate = k*getTheta_point_rate();
		theta_rate = k*getTheta_rate();
		phi_point_rate = k*getPhi_point_rate();
		phi_rate = k*getPhi_rate();
		
	}

	@Override
	public String toString() {
		String s = "theta_rate="+getTheta_rate();
		s = s+" "+"theta_point_rate="+getTheta_point_rate();
		s = s+" "+"phi_rate="+getPhi_rate();
		s = s+" "+"phi_point_rate="+getPhi_point_rate();
		return s;
	}
}

