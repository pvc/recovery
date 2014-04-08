/**
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class ContinuedFraction {
	final static Utils utils = Utils.getSingleton();
	List<Integer>a,p,q;
	private int nextConvergent=0; // points to next convergent not yet calculated
	static final double w=0.5*(Math.pow(5,0.5)-1);
	
	public ContinuedFraction(double x) {calc(x);}
	public ContinuedFraction(double x, int length) {calc(x,length);}
	public ContinuedFraction() {}
	public ContinuedFraction(int ...quotients) {
		setupLists(quotients.length);
		for (int i = 0; i < quotients.length; i++) {
			a.add(Integer.valueOf(quotients[i]));
		}
	}
	
	// Method called to run the class
	public void run() {
		p("Starting run of ContinuedFraction at " + new Date());
//		calc(Math.sqrt(9),10);
//		calc(Math.PI,10);
//		calc(Math.pow(w,3),20);
		calc(89.0/144,10);
		p(this);
//		ContinuedFraction cf = new ContinuedFraction(1,2,3,4,5,6,7,8,9);
		for(int n=0;n<10;n++) {
		p(getConvergent(n));
		}
//		p(getConvergent(2));
//		p(getConvergentj(3));
//		p(cf.getConvergent(2));
//		p(cf.getConvergent(8));
//		p(cf.getConvergent(0));
		p("Finished run of ContinuedFraction at " + new Date());
	}
	
	private void setupLists(int length) {
		a=new ArrayList<Integer>(length);
		p=new ArrayList<Integer>(length);
		q=new ArrayList<Integer>(length);
		nextConvergent=0;
	}
	
	
	ContinuedFraction calc(double x, int length) {
		double in=x;
		setupLists(length);
		for (int i = 0; i < length; i++) {
		double floor=Math.floor(x);
		a.add(Integer.valueOf((int)floor));
		x=1/(x-floor); //should check for 0]
		}
		return this;
	}
	ContinuedFraction calc (double x) {
		return calc(x,10);
	}
	
	Rational getConvergent(int n) {
		//should check n<=len
		calcConvergents(n);
		return new Rational(p.get(n),q.get(n));
	}
	
	

	/**
	 * @param n
	 */
	private void calcConvergents(int n) {
		for (int i = nextConvergent; i <= n; i++) {
			if (i==0) {p.add(a.get(0));q.add(1);}
			else if (i==1) {p.add(a.get(0)*a.get(1)+1);q.add(a.get(1));}
			else {
			p.add(a.get(i)*p.get(i-1)+p.get(i-2));
			q.add(a.get(i)*q.get(i-1)+q.get(i-2));
			}
		}
		nextConvergent=n+1;
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	public String toString() {
		StringBuffer s=new StringBuffer();
		for (Iterator iterator = a.iterator(); iterator.hasNext();) {
			Integer i = (Integer) iterator.next();
			s.append(i).append(',');
		}
		s.append(" Length="+a.size());
		return s.toString();
	}

}
