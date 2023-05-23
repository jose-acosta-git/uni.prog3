package tpeProg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ServicioDFS {

	private Grafo<?> grafo;
	private List<Integer> recorrido;
	private Set<Integer> visited;

	public ServicioDFS(Grafo<?> grafo) {
		this.grafo = grafo;
		this.recorrido = new LinkedList<Integer>();
		this.visited = new HashSet<>();
	}
	
	public List<Integer> dfsForest() {
		Iterator<Integer> it = this.grafo.obtenerVertices();
		
		while (it.hasNext()) {
			Integer verticeActual = it.next();
			if (!visited.contains(verticeActual)) {
				this.dfs(verticeActual);
			}
		}
		return recorrido;
	}
	
	private void dfs(Integer vertice) {
		this.visited.add(vertice);
		this.recorrido.add(vertice);
		Iterator<Integer> adyacentes = this.grafo.obtenerAdyacentes(vertice);
		
		while (adyacentes.hasNext()) {
			Integer adyacente = adyacentes.next();
			if (!this.visited.contains(adyacente)) {
				this.dfs(adyacente);
			}
		}
	}

}
