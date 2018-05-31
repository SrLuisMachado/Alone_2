package principal;

import principal.entes.Jugador;
import principal.inventario.Inventario;
import principal.mapas.Mapa;

public class ElementosPrincipales {
	
	public static Mapa mapa = new Mapa(Constantes.RUTA_MAPA);
	public static Jugador jugador = new Jugador();
	public static Inventario inventario = new Inventario();

}
