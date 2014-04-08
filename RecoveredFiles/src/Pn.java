/**
 * 
 */

import graphs.ScalarSequence;

import java.util.Arrays;
import java.util.Date;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntToDoubleFunction;
import java.util.function.LongToDoubleFunction;

import org.pv.core.Utils;


/**
 * @author Paul
 * Calculates P(n,theta)=Prod from r=0 to n-1 of f=2sin(2pi(theta+rw))
 * P(n,xvec) - returns yvec of P applied to each theta in xvec
 * P(n) - theta=w
 * P(vec1,vec2) - returns a P(n,vec2) for each n in vec1.
 *
 */
public class Pn implements DataEngine{
	final Utils utils = Utils.getSingleton();
//	interface Calc {double calc(double x);}
	static final long[] fibs={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
	final static double TWOPI=2*Math.PI;
	static final double w=0.5*(Math.pow(5,0.5)-1);
	final static double TWOPIw=2*Math.PI*w;
	final static double PIw=Math.PI*w;
//	private int functionIndex=3;
//	private double rotationNumber=w;
	private boolean trace=true;
	final int npoints=250;  // # points calculated on x-axis
	ScalarSequence x;
//	ScalarSequence x=new XSequence(0,0.5,npoints);
	ScalarSequence y;
	
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
	private boolean calcPerformed=false;
//	private int nDataRows;
	private int mode=GraphsOfx;
	public static final int GraphsOfx=0;
	public static final int GraphOfn=1;
	
	
	
	

	// Method called to run the class
	public void run() {
		p("Starting run of Pn at " + new Date());
//		DoubleUnaryOperator logMod=(x)->Math.log(Math.abs(x));
//		DoubleUnaryOperator sin=x->2*Math.sin(x);
//		circleMultiplier=2*Math.PI;
//		initialCondition=0;
//		rotationNumber=w;
//		functionToBeIterated=sin;
//		xValues=new XSequence(0,1,250); // interval [0,1] with 250 increments (sub-intervals)
//		int maxFibIndex=14;
//		iterations=Fibs.range(maxFibIndex);
//		calc();
//		MultiGraph mg = new MultiGraph(3).setGraphSize(150,150).setDataEngine(this);
//		mg.display();
//		displayGraphs(3);
		
		
//		test1();
//		showP();
//		showPeaks2();
//		p("Fibs="+fibs.length);
		p("Finished run of Pn at " + new Date());
	}
	
	/**
	 * @param i
	 * @param j
	 * @return 
	 */


	/**
	 * @param i
	 */
//	private void displayGraphs(int i) {
//		if (!calcPerformed) {calc();}
//		MultiGraph mg=new MultiGraph(i);
//		IntStream.range(0,data.length).forEach(n->{
//			Graph g = new Graph(graphSize);
//			g.add(xValues,data[n]);
////			g.addText(10, 10, "n="+(start+n));
//			mg.add(g);
//		});
//		mg.display();
//	}

//	public void showP() {
//		trace=true;
//		int maxFibIndex=14;
////		y=calcLogP(maxFibIndex);
//		y=calcLogPbyClosure(maxFibIndex);
//		Graph g = new Graph(600,600);
//		g.add(y);
//		g.setAxes(true);
//		g.setXDisplay(-20, fibs[maxFibIndex]+10);
//		g.setYDisplay(-0.5, y.getMax());
//		g.setTicks(50,2);
//		g.display();
//	}
//	
//	public void showPeaks() {
//		trace=true;
//		y=calcLogPeaks(25);
//		Graph g = new Graph(400,400);
//		g.add(y);
//		g.setAxes(true);
//		g.setXDisplay(-2, 24);
//		g.setYDisplay(-0.5, y.getMax());
//		g.setTicks(1,2);
//		g.display();
//	}
//	public void showGraphs() {
//		
//		trace=true;
//		int start=4,end=15;
//		int nGraphs=end-start+1;
//		Point graphSize=new Point(250,250);
//		ScalarSequence[] data = new ScalarSequence[nGraphs];
//		Arrays.setAll(data, (n)->new ScalarSequence(graphSize.x+1));
//		DoubleConsumer addToData = new DoubleConsumer() {
//			int index=0;
//			@Override
//			public void accept(double value) {
//				data[index].add(value);
//				index++;
//				if (index>=nGraphs) {index=0;}
//			}
////			public void reset() {index=0;}
//		};
////		ArrayList<Long> iterationses = fibSeq(0,20);
////		Calc bf = (x)->Math.log(Math.abs(x));
//		
//		XSequence xRange = new XSequence(-1,1,graphSize.x);
//		for (int n=0;n<xRange.getSize();n++) {
//		BurkillIterator fBurkill = new BurkillIterator(functionToBeIterated,circleMultiplier,rotationNumber,xRange.get(n),finalTransform);
//		IntStream.rangeClosed(start,end).map(x->Fibs.get(x)).mapToDouble(fBurkill).forEach(addToData);
//		}
//		//		y=calcSubSequence(iterations, f);
////		Arrays.stream(data).forEach(s->p(""+s.get(249)));
//		MultiGraph mg = new MultiGraph(3);
//		mg.setAxes(true);
//		IntStream.range(0,nGraphs).forEach(n->{
//			Graph g = new Graph(graphSize);
//			g.add(xRange,data[n]);
//			g.addText(10, 10, "n="+(start+n));
//			mg.add(g);
//		});
//		mg.display();
//		boolean stopHere=true;
//		if (stopHere) {return;}
//		
//		Graph g = new Graph(400,400);
//		x=new XSequence(0,20);
//		g.add(x,y);
////		g.setAxes(true);
//		g.setXDisplay(0,x.getMax());
////		g.setYDisplay(-0.5, y.getMax());
//		g.setTicks(1,0.2);
//		g.display();
//	}
	
//	public void calc(final long[] iterations,final XSequence xRange,final ScalarSequence[] data) {
	public void calc() {
		if (calcPerformed) {return;}
		if (mode==GraphsOfx) {calcGraphsOfx();} else {calcGraphOfn();}
		return;
	}
	public void calcGraphsOfx() {
//		p("Starting calc");
		data = new ScalarSequence[iterations.length];
		Arrays.setAll(data, (n)->new ScalarSequence(xValues.getSize()));
		final int nDataRows=data.length;
		DoubleConsumer addToData = new DoubleConsumer() {
			int index=0;
			@Override
			public void accept(double value) {
//				p("Adding "+value+" at "+index);
				data[index].add(value);
				index++;
				if (index>=nDataRows) {index=0;}
			}
		};
//		ArrayList<Long> iterations = fibSeq(0,20);
//		Calc bf = (x)->Math.log(Math.abs(x));
		
//		XSequence xRange = new XSequence(-1,1,graphSize.x);
		for (int n=0;n<xValues.getSize();n++) {
			BurkillIterator fBurkill = new BurkillIterator(functionToBeIterated,circleMultiplier,rotationNumber,xValues.get(n),finalTransform);
//		IntStream.rangeClosed(start,end).map(x->Fibs.get(x)).mapToDouble(fBurkill).forEach(addToData);
			Arrays.stream(iterations).mapToDouble(fBurkill).forEach(addToData);
		}
//		Arrays.stream(data).forEach(s->p(s.get(2)));
		calcPerformed=true;
	}
	
	public void calcGraphOfn() {
//		p("Starting calc");
		xValues=new ScalarSequence(iterations.length);
		Arrays.stream(iterations).forEach(n->xValues.add(n));
		data = new ScalarSequence[1];
		Arrays.setAll(data, (n)->new ScalarSequence(iterations.length));
//		final int nDataRows=data.length;
		y=data[0];
		DoubleConsumer addToData = d->y.add(d);
			
//		ArrayList<Long> iterations = fibSeq(0,20);
//		Calc bf = (x)->Math.log(Math.abs(x));
		
//		XSequence xRange = new XSequence(-1,1,graphSize.x);
//		for (int n=0;n<xValues.getSize();n++) {
		BurkillIterator fBurkill = new BurkillIterator(functionToBeIterated,circleMultiplier,rotationNumber,initialCondition,finalTransform);
//		IntStream.rangeClosed(start,end).map(x->Fibs.get(x)).mapToDouble(fBurkill).forEach(addToData);
		Arrays.stream(iterations).mapToDouble(fBurkill).forEach(addToData);
//		}
//		Arrays.stream(data).forEach(s->p(s.get(2)));
		calcPerformed=true;
	}
	

//	public void test1() {
//		MultiGraph mg = new MultiGraph(4);
//		mg.setAxes(true);
//		trace=true;
//		ScalarSequence product;
//		ScalarSequence[] cache=new ScalarSequence[3];
//		setInitialCondition(0);
//		
//		for (int n=4;n<28;n++) {
//			p("Starting n="+n+" at "+new Date());
//			setFunctionIndex(n);
//			y = calcY(x);
//			mg.add(graph(y,"n="+n));
//			
//			int rem=n%3;
//			cache[rem]=y;
//			if (rem==0) {
//				product=new ScalarSequence(npoints+1);
//				for (int i=0;i<=npoints;i++) {
//					product.add(cache[0].get(i)*cache[1].get(i)*cache[2].get(i));
//				}
//				mg.add(graph(product,"Product"));
//			}
//			
//			p("Finished n="+n+" at "+new Date());
//		}
//		mg.display();
//	}
	

		/**
	 * @param product
	 * @return
	 */
//	private Graph graph(ScalarSequence y,String text) {
//		double ymax = Math.max(Math.abs(y.getMax()),Math.abs(y.getMin()));
//		double yround = Math.floor(100*(ymax+0.005))/100;
//
//		Graph g = new Graph(npoints,200);
//		//		g.setTitle("Any old Title");
//		g.addText(10, 10, text);
//		g.addText(10, 24, "Max|.|="+yround);
//		g.setYDisplay(y.getMin(),y.getMax());
//		g.setXDisplay(0,0.5);
//		
//		double log = Math.log10(ymax/3);
//		double exp = Math.floor(log);
//		double mant = log-exp;
//		int base=1;
//		if (mant>Math.log10(5)) {base=5;} else if (mant>Math.log10(2)) {base=2;}
//		//		double yTick = Math.rint(10*base*Math.pow(10,exp))/10;
//		double yTick = base*Math.pow(10,exp);
//		//		y.dumpRange();
//		//		p("Max at:"+x.get(y.getMaxAt()));
//		//		g.setAxes(true);
//		//		g.setYDisplay(-ymax*0.15,ymax);
//		//		g.setTicks(0.5, Math.rint(base*Math.pow(10,exp)));
//		g.setTicks(0.1,yTick );
//		g.add(x,y);
//		return g;
//	}

		/*
	 * Calculate Hn over 0-1 around phase0. Note H0=0, H1=H2=1 so interesting behaviour starts with n=3
	 * Hn(theta,y)=Sum(0,Fn-1)Prod(0,r)[2sin(2pi.(theta+y.(-w)^n+rw))]
	 */
//	public ScalarSequence calcY(ScalarSequence x) {
//		final ScalarSequence y = new ScalarSequence(x.getSize());
////	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
////	        dataset.addSeries(series1);
//		final long Fn=fibs[functionIndex];
//		final double TWOPIPHASE0=TWOPI*initialCondition;
//		final int points=x.getSize();  // Interval 0-500 has 501 points
////			final double xscale=Math.pow((-w),functionIndex);
////			double last=0;
//		for (int n=0; n<points; n++) { // calc each value of y
//			final double arg=TWOPIPHASE0+TWOPI*x.get(n);
//			double Qn=1;
//			for (long s=0;s<Fn;s++) { //optimisation: actually r=1 to Fn-1 with (r-1)w below 
//				Qn*=2*Math.sin(TWOPIw*s +arg); 
//			}
////				if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
////				last=Qn;
//			y.add(Qn);
//		}
//		return y;
//	}
//	public ScalarSequence calcSubSequence(ArrayList<Long> indices, BurkillIterator f) {
//		final ScalarSequence y = new ScalarSequence(indices.size());
//		int n=0;
//		for (Iterator<Long> iterator = indices.iterator(); iterator.hasNext();) {
//			Long index = iterator.next();
//			double res=f.calc(index);
//			y.add(res);
//			p(n+": "+index+"->"+res);n++;
//		}
//		return y;
//	}
//	
//	public ScalarSequence calcLogPeaks(int maxFibIndex) {
//		final ScalarSequence y = new ScalarSequence(maxFibIndex+1);
//		y.add(0);
//		//	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
//		//	        dataset.addSeries(series1);
//		for (int fibIndex=1;fibIndex<=maxFibIndex;fibIndex++) {
//			final long Fn=fibs[fibIndex];
//			final double TWOPIPHASE0=TWOPI*initialCondition;
//			//			final int points=x.getSize();  // Interval 0-500 has 501 points
//			//			final double xscale=Math.pow((-w),functionIndex);
//			//			double last=0;
//			//			for (int n=0; n<points; n++) { // calc each value of y
//			final double arg=TWOPIPHASE0;
//			double Qn=1;
//			for (long s=1;s<Fn;s++) { //optimisation: actually r=1 to Fn-1 with (r-1)w below 
////				Qn*=2*Math.sin(TWOPIw*s +arg); 
//				Qn*=2*Math.sin(PIw*s +arg); 
//			}
//			y.add(Math.log(Math.abs(Qn)));
//			//				if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
//			//				last=Qn;
//		}
//		//			}
//		return y;
//	}
//	
//	public ScalarSequence calcLogP(int maxFibIndex) {
//		final ScalarSequence y = new ScalarSequence(1+(int)fibs[maxFibIndex]);
//		//	        final ScalarSequenceCollection dataset = new ScalarSequenceCollection();
//		//	        dataset.addSeries(series1);
////		for (int fibIndex=1;fibIndex<=maxFibIndex;fibIndex++) {
//		final long Fn=fibs[maxFibIndex];
//		final double TWOPIPHASE0=TWOPI*initialCondition;
//		//			final int points=x.getSize();  // Interval 0-500 has 501 points
//		//			final double xscale=Math.pow((-w),functionIndex);
//		//			double last=0;
//		//			for (int n=0; n<points; n++) { // calc each value of y
//		final double arg=TWOPIPHASE0;
//		double Qn=1;y.add(Qn);
//		for (long s=1;s<=Fn;s++) { //optimisation: actually r=1 to Fn-1 with (r-1)w below 
////				Qn*=2*Math.sin(TWOPIw*s +arg); 
//			Qn*=2*Math.sin(PIw*s +arg); 
//			y.add(Math.log(Math.abs(Qn)));
//		}
//		//				if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
//		//				last=Qn;
////		}
//		//			}
//		return y;
//	}
//	
//	public ScalarSequence calcLogPbyClosure(int maxFibIndex) {
//		final ScalarSequence y = new ScalarSequence(1+(int)fibs[maxFibIndex]);
//		final long Fn=fibs[maxFibIndex];
//		final double TWOPIPHASE0=TWOPI*initialCondition;
//		final double arg=TWOPIPHASE0;
//		double Qn=1;y.add(Qn);
//		class RunningProduct implements LongToDoubleFunction {
//			DoubleUnaryOperator base;
//			RunningProduct(DoubleUnaryOperator base){this.base=base;}
//			double prod=1;
//			@Override
//			public double applyAsDouble(long index) {
////				prod*=2*Math.sin(PIw*value +arg); 
//				prod*=base.applyAsDouble(PIw*index +arg);
//				return prod;
//			}
//		};
//		LongStream.rangeClosed(1,Fn).mapToDouble(new RunningProduct(d->2*Math.sin(d))).forEach(d->y.add(Math.log(Math.abs(d))));;
////		for (long s=1;s<=Fn;s++) { //optimisation: actually r=1 to Fn-1 with (r-1)w below 
////			//				Qn*=2*Math.sin(TWOPIw*s +arg); 
////			Qn*=2*Math.sin(PIw*s +arg); 
////			y.add(Math.log(Math.abs(Qn)));
////		}
//		//				if (Qn==0 || (Qn*last)<0) {p("Zero at x="+x.get(n));}
//		return y;
//	}

//	public int getFunctionIndex() {
//		return functionIndex;
//	}
//
//	public void setFunctionIndex(int functionIndex) {
//		this.functionIndex = functionIndex;
//	}
	public double getInitialCondition() {
		return initialCondition;
	}

	public Pn setInitialCondition(double initialCondition) {
		this.initialCondition = initialCondition;
		return this;
	}

//	public interface Function {
//		public double calc(double in);
//		public double calc(long in);
//	}

	/*
	 * Calculates Prod(r=1 to index) of 2*Math.sin(2Pi(ic+rw))
	 */
	public class BurkillIterator implements IntToDoubleFunction,LongToDoubleFunction {
		private final double circleMultiplier,inc;  //rotationNumber merged into inc 
		private double initialCondition;
		private long lastIndex;
		private double lastArg;
		private double lastProd;
		private DoubleUnaryOperator functionToBeIterated;
		private DoubleUnaryOperator finalTransform;
		
		public BurkillIterator(DoubleUnaryOperator functionToBeIterated, double circleMultiplier, double rotationNumber, double initialCondition, DoubleUnaryOperator finalTransform) {this.functionToBeIterated=functionToBeIterated;this.circleMultiplier=circleMultiplier;this.initialCondition=initialCondition;this.finalTransform=finalTransform;inc=circleMultiplier*rotationNumber;reset();}
//		public BurkillIterator(double initialCondition) {this.circleMultiplier=Math.PI;this.rotationNumber=w;this.initialCondition=initialCondition;reset();}
//		public BurkillIterator() {this.circleMultiplier=Math.PI;this.rotationNumber=w;this.initialCondition=0;reset();}
		public void reset() {
			lastArg=circleMultiplier*initialCondition;
			lastIndex=0;lastProd=1;
		}
//		public double calc(double in) {return in;}
		public double calc(long nextIndex) {
			if (nextIndex<=lastIndex) {return lastProd;}
//			final double base=circleMultiplier*initialCondition;
//			double nextAngle=0;
			for (long n=lastIndex+1;n<=nextIndex;n++) {
				lastArg+=inc; if (lastArg>=circleMultiplier) {lastArg-=circleMultiplier;}
				lastProd*=functionToBeIterated.applyAsDouble(lastArg);
			}
			lastIndex=nextIndex;
			return finalTransform.applyAsDouble(lastProd);
		}
		/* (non-Javadoc)
		 * @see java.util.function.DoubleUnaryOperator#applyAsDouble(double)
		 */
		
		public double applyAsDouble(long targetIndex) {
			return calc(targetIndex);
		}
		/* (non-Javadoc)
		 * @see java.util.function.IntToDoubleFunction#applyAsDouble(int)
		 */
		@Override
		public double applyAsDouble(int targetIndex) {
			return calc(targetIndex);
		}
		
	}
	// Not Used!
//	public class CircleMap {
//		final double inc;
//		final double initialCondition;
//		final double circleMultiplier;
//		double last;
//		
//		public CircleMap(double rotationNumber,double initialCondition,double circleMultiplier) {
//			this.initialCondition=initialCondition;
//			this.circleMultiplier=circleMultiplier;
//			this.inc=rotationNumber*circleMultiplier;
//			last=initialCondition-inc;
//		}
//		
//		public double next() {
//			last+=inc;
//			if (last>=circleMultiplier) {last-=circleMultiplier;}
//			return last;
//		}
//		
//	}


	// Utility method for quick printing to console
	void p(Object o) {
		if (trace) {utils.log(o);}
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
	public Pn setX(ScalarSequence xValues) {this.xValues=xValues;return this;}
	public Pn setIterations(long[] iterations) {
		this.iterations = iterations;
		return this;
	}

	public double getRotationNumber() {
		return rotationNumber;
	}

	public Pn setRotationNumber(double rotationNumber) {
		this.rotationNumber = rotationNumber;
		return this;
	}

	public double getCircleMultiplier() {
		return circleMultiplier;
	}

	public Pn setCircleMultiplier(double circleMultiplier) {
		this.circleMultiplier = circleMultiplier;
		return this;
	}

	public int getMode() {
		return mode;
	}

	public Pn setMode(int mode) {
		this.mode = mode;
		return this;
	}

	public DoubleUnaryOperator getFunctionToBeIterated() {
		return functionToBeIterated;
	}

	public Pn setFunctionToBeIterated(DoubleUnaryOperator functionToBeIterated) {
		this.functionToBeIterated = functionToBeIterated;
		return this;
	}

	public DoubleUnaryOperator getFinalTransform() {
		return finalTransform;
	}

	public Pn setFinalTransform(DoubleUnaryOperator finalTransform) {
		this.finalTransform = finalTransform;
		return this;
	}
}
