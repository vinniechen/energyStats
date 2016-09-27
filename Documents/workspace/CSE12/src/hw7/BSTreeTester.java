package hw7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/* NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * Tester file to test methods in BSTree class
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-17-16
 */

public class BSTreeTester {
	BSTree<String> empty;
	BSTree<String> animal;

	/** Set up BSTree of animals and its colors */
	
	@Before
	public void setUp() throws Exception {
		animal = new BSTree<>();
		animal.insert("redPanda", "red");
		animal.insert("seal", "gray");
		animal.insert("lion", "gold");
		animal.insert("bear","brown");
		animal.insert("chicken", "white");
		animal.insert("dragon", "purple");

	}

	/** test to insert new nodes */

	@Test
	public void testInsert() {
		empty = new BSTree<>();
		empty.insert("dog", "brown");
		assertEquals("Should be dog", "dog", empty.getRoot(1).getElem());
		assertEquals("Outer brown", "brown", 
				empty.getRoot(1).getOuterNode().getElem());
		assertEquals("Should be brown", "brown", empty.getRoot(2).getElem());
		assertEquals("Outer dog", "dog", 
				empty.getRoot(2).getOuterNode().getElem());

		empty.insert("cat", "white");
		assertEquals("Should be cat", "cat", 
				empty.getRoot(1).getLChild().getElem());
		assertEquals("Should be white", "white", 
				empty.getRoot(1).getLChild().getOuterNode().getElem());
		assertEquals("Should be white", "white", 
				empty.getRoot(2).getRChild().getElem());
		assertEquals("Should be cat", "cat", 
				empty.getRoot(2).getRChild().getOuterNode().getElem());

		empty.insert("unicorn", "rainbow");
		assertEquals("Should be unicorn", "unicorn", 
				empty.getRoot(1).getRChild().getElem());
		assertEquals("Should be rainbow", "rainbow", 
				empty.getRoot(1).getRChild().getOuterNode().getElem());
		assertEquals("Should be rainbow", "rainbow", 
				empty.getRoot(2).getRChild().getLChild().getElem());
		assertEquals("Should be unicorn", "unicorn", 
			empty.getRoot(2).getRChild().getLChild().getOuterNode().getElem());

		assertEquals("Outer unicorn", "rainbow", 
				empty.findMoreInfo("unicorn"));
	}
	/** Tests findElem() and findMoreInfo() */
	@Test
	public void testFind() {
		assertTrue("find redPanda", animal.findElem("redPanda"));		
		assertTrue("find purple", animal.findElem("purple"));
		assertTrue("find dragon", animal.findElem("dragon"));
		assertTrue("find brown", animal.findElem("brown"));
		assertTrue("find white", animal.findElem("white"));
		assertFalse("find goat", animal.findElem("goat"));

		assertEquals("Outer gold", "lion", animal.findMoreInfo("gold"));
		assertEquals("Outer brown", "brown", animal.findMoreInfo("bear"));
	}
	/** tests the printToArray() method */
	@Test
	public void testPrint() {
		
		String[] elemArray = new String[animal.getSize()*2];
		animal.printToArray(elemArray, 2);
		assertEquals("print", "brown", elemArray[0]);
		assertEquals("print", "bear", elemArray[1]);
		assertEquals("print", "gold", elemArray[2]);
		assertEquals("print", "lion", elemArray[3]);
		assertEquals("print", "gray", elemArray[4]);
		animal.printToArray(elemArray, 1);
		assertEquals("print", "purple", elemArray[5]);
		assertEquals("print", "lion", elemArray[6]);
		assertEquals("print", "gold", elemArray[7]);
		assertEquals("print", "redPanda", elemArray[8]);
		assertEquals("print", "red", elemArray[9]);
	}
	/** Tests exceptions are thrown properly */
	@Test
	public void testExceptions() {
		try {
			animal.getRoot(-1);
			animal.getRoot(5);
			animal.insert(null, "blue");
			animal.insert("bird", null);
			animal.findElem(null);
			animal.findMoreInfo(null);
			animal.findMoreInfo("mouse");
			String[] elemArray = new String[animal.getSize()*2];
			animal.printToArray(elemArray, -1);
			animal.printToArray(elemArray, 60);
			empty.printToArray(elemArray, 1);
			fail("Did not throw proper exception");
		}
		catch (IllegalArgumentException e) {
		}
		catch (NullPointerException ex) {
			
		}
	}

}
