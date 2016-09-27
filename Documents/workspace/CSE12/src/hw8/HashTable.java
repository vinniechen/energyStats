package hw8;

import java.util.LinkedList;
import java.io.*;


/*
 * NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * Hash Table that uses separate chaining to handle collisions 
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-24-16
 */
public class HashTable implements IHashTable {
	
	private int nelems;  //Number of element stored in the hash table
	private int expand;  //Number of times that the table has been expanded
	private int collision;  //Number of collisions since last expansion
	//The length of the longest known collision chain (before resizing)
	private int maxCollisionChain; 
	//FilePath for the file to write statistics upon every rehash
	private String statsFileName;     
	//Boolean to decide whether to write 
	//statistics to file or not after rehashing
	private boolean printStats = false;   
	private LinkedList[] hashTable;
	private LinkedList[] expandTable;
	private final double threshold = (double)2/(double)3;
	private int M;
	private File file;
	
	
	
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
	
	/**
	 * Constructor for hash table fills array with linked lists
	 * and initializes the linked lists with type string
	 * @param Initial size of the hash table
	 * @param File path to write statistics
	 */
	public HashTable(int size, String fileName){
		hashTable = new LinkedList[size];
		statsFileName = fileName; // to be used when printing
		file = new File(statsFileName);
		printStats = true;
		M = size;
		for (int i = 0; i < M; i++) { 
			hashTable[i] = new LinkedList<String>();
		}
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
	@Override
	public boolean insert(String value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (lookup(value)) { // checks if value is already in hashTable
			return false;
		}
		// if load factor > threshold, expand and rehash
		if ( (double)nelems/(double)M > threshold ) { 
			expand();
			if (printStats) {
				printStatistics();
			}
			
			reHash();
			resetStatistics();
		}
		int hashVal = hashBrown(value); // take hash of value
		
		if (hashTable[hashVal].size() != 0) { // if there is a collision
			collision++;	
		}
		// if inserting to LinkedList index will create largest chain
		if (hashTable[hashVal].size()+1 > maxCollisionChain) {
			maxCollisionChain = hashTable[hashVal].size()+1;
		}
		hashTable[hashVal].addFirst(value); // add to linked list at hash
		nelems++;
		return true;
	}
	/** Delete the given value from the hash table
	 * 
	 * @param value value to delete
	 * @throws NullPointerException if value is null
	 * @return true if the value was deleted, false if the value was not found
	 */
	@Override
	public boolean delete(String value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (!lookup(value)) { // checks if value is in hashTable
			return false;
		}
		int hashVal = hashBrown(value);
		if (hashTable[hashVal].size() == maxCollisionChain) {
			maxCollisionChain = 0; // find new maxCollisionChain if necessary
			for (int i = 0; i < M; i++) { 
				if (hashTable[i].size() > maxCollisionChain) {
					maxCollisionChain = hashTable[i].size();
				}
			}
		}
		hashTable[hashVal].remove(value); // remove value	
		nelems--;
		return true;
		
	}
	/** Check if the given value is present in the hash table
	 * 
	 * @param value value to look up
	 * @throws NullPointerException if value is null
	 * @return true if the value was found, false if the value was not found
	 */
	@Override
	public boolean lookup(String value) {
		if (value == null) {
			throw new NullPointerException();
		}
		int hashVal = hashBrown(value);
		if (hashTable[hashVal].contains(value)) { //look for value in hashTable
			return true;
		}
		else {
			return false;
		}
	}
	/** Print the contents of the hash table to the given print stream. 
	 * Print nothing if table is empty
	 * 
	 * Example output for this function:
	 * 
	 * 0:
	 * 1:
	 * 2: marina, fifty
	 * 3: table
	 * 4:
	 * 
	 * @param out the output stream
	 */
	@Override
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
	/**
	 * Return the number of elements currently stored in the hashtable
	 * @return nelems
	 */
	@Override
	public int getSize() {
		return nelems;
	}
	/**
	 * Doubles the HashTable when load factor goes over the threshold
	 */
	private void expand() {
		expandTable = new LinkedList[M*2];
		for (int i = 0; i < M*2; i++) { // creates new linked lists
			expandTable[i] = new LinkedList<String>();
		}
		expand++;
	}
	/**
	 * Rehashes the items into the table after expansion
	 */
	private void reHash() {
		int oldM = M;
		M = M*2;
		for (int i = 0; i < oldM; i++) { // reassigns from old table
			for (int j = 0; j < hashTable[i].size(); j++) {
				String potato = (String) hashTable[i].get(j);
				// hashBrowns old potato values and puts into new table
				expandTable[hashBrown(potato)].add(potato); 
			}
		}
		hashTable = expandTable; // points old table reference to new table
	}
	/**
	 * Resets instance variables belonging to statistics after rehashing
	 */
	private void resetStatistics() {
		collision = 0; // resets number of collisions
		maxCollisionChain = 0; // finds new maxCollisionChain
		for (int i = 0; i < M; i++) {
			if (hashTable[i].size() > maxCollisionChain) {
				maxCollisionChain = hashTable[i].size();
			}
		}
	}
	/**
	 * To print the statistics after each expansion. 
	 * This method will be called only if printStats=true
	 */
	private void printStatistics() {
		try(FileWriter writer = new FileWriter(statsFileName, true);
			    BufferedWriter buffer = new BufferedWriter(writer);
			    PrintWriter print = new PrintWriter(buffer))
			{
			    print.println(expand+" resizes " + ((double)nelems/(double)M) +
						 " alpha "+ collision+" collisions "
							+ maxCollisionChain+ " longest chain");
			} 
		catch (IOException e) {

			}	
		//r resizes, load factor alpha, c collisions, n longest chain
	}
	
}

	
