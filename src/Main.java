import java.util.Random;

import BinaryTree.BinaryTree;

public class Main {

	public static void main(String[] args) throws Exception {
		Pilha pilha = new Pilha();
		LinkedList list = new LinkedList();
		BinaryTree tree = new BinaryTree();

		// tree.caminharCentral();
		// tree.caminharPreOrdem();
		// tree.caminharPosOrdem();

		// System.out.println(tree.getAltura());

		Random rand = new Random();
		rand.setSeed(0);

		for (int i = 0; i <= 100; i++) {
			int val;

			do {
				val = Math.abs(rand.nextInt());
			} while (tree.pesquisar(val) == true);
			
			tree.inserir(val);
			
			if(i % 1000 == 0) {
				double lg = (Math.log(i) / Math.log(2));
				lg *= 1.39;
				System.out.println("Numero de nos: " + i + " --- log(i,2) = " + lg + " --- h = " + tree.getAltura());

			}
			
		}
		tree.caminharPosOrdem();
	}

}
