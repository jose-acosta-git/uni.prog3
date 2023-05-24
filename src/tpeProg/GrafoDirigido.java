package tpeProg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

public class GrafoDirigido<T> implements Grafo<T> {
	
	private HashMap<Integer, HashMap<Integer, T>> vertices;
	
	public GrafoDirigido() {
		this.vertices = new HashMap<>();
	}

	/*
	 * Complejidad: O(1): agregar un elemento a un
	 * HashMap tiene complejidad computacional constante
	 * */
	@Override
	public void agregarVertice(int verticeId) {
		this.vertices.put(verticeId, new HashMap<>());
	}

	/*
	 * Complejidad: O(1): eliminar un elemento
	 * de un HashMap tiene complejidad computacional constante
	 * */
	@Override
	public void borrarVertice(int verticeId) {
		vertices.remove(verticeId);
	}

	/*
	 * Complejidad O(1):
	 * 		contieneVertice => HashMap.containsKey: O(1)
	 * 		HashMap.get: O(1)
	 * 		HashMap.put: O(1)
	 * */
	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		if (this.contieneVertice(verticeId1)) {
			vertices.get(verticeId1).put(verticeId2, etiqueta);
		}
	}

	/*
	 * Complejidad O(1):
	 * 		contieneVertice => HashMap.containsKey: O(1)
	 * 		HashMap.get: O(1)
	 * 		HashMap.remove: O(1)
	 * */
	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1)) {
			vertices.get(verticeId1).remove(verticeId2);
		}
	}

	/*
	 * Complejidad O(1): el metodo ContainsKey
	 * de un HashMap tiene complejidad constante
	 * */
	@Override
	public boolean contieneVertice(int verticeId) {
		return vertices.containsKey(verticeId);
	}
	
	/*
	 * Complejidad O(1):
	 *		contieneVertice => HashMap.containsKey: O(1)
	 *		HashMap.get: O(1)
	 *		HashMap.containsKey: O(1)
	 * */
	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1)) {
			return vertices.get(verticeId1).containsKey(verticeId2);
		} return false;
	}
	
	/*
	 * Complejidad: O(1)
	 * 		contieneVertice => HashMap.containsKey: O(1)
	 * 		HashMap.get: O(1)
	 * */
	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
		if (this.contieneVertice(verticeId1) && this.vertices.get(verticeId1).containsKey(verticeId2)) {
			return new Arco<T>(verticeId1, verticeId2, this.vertices.get(verticeId1).get(verticeId2));
		}
		return null;
	}

	/*
	 * Complejidad: O(1)
	 * 		La complejidad computacional del metodo size de HashMap en java es constante
	 * */
	@Override
	public int cantidadVertices() {
		return vertices.size();
	}

	/*
	 * Complejidad: O(X) donde X es la cantidad de vertices del grafo,
	 * ya que si bien los metodos get() y size() del HashMap son constantes,
	 * este metodo recorre todos los vertices para poder sumar los arcos
	 * de cada vertice.
	 * */
	@Override
	public int cantidadArcos() {
		int arcos = 0;
		for (Integer vertice : this.vertices.keySet()) {
			arcos += this.vertices.get(vertice).size();
		}
		return arcos;
	}

	/*
	 * Complejidad: O(1), ya que tanto el metodo keySet del HashMap
	 * como el metodo iterator() del Set que devuelve keySet son
	 * de complejidad constante
	 * */
	@Override
	public Iterator<Integer> obtenerVertices() {
		return this.vertices.keySet().iterator();
	}

	/*
	 * Complejidad O(1)
	 * 		contieneVertice => HashMap.containsKey: O(1)
	 * 		HashMap.get: O(1)
	 * 		HashMap.keySet: O(1)
	 * 		Set.iterator() de la implementacion de Set que devuelve HashMap.keySet(): O(1)
	 * */
	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
		if (this.contieneVertice(verticeId)) {
			return this.vertices.get(verticeId).keySet().iterator();
		}
		return null;
	}

	/*
	 * Complejidad O(V * A) donde V es la cantidad de vertices y A la cantidad de arcos del grafo.
	 * Probablemente haya una mejor forma de implementarlo pero la unica que se me
	 * ocurrió es recorrer todos los adyacentes de todos los vertices para instanciar
	 * los Arcos (ya que en esta implementacion no estan instanciados como objetos Arco<T>),
	 * agregarlos a una lista (LinkedList para que al menos el metodo .add()
	 * tenga complejidad constante) y luego le pido a la LinkedList que me devuelva un iterador (O(1))
	 * */
	@Override
	public Iterator<Arco<T>> obtenerArcos() {
		LinkedList<Arco<T>> arcos = new LinkedList<>();
		for (Integer vertice : this.vertices.keySet()) {
			for (Integer adyacente : this.vertices.get(vertice).keySet()) {
				arcos.add(new Arco<T>(vertice, adyacente, this.vertices.get(vertice).get(adyacente)));
			}
		}
		return arcos.iterator();
	}

	/*
	 * Complejidad O(A) donde A es la cantidad de arcos cuyo origen es el vertice
	 * que recibe el metodo como parametro (o la cantidad de vertices adyacentes al mismo).
	 * Nuevamente, debe haber una mejor forma de implementarlo, pero la que a mi
	 * se me ocurrió consiste en:
	 * 		iterar todos los arcos que salen del vertice indicado (es decir iterar los vertices adyacentes al mismo): O(A)
	 * 		ir instanciando los Arcos<T>: O(1)
	 * 		agregarlos a una LinkedList => LinkedList.add(): O(1)
	 * 		pedirle un iterator() a la Linked List => LinkedList.iterator(): O(1)
	 * */
	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
		if (!this.contieneVertice(verticeId)) {
			return null;
		}
		
		LinkedList<Arco<T>> arcos = new LinkedList<>();
		for (Integer adyacente : this.vertices.get(verticeId).keySet()) {
			arcos.add(new Arco<T>(verticeId, adyacente, this.vertices.get(verticeId).get(adyacente)));
		}
		return arcos.iterator();
	}
	
	//Complejidad O(V * A) donde V es la cantidad de vertices y A la cantidad de arcos del grafo, por recorrerlos.
	public String toString() {
		String r = "";
		for (Integer vertice : this.vertices.keySet()) {
			r += "Vertice valor: " + vertice + ". Arcos:\n";
			for (Entry<Integer, T> adyacencia : this.vertices.get(vertice).entrySet()) {
				r += "adyacente: " + adyacencia.getKey() + " etiqueta: " + adyacencia.getValue() + "\n";
			}
			r += "\n";
		}
		return r;
	}

}
