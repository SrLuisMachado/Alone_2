package principal.dijkstra;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.Enemigo;
/**
 * 
 * @author Luis Ernesto
 *clase que ocasiona la IA
 */
public class Dijkstra {
/**
 * variable que calcula el ancho del mapa en tiles 32
 */
	private int anchoMapaTiles;
	/**
	 * variable que calcula el alto del mapa en tiles 32
	 */
	private int altoMapaTiles;
	/**
	 * variable que genera un Array de nodos(enemigos)
	 */
	private ArrayList<Nodo> nodosMapa;
	/**
	 * variable que genera un Array de nodos que aún no han sifo asignados
	 */
	private ArrayList<Nodo> pendientes;
	/**
	 * variable que genera un Array de nodos que ya han sido calculados
	 */
	private ArrayList<Nodo> visitados;
	/**
	 * variable para saber cuando analiar
	 */
	private boolean constructor = true;
	/**
	 * metodo constructor de la IA
	 * @param centroCalculo
	 * @param anchoMapaTiles
	 * @param altoMapaTiles
	 * @param zonasSolidas
	 */
	public Dijkstra(final Point centroCalculo, final int anchoMapaTiles, final int altoMapaTiles, final ArrayList<Rectangle> zonasSolidas) {
		
		this.anchoMapaTiles = anchoMapaTiles;
		this.altoMapaTiles = altoMapaTiles;
		nodosMapa = new ArrayList<>();
		
		for(int y = 0; y < altoMapaTiles; y++) {
			for(int x = 0; x < anchoMapaTiles; x++) {
				final int lado = Constantes.LADO_SPRITE;
				final Rectangle ubicacionNodo = new Rectangle(x * lado, y * lado, lado, lado);
				
				boolean transitable = true;
				for(Rectangle area : zonasSolidas) {
					if(ubicacionNodo.intersects(area)) {
						transitable = false;
						break;
					}
				}
				
				if(!transitable) {
					continue;
				}
				
				Nodo nodo = new Nodo(new Point(x, y), Double.MAX_VALUE);
				nodosMapa.add(nodo);
			}
		}
		pendientes = new ArrayList<>(nodosMapa);
		
		reiniciarYEvaluar(centroCalculo);
		constructor = false;
	}
	/**
	 * obtiene las coordenadas para que se choquen los enemigos con el personaje
	 * @param puntoJugador
	 * @return Point
	 */
	public Point getCoordenadasNodoCoincidente(final Point puntoJugador) {
		Rectangle rectanguloPuntoExacto = new Rectangle(puntoJugador.x / Constantes.LADO_SPRITE, puntoJugador.y / Constantes.LADO_SPRITE, 1, 1);
		
		Point puntoExacto = null;
		
		for(Nodo nodo : nodosMapa) {
			if(nodo.getArea().intersects(rectanguloPuntoExacto)) {
				puntoExacto = new Point(rectanguloPuntoExacto.x, rectanguloPuntoExacto.y);
				return puntoExacto;
			}
		}
		return puntoExacto;
	}
	/**
	 * cambia los nodos pendientes para saber la posicion y distancia
	 * @return Array
	 */
	private ArrayList<Nodo> clonarNodosMapaANodosPendientes(){
		ArrayList<Nodo> nodosClonados = new ArrayList<>();
		for(Nodo nodo : nodosMapa) {
			Point posicion = nodo.getPosicion();
			double distancia = nodo.getDistancia();
			Nodo nodoClonado = new Nodo(posicion, distancia);
			nodosClonados.add(nodoClonado);
		}
		return nodosClonados;
	}
	/**
	 * reinicia y evalua la IA para averiguar la ruta mas cercana
	 * @param centroCalculo
	 */
	public void reiniciarYEvaluar(final Point centroCalculo) {
		if(!constructor) {
			if(visitados.size() == 0) {
				clonarNodosMapaANodosPendientes();
			}else {
				pendientes = new ArrayList<>(visitados);
				for( Nodo nodo : pendientes) {
					nodo.setDistancia(Double.MAX_VALUE);
				}
			}
		}
		definirCentroCalculoPendientes(centroCalculo);
		visitados = new ArrayList<>();
		evaluarHeuristicaGlobal();
	}
	/**
	 * define la el calculo para los nodo pendientes
	 * @param centroCalculo
	 */
	private void definirCentroCalculoPendientes(final Point centroCalculo) {
		for(Nodo nodo : pendientes) {
			if(nodo.getPosicion().equals(centroCalculo)) {
				nodo.setDistancia(0.0);
			}
		}
	}
	/**
	 * metodo que evalua si el Array del nodo necesita cambios
	 */
	private void evaluarHeuristicaGlobal() {
		while(!pendientes.isEmpty()) {
			int cambios = 0;
			
			for(Iterator<Nodo> iterador = pendientes.iterator(); iterador.hasNext();) {
				Nodo nodo = iterador.next();
				if(nodo.getDistancia() == Double.MAX_VALUE) {
					continue;
				}else {
					evaluarHeuristicaVecinos(nodo);
					visitados.add(nodo);
					iterador.remove();
					cambios++;
				}
			}
			
			if(cambios == 0) {
				break;
			}
		}
	}
	/**
	 * evalua si los nodos cercanos hay colisiones y si esta el avatar
	 * @param nodo
	 */
	private void evaluarHeuristicaVecinos(final Nodo nodo) {
		
		int inicialX = nodo.getPosicion().x;
		int inicialY = nodo.getPosicion().y;
		
		final double DISTANCIA_DIAGONAL = 1.42412;
		
		for(int y = inicialY - 1; y < inicialY + 2; y++) {
			for(int x = inicialX - 1; x < inicialX + 2; x++) {
				//Dentro del rango del mapa (-1 en anchoMapaTiles??)
				if(x <= -1 || y <= -1 || x >= anchoMapaTiles || y >= altoMapaTiles) {
					continue;
				}
				
				//omitir el propio nodo
				if(inicialX == x && inicialY == y) {
					continue;
				}
				
				//el nodo existe en la posicion?
				int indiceNodo = getIndiceNodoPosicionPendientes(new Point(x, y));
				if(indiceNodo == -1) {
					continue;
				}
				
				//solo cambia la distancia si es transitable y si no ha sido cambiada
				if(pendientes.get(indiceNodo).getDistancia() == Double.MAX_VALUE -1) {
					
					//distancia recta vs diagonal
					double distancia;
					if(inicialX != x && inicialY != y) {
						distancia = DISTANCIA_DIAGONAL;
					}else {
						distancia = 1;
					}
					pendientes.get(indiceNodo).setDistancia(nodo.getDistancia() + distancia);
				}
			}
		}
	}
	/**
	 * devuelve el nodo cercano
	 * @param nodo
	 * @return Array
	 */
	private ArrayList<Nodo> getNodosVecinos(Nodo nodo){
		int inicialX = nodo.getPosicion().x;
		int inicialY = nodo.getPosicion().y;
		
		ArrayList<Nodo> nodosVecinos = new ArrayList<>();
		
		for(int y = inicialY - 1; y < inicialY + 2; y++) {
			for(int x = inicialX - 1; x < inicialX + 2; x++) {
				if(x <= -1 || y <= -1 || x >= anchoMapaTiles || y >= altoMapaTiles) {
					continue;
				}
				
				if(inicialX == x && inicialY == y) {
					continue;
				}
				
				int indiceNodo = getIndiceNodoPosicionVisitados(new Point(x, y));
				if(indiceNodo == -1) {
					continue;
				}
				nodosVecinos.add(visitados.get(indiceNodo));
			}
		}
		
		return nodosVecinos;
	}
	/**
	 * busca el siguiente nodo
	 * @param enemigo
	 * @return Nodo
	 */
	public Nodo encontrarSiguienteNodoParaEnemigo(Enemigo enemigo) {
		ArrayList<Nodo> nodosAfectados = new ArrayList<>();
		
		Nodo siguienteNodo = null;
		
		for(Nodo nodo : visitados) {
			if(enemigo.getAreaPosicional().intersects(nodo.getAreaPixeles())) {
				nodosAfectados.add(nodo);
			}
		}
		
		if(nodosAfectados.size() == 1) {
			Nodo nodoBase = nodosAfectados.get(0);
			nodosAfectados = getNodosVecinos(nodoBase);
		}
		
		for(int i = 0; i < nodosAfectados.size(); i++) {
			if(i == 0) {
				siguienteNodo = nodosAfectados.get(0);
			}else {
				if(siguienteNodo.getDistancia() > nodosAfectados.get(i).getDistancia()) {
					siguienteNodo = nodosAfectados.get(i);
				}
			}
		}
		return siguienteNodo;
	}
	/**
	 * deuelve la posicion en el array de la posicion de los pendientes
	 * @param posicion
	 * @return int
	 */
	private int getIndiceNodoPosicionPendientes(final Point posicion) {
		for(Nodo nodo : pendientes) {
			if(nodo.getPosicion().equals(posicion)) {
				return pendientes.indexOf(nodo);
			}
		}
		
		return -1;
	}
	/**
	 * deuelve la posicion en el array de la posicion de los visitados
	 * @param posicion
	 * @return int
	 */
	private int getIndiceNodoPosicionVisitados(final Point posicion) {
		for(Nodo nodo : visitados) {
			if(nodo.getPosicion().equals(posicion)) {
				return visitados.indexOf(nodo);
			}
		}
		
		return -1;
	}
	/**
	 * devuelve los nodo Visitados
	 * @return Array
	 */
	public ArrayList<Nodo> getVisitados() {
		return visitados;
	}
	/**
	 * devuelve los nodos pendientes
	 * @return Array
	 */
	public ArrayList<Nodo> getPendientes() {
		return pendientes;
	}
}
