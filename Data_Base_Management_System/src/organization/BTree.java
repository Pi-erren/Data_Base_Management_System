package organization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BTree {
	
	private final int order = 4;
	private int height;
	private Node root;
	
	public BTree() {
		this.height = 1;
		this.root = new Node(true);
	}

	public boolean search(Integer key) {
		return false;
	}
	
	public void insert(Integer key) throws NodeException {
		this.insert(root, key);
	}

	public void insert(Node node, Integer key)throws NodeException{
		if (node.leaf){
			if (!node.isFull()) node.insertKey(key);
//			else split(Integer key);
			}
		else {
			insert(findNextNode(node, key), key);
		}
	}

	public void splitNode(Node node){

	}
	
	/**
	 * @param node
	 * @param key
	 * @return
	 */
	private Node findNextNode(Node node, Integer key) {
		if (key > node.keys.get(0)) return node.children.get(0);
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
		private boolean leaf;

		public Node(boolean leaf) {
			this.keys = new ArrayList<>();
			this.children = new ArrayList<>();
			this.leaf = leaf;
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
	
		private void insertKey(int key) {
			keys.add(key);
			keys.sort(Comparator.naturalOrder());
		}
	
		private void insertChild(Node child) {
			children.add(child);
			children.sort(new NodeComparator());
		}
	}
}