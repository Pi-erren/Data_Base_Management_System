package organization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
	 * @param key
	 * @return the node where the key is located, null if not found
	 */
	public Node search(int key) {
		return this.search(root, key);
	}
	
	public Node search(Node node, int key) {
		if (node.keys.contains(key)) {
			return node;
		}
		else {
			if (node.isLeaf) return null;
			else return search(findNextNode(node, key), key);
		}
	}
	
	public void insert(int key) throws NodeException {
		this.insert(root, key);
	}

	public void insert(Node node, int key)throws NodeException{
		if (node.isLeaf){
			if (!node.isFull()) node.insertKey(key);
			else splitNode(node, key);
			}
		else {
			insert(findNextNode(node, key), key);
		}
	}
	
	
	/**
	 * This method split a Node in two when we need to insert a key but it is full
	 * (Reminder: a node is full when it has order - 1 elements)
	 * 
	 *	It will be split into two nodes, with the first half of the keys
	 *	that remain in the old node n1, the second half of the keys that go into a new adjacent
	 *	node n2, and the median key, together with the pointer to n2, that is inserted into
	 *	the father node n of n1 
	 *	If n1 is the root and therefore has no parent, it creates it and tree heigh increases
	 * @param node
	 * @param key
	 */
	public void splitNode(Node n1, int key){
		Node n2 = new Node(n1.isLeaf);
		List<Node> n1NewChildren = new ArrayList<>();
		n1.insertKey(key);
		List<Integer> keysToDistribute = new ArrayList<>(n1.keys);
		n1.keys = new ArrayList<>();
		int median = (
				keysToDistribute.size() % 2 == 0 ? keysToDistribute.get(keysToDistribute.size() / 2):
				keysToDistribute.get((int) (keysToDistribute.size() / 2))
		);
		for (int i = 0; i < keysToDistribute.size(); i++) {
			if (i < keysToDistribute.indexOf(median))n1.insertKey(keysToDistribute.get(i));
			if (i > keysToDistribute.indexOf(median))n2.insertKey(keysToDistribute.get(i));
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
		if (!n1.parent.isFull()) {
			n1.parent.insertKey(median);
		}
		else {
			splitNode(n1.parent, key);
		}	
	}
	
	/**
	 * @param node
	 * @param key
	 * @return
	 */
	private Node findNextNode(Node node, int key) {
		if (key < node.keys.get(0)) return node.children.get(0);
		if (key > node.keys.get(node.keys.size() - 1)) return node.children.get(node.children.size() - 1);
		for (int i = 0; i < node.keys.size() - 1; i++) {
			if (node.keys.get(i) < key && key <  node.keys.get(i+1)) return node.children.get(i+1);
		}
		return null;
	}
	
	class Node {
		private Node parent;
		private List<Integer> keys; 
		private List<Node> children;
		private boolean isLeaf;

		public Node(boolean isLeaf) {
			this.keys = new ArrayList<>();
			this.children = new ArrayList<>();
			this.isLeaf = isLeaf;
		}
		
		public boolean isFull() {
			return keys.size() >= order - 1;
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
		
		@Override
		public String toString() {
			return "" + keys;
		}
	}
}