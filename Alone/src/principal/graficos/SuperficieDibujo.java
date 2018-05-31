package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.control.Teclado;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	private static final long serialVersionUID = -6227038142688953660L;
	
	private int ancho;
	private int alto;
	
	private Raton raton;
	
	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;
		
		this.raton = new Raton(this);
		
		setIgnoreRepaint(true);
		setCursor(raton.getCursor());
		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		addMouseListener(raton);
		setFocusable(true);
		requestFocus();
	}
	
	public void actualizar() {
		raton.actualizar(this);
	}
	
	public void dibujar(final GestorEstados ge) {
		final BufferStrategy buffer = getBufferStrategy();
		
		if(buffer == null) {
			createBufferStrategy(3);
			return;
		}
		
		final Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		
		DibujoDebug.reiniciarContadorObjetos();
		
		g.setFont(Constantes.FUENTE_MATRIX);
		DibujoDebug.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA, Color.black);
//		g.setColor(Color.black);
//		g.fillRect(0, 0, Constantes.ANCHO_JUEGO, Constantes.ALTO_JUEGO);
//		g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
		
		if(Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y !=1.0) {
			g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
		}
		
		ge.dibujar(g);
		
		g.setColor(Color.white);
		DibujoDebug.dibujarString(g, "FPS: " + GestorPrincipal.getFPS(), 20, 20);
		DibujoDebug.dibujarString(g, "APS: " + GestorPrincipal.getAPS(), 20, 30);
		
		DatosDebug.enviarDato("Escala X: " + Constantes.FACTOR_ESCALADO_X);
		DatosDebug.enviarDato("Escala Y: " + Constantes.FACTOR_ESCALADO_Y);
		
		raton.dibujar(g);
		
		DatosDebug.enviarDato("Objetos por FRAME: " + DibujoDebug.getContadorObjetos());
		
		if(GestorControles.teclado.debug) {
			DatosDebug.dibujarDatos(g);
		}else {
			DatosDebug.vaciarDatos();
		}
		
		Toolkit.getDefaultToolkit().sync();
		
		g.dispose();
		
		buffer.show();
	}
	
	public int getAncho() {
		return ancho;
	}
	
	public int getAlto() {
		return alto;
	}
	
	public Raton getRaton() {
		return raton;
	}
}
