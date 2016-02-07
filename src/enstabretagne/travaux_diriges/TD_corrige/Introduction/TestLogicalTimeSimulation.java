/**
* Classe TestLogicalTimeSimulation.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.travaux_diriges.TD_corrige.Introduction;

import enstabretagne.base.time.LogicalDateTime;
import enstabretagne.base.time.LogicalDuration;

public class TestLogicalTimeSimulation {

	public static void main(String[] args) {
		LogicalDateTime ldt = new LogicalDateTime("10/12/2014 10:34:47.6789");
		System.out.println(ldt);
		LogicalDuration ld = LogicalDuration.ofMinutes(15).add(LogicalDuration.ofSeconds(12.67));
		System.out.println(ld);
		System.out.println(ldt.add(ld));
	}
}

