/**
 * 
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class GDEATexFormatter {
	final static Utils utils = Utils.getSingleton();
	Function<String, String> processor;

	// Method called to run the class
	public void run() {
		p("Starting run of GDEATexFormatter at " + new Date());
//		p(Charset.defaultCharset()); 
		process();
		p("Finished run of GDEATexFormatter at " + new Date());
	}

	// Utility method for quick printing to console
	void p(Object o) {
		utils.log(o);
	}
	
	public void process()  {
		processor=new Function<String,String>() {
			@Override
			public String apply(String line) {
				// TODO Auto-generated method stub
				return line;			}
			
		};
		String dir = "C:\\Dropbox\\Math\\PhD\\Paper1";
		Path path = FileSystems.getDefault().getPath(dir, "Verschueren-Mestel-201311-arxivR2a.tex");
		String newFile="Verschueren-Mestel-201311-arxivR2a2.tex";
		try	(BufferedWriter out = new BufferedWriter(new FileWriter(dir+"\\"+newFile));
				Stream<String> in=Files.lines(path)) {
			in.filter(line->{
				if ( (line.startsWith("%"))
					||(line.isEmpty())
					||(line.startsWith("\\newtheorem"))
					||(line.startsWith("\\usepackage{ams"))
					||(line.startsWith("\\usepackage{graphicx"))
					||(line.startsWith("\\usepackage{esint"))
					||(line.startsWith("\\pdfpagewidth\\paperwidth"))
					) {return false;} else {return true;} })
//			.map(processor)
			.forEach(line->{
				try { 
					if (line.startsWith("\\documentclass")) {line=line.replaceFirst("article", "gDEA2e");}
					out.write(line+"\n");
					p(line);
				} catch (Exception e) {e.printStackTrace();}  });
		} catch (IOException e1) {e1.printStackTrace();} 
	}
	

}
