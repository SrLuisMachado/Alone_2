package principal.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DatosDebug;
import principal.herramientas.DibujoDebug;
/**
 * 
 * @author Luis Ernesto
 *Clase para saber la posicion y el click del raton
 */
public class Raton extends MouseAdapter {
/**
 * variable para saber la posicion del cursor
 */
	private final Cursor cursor;
	/**
	* variable para saber la posicion en Point
	 */
	private Point posicion;
	/**
	* variable que se utilizara para saber si se hizo click o no
	 */
	private boolean click;
	/**
	 * variableque se utilizara para saber si se hizo click derecho o no
	 */
	private boolean clickDerecho;
	/**
	 * Metodo que detecta el movimiento del raton sobre la ventana
	 * @param sd
	 */
	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();
		
		BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.RUTA_ICONO_RATON);
		
		Constantes.LADO_CURSOR = icono.getWidth();
		
		Point punta = new Point(0,0);
		
		this.cursor = configuracion.createCustomCursor(icono, punta, "Cursor por defecto");
		
		posicion = new Point();
		actualizarPosicion(sd);
		
		click = false;
		clickDerecho = false;
	}
	/**
	 * metodo que llama el metodo que actualiza la posicion del raton
	 * @param sd
	 */
	public void actualizar(final SuperficieDibujo sd) {
		actualizarPosicion(sd);
	}
	/**
	 * metodo que dibuja los registros de donde estaba el raton
	 * @param g
	 */
	public void dibujar(final Graphics g) {
		DatosDebug.enviarDato("Raton X: " + posicion.getX());
		DatosDebug.enviarDato("Raton Y: " + posicion.getY());
	}
	/**
	 * metodo que la localizacion del cursor
	 * @return
	 */
	public Cursor getCursor() {
		return this.cursor;
	}
	/**
	 * metodo que actualiza la posicion donde se registra el raton
	 * @param sd
	 */
	private void actualizarPosicion(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
		
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		
		posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
	}
	/**
	 * metodo que obtiene la posicion del cursor
	 * @return posicion
	 */
	public Point getPuntoPosicion() {
		return posicion;
	}
	
	/**
	 * metodo que crea un rectangulo para detectar la posicion de la imagen del raton
	 * @return area del raton
	 */
	public Rectangle getRectanguloPosicion() {
		final Rectangle area = new Rectangle(posicion.x, posicion.y, 1, 1);
		
		return area;
	}
	/**
	 * metodo para detectar el click del raton
	 */
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			click = true;
		}else if(SwingUtilities.isRightMouseButton(e)) {
			clickDerecho = true;
		}
	}
	/**
	 * metodo para detectar la liberacion del click del raton
	 */
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)) {
			click = false;
		}else if(SwingUtilities.isRightMouseButton(e)) {
			clickDerecho = false;
		}
	}
	/**
	 * metodo que devuelve si se hace click o no
	 * @return true or false
	 */
	public boolean getClick() {
		return click;
	}
	/**
	 * metodo que devuelve si se a hecho click derecho
	 * @return true or false
	 */
	public boolean getClickDerecho() {
		return clickDerecho;
	}
}
