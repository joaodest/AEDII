import BinaryTree.BinaryTree;

public class Main {

	public static void main(String[] args) throws Exception {
		Pilha pilha = new Pilha();
		LinkedList list = new LinkedList();
		BinaryTree tree = new BinaryTree();
		
		tree.inserir(7);
		tree.inserir(2);
		tree.inserir(0);
		tree.inserir(12);

		tree.caminharCentral();
		tree.caminharPreOrdem();
		tree.caminharPosOrdem();
		
	}

}
