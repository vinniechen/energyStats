package hw8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * Finds anagrams to word entered in dictionary
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-24-16
 */

public class HW8_ExtraCredit {
	
	/**
	 * Takes dictionary's name and word whose anagrams are to be found
	 * and prints them
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length<2) {
			System.err.println("Invalid number of arguments passed");
			return;
		}
		
		int size = 0;
		File file = new File(args[0]);
		int numWords = numWords(file);
		Scanner read = null;
		
		try {
			 read = new Scanner(file); // read from file
		}
		catch (FileNotFoundException e) {}
		
		HashTable dictionary = new HashTable(numWords);
		// inserts pairs of (key, word) into hash table from dictionary
		while (read.hasNext()) {
			String word = read.next();
			String key = abcOrder(word);
			dictionary.insert(dictionary.newPair(key, word));
			
		}
		// if the word is in the dictionary
		if (dictionary.lookup(dictionary.newPair(abcOrder(args[1]),args[1]))) {
			int potato = dictionary.hashBrown(abcOrder(args[1]));
			LinkedList<String> anagrams=dictionary.getTable()[potato];	
			// finds anagrams in linked list
			boolean found = false;
			for (int i = 0; i < anagrams.size(); i++) {
				if (abcOrder(anagrams.get(i)).equals(abcOrder(args[1]))) {
					System.out.println(anagrams.get(i));
					found = true;
				}			
			}
			if (!found) {
				System.out.println("No anagram found");
			}
		}
		else {
			System.out.println("No anagram found");
		}

	}
	/**
	 * Arranges characters in word into alphabetical order
	 * @param word to be sorted
	 * @return string in alphabetical order
	 */
	public static String abcOrder(String word) {
		char[] array = word.toCharArray();
		Arrays.sort(array); // sorts in order
		String key = new String(array);
		return key;
	}
	/**
	 * Finds the number of words in the dictionary
	 * @param file for words to be found in
	 * @return number of words
	 */
	public static int numWords(File file) {
		Scanner count = null;
		int numWords = 0;
		
		try {
			count = new Scanner(file);
			while (count.hasNext()) { // counts number of words
				count.next();
				numWords++;
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		count.close();
		return numWords;
	}
	
	/**
	 * Class with two instance variables: key, and original string
	 */
	protected static class Pair {
		private String key;
		private String word;
		/*
		 * Constructs a pair with a key and a word
		 */
		public Pair(String key, String word) {
			this.key = key;
			this.word = word;
		}
		/*
		 * Getter for the key
		 */
		public String getKey() {
			return this.key;
		}
		/*
		 * Getter for the word
		 */
		public String getWord() {
			return this.word;
		}
	}
	/**
	 * Hash Table that uses separate chaining to handle collisions 
	 * @version 1.0
	 * @author Vinnie Chen
	 * @since 5-24-16
	 */
	protected static class HashTable {
		
		
		
		private int nelems;  //Number of element stored in the hash table  
		private LinkedList[] hashTable;
		private int M;
		private File file;
		
		
		public Pair newPair(String key, String word) {
			Pair newPair = new Pair(key, word);
			return newPair;
		}
		/**
		 * Constructor for hash table fills array with linked lists
		 * and initializes the linked lists with type string
		 * @param Initial size of the hash table
		 */
		public HashTable(int size) {
			hashTable = new LinkedList[size];
			M = size; // initializes M to size passed in
			for (int i = 0; i < M; i++) {
				hashTable[i] = new LinkedList<String>();
			}
		}
		
		public LinkedList[] getTable() {
			return hashTable;
		}
		
		/**
		 * Takes value of each character of the string, shifts it left, and right
		 * and performs operations to produce the mod of the value with the
		 * size M of the array
		 * @param str to be hashed
		 * @return int hashed value
		 */
		private int hashBrown(String str) {
			int hashValue = 0;
			for (int i = 0; i < str.length(); i++) {
				int leftShiftedValue = hashValue << 5; // left shift
				int rightShiftedValue = hashValue >>> 27; // right shift
				//| is bitwise OR, ^ is bitwise XOR
				hashValue = (leftShiftedValue | rightShiftedValue ^ str.charAt(i));
			}
			return Math.abs(hashValue % M);
		}
		
		/** Insert the string value into the hash table
		 * Expands and rehashes if the load factor is greater than the threshold
		 * @param value value to insert
		 * @throws NullPointerException if value is null
		 * @return true if the value was inserted, false if the value 
		 * was already present
		 */

		public boolean insert(Pair value) {
			if (value == null) {
				throw new NullPointerException();
			}
			if (lookup(value)) { // checks if value is already in hashTable
				return false;
			}

			int hashVal = hashBrown(value.getKey()); // take hash of value
			
			hashTable[hashVal].addFirst(value.getWord()); // add to linked list at hash
			nelems++;
			return true;
		}
		/**
		 * Prints the table with its linked lists to the console
		 */
		public void printTable() { 
			for (int i = 0; i < M; i++) {
				System.out.print(i+": ");
				for (int j = 0; j < hashTable[i].size(); j++) {
					if (j == hashTable[i].size()-1) { // if last in list
						System.out.print(hashTable[i].get(j)); 
					}
					else {
						System.out.print(hashTable[i].get(j)+", ");
					}
				}
				System.out.println();
			}
		}
		
		
		/** Check if the given value is present in the hash table
		 * 
		 * @param value value to look up
		 * @throws NullPointerException if value is null
		 * @return true if value was found, false if the value was not found
		 */
	
		public boolean lookup(Pair value) {
			if (value == null) {
				throw new NullPointerException();
			}
			int hashVal = hashBrown(value.getKey());
			//look for value in hashTable
			if (hashTable[hashVal].contains(value.getWord())) { 
				
				return true;
			}
			else {
				return false;
			}
		}
		/**
		 * Returns the size of the hash table
		 */
		public int getSize() {
			return nelems;
		}
		
	}
}