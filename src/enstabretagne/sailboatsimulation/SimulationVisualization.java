/**
 * 
 */
package enstabretagne.sailboatsimulation;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.FPSAnimator;

/**
 * La classe <code>SimulationVisualization</code> permet de decrire l'affichage
 * des elements dans une fenetre AWT. Elle implemente egalement les methodes des
 * interfaces:
 * - GLEventListener : methodes d'interface avec OpenGL;
 * - MouseListener : methodes pour gerer la souris;
 * - MouseMotionListener : methodes pour gerer les mouvements de la souris;
 * - KeyListener : methodes pour gerer le clavier.
 * @author Fabrice Le Bars
 * @author Luc Jaulin
 * @author Club Robotique de l'ENSTA Bretagne
 * @author Jean-Philippe Schneider
 * @version 1.0
 * @version 2.0 Portage du code C++/QT en Java avec la librairie JOGL
 *
 */
public class SimulationVisualization implements GLEventListener, MouseListener, 
MouseMotionListener, KeyListener {

	private float zoom;
	private int xCam;
	private int yCam;
	private int zCam;
	private double deltavmax;
	private double deltag;
	private float lightPos[] = {5.0f, 5.0f, 10.0f, 1.0f} ;
	private int lastPosX;
	private int lastPosY;
	private Sailboat sailboat;
	private boolean isStarted;
	private FPSAnimator fpsAnimator;

	private Wind theWind;
	
	public SimulationVisualization() {
		this.zoom = 0.5f;
		this.zCam = 1000;
		this.yCam = 0;
		this.xCam = -1100;
		this.deltag = 0;
		this.deltavmax = 0.3;
		this.sailboat = new Sailboat();
		this.isStarted = false;
		/*
		 * Vent : a = 4
		 * Vent : psi = 0*PI
		 */
		this.theWind = new Wind(10, 0*Math.PI);
	}

	public void setFpsAnimator(FPSAnimator fpsAnimator) {
		this.fpsAnimator = fpsAnimator;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		Frame frame = new Frame("Sailboat Simulation");
		frame.setSize(300, 300);
		frame.add(canvas);
		frame.setVisible(true);
		SimulationVisualization visu = new SimulationVisualization();

		canvas.setFocusable(true);
		canvas.requestFocus();
		canvas.addGLEventListener(visu);
		canvas.addMouseListener(visu);
		canvas.addMouseMotionListener(visu);
		canvas.addKeyListener(visu);

		// by default, an AWT Frame doesn't do anything when you click
		// the close button; this bit of code will terminate the program when
		// the window is asked to close
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		FPSAnimator animator = new FPSAnimator(canvas, 50, true);
		visu.setFpsAnimator(animator);
		animator.start();

	}

	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		//drawable.getAnimator().setUpdateFPSFrames(1, null);
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glClearColor(0.9f, 0.9f, 1f, 1f);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, this.lightPos, 0);
		this.xCam = -90;
		this.yCam = 0;
		this.zCam = 20;
		gl.glEnable(GL2.GL_COLOR_MATERIAL);

	}

	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		//double dt = ((this.isStarted ? drawable.getAnimator().getLastFPSPeriod() / 1000.0 : 0.));
		double dt = (this.isStarted ? 0.020 : 0.);
		update(dt);
		render(drawable.getGL().getGL2());
	}

	private void update(double dt) {
			this.sailboat.update(dt, this.deltag, this.deltavmax, this.theWind);
	}
	
	private void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslated(0.0,-2.0, -40.0);
		gl.glRotated(this.xCam, 1.0, 0.0, 0.0);
		gl.glRotated(this.yCam, 0.0, 1.0, 0.0);
		gl.glRotated(this.zCam, 0.0, 0.0, 1.0);
		gl.glScalef(this.zoom, this.zoom, this.zoom);
		gl.glPushMatrix();

		drawSea(gl);
		drawBuoys(gl);
		gl.glPopMatrix();
		gl.glTranslated(this.sailboat.getX(), this.sailboat.getY(), 0.0);
		drawWind(gl);
		drawBoat(gl);
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub

		GL2 gl = drawable.getGL().getGL2();
		int side = Math.min(width, height);
		gl.glViewport((width - side) / 2, (height - side) / 2, side, side);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1.0, 1.0, -1.0, 1.0, 5.0, 60.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	private void drawSea(GL2 gl) {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.5f, 0.5f, 1f);
		gl.glVertex3f(-100000, 100000, 0);
		gl.glVertex3f(-100000, -100000, 0);
		gl.glVertex3f(100000, -100000, 0);
		gl.glVertex3f(100000, 100000, 0);
		gl.glEnd();
	}

	private void drawBuoys(GL2 gl) {
		for (int i = 0; i < 3; i++) {

			gl.glTranslatef(10.0f, 0.0f, 0.0f);
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			GLU glu = new GLU();
			GLUquadric q = glu.gluNewQuadric();
			glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
			glu.gluSphere(q, 1.0, 10, 10);
			glu.gluDeleteQuadric(q);
		}
	}
	
	private void drawWind(GL2 gl) {
		double psi = this.theWind.getWindDir();
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

	private void drawBoat(GL2 gl) {

		gl.glRotatef((float)(this.sailboat.getTheta()*180.0/Math.PI),0.0f,0.0f,1.0f);
		//gl.glPushMatrix();
		gl.glRotatef((float)(this.sailboat.getPhi()*180.0f/Math.PI),1,0,0);
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
			gl.glNormal3f(-1,0,0);                
			gl.glVertex3f(3+0,-1.5f+0.4f,0);
			gl.glVertex3f(3+0, -1.9f+0.4f, 0.5f);    gl.glVertex3f(3+0,-2+0.4f,1.0f);
			gl.glVertex3f(3+0,2-0.4f,1.0f);         gl.glVertex3f(3+0, 1.9f-0.4f, 0.5f);
			gl.glVertex3f(3+0,1.5f-0.4f,0);         gl.glVertex3f(3+0,-1.5f+0.4f,0);
			gl.glEnd();
			// ------------------------ face arrière -----------------------------------------------
			gl.glTranslatef(-0.01f,0,0);
			gl.glBegin(GL2.GL_POLYGON); //face arrière
			gl.glColor3f(0.5f,1,0);              
			gl.glNormal3f(-1,0,0);
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
		gl.glTranslatef(-1,0,0);
		gl.glRotatef((float)(this.sailboat.getDeltag()*180.0/Math.PI),0,0,1);

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
        gl.glTranslatef(5,0,0);
        gl.glRotatef((float)(this.sailboat.getDeltav()*180.0/Math.PI),0,0,1);

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
                float b=(float)-Math.atan(this.sailboat.getFv()/500);  // courbure de la voile
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

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.lastPosX = arg0.getX();
		this.lastPosY = arg0.getY();
		this.sailboat.setX(0.0);
		this.sailboat.setY(0.0);
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int dx = arg0.getX() - this.lastPosX;
		int dy = arg0.getY() - this.lastPosY;
		if (arg0.getButton() == 0) {
			this.xCam += dy;
			this.zCam += dx;
		}
		this.lastPosX = arg0.getX();
		this.lastPosY = arg0.getY();
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_ADD:
			this.zoom *= 1.1;
			break;
		case KeyEvent.VK_SUBTRACT:
			this.zoom /= 1.1;
			break;
//		case KeyEvent.VK_UP:
		case KeyEvent.VK_D:
			this.deltavmax = Math.max(0.0, this.sailboat.getDeltavmax()-0.05);
			break;
//		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_F:
			this.deltavmax = Math.min(1.2, this.sailboat.getDeltavmax()+0.05);
			break;
//		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_K:
			this.deltag = Math.min(this.sailboat.getDeltag()+0.05, 0.5);
			break;
//		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_J:
			this.deltag = Math.max(this.sailboat.getDeltag()-0.05, -0.5);
			break;
		case KeyEvent.VK_S:
			this.isStarted = !this.isStarted;
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
