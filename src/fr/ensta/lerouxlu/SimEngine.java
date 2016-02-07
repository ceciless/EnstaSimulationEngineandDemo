package fr.ensta.lerouxlu;

import java.util.HashSet;
import java.util.Set;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.simulation.core.SortedList;

public class SimEngine implements ISimulationDateProvider, IEventObserver {

	private LogicalDateTime currentTime;
	private LogicalDateTime maxTime;
	private SortedList<ISimEvent> echeancier = new SortedList<>();
	private Set<SimEntity> entities = new HashSet<>();
	
	public SimEngine(LogicalDateTime maxTime) {
		this.maxTime = maxTime;
	}
	
	@Override
	public LogicalDateTime simulationDate() {
		return currentTime;
	}
	
	@Override
	public void onEventPosted(ISimEvent event) {
		echeancier.add(event);
	}
	
	public void initialize() {
		for (SimEntity entity : entities)
			entity.initialize();
	}
	
	public void pause() {
		for (SimEntity entity : entities)
			entity.pause();
	}
	
	public void resume() {
		for (SimEntity entity : entities)
			entity.activate();
	}
	
	public boolean triggerNextEvent() {
		// TODO add maxTime check
		if (echeancier.size() == 0) {
			for (SimEntity entity : entities)
				entity.terminate();
			return false;
		}
		ISimEvent nextEvent = echeancier.first();
		echeancier.remove(nextEvent);
		currentTime = nextEvent.scheduleDate();
		for (SimEntity entity : entities) {
			if (entity.isAffectedBy(nextEvent))
				entity.lock();
		}
		nextEvent.process();
		for (SimEntity entity : entities) {
			if (entity.isAffectedBy(nextEvent))
				entity.release();
		}
		return true;
	}

	public void addEntity(SimEntity simEntity) {
		entities.add(simEntity);
	}
	
}
