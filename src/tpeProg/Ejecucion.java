package tpeProg;

public class Ejecucion {

	public static void main(String[] args) {
		GrafoDirigido<Integer> grafo = new GrafoDirigido<Integer>();
		CSVReader reader = new CSVReader("datasets/dataset1.txt");
		reader.read(grafo);
		
		System.out.println(grafo);
	}

}
