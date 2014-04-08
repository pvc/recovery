/**
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import org.pv.core.Utils;

/**
 * @author Paul
 *
 */
public class TestRead {
	final static Utils utils = Utils.getSingleton();

	// Method called to run the class
	public void run() {
		p("Starting run of TestRead at " + new Date());
		File in=new File("C:/eclipse/OU/OUGraphs/data/Av.txt");
		Scanner scanner=null;
		try {
			scanner = new Scanner(in).useDelimiter("[