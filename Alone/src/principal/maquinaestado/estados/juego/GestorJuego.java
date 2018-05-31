package principal.maquinaestado.estados.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.entes.Jugador;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.interfaz_usuario.MenuInferior;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;
import principal.sprites.HojaSprites;

public class GestorJuego implements EstadoJuego{
	
	MenuInferior menuInferior;
	
	public GestorJuego() {
		menuInferior = new MenuInferior();
	}
	
	private void recargarjuego() {
		final String ruta = "/mapas/" + ElementosPrincipales.mapa.getSiguienteMapa();
		ElementosPrincipales.mapa = new Mapa(ruta);
	}
	
	public void actualizar() {
		if(ElementosPrincipales.jugador.getLIMITE_ARRIBA().intersects(ElementosPrincipales.mapa.getZonaSalida())) {
			recargarjuego();
		}
		
		ElementosPrincipales.jugador.actualizar();
		ElementosPrincipales.mapa.actualizar();
		
		
	}

	public void dibujar(Graphics g) {
		ElementosPrincipales.mapa.dibujar(g);
		ElementosPrincipales.jugador.dibujar(g);
		menuInferior.dibujar(g);
		
		
		DatosDebug.enviarDato("X = " + ElementosPrincipales.jugador.getPosicionX());
		DatosDebug.enviarDato("Y = " + ElementosPrincipales.jugador.getPosicionY());
		DatosDebug.enviarDato("SiguienteMapa: " + ElementosPrincipales.mapa.getSiguienteMapa());
		DatosDebug.enviarDato("Coordenadas de salida:" + ElementosPrincipales.mapa.getSpriteSalida().getX() + " Y = " + ElementosPrincipales.mapa.getSpriteSalida().getY());
		
		DibujoDebug.dibujarRectanguloRelleno(g, ElementosPrincipales.mapa.getZonaSalida().x, ElementosPrincipales.mapa.getZonaSalida().y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
		//g.fillRect( (int) mapa.getZonaSalida().getX(), (int) mapa.getZonaSalida().getY(), (int) mapa.getZonaSalida().getWidth(), (int) mapa.getZonaSalida().getHeight());
		
	}
	
}
