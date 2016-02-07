/**
* Classe SailBoatState.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.dynamics;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.expertise.cinematique.KineticState;
import enstabretagne.simulation.core.IContinuousRate;

public class SailBoatState extends KineticState {

	private double theta;
	private double theta_point;
	
	private double phi;
	private double phi_point;
	
	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getTheta_point() {
		return theta_point;
	}

	public void setTheta_point(double theta_point) {
		this.theta_point = theta_point;
	}

	public double getPhi() {
		return phi;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}

	public double getPhi_point() {
		return phi_point;
	}

	public void setPhi_point(double phi_point) {
		this.phi_point = phi_point;
	}

	public SailBoatState(Vector3D pos,double theta, double phi) {
		super(pos);
		this.theta=theta;
		this.phi=phi;
		this.theta_point=0;		
		this.phi_point=0;
	}

	public SailBoatState(Vector3D pos, Vector3D vel,double theta, double theta_point, double phi,double phi_point) {
		super(pos, vel);

		this.theta=theta;
		this.phi=phi;
		this.theta_point=theta_point;		
		this.phi_point=phi_point;
	}


	public SailBoatState(Vector3D pos, Vector3D vel,double theta, double theta_point, double phi,double phi_point, double curv) {
		super(pos, vel, curv);
		this.theta=theta;
		this.phi=phi;
		this.theta_point=theta_point;		
		this.phi_point=phi_point;
	}
	
	@Override
	public Object clone() {
		return new SailBoatState(getPos(), getVel(), theta, theta_point,phi,phi_point, getCurv());
	}

	@Override
	public void CopyFrom(Object item) {
		super.CopyFrom(item);
		SailBoatState sks = (SailBoatState)((SailBoatState) item).clone();
		theta=sks.getTheta();
		theta_point=sks.getTheta_point();
		phi=sks.getPhi();
		phi_point=sks.getPhi_point();	
	}
	
	@Override
	public int Dimension() {
		return super.Dimension()+6;
	}
	
	@Override
	public void Update(IContinuousRate rate, double dt) {
		super.Update(rate, dt);
		SailBoatRate skr = (SailBoatRate) rate;
		
		phi = phi+skr.getPhi_rate()*dt;
		phi_point = phi_point+skr.getPhi_point_rate()*dt;
		
		theta = theta+skr.getTheta_rate()*dt;
		theta_point = theta_point+skr.getTheta_point_rate()*dt;
	}
	@Override
	public IContinuousRate ZeroRate() {
		return new SailBoatRate();
	}
	
	@Override
	public String toString() {
		String s = "";
		s+="Phi="+phi;
		s+=" Phi_point="+phi_point;		
		s+=" theta="+theta;		
		s+=" theta_point="+theta_point;
		
		return s;
	}

}

