package jrs.helper.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import cern.colt.Arrays;

import uk.ac.dmu.iesd.cascade.io.CSVReader;
import uk.ac.dmu.iesd.cascade.io.CSVWriter;

public class CSVCompiler {
	
	File homeDir = null;
	String output_name = "compiled_files.csv";
	String[] col_arr = null;
	boolean header = true;
	
	
	public CSVCompiler(String[] args)
	{
		if (args.length == 3)
		{
			output_name = args[2];
		}		

		homeDir = new File(args[0]);

		if (!homeDir.isDirectory())
		{
			badDir(args[1]);
			System.exit(1);
		}
		
		StringTokenizer myTok = new StringTokenizer(args[1]);
		
		if (myTok.countTokens() < 1)
		{
			System.err.println("No columns supplied or wrong format in argument \""+args[1]+"\"");
			System.exit(1);
		}
		
		col_arr = new String[myTok.countTokens()];
		int i =0;
		while (myTok.hasMoreTokens())
		{
			col_arr[i] = myTok.nextToken();
			i++;
		}		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3)
		{
			usage();
			System.exit(1);
		}
				
		CSVCompiler thisComp = new CSVCompiler(args);
		thisComp.compile();
	}

	private void compile() {
		File[] fileList = homeDir.listFiles();
		ArrayList<String[]> cols = new ArrayList<String[]>();
		CSVWriter outFile = new CSVWriter(output_name, true);
		
		for (File f : fileList)
		{
			String name = f.getName();
			int pos = name.lastIndexOf('.');
			String ext = name.substring(pos+1);
			

			
			if (ext.equals("csv"))
			{
				try {
					CSVReader thisReader = new CSVReader(f);
					thisReader.parseByColumn();
					
					for (String thisCol : col_arr) {
						System.out.println("Adding column "+thisCol+" from file "+name);
						cols.add(thisReader.getColumn("\""+thisCol+"\""));
					}

				} catch (FileNotFoundException e) {
					System.err
							.println("Unexpected error - Application can't find a file that it found itself!!!");
					e.printStackTrace();
				}
				
			}
		}
		
		String[][] formattedCols = new String[cols.size()][cols.get(0).length];
		int i = 0;
		for (String[] arr : cols)
		{
			System.out.println("Appending length "+arr.length+" array");
			formattedCols[i] = arr;
			i++;			
		}
		
		outFile.appendRowsAsCols(formattedCols);
		outFile.close();
	}

	private static void badDir(String s) {
		System.err.println("Bad directory "+s);
		
	}

	private static void usage() {
		System.out.println("CSV Compiler usage:");
		System.out.println("===================");
		System.out.println("CSVCompiler <directory> <column_array> <output_name>");
		
		
	}

}
