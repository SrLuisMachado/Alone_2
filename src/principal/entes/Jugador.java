package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.inventario.RegistroObjetos;
import principal.inventario.armas.Arma;
import principal.inventario.armas.Desarmado;
import principal.sprites.HojaSprites;
/**
 * 
 * @author Luis Ernesto
 *Clase del personaje avatar
 */
public class Jugador {
	/**
	 * variable 
	 */
	private double posicionX;
	
	/**
	 * variable 
	 */
	private double posicionY;
	/**
	 * variable 
	 */
	private int direccion;
	/**
	 * variable 
	 */
	private double velocidad = 1;
	/**
	 * variable 
	 */
	private boolean enMovimiento;
	/**
	 * variable 
	 */
	private HojaSprites hs;
	/**
	 * variable 
	 */
	private BufferedImage imagenActual;
	/**
	 * variable 
	 */
	private final int ANCHO_JUGADOR = 16;
	/**
	 * variable 
	 */
	private final int ALTO_JUGADOR = 16;
	/**
	 * variable 
	 */
	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.ANCHO_JUEGO/2 - ANCHO_JUGADOR /2, Constantes.ALTO_JUEGO/2 , ANCHO_JUGADOR, 1); 
	/**
	 * variable 
	 */
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.ANCHO_JUEGO/2 - ANCHO_JUGADOR /2, Constantes.ALTO_JUEGO/2 + ALTO_JUGADOR, ANCHO_JUGADOR, 1); 
	/**
	 * variable 
	 */
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.ANCHO_JUEGO/2 - ANCHO_JUGADOR /2, Constantes.ALTO_JUEGO/2, 1, ALTO_JUGADOR); 
	/**
	 * variable 
	 */
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.ANCHO_JUEGO/2 + ANCHO_JUGADOR /2, Constantes.ALTO_JUEGO/2, 1, ALTO_JUGADOR); 
	/**
	 * variable 
	 */
	private int animacion;
	/**
	 * variable 
	 */
	private int estado;
	/**
	 * variable 
	 */
	private static final int VIDA_TOTAL = 600;
	/**
	 * variable 
	 */
	private int vida = 600;
	/**
	 * variable 
	 */
	private static final int RESISTENCIA_TOTAL = 600;
	/**
	 * variable 
	 */
	private int resistencia = 600;
	/**
	 * variable 
	 */
	private int recuperacion = 0;
	/**
	 * variable 
	 */
	private final int RECUPERACION_MAXIMA = 180;
	/**
	 * variable 
	 */
	private boolean recuperado = true;
	/**
	 * variable 
	 */
	public int limitePeso = 100;
	/**
	 * variable 
	 */
	public int pesoActual = 20;
	/**
	 * variable 
	 */
	private AlmacenEquipo ae;
	/**
	 * variable 
	 */
	private ArrayList<Rectangle> alcanceActual;
	/**
	 * Metodo constructor inicia las variables
	 */
	public Jugador() {
		posicionX = ElementosPrincipales.mapa.getPosicionInicial().getX();
		posicionY = ElementosPrincipales.mapa.getPosicionInicial().getY();
		
		direccion = 0;
		
		enMovimiento = false;
		
		hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.LADO_SPRITE, false);
		
		imagenActual = hs.getSprite(1).getImagen();
		
		animacion = 0;
		estado = 0;
		
		ae = new AlmacenEquipo((Arma) RegistroObjetos.getObjeto(199));
		
		alcanceActual = new ArrayList<>();
	}
	/**
	 * metodo actualiza el avatar
	 */
	public void actualizar() {
		cambiarHojaSprites();
		gestionarVelocidadResistencia();
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();
		actualizarArmas();
	}
	/**
	 * metodo que actualiza las Armas 
	 */
	private void actualizarArmas() {
		if(ae.getArma() instanceof Desarmado) {
			return;
		}
		calcularAlcanceAtaque();
		ae.getArma().actualizar();
	}
	/**
	 * metodo que calcula la distancia de las Armas
	 */
	private void calcularAlcanceAtaque() {
		if(ae.getArma() instanceof Desarmado) {
			return;
		}
		alcanceActual = ae.getArma().getAlcane(this);
	}
	/**
	 * metodo que cambia las hojas de los sprites
	 */
	private void cambiarHojaSprites() {
		if(ae.getArma() instanceof Arma && !(ae.getArma() instanceof Desarmado)) {
			hs = new HojaSprites(Constantes.RUTA_PERSONAJE_PISTOLA, Constantes.LADO_SPRITE, false);
		}
	}
	/**
	 * metodo que gestiona la velocidd para "cansarse"
	 */
	private void gestionarVelocidadResistencia() {
		if(GestorControles.teclado.corriendo && resistencia > 0) {
			velocidad = 2;
			recuperado = false;
			recuperacion = 0;
		} else {
			velocidad = 1;
			if (!recuperado && recuperacion < RECUPERACION_MAXIMA) {
				recuperacion++;
			}
			if (recuperacion == RECUPERACION_MAXIMA && resistencia < 600) {
				resistencia++;
			}
		}
	}
	/**
	 * metodo para cambiar la animacion en el sprite
	 */
	private void cambiarAnimacionEstado() {
		if(animacion < 30) {
			animacion++;
		} else {
			animacion = 0;
		}
		
		if(animacion < 15) {
			estado = 1;
		} else {
			estado = 2;
		}
	}
	/**
	 * metodo que determina la direccion de movimiento del persnaje avatar
	 */
	private void determinarDireccion() {
		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();
		
		if(velocidadX == 0 && velocidadY == 0){
			return;
		}
		
		if (( velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)){
			mover(velocidadX, velocidadY);
		} else {
			//izquierda y arriba
			if(velocidadX == -1 && velocidadY == -1) {
				if(GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			//izquierda y abajo
			if(velocidadX == -1 && velocidadY == 1) {
				if(GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			//derecha y arriba
			if(velocidadX == 1 && velocidadY == -1) {
				if(GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
			//derecha y abajo
			if(velocidadX == 1 && velocidadY == 1) {
				if(GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				}else {
					mover(0, velocidadY);
				}
			}
		}
	}
/**
 * Metodo que calcula la velocidad X lado
 * @return
 */
	private int evaluarVelocidadX() {
		int velocidadX = 0;
		
		if(GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
			velocidadX = -1;
		} else if(GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()) {
			velocidadX = 1;
		}
		
		return velocidadX;
	}
	/**
	 * Metodo que calcula la velocidad Y vertical
	 * @return
	 */
	private int evaluarVelocidadY() {
		int velocidadY = 0;
		
		if(GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
			velocidadY = -1;
		} else if(GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()) {
			velocidadY = 1;
		}
		
		return velocidadY;
	}
	/**
	 * metodo que mueve el mapa para hacer parecer que el personaje se mueve
	 * @param velocidadX
	 * @param velocidadY
	 */
	private void mover(final int velocidadX, final int velocidadY) {
		enMovimiento = true;
		
		cambiarDireccion(velocidadX, velocidadY);
		
		if(!fueraMapa(velocidadX, velocidadY)) {
			if(velocidadX == -1 && !colisionIzquierda(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarResistencia();
			}
			if(velocidadX == 1 && !colisionDerecha(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarResistencia();
			}
			if(velocidadY == -1 && !colisionArriba(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarResistencia();
			}
			if(velocidadY == 1 && !colisionAbajo(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarResistencia();
			}
		}
	}
	/**
	 * metodo que te baja la variable resistencia si mantienes la tecla SHIFT
	 */
	private void restarResistencia() {
		if  (GestorControles.teclado.corriendo && resistencia > 0) {
			resistencia--;
		}
	}
	/**
	 * metodo para detectar la colision por arriba de personaje
	 * @param velocidadY
	 * @return
	 */
	private boolean colisionArriba(int velocidadY) {
		
		for ( int r = 0; r < ElementosPrincipales.mapa.getAreasColisionPorActualizacion().size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.getAreasColisionPorActualizacion().get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
			
			if(LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * metodo para detectar la colision por abajo de personaje
	 * @param velocidadY
	 * @return
	 */
	private boolean colisionAbajo(int velocidadY) {
		for ( int r = 0; r < ElementosPrincipales.mapa.getAreasColisionPorActualizacion().size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.getAreasColisionPorActualizacion().get(r);
			
			int origenX = area.x;
			int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
			
			if(LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * metodo para detectar la colision por la izquierda de personaje
	 * @param velocidadX
	 * @return
	 */
	private boolean colisionIzquierda(int velocidadX) {
		for ( int r = 0; r < ElementosPrincipales.mapa.getAreasColisionPorActualizacion().size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.getAreasColisionPorActualizacion().get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
			
			if(LIMITE_IZQUIERDA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * metodo para detectar la colision por la derecha de personaje
	 * @param velocidadX
	 * @return
	 */
	private boolean colisionDerecha(int velocidadX) {
		for ( int r = 0; r < ElementosPrincipales.mapa.getAreasColisionPorActualizacion().size(); r++) {
			final Rectangle area = ElementosPrincipales.mapa.getAreasColisionPorActualizacion().get(r);
			
			int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;
			int origenY = area.y;
			
			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);
			
			if(LIMITE_DERECHA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * detecta si te acercas a las afueras del mapa y te bloquea
	 * @param velocidadX
	 * @param velocidadY
	 * @return
	 */
	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		
		int posicionFuturaX = (int) posicionX + velocidadX * (int) velocidad;
		int posicionFuturaY = (int) posicionY + velocidadY * (int) velocidad;
		
		final Rectangle bordeMapa = ElementosPrincipales.mapa.getBordes(posicionFuturaX, posicionFuturaY);
		
		final boolean fuera;
		
		if(LIMITE_ARRIBA.intersects(bordeMapa) || LIMITE_ABAJO.intersects(bordeMapa) || LIMITE_IZQUIERDA.intersects(bordeMapa) || LIMITE_DERECHA.intersects(bordeMapa)) {
			fuera = false;
		} else {
			fuera = true;
		}
		
		return fuera;
	}
	/**
	 * metodo que evalua la distancia que quieres seguir
	 * @param velocidadX
	 * @param velocidadY
	 */
	private void cambiarDireccion(final int velocidadX, final int velocidadY) {
		if(velocidadX == -1) {
			direccion = 3;
		} else if(velocidadX == 1) {
			direccion = 2;
		}
		
		if(velocidadY == -1) {
			direccion = 1;
		} else if(velocidadY == 1) {
			direccion = 0;
		}
	}
/**
 * metodo que anima el movimiento
 */
	private void animar() {
		if(!enMovimiento) {
			estado = 0;
			animacion = 0;
		}
		imagenActual = hs.getSprite(direccion, estado).getImagen();
	}
	/**
	 * metodo que dibuja el avatar y el alcance
	 * @param g
	 */
	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		
		DibujoDebug.dibujarImagen(g, imagenActual, centroX, centroY);
		
		if (!alcanceActual.isEmpty()) {
            dibujarAlcance(g);
        }
		
//		AREA DE SPRITE DEL PERSONAJE 32 X 32
		//g.drawRect(centroX, centroY, 32, 32);
		
//		COMPRUEBA EL AREA DE COLISION DEL JUGADOR
//		g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height);
//		g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height);
//		g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
//		g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height);
	}
	/**
	 * 
	 * @param g
	 */
	private void dibujarAlcance(final Graphics g) {
		DibujoDebug.dibujarRectanguloRelleno(g, alcanceActual.get(0));
	}
	/**
	 * cambia la posicion X
	 * @param posicionX
	 */
	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}
	/**
	 * cambia la posicion Y
	 * @param posicionY
	 */
	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}
	/**
	 * obtiene la posicion X
	 * @return double
	 */
	public double getPosicionX() {
		return posicionX;
	}
	/**
	 * obtiene la posicion Y
	 * @return double
	 */
	public double getPosicionY() {
		return posicionY;
	}
	/**
	 *  obtiene la posicion X en int
	 * @return int
	 */
	public int getPosicionXInt() {
		return (int) posicionX;
	}
	/**
	 * obtiene la posicion Y en int
	 * @return int
	 */
	public int getPosicionYInt() {
		return (int) posicionY;
	}
	/**
	 *  obtiene el ancho del jugador
	 * @return int
	 */
	public int getAncho() {
		return ANCHO_JUGADOR;
	}
	/**
	 * obtiene el alto del jugador
	 * @return int
	 */
	public int getAlto() {
		return ALTO_JUGADOR;
	}
	/**
	 * obtiene el limite de arriba del personaje
	 * @return Rectangle
	 */
	public Rectangle getLIMITE_ARRIBA() {
		return LIMITE_ARRIBA;
	}
	/**
	 * obtiene la resistencia del personaje
	 * @return int
	 */
	public int getResistencia() {
		return resistencia;
	}
	/**
	 * obtiene la resistencia total del personaje(la maxima)
	 * @return int
	 */
	public int getRESISTENCIA_TOTAL() {
		return RESISTENCIA_TOTAL;
	}
	/**
	 * obtiene la vida del personaje
	 * @return int
	 */
	public int getVida() {
		return vida;
	}
	/**
	 * modifica la vida
	 * @param vida
	 */
	public void setVida(int vida) {
		this.vida = vida;
	}
	/**
	 * obtiene la vida maxima del personaje
	 * @return
	 */
	public int getVIDA_TOTAL() {
		return VIDA_TOTAL;
	}
	/**
	 * obtiene el alamcen de equipo
	 * @return AlmacenEquipo
	 */
	public AlmacenEquipo getAlmacenEquipo() {
		return ae;
	}
	/**
	 * obtiene la direccion del jugador
	 * @return int
	 */
	public int getDireccion() {
		return direccion;
	}
	/**
	 * obtiene el alcance actual del arma
	 * @return Array
	 */
	public ArrayList<Rectangle> getAlcanceActual(){
		return alcanceActual;
	}
	/**
	 * obtiene la posicion del jugador avatar
	 * @return Rectangle
	 */
	public Rectangle getAreaPosicional() {
		return new Rectangle((int) posicionX, (int) posicionY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}
}
