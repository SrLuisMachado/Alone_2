package principal;

import principal.control.GestorControles;
import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaestado.GestorEstados;

public class GestorPrincipal {
	
	private boolean funcionando;
	private String titulo;
	private int ancho;
	private int alto;
	
	public static SuperficieDibujo sd;
	private Ventana ventana;
	private GestorEstados ge;
	
	private static int fps = 0;
	private static int aps = 0;
	
	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.ancho = ancho;
		this.alto = alto;
	}
	
	
	public static void main(String[] args) {
		GestorPrincipal gp = new GestorPrincipal("Alone", Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
		
		Constantes.ANCHO_JUEGO = 640;
		Constantes.ALTO_JUEGO = 360;
		
		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();
		
	}


	
	private void iniciarJuego() {
		funcionando = true;
		inicializar();
		
	}

	
	private void inicializar() {
		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados(sd);
		
	}


	private void iniciarBuclePrincipal() {
		int actualizacionesAcumuladas = 0;
		int framesAcumulados = 0;
		
		final int NANOSEGUNDOS_POR_SEGUNDO =1000000000;
		final byte ACTUALIZACION_POR_SEGUNDO = 60;
		final double NS_POR_ACTUALIZACION = NANOSEGUNDOS_POR_SEGUNDO/ACTUALIZACION_POR_SEGUNDO;
		
		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();
		
		double tiempoTranscurrido;
		double delta = 0;
		
		while(funcionando){
			final long inicioBucle=System.nanoTime();
			
			tiempoTranscurrido = inicioBucle-referenciaActualizacion;
			referenciaActualizacion=inicioBucle;
			
			delta+=tiempoTranscurrido/NS_POR_ACTUALIZACION;
			
			while(delta >=1) {
			actualizar();
			actualizacionesAcumuladas++;
			delta--;
			}
			
			dibujar();
			framesAcumulados++;
			
			if(System.nanoTime() - referenciaContador > NANOSEGUNDOS_POR_SEGUNDO) {
				
				aps =actualizacionesAcumuladas;
				fps = framesAcumulados;
				
				actualizacionesAcumuladas = 0;
				framesAcumulados = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}

	private void actualizar() {
		if(GestorControles.teclado.menu) {
			ge.cambiarEstadoActual(1);
		}else {
			ge.cambiarEstadoActual(0);
		}
		ge.actualizar();
		sd.actualizar();
	}
	
	private void dibujar() {
		sd.dibujar(ge);
		
	}
	
	public static int getFPS() {
		return fps;
	}
	
	public static int getAPS() {
		return aps;
	}

}
