/**
 * 
 */

import graphs.Graph;
import graphs.MultiGraph;
import graphs.ScalarSequence;
import graphs.XSequence;

import java.util.Date;

import org.pv.core.Utils;


/**
 * @author Paul
 *
 */
public class Hn {
	final Utils utils = Utils.getSingleton();
	static final long[] fibs={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
	final static double TWOPI=2*Math.PI;
	static final double w=0.5*(Math.pow(5,0.5)-1);
	final static double TWOPIw=2*Math.PI*w;
	private int functionIndex=3;
	private double initialPhaseValue=0;
	

	// Method called to run the class
	public void run() {
		p("Starting run of Hn at " + new Date());
		test1();
		p("Finished run of Hn at " + new Date());
	}
	
	public void test1() {
		MultiGraph mg = new MultiGraph(3);
		mg.setAxes(true);
		
		for (int n=4;n<19;n++) {
		Graph g = new Graph(250,200);
//		g.setTitle("Any old Title");
		g.addText(10, 10, "n="+n);
		
		setFunctionIndex(n);
		setInitialPhaseValue(0);
		ScalarSequence x=new XSequence(0,1,250);
		ScalarSequence y = calcY(x);
		double ymax = y.getMax();
		double log = Math.log10(ymax/3);
		double exp = Math.floor(log);
		double mant = log-exp;
		int base=1;
		if (mant>Math.log10(5)) {base=5;} else if (mant>Math.log10(2)) {base=2;}
		double yround = Math.floor(100*(ymax+0.005))/100;
		g.addText(10, 24, "Max="+yround);
//		y.dumpRange();
//		p("Max at:"+x.get(y.getMaxAt()));
//		g.setAxes(true);
		g.setYDisplay(-ymax*0.15,ymax);
		g.setXDisplay(-0.25,1);
		g.setTicks(0.25, base*Math.pow(10,exp));
		g.add(x,y);
		mg.add(g);
		}
		mg.display();
	}
	
	/*
	 * Calculate Hn over 0-1 around phase0. Note H0=0, H1=H2=1 so interesting behaviour starts with n=3
	 * Hn(theta,y)=Sum(0,Fn-1)Prod(0,r)[2sin(2pi.(theta+y.(-w)^n+rw))]
	 */
		public ScalarSequence calcY(ScalarSequence x) {
			final ScalarSequence y = new ScalarSequence(x.getSize());
//	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
//	        dataset.addSeries(series1);
	        final long Fn=fibs[functionIndex];
			final double TWOPIPHASE0=TWOPI*initialPhaseValue;
			final int points=x.getSize();  // Interval 0-500 has 501 points
//			final double xscale=Math.pow((-w),functionIndex);
			final double xscale=0.5; // plot half a period (as second half is same)
			for (int n=0; n<points; n++) { // calc each value of y
				double arg=TWOPIPHASE0+TWOPI*x.get(n)*xscale;
//				p("n="+n+" Phase:"+arg+"  X="+x.get(n));
				double Rn=1;
				double sum=1;if (functionIndex==0) {sum=0;}
				for (long s=0;s<Fn;s++) { //optimisation: actually r=1 to Fn-1 with (r-1)w below 
					Rn*=2*Math.sin(TWOPIw*s +arg); // *lambda squared
					sum+=Rn*Rn;
				}
				y.add(sum);
			}
			return y;
		}

	public int getFunctionIndex() {
			return functionIndex;
		}

		public void setFunctionIndex(int functionIndex) {
			this.functionIndex = functionIndex;
		}
		public double getInitialPhaseValue() {
			return initialPhaseValue;
		}

		public void setInitialPhaseValue(double initialPhaseValue) {
			this.initialPhaseValue = initialPhaseValue;
		}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
