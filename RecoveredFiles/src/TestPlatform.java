/**
 * 
 */

import java.util.Date;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.pv.core.Utils;


/**
 * @author PV
 *
 */
public class TestPlatform {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of Platform at " + new Date());
		testBundles();
		p("Finished run of Platform at " + new Date());
	}
	
	public void testBundles() {
		p("Platform details:");
		p("Install Location: "+Platform.getInstallLocation().getURL());
		p("Instance Location: "+Platform.getInstanceLocation().getURL());
		p("Config Location: "+Platform.getConfigurationLocation().getURL());
		p("User Location: "+Platform.getUserLocation().getURL());
		p("OS: "+Platform.getOS());
		Bundle b = Platform.getBundle("org.pv.core");
		p("Bundle: "+b);
		p("Bundle location: "+b.getLocation());
		p("State location: "+Platform.getStateLocation(b));
//		Plugin b1 = b.adapt(Plugin.class); // does not succeed
//		p("Adapted to: "+b1); 
//		AbstractUIPlugin b1 = (AbstractUIPlugin)b; // does not succeed
//		p("Adapted to: "+b1); 
		
		
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}

}
