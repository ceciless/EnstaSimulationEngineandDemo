/**
* Classe Wind3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat.gl3Drepresentations;

import javax.media.opengl.GL2;

import enstabretagne.entities.boat.IWind;
import enstabretagne.monitor.IDrawAction;


public class Wind3DRepresentation implements IDrawAction {

	@Override
	public void draw(GL2 gl, Object obj) {
		if(!IWind.class.isAssignableFrom(obj.getClass())) return;
		
		IWind wind = (IWind) obj;
		
		double psi = wind.getWindSpeed().getAlpha();

		gl.glPushMatrix();
		gl.glRotatef((float)((psi + 0.5*Math.PI) * 180.0 / Math.PI), 0.0f, 0.0f, 1.0f);
		gl.glBegin(GL2.GL_LINES);
		double a = 1;
		gl.glColor3f(0.7f, 0.1f, 0.0f);
		gl.glVertex3f(3, 14, 13);
		gl.glVertex3f(3,  (float)(14 - a), 13);
		gl.glVertex3f(3,(float)(14-a),13);
		gl.glVertex3f(3,(float)(14-a),14);
		gl.glVertex3f(3,(float)(14-a),14);
		gl.glVertex3f(3,(float)(12.5-a),12.5f);
		gl.glVertex3f(3,(float)(12.5-a),12.5f);
		gl.glVertex3f(3,(float)(14-a),11);
		gl.glVertex3f(3,(float)(14-a),11);
		gl.glVertex3f(3,(float)(14-a),12);
		gl.glVertex3f(3,(float)(14-a),12);
		gl.glVertex3f(3,14,12);
		gl.glVertex3f(3,14,12);
		gl.glVertex3f(3,14,13);
		gl.glEnd();
		gl.glPopMatrix();		
	}

}

