package tpeProg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.binding.BooleanBinding;

public class TunelesBacktracking {
	
	private GrafoDirigido<Integer> grafo;
	private LinkedList<Arco<Integer>> tuneles;
	private LinkedList<Arco<Integer>> mejorSolucion;
	private int kmMejorSolucion;
	private int cantidadLlamadosRecursivos;
	
	public TunelesBacktracking(GrafoDirigido<Integer> grafo) {
		this.grafo = grafo;
		this.tuneles = new LinkedList<>();
		this.mejorSolucion = new LinkedList<>();
		this.kmMejorSolucion = 0;
		this.cantidadLlamadosRecursivos = 0;
	}
	
	public String buscarSolucion() {
		Timer timer = new Timer();
		timer.start();
		obtenerArcos();
		LinkedList<Arco<Integer>> solucion = new LinkedList<Arco<Integer>>();
		backtracking(solucion, 0);
		return escribirRetorno(timer.stop());
	}
	
	private void backtracking(LinkedList<Arco<Integer>> solucionActual, int kmActuales) {
		cantidadLlamadosRecursivos++;
		//Caso de corte o primer caso
		if (tuneles.isEmpty()) {
			if (
					esSolucion(solucionActual) &&
					(kmActuales < kmMejorSolucion || this.mejorSolucion.isEmpty())
				) {
				this.mejorSolucion = (LinkedList<Arco<Integer>>) solucionActual.clone();
				this.kmMejorSolucion = kmActuales;
			}
		} else if (!poda(kmActuales)) {
			//Obtiene y elimina el primer tunel
			Arco<Integer> tunel = tuneles.getFirst();
			tuneles.removeFirst();
			
			//Agrega el nuevo tunel a la solucion
			solucionActual.add(tunel);
			kmActuales += tunel.getEtiqueta();
			
			//Llamado recursivo con el tunel en la solucion (decision de SI tomarlo)
			backtracking(solucionActual, kmActuales);
			
			//Elimina el tunel de la solucion
			solucionActual.removeLast();
			kmActuales -= tunel.getEtiqueta();
			
			//Lllamado recusivo sin el tunel en la solucion (decision de NO tomarlo)
			backtracking(solucionActual, kmActuales);
			tuneles.addFirst(tunel);
		}
		
	}
	
	//Verifica que la solucion conecte todas las estaciones
	private boolean esSolucion(LinkedList<Arco<Integer>> solucionActual) {
		//Verifica que la solucion no este vacia
		if (solucionActual.isEmpty()) {
			return false;
		}
		GrafoNoDirigido<Integer> grafoSolucion = solucionAGrafo(solucionActual);
		return esConexo(solucionActual.getFirst().getVerticeOrigen(), grafoSolucion);
	}
	
	//Verifica que la solucion contenga todas las estaciones y que esten conectadas
	private boolean esConexo(Integer verticeRandom, GrafoNoDirigido<Integer> grafoSolucion) {
		HashSet<Integer> visitados = new HashSet<>();
		dfs(verticeRandom, grafoSolucion, visitados);
		
		Iterator<Integer> estacionesTotales = this.grafo.obtenerVertices();
		while (estacionesTotales.hasNext()) {
			if (!visitados.contains(estacionesTotales.next())) {
				return false;
			}
		}
		return true;
	}
	
	//Metodo recursivo que se utiliza para verificar si un grafo es conexo
	private void dfs(Integer estacion, GrafoNoDirigido<Integer> grafoSolucion, HashSet<Integer> visitados) {
		visitados.add(estacion);
		Iterator<Integer> adyacentes = grafoSolucion.obtenerAdyacentes(estacion);
		while (adyacentes.hasNext()) {
			Integer adyacente = adyacentes.next();
			if(!visitados.contains(adyacente)) {
				dfs(adyacente, grafoSolucion, visitados);
			}
		}
	}
	
	//Transforma la solucion en un grafo no dirigido
	private GrafoNoDirigido<Integer> solucionAGrafo(LinkedList<Arco<Integer>> solucion) {
		GrafoNoDirigido<Integer> grafoSolucion = new GrafoNoDirigido<>();
		for (Arco<Integer> arco : solucion) {
			grafoSolucion.agregarVertice(arco.getVerticeOrigen());
			grafoSolucion.agregarVertice(arco.getVerticeDestino());
			grafoSolucion.agregarArco(arco.getVerticeOrigen(), arco.getVerticeDestino(), arco.getEtiqueta());
		}
		return grafoSolucion;
	}
	
	//Transforma el iterator en una lista para la implementacion del backtracking
	private void obtenerArcos() {
		Iterator<Arco<Integer>> iterator = grafo.obtenerArcos();
		while (iterator.hasNext()) {
			tuneles.add(iterator.next());
		}
	}
	
	//Retorna true si ya existe una mejor solucion con kms <= a la actual
	private boolean poda(int kmActuales) {
		return ( !this.mejorSolucion.isEmpty() && this.kmMejorSolucion <= kmActuales );
	}
	
	private String escribirRetorno(double tiempo) {
		String retorno = "Backtracking\n";
		for (Arco<Integer> tunel : this.mejorSolucion) {
			retorno += "E" + tunel.getVerticeOrigen() + "-E" + tunel.getVerticeDestino() + ",";
		}
		retorno += "\nKm totales de la solución: " + this.kmMejorSolucion + "km\n";
		retorno += "Costo temporal de la búsqueda de solución: " + convertirTiempo(tiempo) + " segundo/s\n";
		retorno += "Cantidad de llamados recursivos necesarios en la solución Backtracking: " + this.cantidadLlamadosRecursivos + " llamados";
		return retorno;
	}
	
	private String convertirTiempo(double segundos) {
		String tiempo = "";
		if (segundos > 60) {
			int minutos = (int) segundos / 60;
			int segundosRestantes = (int) segundos % 60;
			tiempo += minutos + " minuto/s " + segundosRestantes;
		} else {
			tiempo += segundos;
		}
		return tiempo;
	}

}
