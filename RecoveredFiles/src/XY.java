/**
 * 
 */

import graphs.ScalarSequence;

import java.util.Date;
import java.util.function.LongToDoubleFunction;

/**
 * @author Paul
 *
 */
public class XY {
	final static Utils utils = Utils.getSingleton();
	ScalarSequence x,y;
	public XY(ScalarSequence x,ScalarSequence y) {
		this.x=x;this.y=y;
	}
	public XY(ScalarSequence y) {
		this.y=y;
		x=new XSequence(y);
	}
	public XY(ScalarSequence x, LongToDoubleFunction f) {
		this.x=x;
		y=new ScalarSequence(x.streamLong().mapToDouble(f).toArray());
	}
	
	/**
	 * @param peaks
	 */
	public XY(double[] peaks) {
		y=new ScalarSequence(peaks);
		x=new XSequence(y);
	}
	// Method called to run the class
	public void run() {
		p("Starting run of XY at " + new Date());
		p("Finished run of XY at " + new Date());
	}
	
	public MultiGraph createGraph() {
		MultiGraph mg = new MultiGraph(1);
		Graph g = new Graph();
		g.add(x,y);
		return mg.add(g);
	}
	public MultiGraph plot() {
		return createGraph().display();
	}
	
	public void dump() {
		for (int n=0;n<x.getSize();n++) {
			p("n="+n+": x="+x.get(n)+"; y="+y.get(n));
		}
		p("x "+x);p("y "+y);
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

	public ScalarSequence getX() {
		return x;
	}

	public void setX(ScalarSequence x) {
		this.x = x;
	}

	public ScalarSequence getY() {
		return y;
	}

	public void setY(ScalarSequence y) {
		this.y = y;
	}

}
