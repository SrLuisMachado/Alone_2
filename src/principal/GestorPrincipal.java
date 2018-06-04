package principal;

import principal.control.GestorControles;
import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaestado.GestorEstados;
import principal.sonido.Sonido;
/**
 * 
 * @author Luis Ernesto
 *Clase Principal inicia y actualiza la aplicacio
 */
public class GestorPrincipal extends Thread{
	
	/**
	 * Variable variable para iniciar o detener la aplicacion(clandestinamente)
	 */
	private static boolean funcionando;
	/**
	 * Variable representa el titulo de la ventana/Juego
	 */
	private String titulo;
	/**
	 * Variable que almacenara el ancho de la pantalla
	 */
	private int ancho;
	/**
	 * Variable que almacenara el alto de la pantalla
	 */
	private int alto;
	/**
	 * Variable instancia SuperficieDibujo
	 */
	public static SuperficieDibujo sd;
	/**
	 * Variable instancia Ventana
	 */
	private static Ventana ventana;
	/**
	 * Variable instancia GestorEstados
	 */
	private GestorEstados ge;
	/**
	 * Variable que calcula los fotogramas de la pantalla
	 */
	private static int fps = 0;
	/**
	 * Variable que calcula las actualizaciones del juego
	 */
	private static int aps = 0;
	/**
	 * Variable instancia y inicia la clase sonido para tener musica de fondo(despues de programar tanto aconsejo apagar el sonido si quieres no quedarte agobiado)
	 */	
	private static Sonido musica = new Sonido("/sonidos/musica.wav");
	
	/**
	 * 
	 * @param titulo asigna el nombre del juego y el titulo de la ventana
	 * @param ancho Ancho de la ventana
	 * @param alto Alto de la ventana
	 */
	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.ancho = ancho;
		this.alto = alto;
	}
	
	/**
	 * metodo main ejecuta los metodos de inicio del juego
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Para OpenGL
		//System.setProperty("sun.java2d.opengl", "True");
		
		//Para DirectX
		//System.setProperty("sun.java2d.d3d", "True");
		//System.setProperty("sun.java2d.ddforcevran", "True");
		
		//Mejora los graficos transparentes
		//System.setProperty("sun.java2d.transaccel", "True");
		
		GestorPrincipal gp = new GestorPrincipal("Alone", Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);
		
		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();
	}


	/**
	 * metodo de inicio clandestino de seguridad
	 */
	private void iniciarJuego() {
		funcionando = true;
		inicializar();
		musica.repetir();
	}

	/**
	 * metodo que inicia las clases encargadas de la imagen en pantalla
	 */
	private void inicializar() {
		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados(sd);
		
	}
	/**
	 * metodo encargado de cerrar el juego(Muerte)
	 */
	public static void cerrarJuego() {
		funcionando = false;
		musica.detener();
		ventana.dispose();
	}
	/**
	 * metodo que inicia el calculo de actualizaciones para que sean 60 por segundo, se calculo los (aps y fps) y se ejecuta los metodos dibujar() y actualizar()
	 */
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
	/**
	 * metodo que llama a la clase que actualiza la imagen y el estado(menu, juego)
	 */
	private void actualizar() {
		if(GestorControles.teclado.menu) {
			ge.cambiarEstadoActual(1);
		}else {
			ge.cambiarEstadoActual(0);
		}
		ge.actualizar();
		sd.actualizar();
	}
	/**
	 * metodo que llama a la clase que dibuja la imagen
	 */
	private void dibujar() {
		sd.dibujar(ge);
		
	}
	/**
	 * obtiene los fps del juego
	 * @return los fotogramas por segundo
	 */
	public static int getFPS() {
		return fps;
	}
	/**
	 * obtiene las aps que se ejecutan
	 * @return las actualiaciones por segundo
	 */
	public static int getAPS() {
		return aps;
	}

}
