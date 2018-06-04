package principal.inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import principal.Constantes;
import principal.entes.Enemigo;
import principal.entes.Jugador;
import principal.inventario.Objeto;
import principal.sonido.Sonido;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public abstract class Arma extends Objeto{
	
	public Sonido sonidoDisparo;

	public static HojaSprites hojaArmas = new HojaSprites(Constantes.RUTA_OBJETOS_ARMAS, Constantes.LADO_SPRITE, false);
	
	protected int ataqueMinimo;
	protected int ataqueMaximo;
	protected boolean automatica;
	protected boolean penetrante;
	protected double ataquesPorSegundo;
	protected int actualizacionesParaSiguienteAtaque;
	
	public Arma(int id, String nombre, String descripcion, int ataqueMinimo, int ataqueMaximo, final boolean automatica, final boolean penetrante, final double ataquesPorSegundo, final String rutaSonidoDisparo) {
		super(id, nombre, descripcion);
		
		this.ataqueMinimo = ataqueMinimo;
		this.ataqueMaximo = ataqueMaximo;
		this.automatica = automatica;
		this.penetrante = penetrante;
		this.ataquesPorSegundo = ataquesPorSegundo;
		this.actualizacionesParaSiguienteAtaque = 0;
		this.sonidoDisparo = new Sonido(rutaSonidoDisparo);
	}

	public abstract ArrayList<Rectangle> getAlcane(final Jugador jugador);
	
	public void actualizar() {
		if(actualizacionesParaSiguienteAtaque > 0) {
			actualizacionesParaSiguienteAtaque--;
		}
	}
	
	public void atacar(final ArrayList<Enemigo> enemigos) {
		if(actualizacionesParaSiguienteAtaque > 0) {
			return;
		}
		
		actualizacionesParaSiguienteAtaque = (int) (ataquesPorSegundo * 60);
		
		sonidoDisparo.reproducir();
		
		if(enemigos.isEmpty()) {
			return;
		}
		
		float ataqueActual = getAtaqueMedio();
		
		for(Enemigo enemigo : enemigos) {
			enemigo.perderVida(ataqueActual);
		}
	}
	
	public Sprite getSprite() {		
		return hojaArmas.getSprite(id - 100);
	}
	
	protected int getAtaqueMedio() {
		Random r = new Random();
		
		return r.nextInt(ataqueMaximo - ataqueMinimo) + ataqueMinimo;
	}

	public boolean getAutomatica() {
		return automatica;
	}
	
	public boolean getPenetrante() {
		return penetrante;
	}
}
