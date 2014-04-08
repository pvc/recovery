/**
 * 
 */

import graphs.Graph;
import graphs.MultiGraph;
import graphs.ScalarSequence;
import graphs.XSequence;

import java.util.Arrays;
import java.util.Date;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;

import math.Fibs;

import org.pv.core.Utils;


/**
 * @author Paul
 *
 */
public class Qn implements DataEngine{
	final Utils utils = Utils.getSingleton();
	static final long[] fibs={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
	final static double TWOPI=2*Math.PI;
	static final double w=0.5*(Math.pow(5,0.5)-1);
	final static double TWOPIw=2*Math.PI*w;
	final static double PIw=Math.PI*w;
	private int functionIndex=3;
//	private double initialPhaseValue=0.5*Math.sqrt(2);
	private double initialPhaseValue=0.0;
	private boolean trace=true;
	private boolean calcPerformed=false;
	private double initialCondition=0;
	private double rotationNumber=w;
	private double circleMultiplier=Math.PI;
	private DoubleUnaryOperator functionToBeIterated=DoubleUnaryOperator.identity();
	private DoubleUnaryOperator finalTransform=DoubleUnaryOperator.identity();
//	private Point graphSize=new Point(150,150);
//	private double startX=0;
//	private double endX=1;
//	private int nIncrements=1; // x increment will be (endX-startX)/(nIncrements)
	private ScalarSequence xValues;
	private long[] iterations;
	
	private ScalarSequence[] data;
	private double circlePeriod=1;
	

	// Method called to run the class
	public void run() {
		p("Starting run of Qn at " + new Date());
		test1();
//		p("Fibs="+fibs.length);
		p("Finished run of Qn at " + new Date());
	}
	
	public void calc() {
		if (calcPerformed) {return;}
		data = new ScalarSequence[iterations.length];
		Arrays.setAll(data, (n)->calcY(iterations[n]));
		calcPerformed=true;
	}
	
	public void test1() {
		MultiGraph mg = new MultiGraph(3);
		mg.setAxes(true);
		trace=true;
		
		for (int n=13;n<25;n++) {
		p("Starting n="+n+" at "+new Date());
		Graph g = new Graph(300,300);
//		g.setTitle("Any old Title");
		g.addText(10, 10, "n="+n);
		
		setFunctionIndex(n);
//		setInitialPhaseValue(0);
		ScalarSequence x=g.getXValues(-10,10);
		ScalarSequence y = calcY(x);
		double ymax = Math.max(Math.abs(y.getMax()),Math.abs(y.getMin()));
		double log = Math.log10(ymax/3);
		double exp = Math.floor(log);
		double mant = log-exp;
		int base=1;
		if (mant>Math.log10(5)) {base=5;} else if (mant>Math.log10(2)) {base=2;}
//		double yTick = Math.rint(10*base*Math.pow(10,exp))/10;
		double yTick = base*Math.pow(10,exp);
		double yround = Math.floor(100*(ymax+0.005))/100;
		g.addText(10, 24, "Norm="+yround);
//		y.dumpRange();
//		p("Max at:"+x.get(y.getMaxAt()));
//		g.setAxes(true);
//		g.setYDisplay(-ymax*0.15,ymax);
//		g.setYDisplay(y.getMin(),y.getMax());
//		g.setXDisplay(-1,1);
//		g.setTicks(0.5, Math.rint(base*Math.pow(10,exp)));
//		g.setTicks(5,1,(float) yTick,0 );
		g.add(x,y);
		mg.add(g);
		p("Finished n="+n+" at "+new Date());
		}
		mg.display();
	}
	
	/*
	 * Calculate Qn over 0-1 around phase0. Note H0=0, H1=H2=1 so interesting behaviour starts with n=3
	 * Qn(theta,y)=Sum(0,Fn-1)Prod(0,r)[2sin(2pi.(theta+y.(-w)^n+rw))]
	 */
	public ScalarSequence calcYbyClosure(ScalarSequence x) {
		final ScalarSequence y = new ScalarSequence(x.getSize());
//	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
//	        dataset.addSeries(series1);
		final long Fn=fibs[functionIndex];
		final double TWOPIPHASE0=TWOPI*initialPhaseValue;
		final int points=x.getSize();  // Interval 0-500 has 501 points
		final double xscale=Math.pow((-w),functionIndex);
		double last=0;
		for (int n=0; n<points; n++) { // calc each value of y
			double arg=TWOPIPHASE0+TWOPI*x.get(n)*xscale;
//				double arg=Math.PI*initialPhaseValue+Math.PI*x.get(n)*xscale;
//				double arg=Math.PI*w+Math.PI*x.get(n)*xscale;
//				double arg=0;
			double Qn=1;
			for (long s=0;s<Fn;s++) { 
				Qn*=2*Math.sin(TWOPIw*s +arg); 
//					Qn*=2*Math.sin(PIw*s +arg); 
			}
			if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
			last=Qn;
			y.add(Qn);
//				y.add(Math.abs(Qn));
//				y.add(Qn*Qn);
		}
		p("Min="+y.getMin()+" at "+y.getMinAt());
		return y;
	}
	public ScalarSequence calcYwithMultiplier(final long index) {
		final double initArg=circleMultiplier*initialCondition;
		final double inc=circleMultiplier*rotationNumber;
		final double multipliedPeriod=circleMultiplier*circlePeriod;
		final double xscale=Math.pow((-w),index)*circleMultiplier;
		final int points=xValues.getSize();  // Interval 0-500 has 501 points
		final ScalarSequence y = new ScalarSequence(points);
		final long Fn=Fibs.get(index);
		
		for (int n=0; n<points; n++) { // calc each value of y
			double arg=initArg+xValues.get(n)*xscale;
			double prod=1;
			for (long s=0;s<Fn;s++) { 
				prod*=functionToBeIterated.applyAsDouble(arg);
				arg+=inc; if (arg>=multipliedPeriod) {arg-=multipliedPeriod;}
			}
//			if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
			y.add(finalTransform.applyAsDouble(prod));
		}
//		p("Min="+y.getMin()+" at "+y.getMinAt());
		return y;
	}
	public ScalarSequence calcY(final long index) {
		final double initArg=initialCondition;
		final double inc=rotationNumber;
		final double xscale=Math.pow((-w),index);
		final int points=xValues.getSize();  // Interval 0-500 has 501 points
		final ScalarSequence y = new ScalarSequence(points);
		final long Fn=Fibs.get(index);
		
		for (int n=0; n<points; n++) { // calc each value of y
			double arg=initArg+xValues.get(n)*xscale;
			double prod=1;
			for (long s=0;s<Fn;s++) { 
				prod*=functionToBeIterated.applyAsDouble(arg);
				arg+=inc; if (arg>=1) {arg--;}
			}
//			if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
			y.add(finalTransform.applyAsDouble(prod));
		}
//		p("Min="+y.getMin()+" at "+y.getMinAt());
		return y;
	}
		public ScalarSequence calcY(ScalarSequence x) {
			final ScalarSequence y = new ScalarSequence(x.getSize());
//	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
//	        dataset.addSeries(series1);
	        final long Fn=fibs[functionIndex];
			final double TWOPIPHASE0=TWOPI*initialPhaseValue;
			final int points=x.getSize();  // Interval 0-500 has 501 points
			final double xscale=Math.pow((-w),functionIndex);
			double last=0;
			for (int n=0; n<points; n++) { // calc each value of y
				double arg=TWOPIPHASE0+TWOPI*x.get(n)*xscale;
//				double arg=Math.PI*initialPhaseValue+Math.PI*x.get(n)*xscale;
//				double arg=Math.PI*w+Math.PI*x.get(n)*xscale;
//				double arg=0;
				double Qn=1;
				for (long s=0;s<Fn;s++) { 
					Qn*=2*Math.sin(TWOPIw*s +arg); 
//					Qn*=2*Math.sin(PIw*s +arg); 
				}
				if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
				last=Qn;
				y.add(Qn);
//				y.add(Math.abs(Qn));
//				y.add(Qn*Qn);
			}
			p("Min="+y.getMin()+" at "+y.getMinAt());
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

		/* (non-Javadoc)
		 * @see engines.DataEngine#getData()
		 */
		@Override
		public ScalarSequence[] getData() {
			if (!calcPerformed) {calc();calcPerformed=true;}
			return data;
		}
		@Override
		public ScalarSequence getXValues() {
			return xValues;
		}
		public Qn setX(ScalarSequence xValues) {this.xValues=xValues;return this;}
		public Qn setIterations(long[] iterations) {
			this.iterations = iterations;
			return this;
		}

		public double getRotationNumber() {
			return rotationNumber;
		}

		public Qn setRotationNumber(double rotationNumber) {
			this.rotationNumber = rotationNumber;
			return this;
		}

		public double getCircleMultiplier() {
			return circleMultiplier;
		}

		public Qn setCircleMultiplier(double circleMultiplier) {
			this.circleMultiplier = circleMultiplier;
			return this;
		}


		public DoubleUnaryOperator getFunctionToBeIterated() {
			return functionToBeIterated;
		}

		public Qn setFunctionToBeIterated(DoubleUnaryOperator functionToBeIterated) {
			this.functionToBeIterated = functionToBeIterated;
			return this;
		}

		public DoubleUnaryOperator getFinalTransform() {
			return finalTransform;
		}

		public Qn setFinalTransform(DoubleUnaryOperator finalTransform) {
			this.finalTransform = finalTransform;
			return this;
		}	

	// Utility method for quick printing to console
	void p(Object o) {
		if (trace) {utils.log(o);}
	}

	public double getCirclePeriod() {
		return circlePeriod;
	}

	public Qn setCirclePeriod(double circlePeriod) {
		this.circlePeriod = circlePeriod;
		return this;
	}

}
