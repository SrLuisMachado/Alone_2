package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import principal.herramientas.CargadorRecursos;

/**
 * 
 * @author Luis Ernesto
 *Clase creada para tener valores que se repetirian bastante en una sola clase y no tener datos duplicados(llamando los datos desde esta clase)
 */
public class Constantes {
	/**
	 * Variable statica para facilitar el tamaño de los sprite
	 */
	public static final int LADO_SPRITE = 32;
	/**
	 * Variable statica para facilitar el tamaño
	 */
	public static int LADO_CURSOR = 0;
	/**
	 * Variable statica para facilitar el tamaño
	 */
	public static int ANCHO_JUEGO = 640;
	/**
	 * Variable statica para facilitar el tamaño
	 */
	public static int ALTO_JUEGO = 360;
	/**
	 * Variables que se utilizan para redimensionar la ventana la primera sería pantalla completa y la segunda a 1280x720y
	 */
//	public static int ANCHO_PANTALLA_COMPLETA = Toolkit.getDefaultToolkit().getScreenSize().width;
//	public static int ALTO_PANTALLA_COMPLETA = Toolkit.getDefaultToolkit().getScreenSize().height;
//	
//	public static int ANCHO_PANTALLA_COMPLETA = 1280;
//	public static int ALTO_PANTALLA_COMPLETA = 720;
	
	/**
	 * Variable que se utilizan para redimensionar el ancho de la ventana segun sus datos
	 */
	public static int ANCHO_PANTALLA_COMPLETA = 640;
	/**
	 * Variable que se utilizan para redimensionar el alto de la ventana segun sus datos
	 */
	public static int ALTO_PANTALLA_COMPLETA = 360;
	/**
	 * Variable se utiliza para saber el tamaño de escalado de X(ancho)
	 */
	public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA /ANCHO_JUEGO;
	/**
	 * Variable se utiliza para saber el tamaño de escalado de Y(alto)
	 */
	public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA /ALTO_JUEGO;
	/**
	 * Variable tiene el valor del centro de la ventana a lo ancho
	 */
	public static int CENTRO_VENTANA_X = ANCHO_JUEGO /2;
	/**
	 * Variable tiene el valor del centro de la ventana a lo alto
	 */
	public static int CENTRO_VENTANA_Y = ALTO_JUEGO /2;
	/**
	 * Variable utilizada para calculo el area de los entes o dibujar en esta determinada posicion
	 */
	public static int MARGEN_X = ANCHO_JUEGO /2 - LADO_SPRITE / 2;
	/**
	 * Variable utilizada para calculo el area de los entes o dibujar en esta determinada posicion
	 */
	public static int MARGEN_Y = ALTO_JUEGO /2 - LADO_SPRITE / 2;
	/**
	 * Variable tiene la direccion a la imagen del cursor
	 */
	public static String RUTA_ICONO_RATON = "/imagenes/iconos/cursor.png";
	/**
	 * Variable tiene la direccion a la imagen del avatar
	 */
	public static String RUTA_PERSONAJE = "/imagenes/hojasPersonajes/avatar-alone.png";
	/**
	 * Variable tiene la direccion a la imagen del avatar con una pistola
	 */
	public static String RUTA_PERSONAJE_PISTOLA = "/imagenes/hojasPersonajes/avatar-armado.png";
	/**
	 * Variable tiene la direccion a la imagen del icono de la aplicacion(icono decoracion)
	 */
	public static String RUTA_ICONO_VENTANA = "/imagenes/iconos/icono.png";
	/**
	 * Variable tiene la direccion a la imagen de los items consumibles (curativos)
	 */
	public static String RUTA_OBJETOS = "/imagenes/hojasObjetos/items.png";
	/**
	 * Variable tiene la direccion a la imagen de la pistola
	 */
	public static String RUTA_OBJETOS_ARMAS = "/imagenes/hojasObjetos/armas.png";
	/**
	 * Variable tiene la direccion a la imagen de los enemigos (solo esta el zobie)
	 */
	public static String RUTA_ENEMIGOS = "/imagenes/hojasEnemigos/zombie.png";
	/**
	 * Variable tiene la direccion a la fuente de texto
	 */
	public static Font FUENTE_MATRIX = CargadorRecursos.cargarFuente("/fuentes/matrix.ttf");
	/**
	 * Variable que almacena un color que use bastante
	 */
	public final static Color COLOR_BANNER_SUPERIOR = new Color(0xff0000);
}
