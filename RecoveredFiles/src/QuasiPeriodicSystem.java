/**
 * 
 */

import org.pv.core.Utils;

/**
 * @author PV
 *
 */
public class QuasiPeriodicSystem extends DynamicalSystem {
	final double[] angularFrequency;
	final private int increments;
	/**
	 * @param dimension
	 */
	public QuasiPeriodicSystem(double angularFrequency) {
		super(1);
		this.angularFrequency=new double[1];
		this.angularFrequency[0]=angularFrequency%1;
		this.increments=1;
	}
	public QuasiPeriodicSystem(int dimension,double[] angularFrequency) {
		super(dimension);
		this.angularFrequency=new double[dimension];
		for (int n=0;n<dimension;n++) {
		this.angularFrequency[n]=angularFrequency[n]%1;
		}
		this.increments=1;
	}
	
	/**
	 * @param i
	 * @param ds
	 * @param j
	 */
	public QuasiPeriodicSystem(int dimension,double[] angularFrequency,int increments)  {
		super(dimension);
		this.angularFrequency=new double[dimension];
		for (int n=0;n<dimension;n++) {
		this.angularFrequency[n]=angularFrequency[n]%1;
		}
		this.increments=increments;
	}
	/* (non-Javadoc)
	 * @see graphs.DynamicalSystem#applyRule(double[])
	 */
	@Override
	public double[] applyRule(double[] it,int i,final double[] initialPoint) {
		double[] newPoint=newPoint();
		for (int n=0;n<dimension;n++) {
		double x=(initialPoint[n]+(angularFrequency[n]*i)/increments)%1;
//		if (x>=1) {x=x%1;}
		newPoint[n]=x;
		}
		return newPoint;
	}





}
