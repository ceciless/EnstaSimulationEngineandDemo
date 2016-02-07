/**
* Classe SimEngine.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;

import java.util.ArrayList;
import java.util.List;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.IRecordable;
import enstabretagne.base.utility.Logger;

public class SimEngine implements ISimulationDateProvider, ISimEngine {

	List<ISimEntity> list;
	
	public SimEngine() {
		list = new ArrayList<ISimEntity>();
		ldt = new LogicalDateTime("01/01/2014 10:00");
	}

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEngine#addSimEntity(enstabretagne.simulation.core.ISimEntity)
	 */
	@Override
	public void addSimEntity(ISimEntity entity)
	{
		list.add(entity);
	}
	LogicalDateTime ldt;

	/* (non-Javadoc)
	 * @see enstabretagne.simulation.core.ISimEngine#simulateFor(enstabretagne.base.time.LogicalDuration)
	 */
	@Override
	public void simulateFor(LogicalDuration d) {
		LogicalDateTime ldtTarget = ldt.add(d);
		
		while(SimulationDate().compareTo(ldtTarget)<0) {
			LogicalDuration step=null;
			for(ISimEntity e:list)
			{
				if(step==null)
					step=e.getStep();
				else
				{
					if(step.compareTo(e.getStep())<0)
						step=e.getStep();
				}
			}
				
			for(ISimEntity e:list)
			{
				e.calculTaux();
			}
			for(ISimEntity e:list)
			{
				e.update(e.getTaux(), step);
			}
			for(ISimEntity e:list)
			{
				if(IRecordable.class.isAssignableFrom(e.getClass()))
					Logger.Data((IRecordable)e);

				e.watchState();
			}
			LogicalDateTime nextPossibleLdt=ldt.add(step);
			if(nextPossibleLdt.compareTo(ldtTarget)>0){
				ldt=ldtTarget;
			}
			else
			{
				ldt = nextPossibleLdt;
			}
				
			}
		}

	@Override
	public LogicalDateTime SimulationDate() {
		return ldt;
	}
	}
	



