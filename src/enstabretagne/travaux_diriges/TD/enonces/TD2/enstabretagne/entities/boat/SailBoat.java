/**
* Classe SailBoat.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDuration;
import enstabretagne.simulation.core.IEtatDerive;
import enstabretagne.simulation.core.ISimEntity;
import enstabretagne.entities.demo.DemoEtatDerive;

public class SailBoat implements ISailBoat,ISimEntity{

	List<Force> forces;
	public SailBoat() {
		forces = new ArrayList<Force>();
	}

	@Override
	public Vector3D getPosition() {
		return Vector3D.ZERO;
	}

	@Override
	public Vector3D getVitesse() {
		return Vector3D.ZERO;
	}

	@Override
	public Vector3D getAcceleration() {
		return Vector3D.ZERO;
	}

	@Override
	public double getTheta() {
		return 0;
	}

	@Override
	public double getPhi() {
		return 0;
	}

	@Override
	public double getDeltag() {
		return 0;
	}

	@Override
	public double getDeltav() {
		return 0;
	}

	@Override
	public double getPositionRelativeAncrageVoileSurCoque() {
		return 4;
	}

	@Override
	public double getFv() {
		return 0;
	}

	@Override
	public double getPositionRelativeGouvernail() {
		return 1;
	}

	@Override
	public List<Force> getForces() {
		return forces;
	}

	@Override
	public void update(IEtatDerive e, LogicalDuration dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DemoEtatDerive getTaux() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogicalDuration getStep() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculTaux() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void watchState() {
		// TODO Auto-generated method stub
		
	}


}

