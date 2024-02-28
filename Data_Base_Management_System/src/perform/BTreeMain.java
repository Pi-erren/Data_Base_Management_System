package perform;

import java.util.List;

import organization.BTree;

public class BTreeMain {

	public static void main(String[] args)  {
		boolean  valid;
		
		BTree btree = new BTree(5);

		List<Integer> keys=List.of(10,15,30,27,35,40,45,37,20,50,55,46,71,66,74,85,90,79,78,95,25,81,68,60,65);
		for (int key: keys) {
			btree.insert(key);
		}

		System.out.println(btree.search(95)); // is in the btree, should return the node containing it
		System.out.println(btree.search(121)); // is not in the btree, should return null;
		
		btree.delete(65);
		valid = btree.checkValidity();
		btree.delete(35);
		valid = btree.checkValidity();
		btree.delete(79);
		valid = btree.checkValidity();
		btree.delete(46);
		valid = btree.checkValidity();
		btree.delete(10);
	}
}
