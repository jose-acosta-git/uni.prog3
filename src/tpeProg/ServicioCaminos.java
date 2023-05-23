package tpeProg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.prism.shader.FillCircle_Color_AlphaTest_Loader;

public class ServicioCaminos {

	private Grafo<?> grafo;
	private int origen;
	private int destino;
	private int lim;
	private List<List<Integer>> caminos;
	private LinkedList<Integer> camino;
	private Set<Integer> visitados;
	private int arcosRecorridos;
	
	// Servicio caminos
	public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
		this.grafo = grafo;
		this.origen = origen;
		this.destino = destino;
		this.lim = lim;
		this.caminos = new LinkedList<>();
		this.camino = new LinkedList<Integer>();
		this.visitados = new HashSet<>();
		this.arcosRecorridos = 0;
	}

	public List<List<Integer>> caminos() {
		camino.add(origen);
		buscarCamino(origen, 0);
		return caminos;
	}
	
	private void buscarCamino(int verticeActual, int arcosRecorridos) {
		visitados.add(verticeActual);
		
		if (verticeActual == destino) {
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
