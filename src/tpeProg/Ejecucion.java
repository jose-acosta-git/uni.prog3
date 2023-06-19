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
		
		//System.out.println("mejor solucion backtracking: " + backtracking.buscarSolucion());
	}
	
	public static void greedy(GrafoDirigido<Integer> grafo) {
		Arco<Integer> menorDistancia = null;	
		Iterator<Arco<Integer>> iterator = grafo.obtenerArcos();
		while(iterator.hasNext()) {
			Arco<Integer> actual = iterator.next();
			if (menorDistancia == null) {
				menorDistancia = actual;
			}
			else if (actual.getEtiqueta() < menorDistancia.getEtiqueta()) {
				menorDistancia = actual;
			}
		}
		System.out.println(menorDistancia);
	}

}
