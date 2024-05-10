class Celula {
	public Celula prox;
	public int element;

	public Celula() {
		this(0);
	}

	public Celula(int n) {
		this.element = n;
		this.prox = null;
	}

}