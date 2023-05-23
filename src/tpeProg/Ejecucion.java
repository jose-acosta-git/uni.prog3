package tpeProg;

import java.util.LinkedList;

import sun.misc.Queue;

public class Ejecucion {

	public static void main(String[] args) {
		
		GrafoDirigido<Integer> g = new GrafoDirigido<Integer>();
		
		g.agregarVertice(1);
		g.agregarVertice(2);
		g.agregarVertice(3);
		g.agregarVertice(4);
		g.agregarVertice(5);
		g.agregarVertice(6);
		g.agregarVertice(7);
		
		g.agregarArco(1,2,3);
		g.agregarArco(1,3,4);
		g.agregarArco(2,4,6);
		g.agregarArco(2,7,9);
		g.agregarArco(5,3,8);
		g.agregarArco(7,3,10);
		g.agregarArco(7,5,12);
		
		System.out.println(g);
		
		ServicioDFS dfs = new ServicioDFS(g);
		System.out.println(dfs.dfsForest());
		
		ServicioBFS bfs = new ServicioBFS(g);
		System.out.println(bfs.bfsForest());
	}

}
