package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
/**
 * 
 * @author Luis Ernesto
 *Clase que asigna un valor o accion a cada tecla
 */
public class Teclado implements KeyListener {
/**
 * variable que se Instancia de Tecla y asignara a la flecha hacia arriba
 */
	public Tecla arriba = new Tecla();
	/**
	 * variable que se Instancia de Tecla y asignara a la flecha hacia abajo
	 */
	public Tecla abajo = new Tecla();
	/**
	 * variable que se Instancia de Tecla y asignara a la flecha hacia la izquierda
	 */
	public Tecla izquierda = new Tecla();
	/**
	 * variable que se Instancia de Tecla y asignara a la flecha hacia la derecha
	 */
	public Tecla derecha = new Tecla();
	/**
	 * variable que se asignara al shift(para que el personaje corra)
	 */
	public boolean corriendo = false;
	/**
	 * variable que se asignara al f1(para que se muestre todos los objetos de registro)
	 */
	public boolean debug = false;
	/**
	 * variable que se asignara a la M(para que se habra o cierra el menu)
	 */
	public boolean menu = false;
	/**
	 * variable que se asignara a la E(para que el personaje recoga un objeto)
	 */
	public boolean recoger = false;
	/**
	 * variable que se asignara al ESPACIO(para que el personaje ataque)
	 */
	public boolean atacando = false;
	/**
	 * metodo que asigna un valor o accion si se pulsa una tecla
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W :
			arriba.teclaPulsada();
			break;
		case KeyEvent.VK_S :
			abajo.teclaPulsada();
			break;
		case KeyEvent.VK_A :
			izquierda.teclaPulsada();
			break;
		case KeyEvent.VK_D :
			derecha.teclaPulsada();
			break;
		case KeyEvent.VK_E :
			recoger = true; 
			break;
		case KeyEvent.VK_SHIFT :
			corriendo = true;
			break;
		case KeyEvent.VK_F1 :
			debug = !debug;
			break;
		case KeyEvent.VK_M :
			menu = !menu;
			break;
		case KeyEvent.VK_SPACE :
			atacando = true;
			break;
		case KeyEvent.VK_ESCAPE :
			int teclaEsc = JOptionPane.showConfirmDialog(null, "Confirmación para cerrar", "Título de la ventana", JOptionPane.YES_NO_OPTION);
			if (teclaEsc == JOptionPane.YES_OPTION) {
				System.exit(0);	
			}
			break;
		}
	}
	/**
	 * metodo que asigna un valor o accion si se libera una tecla(si dejas de pulsarla)
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W :
			arriba.teclaLiberada();
			break;
		case KeyEvent.VK_S :
			abajo.teclaLiberada();
			break;
		case KeyEvent.VK_A :
			izquierda.teclaLiberada();
			break;
		case KeyEvent.VK_D :
			derecha.teclaLiberada();
			break;
		case KeyEvent.VK_E :
			recoger = false; 
			break;
		case KeyEvent.VK_SHIFT :
			corriendo = false;
			break;
		case KeyEvent.VK_SPACE :
			atacando = false;
			break;
		}
	}
	/**
	 * metodo que se implementa por la Interfaz KeyListener
	 */
	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
