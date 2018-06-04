package principal;

import principal.entes.Jugador;
import principal.inventario.Inventario;
import principal.mapas.Mapa;
import principal.mapas.MapaTiled;

/**
 * 
 * @author Luis Ernesto
 *Clase creada para facilitar la ubicacion de otras clases
 *
 */
public class ElementosPrincipales {
	/**
	 * Variable instancia MapaTiled y así llamand a la clase ElementosPrncipales tener la ubicacion
	 */
	public static MapaTiled mapa = new MapaTiled("/mapas/mapa.json");
	
	/**
	 * Variable instancia Jugador y así llamand a la clase ElementosPrncipales tener la ubicacion
	 */
	public static Jugador jugador = new Jugador();
	
	/**
	 * Variable instancia Inventario y así llamand a la clase ElementosPrncipales tener la ubicacion
	 */
	public static Inventario inventario = new Inventario();

}
