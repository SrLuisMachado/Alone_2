package principal.control;
/**
 * 
 * @author Luis Ernesto
 *clase para generar un metodo de pulscion y liberacion de teclas
 */
public class Tecla {
/**
 * variable para saber cuando hay una pulsacion
 */
	private boolean pulsada = false;
	/**
	 * vaeiable para saber la duracion de la ultim pulsacion
	 */
	private long ultimaPulsacion = System.nanoTime();
	/**
	 * metodo para detectar si se pulsa una tecla
	 */
	public void teclaPulsada() {
		pulsada = true;
		ultimaPulsacion = System.nanoTime();
	}
	/**
	 * metodo para detectar si se libera una tecla
	 */
	public void teclaLiberada() {
		pulsada = false;
	}
	/**
	 * metodo para detectar si se esta pulsando una tecla
	 * @return pulsada
	 */
	public boolean estaPulsada() {
		return pulsada;
	}
	/**
	 * metodo para devolver la duracion de la ultima pulsacion
	 * @return ultimaPulsacion
	 */
	public long getUltimaPulsacion() {
		return ultimaPulsacion;
	}
}
