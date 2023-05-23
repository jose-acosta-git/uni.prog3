package tpeProg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ServicioBFS {

	private Grafo<?> grafo;
	private LinkedList<Integer> fila;
	private LinkedList<Integer> recorrido;
	private Set<Integer> visited;
	
	public ServicioBFS(Grafo<?> grafo) {
		this.grafo = grafo;
		this.fila = new LinkedList<Integer>();
		this.recorrido = new LinkedList<Integer>();
		this.visited = new HashSet<>();
	}
	
	public List<Integer> bfsForest() {
		Iterator<Integer> it = this.grafo.obtenerVertices();
		
		while (it.hasNext()) {
			Integer verticeActual = it.next();
			if (!visited.contains(verticeActual)) {
				this.bfs(verticeActual);
			}
		}
		return recorrido;
	}
	
	private void bfs(Integer vertice) {
		this.visited.add(vertice);
		this.recorrido.add(vertice);
		this.fila.add(vertice);
		
		while (!fila.isEmpty()) {
			Integer v = fila.poll();
			Iterator<Integer> adyacentes = this.grafo.obtenerAdyacentes(v);
			
			while (adyacentes.hasNext()) {
				Integer adyacente = adyacentes.next();
				if (!visited.contains(adyacente)) {
					this.visited.add(adyacente);
					this.recorrido.add(adyacente);
					this.fila.add(adyacente);
				}
			}
		}
	}
	
}
