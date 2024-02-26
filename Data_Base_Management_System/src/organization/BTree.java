package organization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BTree {
	private final int order;
	private int height;
	private Node root;
	
	public BTree(int order) {
		this.height = 1;
		this.root = new Node(true);
		this.order = order;
	}

	/**
	 * Search a key in the B-Tree,
	 * 
	 * @param key the key to search
	 * @return the node where the key is located, null if not found
	 */
	public Node search(int key) {
		return this.search(root, key);
	}
	
	public Node search(Node node, int key) {
		if (node.keys.contains(key))
			return node;
		else {
			if (node.isLeaf)
				return null;
			else
				return search(Objects.requireNonNull(findNextNode(node, key)), key);
		}
	}
	
	public void insert(int key) {
		this.insert(root, key);
	}

	public void insert(Node node, int key) {
		if (node.isLeaf){
			if (!node.isFull())
				node.insertKey(key);
			else
				splitNode(node, key);
			}
		else {
			insert(Objects.requireNonNull(findNextNode(node, key)), key);
		}
	}
	
	/**
	 * This method split a Node in two when we need to insert a key, but it is full
	 * 
	 *	It will be split into two nodes, with the first half of the keys
	 *	that remain in the old node n1, the second half of the keys that go into a new adjacent
	 *	node n2, and the median key, together with the pointer to n2, that is inserted into
	 *	the father node n of n1 
	 *	If n1 is the root and therefore has no parent, it creates it and tree height increases
	 * @param n1 the node to split
	 * @param key the key to insert
	 */
	public void splitNode(Node n1, int key){
		Node n2 = new Node(n1.isLeaf);
		List<Node> n1NewChildren = new ArrayList<>();
		n1.insertKey(key);
		List<Integer> keysToDistribute = new ArrayList<>(n1.keys);
		n1.keys = new ArrayList<>();
		int median = (keysToDistribute.get(keysToDistribute.size() / 2));
		for (int i = 0; i < keysToDistribute.size(); i++) {
			if (i < keysToDistribute.indexOf(median)) n1.insertKey(keysToDistribute.get(i));
			if (i > keysToDistribute.indexOf(median)) n2.insertKey(keysToDistribute.get(i));
		}
		if (!n1.isLeaf) {
			for (int i = 0; i < n1.children.size(); i ++) {
				if (i <= keysToDistribute.indexOf(median))n1NewChildren.add(n1.children.get(i));
				if (i > keysToDistribute.indexOf(median))n2.insertChild(n1.children.get(i));
			}
			n1.children = n1NewChildren;
		}
		if (n1 == root) {
			n1.setParent(new Node(false));
			root = n1.parent;
			height ++;
		}
		n1.parent.insertChild(n2);
		n2.setParent(n1.parent);
		if (!n1.parent.isFull())
			n1.parent.insertKey(median);
		else
			splitNode(n1.parent, key);
	}
	
	/**
	 * @param node the current node being considered
	 * @param key the key for which the next child node is to be found
	 * @return the next child node to traverse based on the key
	 */
	private Node findNextNode(Node node, int key) {
		if (key < node.keys.get(0))
			return node.children.get(0);
		if (key > node.keys.get(node.keys.size() - 1))
			return node.children.get(node.children.size() - 1);
		for (int i = 0; i < node.keys.size() - 1; i++) {
			if (node.keys.get(i) < key && key <  node.keys.get(i+1))
				return node.children.get(i+1);
		}
		return null;
	}

	/**
	 * Root method for deletion
	 * @param key
	 */
	public void delete(int key) {
		delete(root, key);
	}

	/**
	 * Recursive method to delete an element
	 * If the concerned node does not respect the b-tree constraints anymore after the deletion,
	 * then its methode call borrow or merge methods according to the case
	 * 
	 * @param node
	 * @param key
	 */
	private void delete(Node node, int key) {
		if (node == null) {
			return;
		}

		if (!node.keys.contains(key)) {
			Node nextNode = findNextNode(node, key);
			if (nextNode == null)
				return;
			delete(nextNode, key);

			if (nextNode.keys.isEmpty() && !nextNode.isRoot()) {
				borrowOrMerge(nextNode);
			}

			return;
		}

		if (node.isLeaf) {
			node.keys.remove(Integer.valueOf(key));
			if (node == root && node.keys.isEmpty()) {
				root = null;
				height = 0;
			}
			else if (!node.isRoot() && node.keys.size() < order / 2) {
				// Borrow or merge from siblings
				borrowOrMerge(node);
			}
		} else if (node.keys.size() > order / 2 || !node.isRoot()){
			int index = node.keys.indexOf(key);
			int keyToTakeFromChild = node.children.get(index).keys.remove(node.children.get(index).keys.size() - 1);

			node.keys.remove(Integer.valueOf(key));
			node.keys.add(keyToTakeFromChild);
		} else {
			Node lastNode = node.children.get(0);
			while (!lastNode.isLeaf)
				lastNode = lastNode.children.get(lastNode.children.size()-1);

			int keyToMove = lastNode.keys.remove(lastNode.keys.size()-1);

			node.keys.add(keyToMove);
			node.keys.remove(Integer.valueOf(key));

			Node nodeToTest = lastNode;
			while (!nodeToTest.isRoot())
				if (nodeToTest.keys.size() < order / 2) {
					borrowOrMerge(nodeToTest);
					nodeToTest = nodeToTest.parent;
				}
		}
	}

	/**
	 * Let m be the size of a node
	 * Each node must have at least m/2 elements
	 * 
	 * If the node of the deleted elements now has less than m/2 elements,
	 * we must borrow an element from a sibling
	 * Therefore, the sibling must have at least m/2 + 1 elements
	 * If none of the sibling satisfies this condition
	 * Then we merge the current node with one of the siblings
	 * 
	 * @param node
	 */
	private void borrowOrMerge(Node node) {
		int index = node.getParentChildIndex();

		if (index > 0 && node.parent.children.get(index - 1).keys.size() > order / 2) {
			// Borrow from left sibling
			borrowFromLeftSibling(node, index);
		} else if (index < node.parent.children.size() - 1 && node.parent.children.get(index + 1).keys.size() > order / 2) {
			// Borrow from right sibling
			borrowFromRightSibling(node, index);
		} else if (index > 0) {
			// Merge with left sibling
			mergeWithLeftSibling(node, index);
		} else {
			// Merge with right sibling
			mergeWithRightSibling(node, index);
		}
	}

	/**
	 * @param node
	 * @param index
	 */
	private void borrowFromLeftSibling(Node node, int index) {
		Node leftSibling = node.parent.children.get(index - 1);
		int borrowedKey = leftSibling.keys.remove(leftSibling.keys.size() - 1);
		node.parent.keys.add(0, borrowedKey);

		int keyToMoveToChild = node.parent.keys.remove(1);
		node.keys.add(0, keyToMoveToChild);

		if (!leftSibling.isLeaf) {
			Node borrowedChild = leftSibling.children.remove(leftSibling.children.size() - 1);
			node.children.add(0, borrowedChild);
			borrowedChild.parent = node;
		}
	}

	private void borrowFromRightSibling(Node node, int index) {
		Node rightSibling = node.parent.children.get(index + 1);
		int borrowedKey = rightSibling.keys.remove(0);
		node.parent.keys.add(borrowedKey);

		int keyToMoveToChild = node.parent.keys.remove(1);
		node.keys.add(keyToMoveToChild);

		if (!rightSibling.isLeaf) {
			Node borrowedChild = rightSibling.children.remove(0);
			node.children.add(borrowedChild);
			borrowedChild.parent = node;
		}
	}

	private void mergeWithLeftSibling(Node node, int index) {
		Node leftSibling = node.parent.children.get(index - 1);

		// Move key from parent to node
		int parentKey = node.parent.keys.remove(index - 1);
		node.keys.add(0, parentKey);

		// Move keys and children from leftSibling to node
		node.keys.addAll(0, leftSibling.keys);
		node.children.addAll(0, leftSibling.children);

		// Update parent's children list
		node.parent.children.remove(index - 1);

		// Update parent's keys list
		if (node.parent.keys.isEmpty()) {
			if (node.parent == root) {
				root = node;
				height--;
			} else {
				mergeWithParent(node.parent);
			}
		}
	}

	private void mergeWithRightSibling(Node node, int index) {
		Node rightSibling = node.parent.children.get(index + 1);

		// Move key from parent to node
		int parentKey = node.parent.keys.remove(index);
		node.keys.add(parentKey);

		// Move keys and children from rightSibling to node
		node.keys.addAll(rightSibling.keys);
		node.children.addAll(rightSibling.children);

		// Update parent's children list
		node.parent.children.remove(index + 1);

		// Update parent's keys list
		if (node.parent.keys.isEmpty()) {
			if (node.parent == root) {
				root = node;
				height--;
			} else {
				mergeWithParent(node.parent);
			}
		}
	}

	/**
	 * This method manages the case when the parent node does not satisfies the
	 * B-Tree conditions anymore.
	 * It is recursive: stops when the next parent satisfies conditions
	 * 
	 * @param parent
	 */
	private void mergeWithParent(Node parent) {
		int index = parent.getParentChildIndex();
		Node leftSibling = parent.children.get(index - 1);
		Node rightSibling = parent.children.get(index + 1);

		// Move key from parent to leftSibling
		int parentKey = parent.keys.remove(index - 1);
		leftSibling.keys.add(parentKey);

		// Move keys and children from rightSibling to leftSibling
		leftSibling.keys.addAll(rightSibling.keys);
		leftSibling.children.addAll(rightSibling.children);

		// Update parent's children list
		parent.children.remove(index + 1);

		if (parent.keys.isEmpty()) {
			if (parent == root) {
				root = leftSibling;
				height--;
			} else {
				mergeWithParent(parent);
			}
		}
	}
	
	class Node {
		private Node parent;
		private List<Integer> keys; 
		private List<Node> children;
		private final boolean isLeaf;

		public Node(boolean isLeaf) {
			this.keys = new ArrayList<>();
			this.children = new ArrayList<>();
			this.isLeaf = isLeaf;
		}
		
		public boolean isFull() {
			return keys.size() >= order - 1;
		}

		public boolean isRoot() {
			return parent == null;
		}
	
		public int max() {
			return keys.get(keys.size() - 1);
		}
	
		public int min() {
			return keys.get(0);
		}
		
		private void setParent(Node parent) {
			this.parent = parent;
			parent.insertChild(this);
		}
		
		private void insertKey(int key) {
			if (!keys.contains(key)) {
				keys.add(key);
				keys.sort(Comparator.naturalOrder());
			}
		}
	
		private void insertChild(Node child) {
			if (!children.contains(child)) {
				children.add(child);
				children.sort(new NodeComparator());
			}
		}

		public int getParentChildIndex() {
			if (parent == null) {
				throw new IllegalStateException("Node has no parent.");
			}
			return parent.children.indexOf(this);
		}
		
		@Override
		public String toString() {
			return "" + keys;
		}
	}
}