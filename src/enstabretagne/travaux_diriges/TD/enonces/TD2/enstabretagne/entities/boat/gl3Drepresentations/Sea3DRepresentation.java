/**
* Classe Sea3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat.gl3Drepresentations;

import javax.media.opengl.GL2;

import enstabretagne.monitor.IDrawAction;

public class Sea3DRepresentation implements IDrawAction{

	@Override
	public void draw(GL2 gl, Object obj) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.5f, 0.5f, 1f);
		gl.glVertex3f(-100000, 100000, 0);
		gl.glVertex3f(-100000, -100000, 0);
		gl.glVertex3f(100000, -100000, 0);
		gl.glVertex3f(100000, 100000, 0);
		gl.glEnd();		
	}

}

