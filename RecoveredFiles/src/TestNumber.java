/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class TestNumber {
	final static Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of TestNumber at " + new Date());
		ArrayList<Number> nos = new ArrayList<Number>();
		nos.add(Integer.valueOf(12345));
		nos.add(Double.valueOf(12345));
//		Integer m = Integer.valueOf(12345);
//		Double m = Double.valueOf(12345);
		for (Iterator iterator = nos.iterator(); iterator.hasNext();) {
			Number number = (Number) iterator.next();
			
//			Object m = number.add(3);
		}
//		test(m);
		p("Finished run of TestNumber at " + new Date());
	}
	
	public void test(Integer n) {p("Integer: "+n);}
	public void test(Double x) {p("Double: "+x);}
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
