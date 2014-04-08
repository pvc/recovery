/**
 * 
 */

import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class CalcH { 
	final Utils utils = Utils.getSingleton();
	static final double omega=0.5*(Math.sqrt(5)-1);
	final double w;
	double theta=0; // set as a side effect to avoid overhead of creating composite return args
	public CalcH() {this.w=omega;}
	public CalcH(double w) {this.w=w;}
	// Method called to run the class
	public void run() {
		p("Starting run of CalcH at " + new Date());
//		ScalarSequence orbit = calcHOrbit(1,500,new hc(2.8));
//		ScalarSequence orbit = calcHOrbit(1,50000,new hc(Math.E));
		ScalarSequence orbit = calcHOrbit(omega,1000,new hsin(2));
		Graph g = new Graph(1000,800);
		g.add(orbit);
//		g.setAxes(true);
//		g.setXScale(-10, 100);
		g.setTicks(100000,10000);
		g.display();
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalcH at " + new Date());
	}
	
	public double calcHRatio(final int terms, double theta0,Functionh h) {
		double prod=1;
		theta=theta0; // global set as side effect
		for (long n=0;n<terms;n++) {
			theta+=w; if (theta>=1) {theta-=1;}
			prod*=h.h(theta);
		}
		return prod;
	}
	/*
	 * Note 2-w>theta0>=-w works ok. Returns length=terms, first is 1,based on theta0
	 */
	public ScalarSequence calcHOrbit(final double initial, final int iterations, Functionh h) {
		ScalarSequence seq = new ScalarSequence(iterations+1);
//		seq.add(initial);
		double prod=1;
		theta=initial;
		for (int n=0;n<=iterations;n++) {
			prod*=h.h(theta);
			seq.add(prod);
			theta+=w; if (theta>=1) {theta-=1;}
		}
		return seq;
	}
	public ScalarSequence calcHRatioSeq(final int terms, double theta0,Functionh h) {
		ScalarSequence seq = new ScalarSequence(terms);
		double prod=1;seq.add(1);
		theta=theta0; // global set as side effect
		for (int n=1;n<terms;n++) {
			theta+=w; if (theta>=1) {theta-=1;}
			prod*=h.h(theta);
			seq.add(prod);
		}
		return seq;
	}
	
	public double calcH(int terms, double theta0,Functionh h) {
		return calcHRatio(terms, theta0, h)*h.h(theta0);
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
	public class hsin implements Functionh {
		private final double MULTIPLIER;
		public hsin(double multiplier) {this.MULTIPLIER=multiplier;}
		public double h(double theta) {return MULTIPLIER*Math.sin(2*Math.PI*theta);}
	}

}
