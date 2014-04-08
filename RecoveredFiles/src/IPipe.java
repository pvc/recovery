/**
 * 
 */

import graphs.ISequence;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class IPipe {
	final static Utils utils = Utils.getSingleton();
	ArrayList<F> pipe=new ArrayList<F>();

	// Method called to run the class
	public void run() {
		p("Starting run of FPipe at " + new Date());
		// TODO insert code here
		p("Finished run of FPipe at " + new Date());
	}
	
	public void add(F f) {
		pipe.add(f);
	}
	public ISequence x(ISequence in) {
		ISequence out=new ISequence(in.getSize());
		for (int i = 0; i < in.getSize(); i++) {
			out.add(x(in.get(i)));
			
		}
		return out;
	}

	/**
	 * @param n
	 * @return
	 */
	private int x(int n) {
		for (Iterator iterator = pipe.iterator(); iterator.hasNext();) {
			F f = (F) iterator.next();
			n=f.x(n);
		}
		return n;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
