/**
* Classe DemoSimEntity.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.demo;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.simulation.core.IEtatDerive;
import enstabretagne.simulation.core.ISimEntity;
import enstabretagne.simulation.core.SimObject;

public class DemoSimEntity extends SimObject implements ISimEntity{

	double masse;
	DemoEtat etat;
	DemoEtatDerive etatDerive;
	
	LogicalDuration step;
	String name;
	public DemoSimEntity(String name,double masse,double step) {
		this.masse=masse;
		this.step=LogicalDuration.ofSeconds(step);
		etat=new DemoEtat(30, 0);
		etatDerive = new DemoEtatDerive();
		this.name = name;
	}

	public DemoEtatDerive getTaux() {
		return etatDerive;
	}
	public void calculTaux(){
		etatDerive.taux_z=etat.vz;
		etatDerive.taux_vz=-9.81;
	}
	@Override
	public void update(IEtatDerive e, LogicalDuration dt) {
		etat.vz=etat.vz+etatDerive.taux_vz*dt.DoubleValue();
		etat.z=etat.z+etatDerive.taux_z*dt.DoubleValue();
	}
	
	public void watchState()
	{
		Logger.Information(this, "watchState", "etat.z=%g", etat.z);
	}


	@Override
	public LogicalDuration getStep() {
		return step;
	}


}

