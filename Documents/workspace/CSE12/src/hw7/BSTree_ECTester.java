package hw7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BSTree_ECTester {
	BSTree_EC<String> animal;
	BSTree_EC<String> load;
	
	
	@Before
	public void setUp() throws Exception {
		animal = new BSTree_EC<>();
		animal.insert("redPanda", "red");
		animal.insert("seal", "gray");
		animal.insert("lion", "gold");
		animal.insert("bear","brown");
		animal.insert("chicken", "white");
		animal.insert("dragon", "purple");
		
	}

	@Test
	public void test() {
		assertEquals("Height", 4, animal.findHeight(1));
		assertEquals("Height", 3, animal.findHeight(2));
		assertEquals("Height", 3, animal.leafCount(2));
		assertEquals("Height", 2, animal.levelCount(2,2));
		
	
	}
	/* @Test
	public void testLoad() {
		load = new BSTree_EC<>();
		load.loadData("sample.txt");
		String[] elemArray = new String[load.getSize()*2];
		load.printToArray(elemArray, 1);
	} */
	
	@Test
	public void testSave() {
		animal.saveData("Solution1.txt");
	}
	
	@Test
	public void testUpdate() {
		animal.update("gray", "green");
	}

}
