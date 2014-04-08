/**
 * 
 */

import java.util.Date;

import org.pv.core.StopWatch;
import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class ChebFun implements Function {
	final static Utils utils = Utils.getSingleton();
	Function f;
	double[] fValues;
	double[] chebZeroes;
	// Method called to run the class
	public void run() {
		p("Starting run of ChebFun at " + new Date());
//		benchmark(10);
		calcPoly(5);
		double nPoints=10;
		for (int n=0;n<=nPoints;n++) {
			p(evalAt(n/nPoints));
		}
		p("Finished run of ChebFun at " + new Date());
	}
	
	public ChebFun() {f=new Identity();}
	public ChebFun(Function f) {
		this.f=f;
	}
	public void calcPoly(int chebDepth) {
		ChebTree chebTree = new ChebTree();
		chebZeroes=chebTree.getZeroes(chebDepth);
		final int chebLen=chebZeroes.length;
		fValues=new double[chebLen];
		for (int i = 0; i < chebLen; i++) {
			fValues[i]=f.evalAt(chebZeroes[i]);
		}
//		utils.dump(fValues);
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

	/* (non-Javadoc)
	 * @see interpol.Function#evalAt(double)
	 */
	@Override
	public double evalAt(double x) {
		final int chebLen=chebZeroes.length;
		double[] weights=new double[chebLen];
		int sign=1;
		for (int i = 0; i < chebLen; i++) {
			if (x==chebZeroes[i]) {return fValues[i];}
			weights[i]=sign/(x-chebZeroes[i]);
			sign*=-1;
		}
		weights[0]/=2;
		weights[chebLen-1]/=2;
		double num=0,denom=0;
		for (int i = 0; i < chebLen; i++) {
			
			num+=weights[i]*fValues[i];
			denom+=weights[i];
		}
//		p(""+num+":"+denom);
		return num/denom;
	}
	
	public void benchmark(int chebDepth) {
		final int nPoints = (int)Math.pow(2,chebDepth);
		double[] zeroes=new double[nPoints+1];
		StopWatch timer = utils.getTimer();
//		timer.start();
		for (int n=0;n<=nPoints;n++) {
			zeroes[n]=Math.cos((n*Math.PI)/nPoints);
		}
		p("Elapsed:"+timer.getElapsed());
	}

	public class Identity implements Function {

		/* (non-Javadoc)
		 * @see interpol.Function#evalAt(double)
		 */
		@Override
		public double evalAt(double x) {
			return x*x;
		}
		
	}
}

