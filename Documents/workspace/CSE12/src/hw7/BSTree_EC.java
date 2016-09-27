package hw7;

import java.util.*;
import java.io.*;

/* NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * BSTree_EC class implements a binary search tree and its nodes
 * with additional extra credit methods
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-17-16
 */

public class BSTree_EC<T extends Comparable<? super T>>{

	private int nelems;       //Number of nodes in the trees
	private BSTNode root1;    //Root of first tree
	private BSTNode root2;    //Root of second tree
	private BSTNode inserted;
	private BSTNode add1;
	private BSTNode add2;
	private BSTNode found;
	private int index;
	ArrayList<T> list;
	private boolean removeLeft = true; // false = right, true = left
	private boolean tree1;

	protected class BSTNode{

		T elem;
		BSTNode lChild;
		BSTNode rChild;
		BSTNode outer;


		/** lChild and rChild are BSTNodes. They may or not be null 
		 * depending on your code implementation.
		 * Make sure that the parameters are in the order specified.
		 */
		public BSTNode(BSTNode lChild, BSTNode rChild, BSTNode outer, T elem) {
			this.lChild = lChild;
			this.rChild = rChild;
			this.outer = outer;
			this.elem = elem;
		}

		/**
		 * Returns a values stored in a given node
		 */
		public T getElem() {
			return this.elem;
		}
		/**
		 * Returns the left child of a given node
		 * @return
		 */
		public BSTNode getLChild() {
			return this.lChild;
		}

		/**
		 * Returns the right child of a given node
		 * @return
		 */
		public BSTNode getRChild() {
			return this.rChild;
		}
		/**
		 * Returns the outer node (the corresponding node in the other BST)
		 * of a given node
		 * @return
		 */
		public BSTNode getOuterNode() {
			return this.outer;
		}

		/**
		 * Sets the element of the node to newElem
		 * @param newElem
		 */
		public void setElem(T newElem) {
			this.elem = newElem;
		}
		/**
		 * Sets the left child of the node
		 * @param newLChild
		 */
		public void setLChild(BSTNode newLChild) {
			this.lChild = newLChild;
		}
		/**
		 * Sets the right child of the node
		 * @param newRChild
		 */
		public void setRChild(BSTNode newRChild) {
			this.rChild = newRChild;
		}
		/**
		 * Sets the outer node of the node
		 * @param newOuterNode
		 */
		public void setOuterNode(BSTNode newOuterNode) {
			this.outer = newOuterNode;
		}
		/**
		 * Helper method to indicate whether a node has children
		 * @return true or false for children
		 */
		private boolean hasChildren() {
			if (this.getLChild() != null || this.getRChild() != null) {
				return true;
			}
			return false;
		}

	}

	//BSTree methods

	/**
	 * 0-args constructor
	 * Initializes roots to null and nelems elements to 0
	 */
	public BSTree_EC() {
		root1 = null;
		root2 = null;
		nelems = 0;

	}
	/**
	 * Public getter for the root.
	 * @param treeChoice 1 => root of first tree, 2 => root of second tree
	 * @return Node at root
	 * @throws IllegalArgumentException for an invalid treeChoice
	 */
	public BSTNode getRoot(int treeChoice) throws IllegalArgumentException {
		if (treeChoice == 1) { 
			return root1; // root of first tree
		}
		else if (treeChoice == 2) {
			return root2; // root of second tree
		}
		else { 
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the number of nodes in the trees
	 * @return total number of nodes
	 */
	public int getSize() {
		return this.nelems;
	}
	/**
	 * Add elements to corresponding trees
	 * @param firstElem
	 * @param secondElem
	 * @throws NullPointerException if either element is null
	 */
	public void insert(T firstElem, T secondElem) throws NullPointerException{
		if (firstElem == null || secondElem == null) {
			throw new NullPointerException();
		}

		if (getSize() == 0) { // inserts at root
			root1 = new BSTNode(null, null, root2, firstElem);
			root2 = new BSTNode(null, null, root1, secondElem);
			root1.setOuterNode(root2);
			root2.setOuterNode(root1);
		}
		else { // inserts any time after root
			add1 = place(root1, firstElem, root2); // calls recursive methods
			add2 = place(root2, secondElem, root1);
			add1.setOuterNode(add2); // sets outers after adding nodes
			add2.setOuterNode(add1);
		}
		nelems++;
	}
	/**
	 * Helper recursive method for insert(). Recurses to proper place for the
	 * new element and sets it. Sets 
	 * @param parent
	 * @param elem to be inserted
	 * @param otherTree
	 * @return the node added
	 */
	private BSTNode place(BSTNode parent, T elem, BSTNode otherTree) {		
		inserted = new BSTNode(null, null, otherTree, elem);
		if (elem.compareTo(parent.getElem()) < 0) { // checks if moving left
			if (parent.getLChild() == null) { // if found the place, base case
				parent.setLChild(inserted);
			}
			// if there is more to compare
			else if (parent.getLChild() != null) { 
				place(parent.getLChild(), elem, otherTree);
			}
		}
		// checks if moving right
		else if (elem.compareTo(parent.getElem()) > 0) { 
			if (parent.getRChild() == null) { // if found the place, base case
				parent.setRChild(inserted);
			}
			else if (parent.getRChild() != null) { // continue comparing
				place(parent.getRChild(), elem, otherTree);
			}
		}
		return inserted;
	}
	/**
	 * Helper method for findElem(). Recurses through all elements to find
	 * the selected element 
	 * @param parent
	 * @param elem to be found
	 * @return the node found
	 */
	private BSTNode find(BSTNode parent, T elem) {
		if (elem.compareTo(parent.getElem()) == 0) { // if element found
			return parent; 
		}
		// left branch, less than parent
		else if (elem.compareTo(parent.getElem()) < 0 
				&& parent.getLChild() != null) {
			return find(parent.getLChild(), elem);
		}
		// right branch, greater than parent
		else if (elem.compareTo(parent.getElem()) > 0 
				&& parent.getRChild() != null) {
			return find(parent.getRChild(), elem);
		}
		return null; // return null if not found
	}

	/**
	 * Method looks for a value given by the parameter elem, If the value 
	 * is found in either tree, the method will return true. If value is 
	 * not found, return false.
	 * @param elem
	 * @return whether elem was found
	 * @throws NullPointerException if parameter is null
	 */
	public boolean findElem(T elem) throws NullPointerException	 {
		if (elem == null) {
			throw new NullPointerException();
		}
		found = find(root1, elem); // search first tree
		if (found == null) { // if not found in first tree
			found = find(root2, elem); 
			//System.out.println(found.getElem());
		}
		if (found != null) { // if found in either tree
			return true;
		}
		return false; // if not found in either tree
	}

	/**
	 * Given an attribute ‘elem’ as a parameter, find and return the 
	 * corresponding attribute from another tree.
	 * @param elem element to find
	 * @return corresponding attribute from the other tree
	 * @throws NullPointerException if attribute is null
	 * @throws IllegalArgumentException if attribute is not found in 
	 * any of the trees
	 */
	public T findMoreInfo(T elem) 
			throws NullPointerException,IllegalArgumentException {
		if (elem == null) {
			throw new NullPointerException();
		}
		found = find(root1, elem); // search first tree
		if (found == null) { // if not found in first tree
			found = find(root2, elem); 
			//System.out.println(found.getElem());
		}
		if (found == null) { // if found in either tree
			throw new IllegalArgumentException();
		}
		return (T) found.getOuterNode().getElem();
	}

	/**
	 * Prints the tree, using INORDER traversal, one entry per line, 
	 * and also returns an array of values in INORDER. 
	 * If treeChoice is set to 1, use the first tree for traversal and 
	 * for every value, add the value from the second tree after it.
	 * If treeChoice is set to 2, use the second tree for traversal 
	 * and for every value, append the value from the first tree after it.
	 * @param elemArray array to be printed on
	 * @param treeChoice choice of tree 1 or 2
	 * @throws NullPointerException if no elements in BSTree
	 * @throws IllegalArgumentException if tree choice isn't 1 or 2
	 */
	public void printToArray(T[] elemArray, int treeChoice) 
			throws NullPointerException, IllegalArgumentException {
		if (nelems == 0) {
			throw new NullPointerException();
		} 
		index = 0;
		if (treeChoice == 1) { // first tree
			print (root1, elemArray);
		}
		else if (treeChoice == 2) { // second tree
			print(root2, elemArray);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Recursive method to start with leftmost node and print nodes in order
	 * @param node current node
	 * @param elemArray array to be printed on
	 */
	private void print(BSTNode node, T[] elemArray) {
		if (node == null) {
			return;
		}
		print(node.getLChild(), elemArray); // calls to left child

		elemArray[index] = node.getElem(); // current node's element
		// current node's outer element
		elemArray[index+1] = node.getOuterNode().getElem(); 
		System.out.println(elemArray[index]+" "+elemArray[index+1]);
		index = index+2; // increases index

		print(node.getRChild(), elemArray); // calls to right child
	}


	//EXTRA CREDIT METHODS

	/**
	 * Recursive method for findHeight(). Counts the levels gone down
	 * and returns the largest branch's height
	 * @param node to recurse down
	 * @return height of branch
	 */
	private int searchDown(BSTNode node) {
		if (node == null) {
			return 0;
		}
		// searches down left and right branches
		int leftCount = searchDown(node.getLChild());
		int rightCount = searchDown(node.getRChild());
		// returns branch with larger height
		return (Math.max(leftCount, rightCount)+1); 
	}

	/**
	 * Returns the height of the tree. -1 for empty tree.
	 * @param treeChoice tree 1 or 2
	 * @return height of tree
	 * @throws IllegalArgumentException if invalid tree choice
	 */
	public int findHeight(int treeChoice) throws IllegalArgumentException {
		if (nelems == 0) { // if empty, returns -1
			return -1; 
		}
		if (treeChoice == 1) { // tree 1
			return searchDown(root1)-1;
		}
		else if(treeChoice == 2) { // tree 2
			return searchDown(root2)-1;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Recursive methods to search for leaves used by leafCount()
	 * @param node to check if it is a leaf
	 * @return 1 if leaf found, eventually returns number of leaves
	 */
	private int searchLeaves(BSTNode node) {
		if (node == null) {
			return 0;
		}
		if (node.hasChildren() == false) { // if it is a leaf
			return 1;
		}
		else {
			int leftLeaves = searchLeaves(node.getLChild());
			int rightLeaves = searchLeaves(node.getRChild());	
			// sum of leaves from left and right
			return leftLeaves + rightLeaves; 
		}	
	}
	/**
	 * Counts the number of leaves in the BSTree, uses recursive helper
	 * method searchLeaves()
	 * @param treeChoice tree 1 or 2
	 * @return number of leaves
	 * @throws IllegalArgumentException
	 */
	public int leafCount(int treeChoice) throws IllegalArgumentException {
		if (treeChoice == 1) { // tree 1
			return searchLeaves(root1);
		}
		else if(treeChoice == 2) { // tree 2
			return searchLeaves(root2);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Return the number of nodes at a given level in the tree. 
	 * Level 0 is the level of the root node, level 1 are the (up to 2) 
	 * children of the root, and so on
	 * @param level to check at
	 * @param treeChoice tree 1 or 2
	 * @return number of nodes at a level
	 * @throws IllegalArgumentException if tree choice not 1 or 2
	 */
	public int levelCount(int level, int treeChoice) 
			throws IllegalArgumentException {
		// if the level in parameter is greater than number of levels
		if (level > findHeight(treeChoice)-1) { 
			return -1;
		}
		if (treeChoice == 1) { // tree 1
			return levelDown(root1, 0, level);
		}
		else if(treeChoice == 2) { // tree 2
			return levelDown(root2, 0, level);
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Helper method for levelCount(). Recurses down to correct level,
	 * then returns the number of non-null nodes at that level
	 * @param node
	 * @param currLevel
	 * @param level
	 * @return
	 */
	private int levelDown(BSTNode node, int currLevel, int level) {
		int counter = 0;
		if (node == null) {
			return 0;
		}
		if (currLevel < level) { // more recursion down to level
			int left = levelDown(node.getLChild(), currLevel+1, level);
			int right = levelDown(node.getRChild(), currLevel+1, level);
			return left + right;
		}
		else if (currLevel == level) { // if level reached
			counter = count(node);
		}
		return counter;
	}
	/**
	 * Helper method to determine whether node should be counted
	 * @param node to be considered
	 * @return 0 is node is null, 1 if it is not
	 */
	private int count(BSTNode node) {
		if (node == null) {
			return 0;
		}
		else { // Has element data
			return 1;
		}

	}
	/**
	 * Read data from a file which contains data of multiple friends 
	 * and insert as a sequence of multiple insertions. 
	 * @param filePath
	 */
	public void loadData(String filePath) {
		File file = new File(filePath); 
		Scanner read = null;
		T tree1 = null;
		T tree2 = null;

		try {
			read = new Scanner(file);
		}
		// catches instance when file not found
		catch (FileNotFoundException e) { 
			System.out.println("File not Found");
		}

		while (read.hasNext()) {
			tree1 = (T) read.next();
			tree2 = (T) read.next();
			insert(tree1,tree2); // inserts into BSTree
		}


	}
	/**
	 * Write data of both of the trees to a file in a 
	 * level order traversal with breadth first search
	 * Perform BFS on the first tree and append the corresponding 
	 * data from the second tree.
	 * @param filePath file to save to
	 */
	public void saveData(String filePath) {
		PrintWriter save = null;
		try {
			save = new PrintWriter(new File(filePath));
		}
		catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
		list = new ArrayList<T>(); // stores value

		for (int i = 0; i <= findHeight(1); i++) { // prints nodes by level
			level(root1, 0, i);
		}

		for (int i = 0; i < list.size(); i+=2) { // prints to file
			save.print(list.get(i)+ " " +list.get(i+1) +"\n");
		}
		save.close();
	}
	/**
	 * Helper method for levelCount(). Recurses down to correct level,
	 * then returns the number of non-null nodes at that level
	 * @param node
	 * @param currLevel
	 * @param level
	 * @return
	 */
	private void level(BSTNode node, int currLevel, int level) {
		if (node == null) {
			return;
		}
		if (currLevel < level) { // more recursion down to level
			level(node.getLChild(), currLevel+1, level);
			level(node.getRChild(), currLevel+1, level);
		}
		else if (currLevel == level) { // if level reached
			list.add(node.getElem()); // add elements to arraylist
			list.add(node.getOuterNode().getElem());
			return;
		}
	}

	/**
	 * Given an attribute ‘oldData’, find and replace it with ‘newData’.										
	 * @param oldData data to find
	 * @param newData data to replace
	 * @throws NullPointerException if attribute is null
	 * @throws IllegalArgumentException if attribute is not found 
	 * in any of the trees
	 */
	public void update(T oldData, T newData)
			throws NullPointerException,IllegalArgumentException {
		if (oldData == null || newData == null) {
			throw new NullPointerException();
		}
		BSTNode removeParent = null;
		BSTNode removeNode = null;
		tree1 = true;
		removeNode = find(root1, oldData);
		if (removeNode == null) {
			removeNode = find(root2, oldData);
			tree1 = false;
		}
		if (removeNode == null) {
			throw new IllegalArgumentException();
		}
		if (removeNode == root1 || removeNode == root2) { // if removing root
			if (!removeNode.hasChildren()) { // without children 
				removeNode.setElem(newData); // set to new data
				nelems--;
				return;
			}
		}
		BSTNode removedOuter = removeNode.getOuterNode();
		// FIRST STEP OF SEARCH 
		if (removeNode.getRChild() != null) { // first check if there is a right subtree
			// GO DOWN LEFT SUBTREES
			nextStepR(removeNode);
		}
		else {
			nextStepL(removeNode);
		}
		this.insertUpdate(newData, removedOuter);
		nelems--;

	}	
	private void nextStepR(BSTNode node) {
		if (node.getRChild().getLChild() == null) {
			if (node.getRChild() == null) {
				node.setRChild(null);
				return;
			}
			node.setElem(node.getRChild().getElem());
			nextStepR(node.getRChild());
		}
		else if(node.getRChild().getLChild() != null) {
			BSTNode replacement = leftMost(node.getRChild().getLChild());
			node.setElem(replacement.getElem());
			if (replacement.hasChildren()) {
				nextStepR(replacement);
			}
		}
	}
	private void nextStepL(BSTNode node) {
		
		if (node.getLChild().getRChild() != null) {
			BSTNode replacement = rightMost(node.getLChild().getRChild());
			node.setElem(replacement.getElem());
			if (replacement.hasChildren()) {
				nextStepL(replacement);
			}
		}
		else if (node.getLChild().getRChild() == null) {
			if (node.getLChild() == null) {
				node.setLChild(null);
				return;
			}
			node.setElem(node.getLChild().getElem());
			nextStepL(node.getLChild());
		}
	}

	private BSTNode leftMost(BSTNode node) {
		BSTNode leftMostNode = null;
		if (node.getLChild() == null) {
			return node;
		}
		else {
			return leftMost(node.getLChild());
		}
	}

	private BSTNode rightMost(BSTNode node) {
		BSTNode rightMostNode = null;
		if (node.getRChild() == null) {
			return node;
		}
		else {
			return rightMost(node.getRChild()); 

		}
	}
	/**
	 * Add elements to corresponding trees
	 * @param firstElem
	 * @param secondElem
	 * @throws NullPointerException if either element is null
	 */
	public void insertUpdate(T firstElem, BSTNode removedOuter) 
			throws NullPointerException{
		if (firstElem == null) {
			throw new NullPointerException();
		}

		if (tree1) {
			add1 = place(root1, firstElem, removedOuter); 
		}
		else { 
			add1 = place(root2, firstElem, removedOuter);

		}
		// inserts any time after root

		add2 = removedOuter;
		add1.setOuterNode(add2); // sets outers after adding nodes
		add2.setOuterNode(add1);

		nelems++;
	}

}
