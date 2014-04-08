/**
 * 
 */

import java.util.Date;
import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class Sawtooth {
	final static Utils utils = Utils.getSingleton();
	final double w=0.5*(Math.sqrt(5)-1);

	// Method called to run the class
	public void run() {
		p("Starting run of Sawtooth at " + new Date());
//		p(""+Math.pow(w,3));
//		if (true) {return;}
		for(int n=1;n<30;n++) {
			calc(n);
		}
//		calc(5);
		p("Finished run of Sawtooth at " + new Date());
	}
	
	public void calc(int fibIndex) {
		final long q=Fibs.getLong(fibIndex);
		final long p=Fibs.getLong(fibIndex-1);
		final double e=w-p/(double)q;
//		p("Error="+e+"; est="+(1/(Math.sqrt(5)*q*q)) );
		double nw=0;double sum=0;
		long np=0,intSum=0;
		for (long i=0;i<q;i++) {
//			np+=p; if (np>q) {np-=q;}
//			intSum+=np;
			nw+=w; if (nw>=1) {nw-=1;}
			sum+=(nw-0.5);
		}
		
//		p("Fib "+fibIndex+"="+q+"; intSum="+(2*intSum-q*q)/(2.0*q));
		p("Fib "+fibIndex+"="+q+"; sum="+sum);
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
