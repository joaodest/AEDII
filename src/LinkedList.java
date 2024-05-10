
public class LinkedList {
	static Celula primeiro, ultimo;
	int elemento;

	public LinkedList() {
		primeiro = new Celula();
		ultimo = primeiro;
	}

	public LinkedList(int n) {
		this.elemento = n;
	}

	public int length() {
		int count = 0;
		Celula i = primeiro.prox;
		while (i != null) {
			count++;
			i = i.prox;
		}
		return count;
	}
	
	void mostrar() {
		System.out.print("[");
		
		mostrar(primeiro.prox);
		System.out.print("]");
	}
	
	void mostrar(Celula celula) {
		 if(celula == null) {
			 return;
		 }
		 System.out.print(" " + celula.element + " ");
		 mostrar(celula.prox);
	}

	void inserirInicio(int n) {
		Celula temp = new Celula(n);

		temp.prox = primeiro.prox;
		primeiro.prox = temp;

		if (primeiro == ultimo) {
			ultimo = temp;
		}
		temp = null;
	}

	void inserir(int n, int pos) throws Exception {
		int length = length();

		if (pos < 0 || pos > length)
			throw new Exception();
		else if (pos == 0)
			inserirInicio(n);
		else if (pos == length) 
			inserirFim(n);
		else {
			Celula i = primeiro;
			for (int j = 0; j < pos; j++, i = i.prox) {
				Celula temp = new Celula(n);
				temp.prox = i.prox;
				i.prox = temp;
				temp = null;
				i = null;
			}
		}
	}

	void inserirFim(int n) {
		ultimo.prox = new Celula(n);
		ultimo = ultimo.prox;
	}

	int removerInicio() throws Exception {
		if (primeiro == ultimo)
			throw new Exception();

		Celula temp = primeiro;
		primeiro = primeiro.prox;
		int element = primeiro.element;

		temp.prox = null;
		temp = null;

		return element;
	}

	int removerFim() {
		Celula i = primeiro;
		for (; i.prox != ultimo; i = i.prox);
		int element = ultimo.element;
		ultimo = i;
		ultimo.prox = null;
		i = null;

		return element;
	}

	int remover(int pos) throws Exception {
		int length = length();
		if (pos < 0 || pos > length)
			throw new Exception();
		Celula i = primeiro;
		int element;
		for (int j = 0; j < pos; j++, i = i.prox);

		Celula temp = i.prox;
		i.prox = temp.prox;
		element = temp.element;
		
		temp.prox = null;
		temp = i = null;

		return element;
	}
	
	int removeSegundaPosicao() {
		int element;
		
		Celula i = primeiro.prox.prox;
		
		element = i.element;
		primeiro.prox = i.prox;
		
		return element;
	}
}
