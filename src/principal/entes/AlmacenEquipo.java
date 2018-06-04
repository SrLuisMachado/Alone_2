package principal.entes;

import principal.inventario.armas.Arma;
/**
 * 
 * @author Luis Ernesto
 *Clase que atribulle metodos a las armas del avatar
 */
public class AlmacenEquipo {
/**
 * variable que Instancia la clase Arma
 */
	private Arma arma;

	/**
	 * metodo contructor inicia variables
	 * @param arma
	 */
	public AlmacenEquipo(final Arma arma) {
		this.arma = arma;
	}
	/**
	 * metodo Arma devuelve el arma utilizada
	 * @return Arma
	 */
	public Arma getArma() {
		return arma;
	}
	
	/**
	 * cambia el Arma
	 * @param arma
	 */
	public void setArma(final Arma arma) {
		this.arma = arma;
	}
}
