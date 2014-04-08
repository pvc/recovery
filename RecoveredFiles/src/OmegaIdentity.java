/**
 * 
 */

import java.util.Date;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class OmegaIdentity {
	final int terms=10;
	final Utils utils = Utils.getSingleton();
	final double w=0.5*(Math.sqrt(5)-1);
	final double w2=w*w;
	final double[] elts=new double[(int) Math.pow(2, terms)];
//	double x=w;
	double x=0;
	double term=1,result=1+w*x;
	
	// Method called to run the class
	public void run() {
		p("Starting run of OmegaIdentity at " + new Date());
		elts[0]=x;
		int len=1;
		p("Term0="+result);
		
		for (int termIndex=1;termIndex<=terms;termIndex++) {
			
		for (int i=0;i<len;i++) {
			elts[len+i]=f2(elts[i]);
//			p(""+elts[len+i]);
			elts[i]=f1(elts[i]);
//			p(""+elts[i]);
		}
		term=1;
		len=2*len;
		for (int i=0;i<len;i++) {
			term*=1+w*elts[i];
		}
		result*=term;
		p("Index: "+termIndex+", term="+term+", result="+result);
		}
		
		p("Finished run of OmegaIdentity at " + new Date());
	}

	/**
	 * @param d
	 * @return
	 */
	private double f1(double d) {
		return -w*d;
	}

	/**
	 * @param d
	 * @return
	 */
	private double f2(double d) {
		return w2*d+w;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	

}
