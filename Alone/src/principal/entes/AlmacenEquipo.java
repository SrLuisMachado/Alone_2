package principal.entes;

import principal.inventario.armas.Arma;

public class AlmacenEquipo {

	private Arma arma;
	//si sobra tiempo armadura
	// si sobra tiempo municion
	
	public AlmacenEquipo(final Arma arma) {
		this.arma = arma;
	}
	
	public Arma getArma() {
		return arma;
	}
	
	//depende principalmente del tiempo
	public void setArma(final Arma arma) {
		this.arma = arma;
	}
}
