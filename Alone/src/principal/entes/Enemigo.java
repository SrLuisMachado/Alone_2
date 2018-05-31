package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.herramientas.CalculadoraDistancia;
import principal.herramientas.DibujoDebug;

public class Enemigo {

	private int idEnemigo;
	
	private double posicionX;
	private double posicionY;
	
	private String nombre;
	private int vidaMaxima;
	private float vidaActual;
	
	public Enemigo(final int idEnemigo, final String nombre, final int vidaMaxima) {
		this.idEnemigo = idEnemigo;
		
		this.posicionX = 0;
		this.posicionY = 0;
		
		this.nombre = nombre;
		this.vidaMaxima = vidaMaxima;
		this.vidaActual = vidaMaxima;
	}
	
	public void actualizar() {
		
	}
	
	public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
		if(vidaActual <= 0) {
			return;
		}
		
		dibujarBarraVida(g, puntoX,puntoY);
		DibujoDebug.dibujarRectanguloContorno(g, getArea(), Color.white);
		dibujarDistancia(g, puntoX, puntoY);
	}
	
	private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {
		DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 5, Constantes.LADO_SPRITE * (int) vidaActual / vidaMaxima, 2, Color.red);
	}
	
	private void dibujarDistancia(final Graphics g, final int puntoX, final int puntoY) {
		Point puntoJugador = new Point(ElementosPrincipales.jugador.getPosicionXInt() / Constantes.LADO_SPRITE, ElementosPrincipales.jugador.getPosicionYInt() / Constantes.LADO_SPRITE);
		
		Point puntoEnemigo = new Point((int) posicionX, (int) posicionY);
		Double distancia = CalculadoraDistancia.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);
		
		DibujoDebug.dibujarString(g, String.format("%.2f" ,distancia), puntoX, puntoY -8);
	}
	
	public void setPosicion(final double posicionX, final double posicionY) {
		this.posicionX = posicionX;
		this.posicionY = posicionY;
	}
	
	public double getPosicionX() {
		return posicionX;
	}
	
	public double getPosicionY() {
		return posicionY;
	}
	
	public int getIdEnemigo() {
		return idEnemigo;
	}
	
	public float getVidaActual() {
		return vidaActual;
	}
	
	public Rectangle getArea() {
		final int puntoX = (int) posicionX * Constantes.LADO_SPRITE - (int) ElementosPrincipales.jugador.getPosicionX() + Constantes.MARGEN_X;
		final int puntoY = (int) posicionY * Constantes.LADO_SPRITE - (int) ElementosPrincipales.jugador.getPosicionY() + Constantes.MARGEN_Y;
		return new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
}
