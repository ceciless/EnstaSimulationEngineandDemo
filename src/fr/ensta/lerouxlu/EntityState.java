package fr.ensta.lerouxlu;

public enum EntityState {
	
	NONE,
	BORN,
	IDLE,
	HELD,
	ACTIVE,
	DEAD;

	public boolean isBusy() {
		return (this == HELD) || (this == ACTIVE);
	}
	
}
