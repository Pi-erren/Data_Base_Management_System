package perform;

import java.util.List;

import organization.BTree;
import organization.NodeException;

public class BTreeMain {

	public static void main(String[] args) throws NodeException {
		BTree btree = new BTree(5);
//		btree.insert(1);
//		btree.insert(2);
//		btree.insert(3);
//		btree.insert(4);
//		btree.insert(5);
		List<Integer> keys=List.of(10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,79,78,95,25,81,68,60,65);
		for (int key: keys) {
			btree.insert(key);
		}
		
		System.out.println(btree.search(95)); // is in the btree, should return the node containing it
		System.out.println(btree.search(121)); // is not in the btree, should return null;
		int x = 0;
	} 
}
