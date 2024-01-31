package organization;

import java.util.Comparator;

import organization.BTree.Node;

public class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node n1, Node n2) {
		if (n1.max() < n2.min()) return -1;
		else if(n1.max() == n2.max() && n1.min() == n2.min()) return 0;
		else if (n1.min() > n2.min()) return 1;
		return 0;
	}
}
