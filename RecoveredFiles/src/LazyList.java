/**
 * 
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.pv.core.Utils;


/**
 * @author PV
 * @param <B>
 *
 */
public class LazyList<T, B> {
	final Utils utils = Utils.getSingleton();
	final private ArrayList<B> base;
	final private LinkedList<T> list;
	public LazyList(ArrayList<B> base) {
		this.base=base;
		list=new LinkedList<T>();
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#get(int)
	 */
	public T get(int index) {
		T item;
//		p("Getting: "+index);
		for (int n=list.size();n<=index;n++) {list.add(null);} // Populate end to Ensure no indexOutofBounds
		item=list.get(index);
//		p("Got:"+item);
		if (item!=null) {return item;}
		B baseItem=base.get(index);
		if (baseItem==null) {return null;}
//		p("Call lazyGet");
		item=lazyGet(baseItem);
		list.set(index,item);
		return item;
	}


	/**
	 * @param baseItem
	 * @return
	 */
	private T lazyGet(B baseItem) {
		return (T) ((Graph) baseItem).plotImage();
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
