package tpeProg;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TunelesGreedy {
	
	private GrafoDirigido<Integer> grafo;
	private HashSet<Integer> estacionesSolucion;
	private int cantidadCandidatosEvaluados; 
	
	public TunelesGreedy(GrafoDirigido<Integer> grafo) {
		this.grafo = grafo;
		this.estacionesSolucion = new HashSet<>(); 
		this.cantidadCandidatosEvaluados = 0;
	}
	
	public String buscarSolucion() {
		Timer timer = new Timer();
		timer.start();
		
		LinkedList<Arco<Integer>> solucion = new LinkedList<>();
		LinkedList<Arco<Integer>> tuneles = obtenerTuneles();
		ComparadorArcos comparador = new ComparadorArcos();
		tuneles.sort(comparador);

		Arco<Integer> primerTunel = tuneles.getFirst();
		solucion.add(primerTunel);
		this.estacionesSolucion.add(primerTunel.getVerticeOrigen());
		this.estacionesSolucion.add(primerTunel.getVerticeDestino());
		tuneles.removeFirst();
		this.cantidadCandidatosEvaluados++;
		
		while (!tuneles.isEmpty() && !esSolucion(solucion) ) {
			cantidadCandidatosEvaluados++;
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
			return escribirRetorno(solucion, true, timer.stop());
		} else {
			return escribirRetorno(solucion, false, timer.stop());
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
	
	//Es factible si en mi solucion actual ya llego a alguna de las dos estaciones del nuevo tunel, pero no a ambas,
	//ya que sino estaria construyendo 2 tuneles entre 2 estaciones
	private boolean esFactible(Arco<Integer> tunel) {
		return
				(
						this.estacionesSolucion.contains(tunel.getVerticeOrigen()) ||
						this.estacionesSolucion.contains(tunel.getVerticeDestino())
				)
				&&
				!(
						this.estacionesSolucion.contains(tunel.getVerticeOrigen()) &&
						this.estacionesSolucion.contains(tunel.getVerticeDestino())
				);
	}
	
	private String escribirRetorno(LinkedList<Arco<Integer>> solucion, boolean encontroSolucion, double tiempo) {
		String retorno = "Greedy\n";
		if (encontroSolucion) {
			int kmTotales = 0;
			for (Arco<Integer> tunel : solucion) {
				retorno += "E" + tunel.getVerticeOrigen() + "-E" + tunel.getVerticeDestino() + ",";
				kmTotales += tunel.getEtiqueta();
			}
			retorno += "\nKm totales de la solución: " + kmTotales + "km\n";
			retorno += "Costo temporal de la busqueda de solución: " + tiempo + " segundo/s\n";
			retorno += "Cantidad de candidatos evaluados por el Greedy: " + this.cantidadCandidatosEvaluados + " candidatos";
		} else {
			retorno += "No encontró solución.";
		}
		return retorno;
	}

}
