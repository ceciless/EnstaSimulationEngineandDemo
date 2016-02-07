/**
* Classe Moniteur3DBateau.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.media.opengl.GL2;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.entities.boat.ISailBoat;
import enstabretagne.entities.boat.SailBoat;
import enstabretagne.entities.boat.gl3Drepresentations.Boat3DRepresentation;
import enstabretagne.monitor.Graphical3DMonitor;

public class Moniteur3DBateau extends Graphical3DMonitor {

	public Moniteur3DBateau(String SimAppClassName, String AppName, int fps) {
		super(SimAppClassName, AppName, fps);
		drawActionsMapping.put(ISailBoat.class, new Boat3DRepresentation());

	}

	@Override
	protected void initLogger() {
		loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());

		super.initLogger();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		super.keyPressed(arg0);
		switch(arg0.getKeyCode()) {
			}
	}	
	@Override
	protected void setPointOfView(GL2 gl) {
		Vector3D camView = (new Vector3D(this.xCam*Math.PI/180,this.zCam*Math.PI/180));
		camView=camView.scalarMultiply(this.zoom);
		glu.gluPerspective(45, 640/480, 1, 1000);			
	    glu.gluLookAt(camView.getX(), camView.getY(), camView.getZ(), 0, 0, 1, 0, 0, 1);

	}
	
	public static void main(String[] args) {
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		Moniteur3DBateau visu = new Moniteur3DBateau("s","s",50);

		visu.zoom = 10;
		visu.associate3DRepresentationTo(new SailBoat());;
		visu.createGui();

	}

}

