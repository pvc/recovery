/**
 * 
 */

import graphs.Graph;
import graphs.MultiGraph;
import graphs.ScalarSequence;
import graphs.XSequence;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.IntStream;

import math.Fibs;

import org.pv.core.Utils;

import ancillary.For;

/**
 * @author Paul
 *
 */
public class Tests {
	final static Utils utils = Utils.getSingleton();
	final static double TWOPI=2*Math.PI;
	final static double w=0.5*(Math.pow(5,0.5)-1);
	final static double TWOPIw=2*Math.PI*w;
	final static double PIw=Math.PI*w;
	final static DoubleUnaryOperator mod=(x)->Math.abs(x);
	final static DoubleUnaryOperator logMod=(x)->Math.log(Math.abs(x));
	final static DoubleUnaryOperator sin=x->2*Math.sin(x);
	final static DoubleUnaryOperator sin1=x->{double y=2*Math.sin(x);if (y==0) {return 1;} else {return y;}};
	final static DoubleUnaryOperator cos=x->2*Math.cos(x);
	final static DoubleUnaryOperator sinPi=x->2*Math.sin(Math.PI*x);
	final static DoubleUnaryOperator cosPi=x->2*Math.cos(Math.PI*x);
	final static DoubleUnaryOperator sin2Pi=x->2*Math.sin(2*Math.PI*x);
	final static DoubleUnaryOperator cos2Pi=x->2*Math.cos(2*Math.PI*x);
	final static DoubleUnaryOperator square=x->x*x;

	// Method called to run the classlt
	public void run() {
		p("Starting run of Tests at " + new Date());
		testP3();
		p("Finished run of Tests at " + new Date());
	}
	
	/*
	 * Plot Pn over a complete interval [0,1] for various numbers of iterations
	 */
	public void testP1() {
		long[] iterations = new long[]{0,8,13,21};
		Pn f = new Pn().setFunctionToBeIterated(sin).setX(new XSequence(0,1,100))
				.setIterations(iterations);
		MultiGraph mg=new MultiGraph().setDataEngine(f);
		mg.generateGraphs(); // could stream this array if didn't need value of n, but we do
		For.intOf(iterations.length).forEach(n->{
			mg.getGraph(n).addText(20, 15, "Graph "+(n+3));
		});
		mg.display();
	}
	/*
	 * Plot Pn for a given init condition against n
	 *  	 
	 */
	public void testP2() {
		double initCondition=0;
		int start=4;int end=27;
//		long[] iterations = new long[]{0,8,13,21};
//		long[] iterations = LongStream.rangeClosed(0, 20).toArray();
//		long[] iterations = LongStream.rangeClosed(0, Fibs.get(14)).toArray();
		long[] iterations = Arrays.stream(Fibs.range(start,end)).map(m->m).toArray();
		Pn f = new Pn().setInitialCondition(initCondition).setFunctionToBeIterated(sin1).setMode(Pn.GraphOfn)
				.setIterations(iterations).setArgMultiplier(Math.PI).setCirclePeriod(1).setX(new XSequence(start,end))
				.setFinalTransform(mod) // show absolute values 
				;
		ScalarSequence data = f.getData()[0];
		IntStream.rangeClosed(start, end).forEach(n->p(""+n+": "+data.get(n-start)+";"+(data.get(n-start)/Fibs.get(n))));
//		MultiGraph mg=new MultiGraph(1).setGraphSize(400).setDataEngine(f);
//		mg.generateGraphs(); // could stream this array if didn't need value of n, but we do
//		mg.display();
	}
	
	/*
	 * Plot Pn for a given init condition against n
	 *  	 
	 */
	public void testP3() {
		double initCondition=0;
//		long[] iterations = new long[]{0,8,13,21};
		long[] iterations = LongStream.rangeClosed(0, 20).toArray();
//		long[] iterations = LongStream.rangeClosed(0, Fibs.get(14)).toArray();
//		long[] iterations = Fibs.range(4,15);
		Pn f = new Pn().setInitialCondition(initCondition).setFunctionToBeIterated(sin).setMode(Pn.GraphOfn)
				.setIterations(iterations);
		MultiGraph mg=new MultiGraph(1).setGraphSize(400).setDataEngine(f);
		mg.generateGraphs(); // could stream this array if didn't need value of n, but we do
		mg.display();
	}
	
	/*
	 * Plot Pn over a complete interval [0,1] for various numbers of iterations
	 */
	public void testQ1() {
		DecimalFormat df = new DecimalFormat("0.00");
		long[] iterations = LongStream.rangeClosed(16,21).toArray();
		Qn f = new Qn().setFunctionToBeIterated(sin).setArgMultiplier(2*Math.PI).setCirclePeriod(1)
				.setX(new XSequence(-10,10,300))
				.setIterations(iterations);
		MultiGraph mg=new MultiGraph().setDataEngine(f);
		mg.generateGraphs(); // could stream this array if didn't need value of n, but we do
		For.intOf(iterations.length).forEach(n->{
			Graph g = mg.getGraph(n).addText(20, 15, "Fib="+iterations[n]);
			g.addText(20, 25, "Norm="+df.format(g.getY(0).getNorm()));
		});
		mg.display();
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
