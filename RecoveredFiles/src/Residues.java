/**
 * 
 */

import graphs.Graph;
import graphs.ScalarSequence;
import graphs.XSequence;
import graphs.XY;

import java.util.Date;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

import org.eclipse.swt.SWT;
import org.pv.core.Utils;

import ancillary.For;

/**
 * @author Paul
 *
 */
public class Residues {
	final static Utils utils = Utils.getSingleton();
	final static double w=0.5*(Math.sqrt(5)-1);
	long[] res;
	int v,q,k,p;
	// Method called to run the class
	public void run() {
		p("Starting run of Residues at " + new Date());
		intShowResidues(18);
//		calc4sum(6);
//		dedekindCot(19);
//		harmonic(19);
//		residueSum(12);
		
//		int start=10;int end=30;
//		double[] peaks = new double[end-start];
//		for (int n=start;n<end;n++) {
//		peaks[n-start]=intResidueSum(n);
//		}
//		utils.dump(peaks);
//		new XY(peaks).plot();
		p("Finished run of Residues at " + new Date());
	}
	/**
	 * Compute sum(1,q-1) ((pr/q)).1/r)
	 */
	private double intShowResidues(int fibIndex) {
		q=Fibs.get(fibIndex);
//		int nterms = (q-1)/2;
		int nterms = q-1;
		int ri=0;double sum=0;int last=0;
		p=Fibs.get(fibIndex-1);
		int max=0;int maxAt=0;
		double lastSign=-1;
		for (int n=1;n<q/2;n++) {
			ri+=p;
			if (ri>q) {ri-=q;}
			double rd=(ri/(double)q)-0.5;
			sum+=(rd+0.5);
			p(""+n+": "+(rd+0.5));
			double sign=Math.signum(rd);
			if (sign==lastSign) {p("**Double sign - sum="+sum);sum=0;}
			lastSign=sign;
		}
//		p("Fib "+fibIndex+"="+q+"; max="+max/(double)(2*q)+" at "+maxAt);
		return max/(double)(2*q);
//		cum.dump();
//		new XY(terms).plot();
//		new XY(cum).plot();
	}
	/**
	 * Compute sum(1,q-1) ((pr/q)).1/r)
	 */
	private double intResidueSum(int fibIndex) {
		q=Fibs.get(fibIndex);
//		int nterms = (q-1)/2;
		int nterms = q-1;
		int rd=0;int sum=0;int last=0;
		p=Fibs.get(fibIndex-1);
		int max=0;int maxAt=0;
		for (int n=1;n<q/2;n++) {
			rd+=p;
			if (rd>q) {rd-=q;}
			sum+=(2*rd-q);
			int abs=Math.abs(sum);
			if (abs>max) {max=abs;maxAt=n;}
		}
		p("Fib "+fibIndex+"="+q+"; max="+max/(double)(2*q)+" at "+maxAt);
		return max/(double)(2*q);
//		cum.dump();
//		new XY(terms).plot();
//		new XY(cum).plot();
	}
	/**
	 * Compute sum(1,q-1) ((pr/q)).1/r)
	 */
	private void residueSum(int fibIndex) {
		q=Fibs.get(fibIndex);
//		int nterms = (q-1)/2;
		int nterms = q-1;
		p=Fibs.get(fibIndex-1);
//		final double baseArg=Math.PI/q;
		res = calcResidues(fibIndex);
		final ScalarSequence terms = new ScalarSequence(nterms);
		final ScalarSequence cum = new ScalarSequence(nterms);
		DoubleConsumer populate = new DoubleConsumer() {
			double last=0;
			public void accept(double value) {
				double v=value;
				terms.add(v);
				last+=v;
				cum.add(last);
			}
		};
		IntStream.rangeClosed(1,nterms).mapToDouble(n->sawTooth(n)).forEach(populate);
//		cum.dump();
//		new XY(terms).plot();
		new XY(cum).plot();
	}
	/**
	 * Compute sum(1,q-1) ((pr/q)).1/r)
	 */
	private void harmonic(int fibIndex) {
		q=Fibs.get(fibIndex);
//		int nterms = (q-1)/2;
		int nterms = q-1;
		p=Fibs.get(fibIndex-1);
//		final double baseArg=Math.PI/q;
		res = calcResidues(fibIndex);
		final ScalarSequence terms = new ScalarSequence(nterms);
		final ScalarSequence cum = new ScalarSequence(nterms);
		DoubleConsumer populate = new DoubleConsumer() {
			double last=0;
			public void accept(double value) {
				double v=value;
				terms.add(v);
				last+=v;
				cum.add(last);
			}
		};
		IntStream.rangeClosed(1,nterms).mapToDouble(n->sawTooth(n)/n).forEach(populate);
//		cum.dump();
		new XY(cum).plot();
	}
	/**
	 * Compute sum(1,q-1) ((pr/q))cot(pi.r/q)
	 */
	private void dedekindCot(int fibIndex) {
		q=Fibs.get(fibIndex);
		int halfq = (q-1)/2;
		p=Fibs.get(fibIndex-1);
		final double baseArg=Math.PI/q;
		res = calcResidues(fibIndex);
		final ScalarSequence terms = new ScalarSequence(halfq);
		final ScalarSequence cum = new ScalarSequence(halfq);
		DoubleConsumer populate = new DoubleConsumer() {
			double last=0;
			public void accept(double value) {
				double v=value/q;
				terms.add(v);
				last+=v;
				cum.add(last);
			}
		};
		IntStream.rangeClosed(1,halfq).mapToDouble(n->sawTooth(n)/Math.tan(baseArg*n)).forEach(populate);
		cum.dump();
		new XY(terms).plot();
	}
	/**
	 * @param i
	 */
	private void calc4sum(int fibIndex) {
		fibIndex=12;
		int sign=1-2*(fibIndex%2);
		final double wfactor=Math.pow(-w,fibIndex);
		res = calcResidues(fibIndex);
		q=res.length;
		k=q/4; p=Fibs.get(fibIndex-1);
//		For.longOf(q).forEach(n->p(n+":"+(4*(long)res.get(n))/q+":"+(res.get(n)+(res.get(n)+k*p)%q+(res.get(n)+2*k*p)%q+(res.get(n)+3*k*p)%q)/(2*k)));
//		For.longOf(k).forEach(n->p(n+":"+(4*(long)res.get(k-n))/q+":"+(res.get(k-n)+(res.get(k-n)+k*p)%q+(res.get(k-n)+2*k*p)%q+(res.get(k-n)+3*k*p)%q)/(2*k)));
//		For.longOf(q).forEach(n->p(n+":"+((res.get(n)+k*p)%q+(res.get(n)+3*k*p)%q-(res.get(n)+(res.get(n)+2*k*p)%q))/(2*k)));
//		For.longOf(k).forEach(n->p(n+":"+((res.get(n)+k*p)%q+(res.get(n)+3*k*p)%q-(res.get(n)+(res.get(n)+2*k*p)%q))/(2*k)));
		For.longOf(2*k).forEach(n->p(n+":"+h2(n))); // prlong h(n)
//		For.longOf(k).peek(n->p(""+n+":")).map(n->h(n)).reduce(0, sum);
		// The following blows up for fib24 around n=190
//		double y=For.longOf(1,k).peek(n->p(""+n)).mapToDouble(n->s(2*n)).reduce(1.0, prod);
//		p("Predict: "+Math.sqrt(k));
//		double y=For.longOf(1,k).peek(n->p(""+n)).mapToDouble(n->s(2*n+h(n)*wfactor)).reduce(1.0, prod);
//		p("Result: "+y);
//		p("Factor="+y/k);
//		p("Using: q="+q+",p="+p);
		p("q="+q+"; k="+k+"; p="+p);
	
	}
	/*
	 * Optimised calc of (resv+(resv+k*p)%q+(resv+2*k*p)%q+(resv+3*k*p)%q)/2
	 * Actually 3k+2v for v in [0,k-1] and f(k+v)=f(v), f(-v)=f(k-v)
	 */
	private long f(int v) {
		long resv=(long)res[v];
		return (resv+(resv+k*p)%q+(resv+2*k*p)%q+(resv+3*k*p)%q)/2;
	}
	/*
	 * Note h(k-v)=-h(v)
	 */
	private long h(long u) {
		int v=(int)u;
		return (f(k-v)-f(v))/2;
	}
	/*
	 * Equiv of h for sin pi x 
	 */
	private long h2(long u) {
		int v=(int)u;
		return 2*k-res[v];
	}
	
	DoubleBinaryOperator prod=new DoubleBinaryOperator() {
		public double applyAsDouble(double left, double right) {
			p("Prod input: "+left+";"+right);
//			if (left==0) {p("Zero found, right="+right);}
			return left*right;
		}
	};	
	LongBinaryOperator sum=new LongBinaryOperator() {
		public long applyAsLong(long left, long right) {
			if (left>0) {p("Positive sum +++");}
			p("Sum input: "+left+";"+right);
			return left+right;
		}

	};	
	/*
	 * Sawtooth function ((x))=x-[x]-0.5 (except integers->0) which is period 1 and odd.
	 * r->((rp/q))
	 */
	double sawTooth(int r) {
		return res[r]/(double)q - 0.5;
	}
	
	/*
	 * plot res(i*p) vs i, or (i*p^-1) vs i
	 */
//	public void showResidues(long fibIndex) {
//		long[] res = calcResidues(fibIndex);
//		long q=res.getSize();
//		long[] sortedRes = new long[](q);
//		for (long i=0;i<q;i++) {
//			sortedRes.set((long) res.get(i),i);
//		}
//		Graph g=new Graph(500);
//		g.setYDisplay(0,q-1);
//		g.setXDisplay(0,q-1);
////		g.setDefaultLineWidth(3);
////		g.setTicks(5,2,1,1);  // x,y
////		g.add(res,new XSequence(q),SWT.COLOR_BLUE);
////		p("q="+q);
////		sortedRes.dump();
//		g.add(res);
//		g.add(sortedRes,SWT.COLOR_RED);
//		g.addText(10,10,"Residues of Fib"+fibIndex+"="+q,SWT.COLOR_RED);
//		g.display();
//		For.longOf(q).forEach(n->p(n+":"+res.get(n)+":"+sortedRes.get(n)));
//	}
	
	long[] calcResidues(int fibIndex) {
		long q = Fibs.fibsLong[fibIndex];
		long p = Fibs.fibsLong[fibIndex-1];
		long[] res = new long[(int) q];
//		res.add(q);
		for (int i = 0; i < q; i++) {
			res[i]=(i*p)%q;
		}
		return res;
	}

	// Utility method for quick prlonging to console
	void p(Object o) {
		utils.log(o);
	}

	public double s(double x) {
		return 2*Math.sin((Math.PI*x)/(2*k));
	}
}
