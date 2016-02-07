/**
* Classe Boat3DRepresentation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.entities.boat.gl3Drepresentations;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import enstabretagne.entities.boat.Force;
import enstabretagne.entities.boat.ISailBoat;
import enstabretagne.monitor.IDrawAction;

public class Boat3DRepresentation implements IDrawAction{

	Force3DRepresentation force3DRepresentation = new Force3DRepresentation();
	
	@Override
	public void draw(GL2 gl, Object obj) {
		
		if(!(ISailBoat.class.isAssignableFrom(obj.getClass()))) return;
		
		ISailBoat boat = (ISailBoat) obj;
		gl.glTranslated(boat.getPosition().getX(), boat.getPosition().getY(), boat.getPosition().getZ());

		for(Force f:boat.getForces()) {
			gl.glPushMatrix();
			force3DRepresentation.draw(gl, f);
			gl.glPopMatrix();
			}

		
		gl.glRotatef((float)(boat.getTheta()*180.0/Math.PI),0.0f,0.0f,1.0f);
		gl.glRotatef((float)(boat.getPhi()*180.0f/Math.PI),1,0,0);

		gl.glPushMatrix();

		{
			gl.glBegin(GL2.GL_QUADS);   //plancher
			gl.glVertex3f(-1,-1.5f,0.1f);        gl.glVertex3f(3,-1.5f, 0.1f);
			gl.glVertex3f(3,1.5f,0.1f);          gl.glVertex3f(-1,1.5f,0.1f);
			gl.glVertex3f(4.5f,-0.75f, 0.1f);     gl.glVertex3f(5.06f,-0.375f, 0.1f);
			gl.glVertex3f(5.25f,0, 0.1f);       gl.glVertex3f(5.06f,0.375f, 0.1f);
			gl.glVertex3f(4.5f,0.75f, 0.1f);
			gl.glEnd();
			// ---------------------------- dessus avant ----------------------------------------------------------------------
			gl.glTranslatef(0.01f,0,0.01f);
			gl.glBegin(GL2.GL_POLYGON);  //dessus avant
			gl.glColor3f(0.8f,0.2f,0.2f);            gl.glVertex3f(3,-2,1.0f);
			gl.glVertex3f(3.1f,-1.9f,1.01f);         gl.glVertex3f(3.75f,-1.6f, 1.01f);
			gl.glVertex3f(5.0f,-1,1.01f);           gl.glVertex3f(5.75f,-0.5f,1.01f);
			gl.glVertex3f(6.0f, 0, 1.01f);          gl.glVertex3f(5.75f,0.5f,1.01f);
			gl.glVertex3f(5.0f,1,1.01f);            gl.glVertex3f(3.75f,1.6f,1.01f);
			gl.glVertex3f(3.1f,1.9f,1.01f);          gl.glVertex3f(3,2,1.01f);
			gl.glEnd();

			//--------------------------- faces latérales ---------------------------------------------------------------------
			gl.glTranslatef(-0.01f,0,0);
			gl.glBegin(GL2.GL_POLYGON); //faces latérales droite
			gl.glColor3f(0,1,0);
			gl.glVertex3f(-1,-1.5f,0);       gl.glVertex3f(-1, -1.9f, 0.5f);
			gl.glVertex3f(-1,-2,1.0f);       gl.glVertex3f(3,-2,1.0f);
			gl.glVertex3f(3, -1.9f, 0.5f);    gl.glVertex3f(3,-1.5f,0);
			gl.glVertex3f(-1,-1.5f,0);
			gl.glEnd();
			gl.glBegin(GL2.GL_POLYGON); //faces latérales gauche
			gl.glColor3f(0,0,1);
			gl.glVertex3f(-1,1.5f,0);        gl.glVertex3f(-1, 1.9f, 0.5f);
			gl.glVertex3f(-1,2,1.0f);        gl.glVertex3f(3,2,1.0f);
			gl.glVertex3f(3, 1.9f, 0.5f);     gl.glVertex3f(3,1.5f,0);
			gl.glVertex3f(-1,1.5f,0);
			gl.glEnd();
			//--------------------------- banc ----------------------------------------------------------------------------------
			gl.glTranslatef(0,-0.01f,0);
			gl.glBegin(GL2.GL_QUADS);              //banc
			gl.glColor3f(1,1,1);
			gl.glVertex3f(-1,2,1.0f);         gl.glVertex3f(-1, 1.3f, 1.0f);
			gl.glVertex3f(3, 1.3f, 1.0f);      gl.glVertex3f(3,2,1.0f);
			gl.glVertex3f(-1, 1.3f, 1.0f);     gl.glVertex3f(-1, 1.1f, (float)(4*1.0/5));
			gl.glVertex3f(3, 1.1f, (float)(4*1.0/5));  gl.glVertex3f(3, 1.3f, 1.0f);
			gl.glVertex3f(-1, 1.1f, (float)(4*1.0/5)); gl.glVertex3f(-1, 0.9f, (float)(3*1.0/5));
			gl.glVertex3f(3, 0.9f, (float)(3*1.0/5));  gl.glVertex3f(3, 1.1f, (float)(4*1.0/5));
			gl.glVertex3f(-1, 0.9f, (float)(3*1.0/5)); gl.glVertex3f(-1,0.8f,0);
			gl.glVertex3f(3,0.8f,0);          gl.glVertex3f(3, 0.9f, (float)(3*1.0/5));
			gl.glEnd();
			gl.glTranslatef(0,0.01f,0);

			// -------------------------------- autre banc ----------------------------------------------------------------
			gl.glBegin(GL2.GL_QUADS);              //banc
			gl.glColor3f(1,1,1);
			gl.glVertex3f(-1,-2,1.0f);        gl.glVertex3f(-1, -1.3f, 1.0f);
			gl.glVertex3f(3, -1.3f, 1.0f);     gl.glVertex3f(3,-2,1.0f);
			gl.glVertex3f(-1, -1.3f, 1.0f);    gl.glVertex3f(-1, -1.1f, (float)(4*1.0/5));
			gl.glVertex3f(3, -1.1f, (float)(4*1.0/5)); gl.glVertex3f(3, -1.3f, 1.0f);
			gl.glVertex3f(-1, -1.1f, (float)(4*1.0/5));gl.glVertex3f(-1, -0.9f, (float)(3*1.0/5));
			gl.glVertex3f(3, -0.9f, (float)(3*1.0/5)); gl.glVertex3f(3, -1.1f, (float)(4*1.0/5));
			gl.glVertex3f(-1, -0.9f, (float)(3*1.0/5));gl.glVertex3f(-1,-0.8f,0);
			gl.glVertex3f(3,-0.8f,0);         gl.glVertex3f(3, -0.9f, (float)(3*1.0/5));
			gl.glEnd();
			gl.glTranslatef(0,-0.01f,0);
			// ---------------------- face avant mat --------------------------------
			gl.glBegin(GL2.GL_POLYGON); //face "avant mat"
			gl.glColor3f(0,0,1);
			gl.glNormal3f(-1,0,0);                gl.glVertex3f(3+0,-1.5f+0.4f,0);
			gl.glVertex3f(3+0, -1.9f+0.4f, 0.5f);    gl.glVertex3f(3+0,-2+0.4f,1.0f);
			gl.glVertex3f(3+0,2-0.4f,1.0f);         gl.glVertex3f(3+0, 1.9f-0.4f, 0.5f);
			gl.glVertex3f(3+0,1.5f-0.4f,0);         gl.glVertex3f(3+0,-1.5f+0.4f,0);
			gl.glEnd();
			// ------------------------ face arrière -----------------------------------------------
			gl.glTranslatef(-0.01f,0,0);
			gl.glBegin(GL2.GL_POLYGON); //face arrière
			gl.glColor3f(0.5f,1,0);              gl.glNormal3f(-1,0,0);
			gl.glVertex3f(-1,-1.5f,0);           gl.glVertex3f(-1, -1.9f, 0.5f);
			gl.glVertex3f(-1,-2,1.0f);           gl.glVertex3f(-1,2,1.0f);
			gl.glVertex3f(-1, 1.9f, 0.5f);        gl.glVertex3f(-1,1.5f,0);
			gl.glVertex3f(-1,-1.5f,0);
			gl.glEnd();
			gl.glTranslatef(0.01f,-0.01f,0);
			// ----------------------------- face avant ------------------------------------------
			gl.glBegin(GL2.GL_POLYGON);
			gl.glVertex3f(3,-2,1.0f);           gl.glVertex3f(3.1f,-1.7f,1.0f);
			gl.glVertex3f(3.75f,-1.5f, 1.0f);     gl.glVertex3f(5.0f,-1,1.0f);
			gl.glVertex3f(5.75f,-0.5f,1.0f);      gl.glVertex3f(6.0f, 0, 1.0f);
			gl.glVertex3f(5.25f,0, 0);          gl.glVertex3f(5.06f,-0.375f, 0);
			gl.glVertex3f(4.5f,-0.75f, 0);       gl.glVertex3f(3,-1.5f,0);
			gl.glVertex3f(3, -1.9f, 0.5f);       gl.glVertex3f(3,-2,1.0f);
			gl.glVertex3f(3,2,1.0f);            gl.glVertex3f(3.1f,1.7f,1.0f);
			gl.glVertex3f(3.75f,1.5f, 1.0f);      gl.glVertex3f(5.0f,1,1.0f);
			gl.glVertex3f(5.75f,0.5f,1.0f);       gl.glVertex3f(6.0f, 0, 1.0f);
			gl.glVertex3f(5.25f,0, 0);          gl.glVertex3f(5.06f,0.375f, 0);
			gl.glVertex3f(4.5f,0.75f, 0);        gl.glVertex3f(3,1.5f,0);
			gl.glVertex3f(3, 1.9f, 0.5f);        gl.glVertex3f(3,2,1.0f);
			gl.glEnd();
		}
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		
		gl.glTranslatef((float) -boat.getPositionRelativeGouvernail(),0,0);
		gl.glRotatef((float)(boat.getDeltag()*180.0/Math.PI),0,0,1);

        // ***************************************
        //       RUDDER
        // ***************************************
        {   
        	gl.glColor3f(0,0,1);
        	gl.glBegin(GL2.GL_POLYGON);
        	gl.glVertex3f(0,-0.05f,1);       gl.glVertex3f(0,-0.05f,0);      gl.glVertex3f(-0.6f,-0.05f,0);
        	gl.glVertex3f(-0.3f,-0.05f,1);    gl.glVertex3f(0,-0.05f,1);      gl.glVertex3f(0,0.05f,1);
        	gl.glVertex3f(0,0.05f,0);       gl.glVertex3f(-0.6f,0.05f,0);    gl.glVertex3f(-0.3f,0.05f,1);
        	gl.glVertex3f(0,0.05f,1);
        	gl.glVertex3f(0,-0.05f,1);       gl.glVertex3f(0,0.05f,1);       gl.glVertex3f(0,-0.05f,0);
        	gl.glVertex3f(0,0.05f,0);        gl.glVertex3f(-0.6f,-0.05f,0);   gl.glVertex3f(-0.6f,0.05f,0);
        	gl.glVertex3f(-0.3f,-0.05f,1);    gl.glVertex3f(-0.3f,0.05f,1);    gl.glVertex3f(0,-0.05f,1);
        	gl.glVertex3f(0,0.05f,1);
        	gl.glEnd();
        	gl.glDisable(GL2.GL_TEXTURE_2D);
        	gl.glTranslatef(-0.6f,0,1);
        	gl.glRotatef(90,0,1,0);
        	
        	GLU glu = new GLU();
			GLUquadric q1 = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(q1, GLU.GLU_FILL);
			glu.gluQuadricOrientation(q1, GLU.GLU_OUTSIDE);
			glu.gluCylinder(q1, 0.05, 0.05, 3, 10, 10);
			glu.gluDeleteQuadric(q1);
        	
        }

        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef((float) (boat.getPositionRelativeAncrageVoileSurCoque()),0,0);
        gl.glRotatef((float)(boat.getDeltav()*180.0/Math.PI),0,0,1);

           // ***************************************
           //       SAIL
           // ***************************************

            {   
            	gl.glColor3f(0.9f,0.9f,0.9f);
                GLU glu = new GLU();
            	GLUquadric q1 = glu.gluNewQuadric();  //mât
            	glu.gluQuadricOrientation(q1, GLU.GLU_OUTSIDE);
            	glu.gluQuadricTexture(q1, true);
            	glu.gluCylinder(q1,0.08,0.08,14, 10,10);
            	glu.gluDeleteQuadric(q1);
                
                gl.glDisable(GL.GL_TEXTURE_2D);

                gl.glColor3f(1,1,0);
                float b=(float)-Math.atan(boat.getFv()/500);  // courbure de la voile
                gl.glEnable(GL.GL_TEXTURE_2D);
                gl.glBegin(GL.GL_TRIANGLE_FAN);
                gl.glVertex3f(0,0,2);     gl.glVertex3f(0,0,12);    gl.glVertex3f(-1,b*1.5f,10);
                gl.glVertex3f(-2,b*2,8);  gl.glVertex3f(-3,b*2,6);  gl.glVertex3f(-4,b*1.5f,4);  
                gl.glVertex3f(-5,0,2);
                gl.glEnd();

                gl.glDisable(GL.GL_BLEND);
                gl.glDisable(GL.GL_TEXTURE_2D);
                gl.glTranslatef(0,0,2);
                gl.glRotatef(-90,0,1,0);
                gl.glEnable(GL.GL_TEXTURE_2D);         //bome
                gl.glBindTexture(GL.GL_TEXTURE_2D,5);

                q1=glu.gluNewQuadric();
                glu.gluQuadricOrientation(q1, GLU.GLU_OUTSIDE);
                glu.gluQuadricTexture(q1, true);
                glu.gluCylinder(q1,0.1,0.1,5.5, 10,10);
                glu.gluDeleteQuadric(q1);
                gl.glDisable(GL.GL_TEXTURE_2D);
            }
            gl.glPopMatrix();		
	}

}

