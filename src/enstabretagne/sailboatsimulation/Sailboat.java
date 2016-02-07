/**
 * 
 */
package enstabretagne.sailboatsimulation;

/**
 * La classe <code>Sailboat</code> decrit le comportement d'un voilier.
 * @version 1.0
 * @version 2.0 Portage du code C++/QT en Java avec la librairie JOGL
 * @since 28/07/2014
 * @author Fabrice Le Bars
 * @author Luc Jaulin
 * @author Club Robotique de l'ENSTA Bretagne
 * @author Jean-Philippe Schneider
 * @author Bruno Aizier
 */
public class Sailboat {

	private double x;
	private double y;
	private double deltavmax;
	//private double deltag;
	/*private double psi;
	private double a;*/
	private double theta;
	private double phi;
	//private double deltav;
	//private double fv;
	private double v;
	//private double gamma;
	//private double fg;
	//private double alphag;
	//private double alphav;
	//private double beta;
	private double omega;
	/*private double Jz;
	private double Jx;
	private double rv;
	private double rg;
	private double alphatheta;
	private double m;
	private double alphaf;*/
	private double phiPoint;
	private double rCCMax;
	private double dcgCcMax;
	private double vt;
	private double fAntiDerive;
	/*private double hv;
	private double l;*/
	
	//private Wind theWind;
	private Hull theHull;
	private Sail theSail;
	private Rudder theRudder;
	
	
	public Sailboat() {
		//this.deltag = 0;
		this.deltavmax = 0;
		this.x = 0;
		this.y = 0;
		/*this.a = 2;
		this.psi = 1.5 * Math.PI;*/
		this.theta = 0.2;
		this.phi = -0.25;
		//this.fv = 0;
		this.v = 1;
		//this.gamma = 0;
		//this.fg = 0;
		//this.alphag = 2000.0;
		//this.alphav = 1000.0;
		//this.beta = 0.1;
		this.omega = 0.0;
		/*this.Jz = 10000.0;
		this.Jx = 3000.0;
		this.rv = 1.0;
		this.rg = 2.0;
		this.alphatheta = 6000;
		this.m = 300;
		this.alphaf = 1.0;*/
		this.phiPoint = 0;
		this.rCCMax = 0.8;
		this.dcgCcMax = 0.8;
		this.vt = 0.;
		this.fAntiDerive = 0.;
		/*this.hv = 4.0;
		this.l = 1.0;*/
		
		//this.theWind = new Wind(2, 1.5*Math.PI);
		/*
		 * Coque : masse = 500
		 * Coque : Jx = 10000
		 * Coque : Jz = 10000
		 * Coque : alphaf = 1
		 * Coque : alphatheta = 6000
		 * Coque : rG = 2.0
		 * Coque : rV = 1.0
		 * Coque : beta = 0.1
		 */
		this.theHull = new Hull(500, 10000.0, 10000.0, 1.0, 6000.0, 2.0, 1.0, 0.1);
		/*
		 * Voile : alphaV = 1500.0
		 * Voile : deltaV = 45
		 * Voile : surfaceVoile = 12
		 * Voile : hV = 6
		 * Voile : l = 1
		 */
		this.theSail = new Sail(4000.0, 45, 12, 6.0, 1.0);
		/*
		 * Gouvernail : alphaG = 1500.0
		 * Gouvernail : rudderAng = 0
		 * Gouvernail : surfaceSafran = 0.2
		 * Gouvernail : estSafranVersBabord = false
		 */
		this.theRudder = new Rudder(6000.0, 0, 0.2, false);
	}
	
	public double getDeltavmax() {
		return deltavmax;
	}

	public void setDeltavmax(double deltavmax) {
		this.deltavmax = deltavmax;
	}

	public double getDeltag() {
		return this.theRudder.getRudderAng();
	}

	public void setDeltag(double deltag) {
		this.theRudder.setRudderAng(deltag);
	}

	

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	/*public double getPsi() {
		return this.theWind.getWindDir();
	}

	public void setPsi(double psi) {
		this.theWind.setWindDir(psi);
	}

	public double getA() {
		return this.theWind.getWindForce();
	}

	public void setA(double a) {
		this.theWind.setWindForce(a);
	}*/

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getPhi() {
		return phi;
	}

	public void setPhi(double phi) {
		this.phi = phi;
	}

	public double getDeltav() {
		return this.theSail.getDeltaV();
	}

	public void setDeltav(double deltav) {
		this.theSail.setDeltaV(deltav);;
	}

	public double getFv() {
		return this.theSail.getfV();
	}

	public void setFv(double fv) {
		this.theSail.setfV(fv);
	}
	
	public double getL() {
		return this.theSail.getL();
	}

	public void setL(double l) {
		this.theSail.setL(l);;
	}

	public void update(double dt, double deltag, double deltavmax, Wind theWind) {
		//this.deltag = deltag;
		this.deltavmax = deltavmax;
		
		double a = theWind.getWindForce();
		double psi = theWind.getWindDir();
		
		double beta = this.theHull.getBeta();
		double Jz = this.theHull.getJz();
		double l = this.theSail.getL();
		double rv = this.theHull.getrV();
		double rg = this.theHull.getrG();
		double alphatheta = this.theHull.getAlphaTheta();
		double m = this.theHull.getM();
		double alphaf = this.theHull.getAlphaF();
		double Jx = this.theHull.getJx();
		double hv = this.theSail.gethV();

		//TODO modify deltav and fv
		//double deltav = this.theSail.getDeltaV();
		//double fv = this.theSail.getfV();
		//double alphav = this.theSail.getAlphaV();
		
		//double fg = this.theRudder.getFg();
		/*this.theRudder.setRudderAng(deltag);
		this.deltag = this.theRudder.getRudderAng();
		double alphag = this.theRudder.getAlphaG();*/
		
		
		//Composante longitudinale du vent apparent
		double xw_ap = a * Math.cos(psi - this.theta) - this.v;
		//composante transversale du vent apparent
		double yw_ap = a * Math.sin(psi - this.theta);
		//Direction du vent apparent
		double psi_ap = Math.atan2(yw_ap, xw_ap);
		//Force du vent apparent
		double a_ap = Math.sqrt(xw_ap*xw_ap + yw_ap*yw_ap);
		
		/*this.gamma = Math.cos(psi_ap)+Math.cos(this.deltavmax);
		if (this.gamma < 0) {
			//Voile en drapeau
			deltav = Math.PI + psi_ap;
		} else if(Math.sin(-psi_ap) > 0) {
			deltav = this.deltavmax;
		} else {
			deltav = -this.deltavmax;
		}
		//Force velique propulsive
		fv = alphav * a_ap * Math.sin(deltav - psi_ap);*/
		
		//Mise a jour de la composante velique
		this.theSail.update(psi_ap, deltavmax, a_ap);
		double deltav = this.theSail.getDeltaV();
		//Force velique propulsive
		double fv = this.theSail.getfV() * Math.sin(deltav - psi_ap);
		
		
		//Force gouvernail (trainee s'oppose a l'avancement)
		this.theRudder.update(this.v, deltag);
		double fg = this.theRudder.getFg();
		deltag = this.theRudder.getRudderAng();
		
		
		//Calcul nouvelle position du centre de gravite
		this.x += (this.v * Math.cos(this.theta) * dt); //x = x + Vx * dt
		this.x += (this.vt * Math.sin(this.theta) * dt);
		this.x += (beta * a * Math.cos(psi) * dt); //x = x + CoeffDerive * VitesseVent * cos(DirVent) * dt
		
		
		this.y += (this.v * Math.sin(this.theta) * dt); //y = y + Vy *dt
		this.y -= (this.vt * Math.cos(this.theta) * dt);
		this.y += (beta * a * Math.sin(psi) * dt); //y = y + CoeffDerive * VitesseVent * sin(DirVent) * dt
		
		this.y -= (this.vt * Math.cos(this.theta) * dt);
		
		//Calcul nouvelle routation autour Axe Z par centre de gravite du voilier
		this.theta += (this.omega * dt);
		
		this.omega += dt * (1.0/Jz) * ((l - rv * Math.cos(deltav)) * fv //Contribution moment velique
						-rg * Math.cos(deltag) * fg						//Contribution Moment gouvernail
						- alphatheta * this.omega);						//v ??? Contribution moment frottement
		
		//Calcul nouvelle vitesse, suite aux forces sur centre de gravite du voilier (Gamma = Forces / Masse)
		this.v += dt * (1./m) * (Math.sin(deltav) * fv	//Contribution Force velique
					- Math.sin(deltag) * fg				//Contribution force gouvernail
					- alphaf * this.v);					//v*v ??? Contribution moment frottement
		
		fAntiDerive = 0.5 * 1000 * (0.4 * this.vt * this.vt * 1.5);
		
		this.vt += dt * (1. / m) * (Math.cos(deltav) * fv);
		this.vt -= dt * (1. /m) * fAntiDerive;
		
		//Calcul cap route fond
		// Calculs nouvelle Gite (rotation autour Axe X, par CC du voilier) : d_phi/dt
	    //
	    //  Bruno : Manque le couple de redressement CdG CC, je vais l'ajouter
	    //  Bruno : J'ai besoin de la distance CdG_CC (Centre Gravité à Centre Carène)
	    //          qui varie avec la gite (phi):   r_ccMax*sin(phi)

	    //  Bruno : Et du Poids ou déplacement du voilier  m,

	    phiPoint +=  dt*(-this.phiPoint
	                     +fv*hv*Math.cos(deltav)*Math.cos(this.phi)/Jx         // Couple vélique de gite
	                     - 10000*9.81*Math.sin(this.phi)/Jx               // Meq*Leq*g*sin(phi)/Jx
	                         - m*this.dcgCcMax*Math.sin(this.phi)/Jx);          // Couple redressement M*CdG_CC  f(sin (Gite))
		this.phi += (this.phiPoint * dt);
	}
}
 