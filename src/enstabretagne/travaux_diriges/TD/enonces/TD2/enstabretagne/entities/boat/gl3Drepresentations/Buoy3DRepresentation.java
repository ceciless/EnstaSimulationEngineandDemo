/**
* Classe Buoy3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat.gl3Drepresentations;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import enstabretagne.entities.boat.IBuoy;
import enstabretagne.monitor.IDrawAction;

public class Buoy3DRepresentation implements IDrawAction{

	@Override
	public void draw(GL2 gl, Object obj) {
		IBuoy b = (IBuoy) obj;
		gl.glTranslated(b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ());
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
		glu.gluSphere(q, 1.0, 10, 10);
		glu.gluDeleteQuadric(q);
	}

}

