package tpeProg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ServicioCaminos {

	private Grafo<?> grafo;
	private int origen;
	private int destino;
	private int lim;
	//lista que voy a retornar
	private List<List<Integer>> caminos;
	//lista que voy a usar para ir armando los posibles caminos
	private LinkedList<Integer> camino;
	//para marcar los viistados uso un Set ya que tanto el .add como el .remove tienen complejidad 0
	//y no necesito que los elementos esten ordenados
	private Set<Integer> visitados;
	
	// Servicio caminos
	public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
		this.grafo = grafo;
		this.origen = origen;
		this.destino = destino;
		this.lim = lim;
		this.caminos = new LinkedList<>();
		this.camino = new LinkedList<Integer>();
		this.visitados = new HashSet<>();
	}

	public List<List<Integer>> caminos() {
		camino.add(origen);
		buscarCamino(origen, 0);
		return caminos;
	}
	
	//Metodo recursivo que busca un camino dado un vertice y la cantidad de arcos que recorrí
	private void buscarCamino(int verticeActual, int arcosRecorridos) {
		visitados.add(verticeActual);
		
		if (verticeActual == destino) {
			//Agrega la solucion a la lista de soluciones
			caminos.add(new LinkedList<>(camino));
		} else if (arcosRecorridos < lim) {
			Iterator<Integer> adyacentes = this.grafo.obtenerAdyacentes(verticeActual);
			while (adyacentes.hasNext()) {
				Integer adyacente = adyacentes.next();
				if (!visitados.contains(adyacente)) {
					camino.add(adyacente);
					buscarCamino(adyacente, arcosRecorridos + 1);
					camino.remove(camino.size() - 1);				
				}
			}
		}
		visitados.remove(verticeActual);
	}

}
