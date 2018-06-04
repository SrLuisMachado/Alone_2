package principal.inventario;

import principal.inventario.armas.Desarmado;
import principal.inventario.armas.Pistola;
import principal.inventario.consumibles.Consumible;

public class RegistroObjetos {
	
	public static Objeto getObjeto(final int idObjeto) {
		
		Objeto objeto = null;
		
		switch(idObjeto) {
		//0-99: objetos consumibles
		case 0 :
			objeto = new Consumible(idObjeto,"Kit curacion", "");
			break;
		case 1:
			objeto = new Consumible(idObjeto, "Barra anergetica", "");
			break;
		case 2:
			objeto = new Consumible(idObjeto, "Bebida energetica", "");
			break;
		case 3:
			objeto = new Consumible(idObjeto, "Baston de navidad", "");
			break;
		// 100 - 199: armas
		case 100:
			objeto = new Pistola(idObjeto, "Glock pistol", "", 1, 3, false, true, 0.4);
			break;
		case 199:
			objeto = new Desarmado(idObjeto, "Melee", "", 0, 0, false, false, 0);
			break;
		}
		return objeto;
	}
}
