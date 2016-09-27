package hw8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
* NAME: Vinnie Chen
* PID: A12148745
* LOGIN: cs12sau
*/

/**
* Tester for Hash Table that uses separate chaining to handle collisions 
* @version 1.0
* @author Vinnie Chen
* @since 5-24-16
*/

public class HashTableTester {
	HashTable tableSmall;
	HashTable tableFile;
	HashTable tableExpand;
	
	@Before
	public void setUp() throws Exception {
		tableSmall = new HashTable(5);
		tableFile = new HashTable(5, "Solution1.txt");
		tableExpand = new HashTable(3, "Solution1.txt");
	}
	/**
	 * Tests the expand
	 */
	@Test
	public void testExpand() {
		tableExpand.insert("mouse");
		tableExpand.insert("cat");
		tableExpand.insert("dog");
		tableExpand.insert("horse");
		tableExpand.insert("spider");
		tableExpand.insert("fly");
		tableExpand.insert("newt");
		tableExpand.insert("salamander");
		tableExpand.insert("lemur");
		tableExpand.insert("panda");
	}
	
	/**
	 * Test insert, delete,  printing the table, and expanding and rehashing
	 */
	@Test
	public void testMethods() {
		assertTrue(tableSmall.insert("pug"));
		assertTrue(tableSmall.lookup("pug")); // test lookup
		tableSmall.printTable();
		assertTrue(tableSmall.delete("pug")); // test delete
		assertFalse(tableSmall.lookup("pug"));
	
		tableSmall.insert("noodle"); // test insert
		
		tableSmall.printTable();
		tableSmall.insert("llama");
		tableSmall.printTable();
		tableSmall.insert("potato");
		tableSmall.printTable();
		tableSmall.insert("coffee");
		tableSmall.insert("poodle");
		tableSmall.printTable();
		tableSmall.insert("meow");
		tableSmall.insert("cat");
		tableSmall.insert("flower");
		tableSmall.insert("pineapple");
		assertEquals("Size", 9, tableSmall.getSize());
	
		tableSmall.delete("noodle");
		assertFalse(tableSmall.lookup("noodle"));
		tableSmall.delete("llama");
		tableSmall.delete("potato");
		tableSmall.delete("coffee");
		tableSmall.delete("poodle");
		tableSmall.delete("meow");
		tableSmall.delete("cat");
		tableSmall.delete("flower");
		tableSmall.delete("pineapple");
		assertEquals("Empty", 0, tableSmall.getSize()); // empty
		
	}
	/**
	 * Tests the second constructor
	 */
	@Test
	public void testFile() {
		tableFile.insert("corgi");
		tableFile.insert("german_shepard");
		tableFile.insert("border_collie");
		tableFile.insert("shiba_inu");
		tableFile.insert("daschund");
		assertEquals("Size", 5, tableFile.getSize()); 
		tableFile.insert("labradoodle");
		tableFile.delete("daschund");
		tableFile.printTable();	
		assertTrue(tableFile.lookup("shiba_inu"));
	}
	/**
	 * Tests the exceptions
	 */
	@Test
	public void testExceptions() {
		try {
			tableSmall.insert(null);
			tableSmall.delete(null);
			tableSmall.lookup(null);
			tableFile.insert(null);
			tableFile.delete(null);
			tableFile.lookup(null);
		}
		catch (NullPointerException e) {
			
		}
		
	}

}
