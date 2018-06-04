package principal.dijkstra;

import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
/**
 * 
 * @author Luis Ernesto
 *Clase que representa el enemigo como un nodo para la IA
 */
public class Nodo {
/**
 * variable para saber la posicion del nodo
 */
	private Point posicion;
	/**
	 * variable para averiguar la distancia hacia el nodo
	 */
	private double distancia;
	/**
	 * metodo constructor inicia las variables
	 * @param posicion
	 * @param distancia
	 */
	public Nodo(final Point posicion, final double distancia) {
		this.posicion = posicion;
		this.distancia = distancia;
	}
	/**
	 * obtiene el area en pixeles de los sprites
	 * @return Rectangle
	 */
	public Rectangle getAreaPixeles() {
		return new Rectangle(posicion.x * Constantes.LADO_SPRITE, posicion.y * Constantes.LADO_SPRITE, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
	/**
	 * obtiene el area
	 * @return Rectangle
	 */
	public Rectangle getArea() {
		return new Rectangle(posicion.x, posicion.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
	/**
	 * obtiene la posicion 
	 * @return posicion
	 */
	public Point getPosicion() {
		return posicion;
	}
	/**
	 * obtiene la distancia
	 * @return double
	 */
	public double getDistancia() {
		return distancia;
	}
	/**
	 * cambia la distancia por la nueva distancia actualizada
	 * @param distancia
	 */
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
}
