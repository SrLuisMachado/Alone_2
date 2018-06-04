package principal.interfaz_usuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.Jugador;
import principal.herramientas.DibujoDebug;

public class MenuInferior {
	
	private Rectangle areaInventario;
	private Rectangle bordeAreaInventario;
	
	private Color negroDesaturado;
	private Color rojoClaro;
	private Color rojoOscuro;
	private Color verdeClaro;
	private Color verdeOscuro;
	private Color azulClaro;
	private Color azulOscuro;
	
	public MenuInferior() {
		
		int altoMenu = 64;
		areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);
		bordeAreaInventario = new Rectangle(areaInventario.x, areaInventario.y - 1, areaInventario.width, 1);
		
		negroDesaturado = new Color(23, 23, 23);
		rojoClaro = new Color(255, 0, 0);
		rojoOscuro = new Color(150, 0, 0);
		verdeClaro = new Color(0, 255, 0);
		verdeOscuro = new Color(0, 150, 0);
		azulClaro = new Color(0, 200, 255);
		azulOscuro = new Color(0, 133, 175);
	}
	
	public void dibujar(final Graphics g) {
		dibujarAreaInventario(g);
		dibujarBarraVitalidad(g);
		dibujarBarraResistencia(g);
		dibujarBarraExperiencia(g, 75);
		dibujarRanurasObjetos(g);
	}
	
	private void dibujarAreaInventario(final Graphics g) {
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario, negroDesaturado);
		DibujoDebug.dibujarRectanguloRelleno(g, bordeAreaInventario, Color.white);
	}
	
	private void dibujarBarraVitalidad(final Graphics g) {
		final int medidaVertical = 5;
		final int medidaHorizontal = 100;
		final int anchoVision = medidaHorizontal * ElementosPrincipales.jugador.getVida() / ElementosPrincipales.jugador.getVIDA_TOTAL();
		
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical, anchoVision , medidaVertical, rojoClaro);
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 2, anchoVision , medidaVertical, rojoOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "VIT", areaInventario.x + 10, areaInventario.y + medidaVertical * 3);
		DibujoDebug.dibujarString(g, "" + ElementosPrincipales.jugador.getVida(), medidaHorizontal + 40, areaInventario.y + medidaVertical * 3);
	}
	
	private void dibujarBarraResistencia(final Graphics g) {
		final int medidaVertical = 5;
		final int medidaHorizontal = 100;
		final int anchoVision = medidaHorizontal * ElementosPrincipales.jugador.getResistencia() / ElementosPrincipales.jugador.getRESISTENCIA_TOTAL();
		
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 5, anchoVision, medidaVertical, verdeClaro);
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 6, anchoVision, medidaVertical, verdeOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "RST", areaInventario.x + 10, areaInventario.y + medidaVertical * 7);
		DibujoDebug.dibujarString(g, "" + ElementosPrincipales.jugador.getResistencia(), medidaHorizontal + 40, areaInventario.y + medidaVertical * 7);
	}
	
	private void dibujarBarraExperiencia(final Graphics g, final int experiencia) {
		final int medidaVertical = 5;
		final int medidaHorizontal = 100;
		final int anchoVision = medidaHorizontal * experiencia / medidaHorizontal;
		
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 9, anchoVision, medidaVertical, azulClaro);
		DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + medidaVertical * 10, anchoVision, medidaVertical, azulOscuro);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "EXP", areaInventario.x + 10, areaInventario.y + 3 * 18);
		DibujoDebug.dibujarString(g, experiencia + "%", medidaHorizontal + 40, areaInventario.y + 3 * 18);
	}
	
	private void dibujarRanurasObjetos(final Graphics g) {
		final int anchoRanura = 32;
		final int numeroRanuras = 10;
		final int espaciadoRanuras = 10;
		final int anchoTotal = anchoRanura * numeroRanuras + espaciadoRanuras * numeroRanuras;
		final int xInicial = Constantes.ANCHO_JUEGO - anchoTotal;
		final int anchoRanuraYespacio = anchoRanura + espaciadoRanuras;
		
		g.setColor(Color.white);
		
		for ( int i = 0; i < numeroRanuras; i++) {
			int xActual = xInicial + anchoRanuraYespacio * i;
			
			Rectangle ranura = new Rectangle(xActual, areaInventario.y + 8, anchoRanura, anchoRanura);
			DibujoDebug.dibujarRectanguloRelleno(g, ranura);
			DibujoDebug.dibujarString(g, "" + i, xActual + 13, areaInventario.y + 54);
		}
	}
}
