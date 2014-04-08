/**
 * 
 */

import java.util.Date;

import org.eclipse.swt.SWT;
import org.pv.core.Utils;



/**
 * @author PV
 * Graph fixed point through nGraphs iterations - choose which via fp0 switch
 *	
 */
public class CalcFixedPoint {
	final Utils utils = Utils.getSingleton();
//	final  boolean fp0=false; // true for logz seed, false for log z-1 seed
	boolean fp0=true; // true for logz seed, false for log z-1 seed
	static final double omega=0.5*(Math.sqrt(5)-1);
//	final long[] fib={0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169};
//	final double twoPi=2*Math.PI;
//	final double twoPiw=2*Math.PI*w;
	final double w=omega;
	final double wSquared=w*w;
//	double denom;  // select w or -w to vary fixed point
	public CalcFixedPoint() {
//		if (fp0) {denom=-w;} else {denom=w;};
	}
	
	// Method called to run the class
	public void run() {
		p("Starting run of CalFixedPoint at " + new Date());
		int iterations=10;
		int nPoints=1000;
		ScalarSequence x = new XSequence(-21,21,nPoints);
		MultiGraph mg = new MultiGraph(1);
		mg.setAxes(true);
		fp0=true;
//		p("Fits calc");
		ScalarSequence y = calc(x,iterations);
		Graph g=new Graph(nPoints,250);
		g.setYScale(-3.5,3.5);
		g.setDefaultLineWidth(3);
		g.setTicks(5,2,1,1);  // x,y
		g.add(x,y,SWT.COLOR_BLUE);
		g.addText(50,200,"exp(f1)",SWT.COLOR_BLUE);
//			g.addText(50,200,"max="+y.getMax());
//			g.addText(50,214,"min="+y.getMin());
//			g.display();
//			g.save("C:/OUGraphs/Test.png");
//		mg.add(g);
		fp0=false;
//		p("2nd calc");
			y = calc(x,iterations);
//			g=new Graph(nPoints,250);
			g.add(x,y,SWT.COLOR_RED);
			g.addText(50,215,"exp(f2)",SWT.COLOR_RED);
//			g.addText(50,200,"max="+y.getMax());
//			g.addText(50,214,"min="+y.getMin());
//			g.display();
//			g.save("C:/OUGraphs/Test.png");
			mg.add(g);
		mg.display();
		
//		p(calcHRatio(1,1,new h1()));
//		p(""+theta);
//		calcHRatioSeq(15,0,new hc(Math.E)).dumpRange();
//		p(calcH(1,1,new hc(Math.E)));
		p("Finished run of CalFixedPoint at " + new Date());
	}
	
	public ScalarSequence calc(ScalarSequence xSeq,int degree) {
		final ScalarSequence y = new ScalarSequence(xSeq.getSize());
		double term;
		final double denom;
		if (fp0) {denom=-w;} else {denom=w;};
		for (int point=0; point<xSeq.getSize(); point++) { // calc each value of y
			double in=xSeq.get(point);
//			double out=in/-w;
			int len=1;
			double[] termsx,termsw,prevx,prevw;
			termsx=new double[]{in};
			termsw=new double[]{denom};
			
			if (fp0) {term=(in/denom);} else {term=(1-in)/(wSquared);}
			term*=((1+w*in)/(1+w*denom));
			for (int n=0;n<degree;n++) {
				len=2*len;
				prevx=termsx;prevw=termsw;
				termsx=new double[len];termsw=new double[len];
				for (int m=0;m<len/2;m++) {
					termsx[2*m]=f1(prevx[m]);
					termsx[2*m+1]=f2(prevx[m]);
					termsw[2*m]=f1(prevw[m]);
					termsw[2*m+1]=f2(prevw[m]);
					term*=(1+w*termsx[2*m])/(1+w*termsw[2*m])*(1+w*termsx[2*m+1])/(1+w*termsw[2*m+1]);
				}
			}
			y.add(term);
//			p(""+in+"->"+term);
		}
		return y;
	}
	
	double f1(double x){
		return -w*x;
	}
	double f2(double x){
		return w*w*x+w;
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
