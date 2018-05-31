package principal.inventario;

import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public abstract class Objeto {

	// public static HojaSprites hojaObjetos = new HojaSprites(Constantes.RUTA_OBJETOS, Constantes.LADO_SPRITE, false);
	
	protected final int id;
	protected final String nombre;
	protected final String descripcion;
	
	protected int cantidad;
	protected int cantidadMaxima;
	
	protected Rectangle posicionMenu;
	protected Rectangle posicionFlotante;
	
	public Objeto(final int id, final String nombre, final String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		
		cantidad = 0;
		cantidadMaxima = 10;
		
		this.posicionMenu = new Rectangle (0,0,0,0);
		this.posicionFlotante = new Rectangle (0,0,0,0);
	}
	
	public Objeto(final int id, final String nombre, final String descripcion, final int cantidad) {
		this(id, nombre, descripcion);
		if(cantidad <= cantidadMaxima){
			this.cantidad = cantidad;
		}
		
	}
	
	public abstract Sprite getSprite();
	
	public boolean incrementarCantidad(final int incremento) {
		boolean incrementado = false;
		
		if(cantidad + incremento <= cantidadMaxima) {
			cantidad += incremento;
			incrementado = true;
		}
		
		return incrementado;
	}
	
	public boolean reducirCantidad(final int reduccion) {
		boolean reducido = false;
		
		if(cantidad + reduccion <= cantidadMaxima) {
			cantidad += reduccion;
			reducido = true;
		}
		
		return reducido;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public Rectangle getPosicionMenu() {
		return posicionMenu;
	}
	
	public Rectangle getPoscionFlotante() {
		return posicionFlotante;
	}
	
	public void setPosicionMenu(final Rectangle posicionMenu) {
		this.posicionMenu = posicionMenu;
	}
	
	public void setPosicionFlotante(final Rectangle posicionFlotante) {
		this.posicionFlotante = posicionFlotante;
	}
}
