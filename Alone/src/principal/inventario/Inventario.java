package principal.inventario;

import java.util.ArrayList;

import principal.Constantes;
import principal.inventario.armas.Arma;
import principal.inventario.consumibles.Consumible;
import principal.sprites.HojaSprites;

public class Inventario {

	public final ArrayList<Objeto> objetos;
	
	public Inventario() {
		objetos = new ArrayList<Objeto>();
		
//		objetos.add(RegistroObjetos.getObjeto(0));
//		objetos.add(RegistroObjetos.getObjeto(1));
//		objetos.add(RegistroObjetos.getObjeto(2));
//		objetos.add(RegistroObjetos.getObjeto(3));
		
		//incrementarObjeto(RegistroObjetos.objetos[0], 10);
	}
	
	public void recogerObjetos(final ContenedorObjetos co) {
		for(Objeto objeto : co.getObjetos()) {
			if(objetoExiste(objeto)) {
				incrementarObjeto(objeto, objeto.getCantidad());
			} else {
				objetos.add(objeto);
			}
		}
	}
	
	public boolean incrementarObjeto(final Objeto objeto, final int cantidad) {
		boolean incrementado = false;
		
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getId() == objeto.getId()) {
				objetoActual.incrementarCantidad(cantidad);
				incrementado = true;
				break;
			}
		}
		return incrementado;
	}
	public boolean objetoExiste(final Objeto objeto) {
		boolean existe = false;
		
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getId() == objeto.getId()) {
				existe = true;
				break;
			}
		}
		return existe;
	}
	
	public ArrayList<Objeto> getConsumibles() {
		ArrayList<Objeto> consumibles = new ArrayList<Objeto>();
		
		for(Objeto objeto : objetos) {
			if(objeto instanceof Consumible) {
				consumibles.add(objeto);
			}
		}
		return consumibles;
	}
	
	public ArrayList<Objeto> getArmas(){
		ArrayList<Objeto> armas = new ArrayList<Objeto>();
		
		for(Objeto objeto : objetos) {
			if(objeto instanceof Arma) {
				armas.add(objeto);
			}
		}
		return armas;
	}
	
	public Objeto getObjeto(final int id) {
		for(Objeto objetoActual : objetos) {
			if(objetoActual.getId() == id) {
				return objetoActual;
			}
		}
		return null;
	}
}
