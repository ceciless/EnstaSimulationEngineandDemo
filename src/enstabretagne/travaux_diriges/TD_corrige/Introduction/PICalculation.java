/**
* Classe PICalculation.java
*@author Olivier VERRON
*@version 1.0.
*/
	package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import enstabretagne.base.math.MoreRandom;

public class PICalculation {

	MoreRandom r;
	int nombreLances;
	public PICalculation(int nombreLances) {
		r = new MoreRandom(5);
		this.nombreLances=nombreLances;
	}
	
	public void calculerPI() {
		int nbLancesOK=0;
		double PI;
		
		for(int i = 0;i<nombreLances;i++)
		{
			double x = r.nextDouble();
			double y = r.nextDouble();
			
			double rayon = Math.sqrt(x*x+y*y);
			if(rayon<1){
				nbLancesOK++;
			}
		}
		
		PI=((double) nbLancesOK)/nombreLances * 4;
		System.out.println(PI);
		
	}
	
	public static void main(String[] args) {
		PICalculation pi = new PICalculation(100000);
		pi.calculerPI();
	}

}

