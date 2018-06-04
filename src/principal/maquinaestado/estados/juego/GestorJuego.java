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
	
	public void actualizar() {
		
		ElementosPrincipales.jugador.actualizar();
		ElementosPrincipales.mapa.actualizar();
		
		
	}

	public void dibujar(Graphics g) {
		ElementosPrincipales.mapa.dibujar(g);
		ElementosPrincipales.jugador.dibujar(g);
		menuInferior.dibujar(g);
		
		
		DatosDebug.enviarDato("X = " + ElementosPrincipales.jugador.getPosicionX());
		DatosDebug.enviarDato("Y = " + ElementosPrincipales.jugador.getPosicionY());
		
	}
	
}
