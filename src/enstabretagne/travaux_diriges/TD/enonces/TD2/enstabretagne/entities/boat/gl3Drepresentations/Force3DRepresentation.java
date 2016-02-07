/**
* Classe Force3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat.gl3Drepresentations;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.entities.boat.Force;
import enstabretagne.monitor.IDrawAction;

public class Force3DRepresentation implements IDrawAction{

	@Override
	public void draw(GL2 gl, Object obj) {
		if(!(Force.class.isAssignableFrom(obj.getClass()))) return;

		Force force = (Force) obj;		
		
		gl.glLineWidth(2.5f);
		gl.glColor3f(1.0f,0f,0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3d(force.getPointApplication().getX(), force.getPointApplication().getY(),force.getPointApplication().getZ());
		
		Vector3D finalPoint = force.getPointApplication().add(force.getForce());
		gl.glVertex3d(finalPoint.getX(), finalPoint.getY(), finalPoint.getZ());
		gl.glEnd();
		
		gl.glTranslated(force.getPointApplication().getX(), force.getPointApplication().getY(),force.getPointApplication().getZ());
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		GLU glu = new GLU();
		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
		glu.gluSphere(q, Force.originRadius, 5, 5);
		glu.gluDeleteQuadric(q);

		

		
	}

}

