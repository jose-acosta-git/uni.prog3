package tpeProg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class GrafoDirigido<T> implements Grafo<T> {
	
	private HashMap<Integer, HashMap<Integer, T>> vertices;
	
	public GrafoDirigido() {
		this.vertices = new HashMap<>();
	}

	@Override
	public void agregarVertice(int verticeId) {
		this.vertices.put(verticeId, new HashMap<>());
	}

	@Override
	public void borrarVertice(int verticeId) {
		vertices.remove(verticeId);
	}

	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		if (this.contieneVertice(verticeId1)) {
			vertices.get(verticeId1).put(verticeId2, etiqueta);
		}
	}

	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1)) {
			vertices.get(verticeId1).remove(verticeId2);
		}
	}

	@Override
	public boolean contieneVertice(int verticeId) {
		return vertices.containsKey(verticeId);
	}

	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1)) {
			return vertices.get(verticeId1).containsKey(verticeId2);
		} return false;
	}

	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1) && this.vertices.get(verticeId1).containsKey(verticeId2)) {
			return new Arco<T>(verticeId1, verticeId2, this.vertices.get(verticeId1).get(verticeId2));
		}
		return null;
	}

	@Override
	public int cantidadVertices() {
		return vertices.size();
	}

	@Override
	public int cantidadArcos() {
		int arcos = 0;
		for (Integer vertice : this.vertices.keySet()) {
			arcos += this.vertices.get(vertice).size();
		}
		return arcos;
	}

	@Override
	public Iterator<Integer> obtenerVertices() {
		return new IteradorVertices(this.vertices.keySet());
	}

	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
		if (this.contieneVertice(verticeId)) {
			return new IteradorVertices(this.vertices.get(verticeId).keySet());
		}
		return null;
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos() {
		LinkedList<Arco<T>> arcos = new LinkedList<>();
		for (Integer vertice : this.vertices.keySet()) {
			for (Integer adyacente : this.vertices.get(vertice).keySet()) {
				arcos.add(new Arco<T>(vertice, adyacente, this.vertices.get(vertice).get(adyacente)));
			}
		}
		IteradorArcos<T> it = new IteradorArcos<T>(arcos);
		return it;
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
		if (!this.contieneVertice(verticeId)) {
			return null;
		}
		LinkedList<Arco<T>> arcos = new LinkedList<>();
		for (Integer adyacente : this.vertices.get(verticeId).keySet()) {
			arcos.add(new Arco<T>(verticeId, adyacente, this.vertices.get(verticeId).get(adyacente)));
		}
		IteradorArcos<T> it = new IteradorArcos<T>(arcos);
		return it;
	}

}
