/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;

import math.Fibs;

import org.pv.core.Utils;

/**
 * @author PV
 * H here is the product of h(rw) over r in (0,n)
 */
public class CalcProducts { 
	final Utils utils = Utils.getSingleton();
	static final double omega=0.5*(Math.sqrt(5)-1);
	final double w;
	double theta=0; // set as a side effect to avoid overhead of creating composite return args
	private double initialCondition=omega;
	public CalcProducts() {this.w=omega;}
	public CalcProducts(double w) {this.w=w;}
	// Method called to run the class
	public void run() {
		p("Starting run of CalcProducts at " + new Date());
//		initialCondition=omega;
		initialCondition=0;
//		showOrbits(24);
//		showPeaks(initialCondition,35);		
		ScalarSequence orbit = calcTest(4, new hsin(2));
		orbit.dump();
//		ScalarSequence orbit = calcHOrbit(1,500,new hc(2.8));
//		ScalarSequence orbit = calcHOrbit(1,50000,new hc(Math.E));
//		ScalarSequence orbit = calcHOrbit(omega,100000,new hsin(2.00));
//		Graph g = new Graph(1000,800);
//		g.add(orbit);
//		g.setAxes(true);
//		g.setXScale(-5000, 105000);
//		g.setTicks(10000,10000);
//		g.display();
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalcProducts at " + new Date());
	}
	
	/**
	 * Calc (Fn-1)th points of orbit and graph
	 */
	private void showPeaks(double initialCondition, int nPeaks) {
		ScalarSequence peaks = calcHRelPeaks(initialCondition, nPeaks, new hsin(2));
		Graph g = new Graph(250,200);
//		g.add(peaks);
//		g.display();
		peaks.dump();
	}
	public void showOrbit() {
//		ScalarSequence orbit = calcHOrbit(1,500,new hc(2.8));
//		ScalarSequence orbit = calcHOrbit(1,50000,new hc(Math.E));
		ScalarSequence orbit = calcHOrbit(omega,100000,new hsin(2.00));
		Graph g = new Graph(1000,800);
		g.add(orbit);
//		g.setAxes(true);
		g.setXScale(-5000, 105000);
		g.setTicks(10000,10000);
		g.display();
	}
	public void showOrbits(int terms) {
		ScalarSequence orbit=null;
		ScalarSequence peaks=new ScalarSequence(terms);
		ArrayList<Store> stores = new ArrayList<Store>(10);
		MultiGraph mg = new MultiGraph();
//		int terms=2;
//		ScalarSequence orbit = calcHOrbit(1,500,new hc(2.8));
//		ScalarSequence orbit = calcHOrbit(1,50000,new hc(Math.E));
		Store last=new Store();
		last.lastx=initialCondition;
		hsin h = new hsin(2);
		last.lastProd=last.sum=last.runningSum=h.h(initialCondition);
		for (int n=0;n<terms;n++){
		Store next=new Store();
		orbit = calcHOrbit2(last,next,h);
		last=next;
		stores.add(next);
		peaks.add(Math.log(next.max));
		if (n>8) {
		Graph g = new Graph(250,200);
		g.add(orbit);
		mg.add(g);
		g.setAxes(false);
		g.addText(10, 10,"Segment "+next.functionIndex+ "; max="+next.max);
		g.addText(10, 25, "at="+next.maxAt/(next.nterms+0.0));
//		g.setXScale(-5000, 105000);
//		g.setTicks(10000,10000);
		}
		}
		mg.display();
		for (Store s: stores){
			if (s.nterms>1) {
			p(s.functionIndex+": "+s.average+"; "+s.max+"/"+(s.maxAt/(0.+s.nterms)));
			}
		}	
		Graph g = new Graph(250,200);
		g.add(peaks);
		g.display();
		peaks.dump();		
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
	 * Calc simple orbit of initial condition
	 */
	public ScalarSequence calcHOrbit(final double initial, final int iterations, Functionh h) {
		ScalarSequence seq = new ScalarSequence(iterations+1);
//		seq.add(initial);
		theta=initial;
		double prod=h.h(theta);
		if (prod==0) {prod=h.getMultiplier();}
		for (int n=1;n<=iterations;n++) {
			prod*=h.h(theta);
			seq.add(prod);
			theta+=w; if (theta>=1) {theta-=1;}
		}
		return seq;
	}
	/*
	 * Calc each (Fn-1)th point of orbit of initial condition from n=2,ie F2=1
	 * 
	 */
	public ScalarSequence calcHRelPeaks(final double initial, final int terms, Functionh h) {
		ScalarSequence seq = new ScalarSequence(terms);
//		seq.add(initial);
		int count=1;
		theta=initial;
		double prod=h.h(theta);
//		int functionIndex=2;
		if (prod==0) {prod=h.getMultiplier();}
		p("ic="+theta+"; prod0="+prod);
		for (int n=0;n<terms;n++) {
			prod=1; // reset for rel peaks
			for (int m=0;m<Fibs.fibsInt[n];m++){
				theta+=w; if (theta>=1) {theta-=1;}
				prod*=h.h(theta);
//				count++;
//				p("Count:"+count);
			}
			seq.add(prod);
//			p("Stored:"+count+"; theta="+theta);
		}
		return seq;
	}
	/*
	 * Calc each (Fn-1)th point of orbit of test hypothesis
	 * 
	 */
	public ScalarSequence calcTest(final int terms, Functionh h) {
		ScalarSequence seq = new ScalarSequence(terms);
//		seq.add(initial);
//		int count=1;
		final double om2=w*w;
		double prod;
//		int functionIndex=2;
//		if (prod==0) {prod=h.getMultiplier();}
//		p("ic="+theta+"; prod0="+prod);
		for (int n=1;n<=terms;n++) {
			double theta1=-Math.pow(-w,n);
			double theta2=theta1*om2;
			prod=1; // reset for rel peaks
			for (int m=0;m<Fibs.fibsInt[n+1];m++){
				prod*=h.h(theta1)*h.h(theta2);
				p(n+":"+m+";"+prod);
				theta1+=w; if (theta1>=1) {theta1-=1;}
				theta2+=w; if (theta2>=1) {theta2-=1;}
			}
			seq.add(prod);
//			p("Stored:"+count+"; theta="+theta);
	}
	return seq;
	}
	/*
	 * Calc orbit in chunks of Fn-1 points
	 * 
	 */
	public ScalarSequence calcHOrbit2(Store last,Store next, Functionh h) {
		next.functionIndex=last.functionIndex+1;
		final int iterations=Fibs.fibsInt[next.functionIndex-2];
		p("Orbit seg "+next.functionIndex+"="+iterations+" terms");
		next.nterms=iterations;
		
		ScalarSequence seq = new ScalarSequence(iterations+1);
		double prod=last.lastProd;
		seq.add(prod);
		double x=last.lastx;
		double sum=0;
		for (int n=0;n<iterations;n++) {
			x+=w; if (x>=1) {x-=1;}
			prod*=h.h(x);
			seq.add(prod);
			sum+=prod;
		}
		next.lastx=x;next.lastProd=prod;next.sum=sum;next.runningSum=last.runningSum+sum;
		next.max=seq.getMax();next.maxAt=seq.maxAt;next.average=sum/iterations;
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
		public double getMultiplier();
		public double h(double theta);
	}
	public class h1 implements Functionh {
		public double h(double theta) {return theta;}
		public double getMultiplier() {
			return Math.E;
		}
	}
	public class hc implements Functionh {
		public final double MULTIPLIER;
		public hc(double multiplier) {this.MULTIPLIER=multiplier;}
		public double h(double theta) {return MULTIPLIER*theta;}
		public double getMultiplier() {
			return MULTIPLIER;
		}
	}
	public class hsin implements Functionh {
		public final double MULTIPLIER;
		public hsin(double multiplier) {this.MULTIPLIER=multiplier;}
		public double h(double theta) {return Math.abs(MULTIPLIER*Math.sin(2*Math.PI*theta));}
		public double getMultiplier() {
			return MULTIPLIER;
		}
	}
	public class Store {
//		public Store(double theta){}
		int functionIndex=1;
		int nterms=0;
		double sum=0,runningSum=0,lastProd=1,lastx=0;
		double average=0;
		double max=0;
		int maxAt=1;
	}	

}
