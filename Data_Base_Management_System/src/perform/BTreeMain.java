package perform;

import java.util.List;

import organization.BTree;
import organization.NodeException;

public class BTreeMain {

	public static void main(String[] args) throws NodeException {
		BTree btree = new BTree(5);

		List<Integer> keys=List.of(10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,79,78,95,25,81,68,60,65);
		for (int key: keys) {
			btree.insert(key);
		}

		System.out.println(btree.search(95)); // is in the btree, should return the node containing it
		System.out.println(btree.search(121)); // is not in the btree, should return null;

		btree.delete(65);
		btree.delete(35);
		btree.delete(79);
		btree.delete(46);

//		BTree btree = new BTree(5);
//
//		List<Integer> keys=List.of(1,9,15,17,19,21,22,23,25,27,30,31,32,39,40,41,47,50,55,56,60,63,72,90);
//		for (int key: keys) {
//			btree.insert(key);
//		}
//
//		btree.delete(21);
//		btree.delete(30);
//		btree.delete(27);
//		btree.delete(22);
//		btree.delete(17);
//		btree.delete(9);
	}
}
