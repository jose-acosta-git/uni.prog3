package tpeProg;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TunelesGreedy {
	
	private GrafoDirigido<Integer> grafo;
	private HashSet<Integer> estacionesSolucion;
	
	public TunelesGreedy(GrafoDirigido<Integer> grafo) {
		this.grafo = grafo;
		this.estacionesSolucion = new HashSet<>(); 
	}
	
	public List<Arco<Integer>> buscarSolucion() {
		LinkedList<Arco<Integer>> solucion = new LinkedList<>();
		LinkedList<Arco<Integer>> tuneles = obtenerTuneles();
		ComparadorArcos comparador = new ComparadorArcos();
		tuneles.sort(comparador);

		Arco<Integer> primerTunel = tuneles.getFirst();
		solucion.add(primerTunel);
		this.estacionesSolucion.add(primerTunel.getVerticeOrigen());
		this.estacionesSolucion.add(primerTunel.getVerticeDestino());
		tuneles.removeFirst();
		
		while (!tuneles.isEmpty() && !esSolucion(solucion) ) {
			Arco<Integer> tunel = tuneles.getFirst();
			tuneles.removeFirst();
			if (esFactible(tunel)) {
				solucion.add(tunel);
				if (!this.estacionesSolucion.contains(tunel.getVerticeOrigen())) {
					this.estacionesSolucion.add(tunel.getVerticeOrigen());
				}
				if (!this.estacionesSolucion.contains(tunel.getVerticeDestino())) {
					this.estacionesSolucion.add(tunel.getVerticeDestino());
				}
			}
		}
		if (esSolucion(solucion)) {
			return solucion;
		} else {
			System.out.println("No se encontró una solución.");
			return null;
		}
	}
	
	private LinkedList<Arco<Integer>> obtenerTuneles() {
		LinkedList<Arco<Integer>> tuneles = new LinkedList<>();
		Iterator<Arco<Integer>> iteradorTuneles = this.grafo.obtenerArcos();
		while (iteradorTuneles.hasNext()) {
			tuneles.add(iteradorTuneles.next());
		}
		return tuneles;
	}
	
	//Es solucion si abarca todas las estaciones
	private boolean esSolucion(LinkedList<Arco<Integer>> solucion) {
		Iterator<Integer> estaciones = this.grafo.obtenerVertices();
		while (estaciones.hasNext()) {
			if (!this.estacionesSolucion.contains(estaciones.next())) {
				return false;
			}
		}
		return true;
	}
	
	//Es factible si en mi solucion actual ya llego a alguna de las dos estaciones del nuevo tunel
	private boolean esFactible(Arco<Integer> tunel) {
		return
				this.estacionesSolucion.contains(tunel.getVerticeOrigen()) ||
				this.estacionesSolucion.contains(tunel.getVerticeDestino()) ;
	}

}
