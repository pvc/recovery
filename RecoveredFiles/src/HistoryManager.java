/**
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class HistoryManager {
	private static final int HISTORY_LENGTH = 10;
//	private static final String SECTIONKEY="OUGraphs001";
	final static Utils utils = Utils.getSingleton();
	private static Map<String,HistoryManager> instances=new HashMap<String,HistoryManager>();
	
	private IDialogSettings settings;
//	private static final String myPath="C:/eclipseBase/OU/OUGraphs/History.xml";
//	private static boolean loaded=false;
	
	private HistoryManager(String key) {
		settings=utils.getSettingsManager(key);
//		p(settings.getName());
//		p("sections:"+settings.getSections().length);
//		if (settings!=null) {return;}
//		if (!loaded) {
//			loaded=true;
//			File file=new File(myPath);
//			if (file.exists()) {
//				try {
//					settings.load(myPath);
//					p("Loaded settings from disk!");
//				} catch (IOException e) {
//					e.printStackTrace(utils.getLogger());
//				}
//			}
//		}
//		p("sections:"+settings.getSections().length);
//		p("sections:"+settings.getSections().length);

		
//	    settings = settings.getSection(WMS_WIZARD);
//	    if (settings == null) {
//	      settings = WmsPlugin.getDefault().getDialogSettings().addNewSection(WMS_WIZARD);
//	    }
	  }

	// Method called to run the class
	public static void run() {
		HistoryManager.getInstance("testKey").test();
	}
	
	/**
	 * 
	 */
	private void test() {
		p("Starting run of HistoryManager at " + new Date());
		String[] h = getHistory("GraphFile");
		p("Entries: "+h.length);
		addHistory("GraphFile","Val"+utils.getUniqueString());
		addHistory("GraphFile","Val"+utils.getUniqueString());
		addHistory("GraphFile","Val"+utils.getUniqueString());

		h = getHistory("GraphFile");
		p("Entries: "+h.length);
		p("Finished run of HistoryManager at " + new Date());
	}

//	public static HistoryManager getInstance() {
//		if (instance==null) {instance=new HistoryManager();}
//		return instance;
//	} 
	public static HistoryManager getInstance(String key) {
		HistoryManager instance=instances.get(key);
		if (null==instance) {instance=new HistoryManager(key);instances.put(key,instance);}
		return instance;
	} 
	public String[] getHistory(String key) {
		String[] history = settings.getArray(key);
		if (history==null) {history=new String[0];settings.put(key, history);}
		return history;
	}
	
	
	public String[] addHistory(String key,String value) {
		String[] history=getHistory(key);
		history=addToHistory(history,value);
		settings.put(key, history);
		return history;
	}


		private String[] addToHistory(String[] history, String newEntry) {
		  ArrayList<String> l = new ArrayList<String>(Arrays.asList(history));
		  addToHistory(l, newEntry);
		  String[] r = new String[l.size()];
		  l.toArray(r);
		  return r;
		}

		private void addToHistory(List<String> history, String newEntry) {
		  history.remove(newEntry);
		  history.add(0,newEntry);

		  if (history.size() > HISTORY_LENGTH) {
		    history.remove(HISTORY_LENGTH);
		  }
		}
		
//		protected void finalize()  {
//			p("Finalising!!!");
//			try {
//				settings.save(myPath);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace(utils.getLogger());
//			}
//		}
	

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
