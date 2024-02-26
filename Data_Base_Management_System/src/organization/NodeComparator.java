package organization;

import java.util.Comparator;

import organization.BTree.Node;

/**
 * Compare two B-Tree nodes by comparing their min and max elements
 * [1,4,6,9] is inferior to [12, 14]
 * [4, 8, 10] is superior to [1, 3]
 */
public class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node n1, Node n2) {
		if (n1.max() < n2.min()) return -1;
		else if(n1.max() == n2.max() && n1.min() == n2.min()) return 0;
		else if (n1.min() > n2.min()) return 1;
		return 0;
	}
}
