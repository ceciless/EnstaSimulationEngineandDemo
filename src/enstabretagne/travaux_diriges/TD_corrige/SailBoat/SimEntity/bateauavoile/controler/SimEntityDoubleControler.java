/**
* Classe SimEntityDoubleControler.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler;

import enstabretagne.base.utility.Logger;
import enstabretagne.monitors.ISimControler;
import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.simulation.core.SimEvent;

public class SimEntityDoubleControler extends SimEntity implements ISimControler{

	@FunctionalInterface
	public interface DoubleGetter{
		double get();
	}
	
	@FunctionalInterface
	public interface DoubleSetter{
		void set(double d);
	}

	public interface IRequired{
		DoubleGetter getter();
		DoubleSetter setter();
	}
	
	DoubleGetter getDouble;
	DoubleSetter setDouble;
	
	public SimEntityDoubleControler(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		
	}

	@Override
	public void onParentSet() {
		if(!IRequired.class.isAssignableFrom(getParent().getClass()))
			Logger.Fatal(this, "constructeur", "Parent n'implémentant pas l'interface requise.");
		else {
			getDouble=((IRequired) getParent()).getter();
			setDouble=((IRequired) getParent()).setter();
		}
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		double d=getDouble.get()+3*((SimEntityDoubleControlerFeatures)getFeatures()).getRadiansParIncrement();
//		Post(new ChangeValueOfEvent(-d),LogicalDuration.ofSeconds(2.0));
	}

	
	public void changeValue(double d) {
		Post(new ChangeValueOfEvent(d*((SimEntityDoubleControlerFeatures)getFeatures()).getRadiansParIncrement()));
	}
	
	public void incrementValueOf(double d) {
		d=getDouble.get()+d*((SimEntityDoubleControlerFeatures)getFeatures()).getRadiansParIncrement();
		Post(new ChangeValueOfEvent(d));
	}

	class ChangeValueOfEvent extends SimEvent{
		double d;
		
		public ChangeValueOfEvent(double d) {
			this.d=d;
		}

		@Override
		public void Process() {
			setDouble.set(d);			
		}
		
		
	}

	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		// TODO Auto-generated method stub
		
	}


}

