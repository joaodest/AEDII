package BinaryTree;

public class BinaryTree {
	Node root;
	public BinaryTree() { root = null; }
	
	public void inserir(int n) throws Exception{
		root = inserir(n, root);
	}
	
	Node inserir(int n, Node node) throws Exception{
		if(node == null) 
			node = new Node(n);
		
		else if(n < node.element) 
			node.esq = inserir(n, node.esq);
		
		else if(n > node.element)
			node.dir = inserir(n, node.dir);
		
		else
			throw new Exception("Nao eh possivel inserir elementos repetidos");
		
		return node;
	}
	
	public void caminharCentral() {
		System.out.print("[ ");
		caminharCentral(root);
		System.out.print("]");
	}
	
	void caminharCentral(Node node) {
		if (node != null) {
			caminharCentral(node.esq);
			System.out.print(node.element + " ");
			caminharCentral(node.dir);
		}
	}
	
	public void caminharPreOrdem() {
		System.out.print("[ ");
		caminharPreOrdem(root);
		System.out.print("]");
	}
	
	void caminharPreOrdem(Node node) {
		if (node != null) {
			System.out.print(node.element + " ");
			caminharPreOrdem(node.esq);
			caminharPreOrdem(node.dir);
		}
	}
	public void caminharPosOrdem() {
		System.out.print("[ ");
		caminharPosOrdem(root);
		System.out.print("]");
	}
	
	void caminharPosOrdem(Node node) {
		if (node != null) {
			caminharPosOrdem(node.esq);
			caminharPosOrdem(node.dir);
			System.out.print(node.element + " ");
		}
	}

}
