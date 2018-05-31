package principal.maquinaestado;

import java.awt.Graphics;

import principal.graficos.SuperficieDibujo;
import principal.maquinaestado.estados.juego.GestorJuego;
import principal.maquinaestado.estados.menuJuego.GestorMenu;

public class GestorEstados {
	
	private EstadoJuego[] estados;
	/**
	 * Instancia el estado actual del juego
	 */
	private EstadoJuego estadoActual;

	public GestorEstados(final SuperficieDibujo sd) {
		iniciarEstados(sd);
		iniciarEstadoActual();
	}
	
	private void iniciarEstados(final SuperficieDibujo sd) {
		estados = new EstadoJuego[2];
		estados[0] = new GestorJuego();
		estados[1] = new GestorMenu(sd);
		//Añadir y iniciar los démas estados a medida que se creen
	}
	private void iniciarEstadoActual() {
		estadoActual = estados[0];
		
	}
	
	public void actualizar() {
		estadoActual.actualizar();
	}
	
	public void dibujar(final Graphics g) {
		estadoActual.dibujar(g);
	}
	
	public void cambiarEstadoActual(final int nuevoEstado) {
		estadoActual = estados[nuevoEstado];
	}
	
	public EstadoJuego getEstadoActual() {
		return estadoActual;
	}
}
