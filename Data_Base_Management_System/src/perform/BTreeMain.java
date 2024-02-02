package perform;

import organization.BTree;
import organization.NodeException;

public class BTreeMain {

	public static void main(String[] args) throws NodeException {
		BTree btree = new BTree(3);
		btree.insert(1);
		btree.insert(2);
		btree.insert(3);
		btree.insert(4);
		btree.insert(5);
		int x = 0;
	} 
}
