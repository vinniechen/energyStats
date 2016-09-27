package hw7;

/* NAME: Vinnie Chen
 * PID: A12148745
 * LOGIN: cs12sau
 */

/**
 * BSTree class implements a binary search tree and its nodes
 * @version 1.0
 * @author Vinnie Chen
 * @since 5-17-16
 */

public class BSTree<T extends Comparable<? super T>>{

	private int nelems;       //Number of nodes in the trees
	private BSTNode root1;    //Root of first tree
	private BSTNode root2;    //Root of second tree
	private BSTNode inserted;
	private BSTNode add1;
	private BSTNode add2;
	private BSTNode found;
	private int index;

	protected class BSTNode{

		T elem;
		BSTNode lChild;
		BSTNode rChild;
		BSTNode outer;

		/** lChild and rChild are BSTNodes. They may or not be null depending 
		 * on your code implementation.
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
	public BSTree() {
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
}
