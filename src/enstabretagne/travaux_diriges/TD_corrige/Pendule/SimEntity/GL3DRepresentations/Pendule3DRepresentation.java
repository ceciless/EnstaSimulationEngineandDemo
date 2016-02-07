/**
* Classe Pendule3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Pendule.SimEntity.GL3DRepresentations;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import enstabretagne.monitors.IDrawAction;

public class Pendule3DRepresentation implements IDrawAction{

	@Override
	public void draw(GL2 gl, Object obj) {
		if(!(IPendule.class.isAssignableFrom(obj.getClass()))) return;

		IPendule pendule = (IPendule) obj;
		
		
		gl.glLineWidth(2.5f);
		gl.glColor3f(1.0f,0f,0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(pendule.getPosition().getX(), pendule.getPosition().getY(),pendule.getPosition().getZ());
		gl.glVertex3d(0d, 0d, 2d);
		gl.glEnd();
		
		gl.glTranslated(pendule.getPosition().getX(), pendule.getPosition().getY(),pendule.getPosition().getZ());
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
		glu.gluSphere(q, pendule.getRadius(), 5, 5);
		glu.gluDeleteQuadric(q);

		
	}

}

