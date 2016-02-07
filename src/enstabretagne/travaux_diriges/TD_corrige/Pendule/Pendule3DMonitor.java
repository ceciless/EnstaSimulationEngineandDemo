/**
* Classe Pendule3DMonitor.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule;

import java.util.HashMap;

import javax.media.opengl.GL2;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.SysOutLogger;
import enstabretagne.base.utility.loggerimpl.XLSXExcelDataloggerImpl;
import enstabretagne.monitors.Graphical3DMonitor;
import enstabretagne.monitors.NewGraphical3DMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.GL3DRepresentations.IPendule;
import enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.GL3DRepresentations.Pendule3DRepresentation;

public class Pendule3DMonitor extends NewGraphical3DMonitor {

	public Pendule3DMonitor(HashMap<String,HashMap<String,Object>> loggersNames, int fps) {
		super(loggersNames, fps);
		drawActionsMapping.put(IPendule.class, new Pendule3DRepresentation());

	}

	@Override
	protected void setPointOfView(GL2 gl) {
		this.xCam=90;
		this.zCam=0;
		Vector3D camView = (new Vector3D(this.xCam*Math.PI/180,this.zCam*Math.PI/180));
		camView=camView.scalarMultiply(this.zoom);
		glu.gluPerspective(45, 640/480, 1, 1000);			
	    glu.gluLookAt(camView.getX(), camView.getY(), camView.getZ(), 0, 0, 1, 0, 0, 1);

	}
	
	public static void main(String[] args) {
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");
		
		//Initialisation du logger sans initialiser avec engine
		//On souhaite en effet logger toute l'exécution dans un même tableau Excel 
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
//		loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());
		
		HashMap<String,Object> xlsxParams=new HashMap<String,Object>();		
		xlsxParams.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir") + "\\log");
		xlsxParams.put(LoggerParamsNames.FileName.toString(), "SalonCoiffure.xlsx");
		loggersNames.put(XLSXExcelDataloggerImpl.class.getCanonicalName(),xlsxParams);


		Pendule3DMonitor visu = new Pendule3DMonitor(loggersNames,50);
		SimScenario s = new PenduleScenario(visu.getEngine(), new ScenarioId("Scenario1",0), null,start,start.add(LogicalDuration.ofSeconds(2)));

		visu.run(s,0);

	}

}

