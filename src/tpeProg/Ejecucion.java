package tpeProg;

import java.util.Iterator;

public class Ejecucion {

	public static void main(String[] args) {
		GrafoDirigido<Integer> grafo = new GrafoDirigido<Integer>();
		CSVReader reader = new CSVReader("datasets/dataset2.txt");
		reader.read(grafo);
		
		TunelesBacktracking backtracking = new TunelesBacktracking(grafo);
		
		TunelesGreedy greedy = new TunelesGreedy(grafo);
		
		System.out.println(greedy.buscarSolucion());
		
		System.out.println();
		
		System.out.println(backtracking.buscarSolucion());
	}

}
