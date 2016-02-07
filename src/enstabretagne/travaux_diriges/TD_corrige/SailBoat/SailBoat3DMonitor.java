/**
* Classe SailBoat3DMonitor.java
* @author Fabrice Le Bars
* @author Luc Jaulin
* @author Club Robotique de l'ENSTA Bretagne
*@author Olivier VERRON
*@author Bruno AIZIER
*@author Jean-Philippe SCHNEIDER
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat;

import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import javax.media.opengl.GL2;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;
import enstabretagne.base.utility.Logger;
import enstabretagne.base.utility.LoggerParamsNames;
import enstabretagne.base.utility.loggerimpl.XLSXExcelDataloggerImpl;
import enstabretagne.monitors.Graphical3DMonitor;
import enstabretagne.monitors.NewGraphical3DMonitor;
import enstabretagne.simulation.components.ScenarioId;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimScenario;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.Boat3DRepresentation;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.Buoy3DRepresentation;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.IBuoy;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.ISailBoat;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.ISea;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.IWind;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.Sea3DRepresentation;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.GL3DRepresentations.Wind3DRepresentation;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail.GouvernailFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.voile.VoileFeatures;

public class SailBoat3DMonitor extends NewGraphical3DMonitor {

	public SailBoat3DMonitor(HashMap<String,HashMap<String,Object>> loggersNames, int fps) {
		super(loggersNames, fps);
		drawActionsMapping.put(ISea.class, new Sea3DRepresentation());
		drawActionsMapping.put(IBuoy.class, new Buoy3DRepresentation());
		drawActionsMapping.put(IWind.class, new Wind3DRepresentation());
		drawActionsMapping.put(ISailBoat.class, new Boat3DRepresentation());
	}

	@Override
	protected void setPointOfView(GL2 gl) {

		SimEntity e = getCurrentScenario().getEntityToFollow();
		glu.gluPerspective(45, 640/480, 1, 1000);		
		
		if(e!=null && ISailBoat.class.isAssignableFrom(e.getClass())) {
			ISailBoat bav = (ISailBoat)e;
			Vector3D camView = (new Vector3D(this.xCam*Math.PI/180,this.zCam*Math.PI/180));
			
			camView = bav.getPosition().add(camView.normalize().scalarMultiply(zoom*10)); 
		    glu.gluLookAt(camView.getX(), camView.getY(), camView.getZ(), bav.getPosition().getX(), bav.getPosition().getY(), bav.getPosition().getZ(), 0, 0, 1);
		}
		else {
		    glu.gluLookAt(this.xCam, this.yCam, this.zCam, 0, 0, 0, 0, 0, 1);
		}
		
//		gl.glTranslated(0,0, -20);
//		gl.glRotated(this.xCam, 1.0, 0.0, 0.0);
//		gl.glRotated(this.yCam, 0.0, 1.0, 0.0);
//		gl.glRotated(this.zCam, 0.0, 0.0, 1.0);
//		gl.glScalef(this.zoom, this.zoom, this.zoom);
		
	}
	

	
	public static void main(String[] args) {
		System.out.println("Debut =" + Instant.now());

		
		//Initialisation du logger sans initialiser avec engine
		//On souhaite en effet logger toute l'exécution dans un même tableau Excel 
		HashMap<String,HashMap<String,Object>> loggersNames = new HashMap<String,HashMap<String,Object>>();
//		loggersNames.put(SysOutLogger.class.getCanonicalName(),new HashMap<String,Object>());
		
		HashMap<String,Object> xlsxParams=new HashMap<String,Object>();		
		xlsxParams.put(LoggerParamsNames.DirectoryName.toString(), System.getProperty("user.dir") + "\\log");
		xlsxParams.put(LoggerParamsNames.FileName.toString(), "SalonCoiffure.xlsx");
		loggersNames.put(XLSXExcelDataloggerImpl.class.getCanonicalName(),xlsxParams);

		SailBoat3DMonitor visu = new SailBoat3DMonitor(loggersNames,50);
		LogicalDateTime start = new LogicalDateTime("01/09/2014 06:00");

		SimScenario s = new SailBoatScenario(visu.getEngine(),new ScenarioId("Scenario1",0), null,start,start.add(LogicalDuration.ofSeconds(2)));
		
		visu.run(s,0);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		super.keyPressed(arg0);
		
		SimEntityDoubleControler controler ;

		switch(arg0.getKeyCode()) {
		case 39://>>
			controler = (SimEntityDoubleControler) getIdentifiedControlerOf(getCurrentScenario().getEntityToFollow(),GouvernailFeatures.BasicGouvernailFeatures.getControlerName());
			if(controler!=null)
			controler.incrementValueOf(1);
			break;
		case 37://<<
			controler = (SimEntityDoubleControler) getIdentifiedControlerOf(getCurrentScenario().getEntityToFollow(),GouvernailFeatures.BasicGouvernailFeatures.getControlerName());
			if(controler!=null)
				controler.incrementValueOf(-1);
			break;		
		case 38://^
			controler = (SimEntityDoubleControler) getIdentifiedControlerOf(getCurrentScenario().getEntityToFollow(),VoileFeatures.BasicVoileF.getControlerName());
			if(controler!=null)
				controler.incrementValueOf(1);
			break;
		
		case 40://v
			controler = (SimEntityDoubleControler) getIdentifiedControlerOf(getCurrentScenario().getEntityToFollow(),VoileFeatures.BasicVoileF.getControlerName());
			if(controler!=null)
				controler.incrementValueOf(-1);
			break;
		}

	}


}

