/**
 * Calc Gamma(p,q,alpha) 
 */

import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class CalcHGrowth {
	final Utils utils = Utils.getSingleton();
	final double w=0.5*(Math.sqrt(5)-1);
//	final double w=0.5*Math.sqrt(2);
	// Method called to run the class
	public void run() {
		p("Starting run of CalcHGrowth at " + new Date());
//		calc(w,w,3100);
//		calcFn(w,w,30);
//		calcFnRange(w,30);
//		for (int n=30;n<36;n++) {
//			p("n="+n);
//			graphH(500,n);
//		}
		graphH(1000,50000); // show H(arg2)(x) for x in [0,1] in square of arg1 pixels
//		calcPeaks(250, 54);
//		calcProdGrowth(1003,0.3);
		p("Finished run of CalcHGrowth at " + new Date());
	}
	
	/*
	 * Product of e{iw} (i=1..it)
	 */
	public void calc(final double base, final double w, final int iterations) {
		double theta=base%1;double prod=1;char sign;
		for (int n=0;n<=iterations;n++) {
			prod*=Math.E*theta;
			if (theta>1/Math.E) {sign='+';} else {sign='-';}
			p(n+","+theta+","+sign+","+prod);
			theta+=w; if (theta>=1) {theta-=1;}
		}
		
		
	}
	
	public double calcFn(final double base, final double w, final int iterations) {
		double theta=base%1;
		int qn=1,qn1=0,temp;
		double prod=1,lastprod=1,lastprod1=1;
		for (int n=1;n<=iterations;n++) {
			temp=qn;qn+=qn1;qn1=temp;
			lastprod1=lastprod;lastprod=prod;
			for (int m=0;m<qn1;m++) {
				prod*=Math.E*theta;
				theta+=w; if (theta>=1) {theta-=1;}
			}
//			if (theta>1/Math.E) {sign='+';} else {sign='-';}
			if (n%1==0) {
			p(n+","+qn+","+prod+","+(prod/lastprod1)+","+prod/Math.sqrt(qn));
			}
			
		}
		return (prod/lastprod1);
		
		
	}
	
	public void calcFnRange(final double w, final int iterations) {
		for (int n=0;n<100;n++) {
			p(n+": "+calcFn((n/100.0),w,iterations));
		}
	}
	
	/**
	 * Graph of size npoints pixel square, of Hn(x) over x=[0,1]  
	 * @param npoints
	 * @param hn
	 */
	public void graphH(final int npoints,final int hn) {
		ScalarSequence vals=new ScalarSequence(npoints+1);
		double theta;
		for (int i=0;i<=npoints;i++) {
			theta=(i+0.)/npoints;
			vals.add(H(hn,theta));
		}
//		vals.dump();
		vals.dumpRange();
		Graph g = new Graph(npoints);
		g.add(vals);
		g.setXScale(-npoints/10,npoints);
		g.setTicks(0.1,10000000);
		g.display();
		
	}
	
	public void calcPeaks(final int npoints,final int hn) {
		ScalarSequence thetas=new ScalarSequence(hn+1);
		double theta=0;
		thetas.add(theta);
		for (int i=0;i<hn;i++) {
			theta-=w; if (theta<0) {theta+=1;}
			thetas.add(theta);
		}
		thetas.dump();
		ScalarSequence prods=new ScalarSequence(hn+1);
		double prod=0;
		prods.add(prod);
		
		for (int j=1;j<=hn;j++) {
			theta=thetas.get(j);
			prod=theta*Math.exp(2);
		for (int i=1;i<=hn;i++) {
			theta+=w; if (theta>1) {theta-=1;}
			if (i==j) {continue;}
			prod*=theta*Math.E;
		}
		prods.add(prod);
		}
		prods.dump();
		Graph g = new Graph(npoints);
		g.add(prods);
		g.display();
		
	}
	
	
	
	/*
	 * calc nth iterate of base
	 */
	public double H(final int n, final double base) {
		double theta=base;double prod=base*Math.E;
		for (int i=0;i<n;i++) {
			theta+=w; if (theta>=1) {theta-=1;}
			prod*=theta*Math.E;
		}
		return prod;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public void calcProdGrowth(final int q, final double alpha) {
		for (int p=1;p<q;p++) {
		double pn=0;double tot=1;
		for (int r=1;r<q;r++) {
			pn+=p; if (pn>q) {pn-=q;}
			tot*=(1+(r*alpha)/(q*pn));
//			p(r+":"+tot+","+pn);
		}
		p("p:"+p+",tot="+tot);
		}
	}

}
