/**
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import org.pv.core.Utils;



/**
 * @author PV
 *
 */
public class ProcessPHPListContacts {
	final Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of ProcessIBMContacts at " + new Date());
		String inFilePath = "C:/Users/PV/Downloads/PHPList Export from 2009-03-14 to 2012-03-14 (2012-Mar-14).csv";
		String outFilePath = "C:/Users/PV/Downloads/phplist.txt";
		BufferedReader in=null;BufferedWriter out=null;
		try {
			in= new BufferedReader(new FileReader(inFilePath));
			out = new BufferedWriter(new FileWriter(outFilePath));
			
			String line="";
			Data data = new Data();
			String sname,name;
			while (null!=(line=in.readLine())) {
				if (line.indexOf("LWAMailingList")==-1) {continue;}	
//				line=line.trim();
//				if (line.length()==0) {data.write(out);continue;}
				String[] parts=line.split("\t");
				String email=parts[1];
				String fname=parts[13]; 
				if (fname.trim().length()!=0) {sname=parts[11];}
				else {
					name=parts[11].replace(',',' ').replace(',',' '); 
					int i=name.indexOf('@');
					if (i!=-1) {name=name.substring(0,i);}
					i=name.indexOf(" (");
					if (i!=-1) {name=name.substring(0,i);}
					i=name.lastIndexOf(" ");
					if (i==-1) {fname=name;sname="";} 
					else {
					fname=name.substring(0,i);
					sname=name.substring(i+1);
					}
				}
				p(fname+","+sname);
				out.write(email+','+fname+','+sname+"\r\n");
				
				
//				p("parts="+parts.length);
//				break;
//				if (parts.length!=15) {p(line);}
//				switch (parts[0]) {
//				case "InternetAddress":data.email=parts[1].trim();break;
//				case "FirstName":data.first=parts[1].trim();break;
//				case "MiddleInitial":data.init=parts[1].trim();break;
//				case "LastName":data.last=parts[1].trim();break;
//				case "MailAddress":data.addr=parts[1].trim();break;
//				default:break;
//				}
			}
		} catch (IOException e) {
			e.printStackTrace(utils.getLogger());
		} finally {
			try {
				if (in!=null) {in.close();}
				if (out!=null) {out.close();}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		p("Finished run of ProcessIBMContacts at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	class Data {
		String email,addr,first,init,last;
		public Data() {reset();}
		public void reset() {email="";addr="";first="";init="";last="";}
		public void write(Writer w) {
			int at;
			if (email.length()==0 && addr.length()!=0) {
				if (-1!=(at=addr.lastIndexOf('@'))) {
					String temp=addr.substring(at+1);
					if (!temp.startsWith("IBM")) {email=addr;}
				}
			}
			if (email.length()==0) {return;}
			try {
				w.write(last+','+first+','+init+','+email+"\r\n");
			} catch (IOException e) {
				e.printStackTrace(utils.getLogger());
			}
			reset();
		}
	}

}
