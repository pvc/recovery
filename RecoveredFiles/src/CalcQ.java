/**
 * 
 */

import java.util.Date;

import org.pv.core.Utils;



/**
 * @author PV
 *
 */
public class CalcQ {
	final Utils utils = Utils.getSingleton();
	static final double omega=0.5*(Math.sqrt(5)-1);
	final long[] fib={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
	final double twoPi=2*Math.PI;
	final double w;
	final double twoPiw;
	final double phase0;
	final double xFactor=1;
	double theta=0; // set as a side effect to avoid overhead of creating composite return args
	public CalcQ() {this.w=omega;this.phase0=0;twoPiw=2*Math.PI*w;}
	public CalcQ(double w,double phase) {this.w=w;this.phase0=phase;twoPiw=2*Math.PI*w;}
	// Method called to run the class
	public void run() {
		p("Starting run of CalcH at " + new Date());
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalcH at " + new Date());
	}
	
	public ScalarSequence calc(ScalarSequence x,int degree) {
		final ScalarSequence series1 = new ScalarSequence();
        final long Fn=fib[degree];
		final double twopiphase0=2*Math.PI*phase0;
		final double pointsD=x.getSize()-1;
//		List<Double> vals=new ArrayList<Double>(points+1);
		double last=1; double val;
		final double scale=Math.pow(-w,degree);
		System.out.println("Scale="+scale);
//		int minusw=(int) Math.floor(pointsD*(-w)-1);
		
//		for (int n=minusw; n<=points; n++) { // calc each value of y
		for (int n=0; n<x.getSize(); n++) { // calc each value of y
			val=1;
			double phase=twopiphase0 + scale*twoPi*xFactor*x.get(n);
			for (int r=0;r<Fn;r++) {
				val*=2*Math.sin(twoPiw*r +phase);
			}
//			vals.add(n, val);
//			if ( (val<0.1) & (val>-0.1) ) {System.out.println(n+":"+val);};
			System.out.println(n+":"+val);
			series1.add(val);
			//last=val;
		}
		return series1;
	}
	
	
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public interface Functionh {
		public double h(double theta);
	}
	public class h1 implements Functionh {
		public double h(double theta) {return theta;}
	}
	public class hc implements Functionh {
		private final double MULTIPLIER;
		public hc(double multiplier) {this.MULTIPLIER=multiplier;}
		public double h(double theta) {return MULTIPLIER*theta;}
	}

}
