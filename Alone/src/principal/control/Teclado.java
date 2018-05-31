package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

public class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla izquierda = new Tecla();
	public Tecla derecha = new Tecla();
	
	public boolean corriendo = false;
	public boolean debug = false;
	public boolean menu = false;
	public boolean recoger = false;
	
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
		case KeyEvent.VK_ESCAPE :
//			int teclaEsc = JOptionPane.showConfirmDialog(null, "Confirmación para cerrar", "Título de la ventana", JOptionPane.YES_NO_OPTION);
//			if (teclaEsc == JOptionPane.YES_OPTION) {
				System.exit(0);	
//			}
			break;
		}
	}

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
		}
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
