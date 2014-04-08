/**
 * 
 */

import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class ScalarTransform {
	final Utils utils = Utils.getSingleton();
	
	void applyTo(final ScalarSequence seq) {
		final int size=seq.getSize();
		for (int n=0;n<size;n++) {
			seq.set(n,calc(seq.get(n)));
		}
	}

	// Method called to run the class
	public void run() {
		p("Starting run of ScalarTransform at " + new Date());
		// TODO insert code here
		p("Finished run of ScalarTransform at " + new Date());
	}
	// Override this method with transform calculation
	double calc(double d) {return d;}


	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
