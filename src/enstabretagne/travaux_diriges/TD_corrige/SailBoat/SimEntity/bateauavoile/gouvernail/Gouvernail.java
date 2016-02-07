/**
* Classe Gouvernail.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.gouvernail;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import enstabretagne.simulation.components.IEntity;
import enstabretagne.simulation.components.SimEntity;
import enstabretagne.simulation.components.SimFeatures;
import enstabretagne.simulation.components.SimInitParameters;
import enstabretagne.simulation.core.SimEngine;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.BateauAVoile;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler.DoubleGetter;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControler.DoubleSetter;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControlerFeatures;
import enstabretagne.travaux_diriges.TD_corrige.SailBoat.SimEntity.bateauavoile.controler.SimEntityDoubleControlerInit;

public class Gouvernail extends SimEntity implements SimEntityDoubleControler.IRequired{

	double alpha_gouvernail;
	public double getAlpha_gouvernail() {
		return alpha_gouvernail;
	}
	private void setAlpha_gouvernail(double a) {
		alpha_gouvernail=a;
	}
	SimEntityDoubleControler controler;
	public Gouvernail(SimEngine engine,String name, SimFeatures features) {
		super(engine,name, features);
		GouvernailFeatures gf = (GouvernailFeatures) features;
		getAlphaPointeur=this::getAlpha_gouvernail;
		setAlphaPointeur=this::setAlpha_gouvernail;

		controler=(SimEntityDoubleControler) createChild(engine, SimEntityDoubleControler.class, gf.controleurName, SimEntityDoubleControlerFeatures.basicFeature);
	}

	@Override
	protected void InitializeSimEntity(SimInitParameters init) {
		GouvernailInit i = (GouvernailInit) init;
		alpha_gouvernail=i.getAlpha_gouvernail();		
		controler.Initialize(SimEntityDoubleControlerInit.basicInit);
		
	}

	@Override
	protected void AfterActivate(IEntity sender, boolean starting) {
		controler.activate();
	}

	public Vector3D getFg() {
		GouvernailFeatures gf = (GouvernailFeatures) getFeatures();
		BateauAVoile bav = (BateauAVoile) getParent();
		
		//identification des axes du bateau
		Rotation rz=new Rotation(Vector3D.PLUS_K,bav.getTheta());
		Vector3D axe_x=rz.applyTo(Vector3D.PLUS_I);

		Rotation r_axe_x=new Rotation(axe_x,bav.getPhi());
		Vector3D axe_z=r_axe_x.applyTo(Vector3D.PLUS_K);
		
		Rotation r_gouvernail=new Rotation(axe_z,getAlpha_gouvernail()); 
		
		//conversion de la force du vent en force de voile (orientation de la force liée à la normale à la voile)
		Vector3D normaleGouvernail = r_gouvernail.applyTo(r_axe_x.applyTo(rz.applyTo(Vector3D.PLUS_J)));

		//la force du gouvernail s'oppose au mouvement
		double forceGouv=-normaleGouvernail.dotProduct(bav.getVitesse())*gf.getAlphaG();
		return normaleGouvernail.scalarMultiply(forceGouv);//récupération de la normale au gouvernail
	}

	@Override
	public void onParentSet() {
	}

	DoubleGetter getAlphaPointeur;
	DoubleSetter setAlphaPointeur;
	@Override
	public DoubleGetter getter() {

		return getAlphaPointeur;
	}

	@Override
	public DoubleSetter setter() {
		return setAlphaPointeur;
	}
	@Override
	protected void BeforeDeactivating(IEntity sender, boolean starting) {
		controler.deactivate();		
	}

}

