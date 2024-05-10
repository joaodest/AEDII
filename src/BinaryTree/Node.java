package BinaryTree;

public class Node {
	Node dir, esq;
	int element;
	
	public Node(int element) {
		this(element, null, null);
	}
	public Node(int element, Node esq, Node dir) {
		this.element = element;
		this.esq = esq;
		this.dir = dir;
	}
}
