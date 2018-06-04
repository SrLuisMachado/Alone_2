package principal.inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;

import principal.entes.Jugador;

public class Desarmado extends Arma{

	public Desarmado(int id, String nombre, String descripcion, int ataqueMinimo, int ataqueMaximo, final boolean automatica, final boolean penetrante, final double ataquesPorSegundo) {
		super(id, nombre, descripcion, ataqueMinimo, ataqueMaximo, automatica, penetrante, ataquesPorSegundo, "/sonidos/mano.wav");
	}

	public ArrayList<Rectangle> getAlcane(Jugador jugador) {
		return null;
	}

	

}
