/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class Test {
	final static Utils utils = Utils.getSingleton();
	final static Qw[] sf={new Qw(0,-1),new Qw(1,-1)};
	final static Qw[] tf={new Qw(0),new Qw(0,1,1)};

	// Method called to run the class
	public void run() {
		p("Starting run of Test at " + new Date());
//		p(Qw.power(3).plus(1).inverse());
//		p(""+Qw.ZERO);
//		p(""+f2(Qw.ZERO));
//		p(""+CalcFP("121"));
//		char[][] s = perm("123",2);
//		p(apply("12",Qw.ONE));
		List<Qw> z=new ArrayList<Qw>();
//		Set<Qw> z2=new HashSet<Qw>();
		char[][] s = perm("12",3);
		for (int i = 0; i < s.length; i++) {
//			p(""+s[i][0]+s[i][1]+s[i][2]);
//			p(""+s[i][0]+s[i][1]);
			Qw q=calcFP(s[i]).simplify();
			z.add(q);
			p(new String(s[i])+"->"+q.simplify()+" ("+q.value()+")");
		}
		for (int i = 0; i < s.length; i++) {
			for (Qw q:z) {
				Qw q2=apply(s[i],q).simplify(); 
				if (!q2.equals(q)) {
					for (Qw q3:z) {
						if (q2.equals(q3)) {
							p("Code "+new String(s[i])+" maps "+q+" to "+q2);
						}
					}
				}
			}
		}
		
		p("Finished run of Test at " + new Date());
	}
	
	void doIt() {
		
	}
	public Qw f1(Qw q) {
		return q.times(sf[0]);
	}
	public Qw f2(Qw q) {
		return q.times(sf[1]).plus(tf[1]);
	}
	public Qw apply(char[] code,Qw q) {
		final int len=code.length;
		for (int i = len-1; i >=0; i--) {
			if (code[i]=='1') {q=f1(q);} else {q=f2(q);}
		}
		return q;
	}
	public Qw apply(String code,Qw q) {
		return apply(code.toCharArray(),q);
	}
	public Qw calcFP(String code) {
		return calcFP(code.toCharArray());
	}
	public Qw calcFP(char[] code) {
		final int len=code.length;
		Qw[] s=new Qw[len];
		Qw[] t=new Qw[len];
		for (int i = 0; i < len; i++) {
			if (code[i]=='1') {s[i]=sf[0];t[i]=tf[0];} else {s[i]=sf[1];t[i]=tf[1];}
		}
		Qw den=Qw.ONE,num=Qw.ZERO;
		for (int i = len-1;i>=0; i--) {
			den=den.times(s[i]);
			num=num.times(s[i]).plus(t[i]);
		}
		den=den.minus(1).minus();
//		p(den);
		return num.times(den.inverse());
		
	}

	char[][] perm(String input, int num) {
		final int len=input.length();final int nPerms=(int)Math.pow(len,num);
		char[] inchars = input.toCharArray();
		char[][] perms=new char[nPerms][num];
		for (int j = 0; j <num; j++) {
			for (int i = 0; i < nPerms; i++) {
				int k=(int)Math.pow(len,j);
				perms[i][num-j-1]=inchars[(i/k)%len];
//				p(""+i+","+j+","+k+":"+(i/k)%len+";"+perms[i][j]);
			}
		}
		return perms;
	}
	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
