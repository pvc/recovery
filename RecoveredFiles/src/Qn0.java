/**
 * 
 */

import java.util.Date;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

import math.Fibs;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class Qn0 {
	final static Utils utils = Utils.getSingleton();
	final double w=0.5*(Math.sqrt(5)-1);

	// Method called to run the class
	public void run() {
		p("Starting run of Qn0 at " + new Date());
		for (int n=6;n<30;n++) {
			p(""+calc2(n));
		}
		p("Finished run of Qn0 at " + new Date());
	}
	/*
	 * Simple calc of prod(1..Fn) of 2sin(pi.n.w)
	 */
	public double calc(int fibIndex) {
		final int q = Fibs.get(fibIndex);
		double prod=1;
		for (int n=1;n<=q;n++) {
			prod*=2*Math.sin(Math.PI*n*w);
		}
		return prod;
	}
	
	public double calc2(int fibIndex) {
		final int q = Fibs.get(fibIndex);
		double prod=1,arg=0;
		final double inc=Math.PI*w;
		final double twoPI=2*Math.PI;
		for (int n=1;n<=q;n++) {
			arg+=inc;
			if (arg>twoPI) {arg-=twoPI;}
			prod*=2*Math.sin(arg);
		}
		return prod;
	}


	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
