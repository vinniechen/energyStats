package hw8;

import java.io.*;
import java.util.*;

/*
 * NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * Counts the number of similar lines in each file
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-24-16
 */

public class LineCounter {
	
	/**
	 * Prints specific message to console
	 * @param filename name of file
	 * @param compareFileName prints statistics
	 * @param percentage percent of similar lines
	 */
	public static void printToConsole(String filename, String compareFileName,
			int percentage) {
		if(!filename.isEmpty()) // enter in "" to not use
			System.out.println("\n"+filename+":");
		
		if(!compareFileName.isEmpty()) // enter in "" to not use
				System.out.println(percentage+"% of lines are also in "
		+compareFileName);
	}
	
	/**
	 * Takes in files as arguments to compare
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		
		if(args.length<1) {
			System.err.println("Invalid number of arguments passed");
			return;
		}
		
		int numArgs = args.length;
		// stores hash tables for files
		HashTable[] tableList = new HashTable[numArgs]; 
		int numLines = 0;
		int num = 15;
		
		//Preprocessing: Read every file and create a HashTable
		
		for(int i=0; i<numArgs; i++) {
			// create hash table at index for file
			tableList[i] = new HashTable(num); 
			File file = new File(args[i]);
			Scanner read = null;
			try {
				 read = new Scanner(file); // read from file
			}
			catch (FileNotFoundException e) {
				
			}
			numLines = 0;
			while (read.hasNextLine()) { // goes through lines of file
				String line = read.nextLine();
				// adds to appropriate linked list at index of 
				tableList[i].insert(line); 
			}
			read.close();
		}
		//tableList[0].printTable();
		
		//Find similarities across files
		
		for(int i=0; i<numArgs; i++) {
			File file = new File(args[i]);
			 // read from file
			printToConsole(args[i],"",0);
			numLines = 0;
			double numFound = 0;
			int percent = 0;
			boolean count = true;
			// compares against other files' hash tables
			
			for (int j = 0; j < numArgs; j++) {
				Scanner find = null;
				try {
					find = new Scanner(file);
				}
				catch (FileNotFoundException e) {
					
				}
				if (j != i) { // does not compare against itself
					numFound = 0;
					while (find.hasNextLine()) { // goes through lines of file
						if (count) {
							numLines++;
							
						}
						String line = find.nextLine();
						// if it is found in other file
						if (tableList[j].lookup(line)) { 
							numFound++; 
						}
					}
					count = false;
					percent = (int) ((double)numFound/(double)numLines*100);
					printToConsole("", args[j], percent);
				}
				find.close();
			}
			System.out.println();
		}
	}

}
