public class Pilha {
	private Celula topo;

	public Pilha() {
		topo = null;
	}

	public void inserir(int n) {
		Celula temp = new Celula(n);
		temp.prox = topo;
		topo = temp;

		temp = null;
	}

	public void mostrar() {
		System.out.print("[");

		for (Celula i = topo; i != null; i = i.prox) {
			System.out.print(" " + i.element + " ");
		}
		System.out.println("]");
	}

	public int remover() {
		Celula temp = topo;

		int element = temp.element;

		topo = temp.prox;
		temp.prox = null;
		temp = null;

		return element;
	}

	public void somaElementos() {
		int sum = 0;

		for (Celula i = topo; i != null; i = i.prox) {
			sum += i.element;
		}

		System.out.println("soma: " + sum);
	}
	
	public int somaRecursiva() {
		return somaRecursiva(topo);
	}
	
	private int somaRecursiva(Celula atual) {
		if(atual == null) {
			return 0;
		} else {
			return atual.element + somaRecursiva(atual.prox);
		}
	}

}


