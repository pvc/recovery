/**
 * 
 */

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class ScalarSequence {
	final Utils utils = Utils.getSingleton();
	int index=0; // pointer to next pos afterend of population of seq (difft from capacity=x.length), returned by getsize
	final int capacity;
	final double[] x;
	
	double max=Double.NEGATIVE_INFINITY,min=Double.POSITIVE_INFINITY;int minAt=0,maxAt=0;
	boolean validStats=false;
	public ScalarSequence() {
		capacity=100;
		x=new double[capacity];
	}
	public ScalarSequence(int size) {
		capacity=size;
		x=new double[capacity];
	}
	public ScalarSequence(long[] in) {
		capacity=in.length;
		x=new double[capacity];
		Arrays.stream(in).forEach(n->add((double)n));
	}
	public ScalarSequence(double[] in) {
		index=capacity=in.length;
		x=in;
		calcStats();
	}
	public ScalarSequence(DoubleStream in) {
		x=in.toArray();
		index=capacity=x.length;
		calcStats();
	}
	public ScalarSequence(LongStream in) {
		x=in.mapToDouble(n->(double)n).toArray();
		index=capacity=x.length;
		calcStats();
	}

	
	
	public void add(double x) {
		this.x[index]=x;
//		validStats=false;
		if (index==0) {min=max=x;}
		if (x<min) {min=x;minAt=index;} else if (x>max) {max=x;maxAt=index;} 
		index++;
	}
	public double get(int n) {return x[n];}
	public double[] getArray() {return x;}
	public int getSize() {return index;}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public String toString() {
		return "Points: "+(index)+"; range=["+min+","+max+"]"; 
	}
	public void dump() {
		for (int n=0;n<index;n++) {
			p(n+": "+get(n));
		}
	}
	/**
	 * @param logger
	 */
	public void dumpRange() {
		p(getSize()+" values (indexed from 0)");
		p("Min: "+min+" at index: "+minAt);
		p("Max: "+max+" at index: "+maxAt);
		
	}
	public int getMinAt() {
		return minAt;
	}
	public void setMinAt(int minAt) {
		this.minAt = minAt;
	}
	public int getMaxAt() {
		return maxAt;
	}
	public void setMaxAt(int maxAt) {
		this.maxAt = maxAt;
	}
	/**
	 * @param i
	 * @param j
	 */
	public void dump(int i, int j) {
		for (int n=i;n<=j;n++) {
			p(n+","+get(n));
		}
			
		
	}
	/**
	 * @param d
	 * @param e
	 */
	public void dump(double start, double end) {
		dump((int)start,(int)end);
	}
	/**
	 * @param d
	 * @param e
	 */
	public int max2(int first, int last) {
//		return max((int)first,(int)last);
		double max=get(first);int inx=first;
		for (int n=(int)first+1;n<=(int)last;n++) {
			double temp=get(n);
			if (temp>max) {max=temp;inx=n;}
		}
		return inx;
	}
	public void calcStats() {
//		return max((int)first,(int)last);
//		p("stats ");
//		maxAt=minAt=0; min=max=x[0];
//		p("stats:index="+index+";x.len="+x.length);
		for (int n=0;n<index;n++) {
//			p("loop:"+n);
			double temp=x[n];
			if (temp>max) {max=temp;maxAt=n;}
			if (temp<min) {min=temp;minAt=n;} // dont use else since min may never get a value
//			p(""+temp);
		}
		validStats=true;
//		p("stats done");
	}
	/**
	 * @param i
	 * @param j
	 * @return 
	 */
	public ScalarSequence sub(int first, int last) {
		ScalarSequence sub = new ScalarSequence(last-first+1);
		for (int n=first;n<=last;n++) {
			sub.add(get(n));
		}
		return sub;
	}
	/**
	 * @param n
	 * @param calc
	 */
	public void set(int n, double d) {
		x[n]=d;
		if (d<min) {min=d;minAt=n;} else if (d>max) {max=d;maxAt=n;} 
		if (index<=n) {index=n+1;}
//		validStats=false;
	}
	/**
	 * @return
	 */
	public double getMin() {
//		if (!validStats) {calcStats();}
		return min;
	}
	public double getMax() {
//		if (!validStats) {calcStats();}
		return max;
	}
	/**
	 * @return
	 */
	public double getNorm() {
		return Math.max(Math.abs(max), Math.abs(min));
	}
	/**
	 * this ought to be an override in Xseq - here should stream array
	 */
	public LongStream streamLong() {
		return LongStream.rangeClosed((long)getMin(),(long)getMax());
	}
	public DoubleStream stream() {
		return Arrays.stream(x);
	}
	

}
