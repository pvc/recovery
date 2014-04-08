/**
 * 
 */

import graphs.Graphs;
import graphs.XSequence;
import graphs.XY;

import java.util.Date;
import java.util.function.LongToDoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import math.Fibs;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class DedekindSum {
	final static Utils utils = Utils.getSingleton();
	private LongToDoubleFunction calcTerm;
	final double w=0.5*(Math.sqrt(5)-1);

	// Method called to run the class
	public void run() {
		p("Starting run of DedekindSum at " + new Date());
//		p(""+(-2.65%-1));
//		calc(28);
		XY xy = new XY(new XSequence(9,27), n->dedSum(n));
//		xy.plot();
		xy.dump();
//		Graphs.plot(new XSequence(9,27), n->dedSum(n)).display();
//		IntStream.range(4,26).forEach(n->calc(n));
		p("Finished run of DedekindSum at " + new Date());
	}
	
	/*
	 * Calculate the semi Dedekind sum of cot x alphat
	 */
	public double dedSum(long n) {
		final int sign;
		if (n%2==0) {sign=1;} else {sign=-1;}
//		p("Sign="+sign);
		final long q=Fibs.get(n);
		final double pbyq=Fibs.get(n-1)/(double)q;
		final double wn=sign*Math.pow(w,n);
//		p("wn:"+wn);
		calcTerm=new LongToDoubleFunction() {
			public double applyAsDouble(long r) {
				double res=Math.PI*wn*sign*(0.5-(pbyq*r)%1)/Math.tan(Math.PI*r/q);
//				p(""+r+":"+sign*(0.5-(pbyq*r)%1)+";"+Math.tan(Math.PI*r/q)+";"+res);
				return res;
			}
		};
		double sum=LongStream.range(1,q).mapToDouble(calcTerm).sum();
		p("Sum for "+n+": "+sum);
		return sum;
		
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
