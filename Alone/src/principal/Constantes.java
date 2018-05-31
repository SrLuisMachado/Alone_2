package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import principal.herramientas.CargadorRecursos;

public class Constantes {

	public static final int LADO_SPRITE = 32;
	public static int LADO_CURSOR = 0;
	
	public static int ANCHO_JUEGO = 640;
	public static int ALTO_JUEGO = 360;
	
//	public static int ANCHO_PANTALLA_COMPLETA = Toolkit.getDefaultToolkit().getScreenSize().width;
//	public static int ALTO_PANTALLA_COMPLETA = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public static int ANCHO_PANTALLA_COMPLETA = 1280;
	public static int ALTO_PANTALLA_COMPLETA = 720;
	
	public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA /ANCHO_JUEGO;
	public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA /ALTO_JUEGO;
	
	public static int CENTRO_VENTANA_X = ANCHO_JUEGO /2;
	public static int CENTRO_VENTANA_Y = ALTO_JUEGO /2;
	
	public static int MARGEN_X = ANCHO_JUEGO /2 - LADO_SPRITE / 2;
	public static int MARGEN_Y = ALTO_JUEGO /2 - LADO_SPRITE / 2;
	
	public static String RUTA_MAPA = "/mapas/01.lm";
	public static String RUTA_ICONO_RATON = "/imagenes/iconos/cursor.png";
	public static String RUTA_PERSONAJE = "/imagenes/hojasPersonajes/avatar-alone.png";
	public static String RUTA_PERSONAJE_PISTOLA = "/imagenes/hojasPersonajes/avatar-alone.png";
//	public static String RUTA_PERSONAJE_PISTOLA = "/imagenes/hojasPersonajes/avatar-armado.png";
	public static String RUTA_ICONO_VENTANA = "/imagenes/iconos/icono.png";
	public static String RUTA_OBJETOS = "/imagenes/hojasObjetos/items.png";
	public static String RUTA_OBJETOS_ARMAS = "/imagenes/hojasObjetos/armas.png";
	public static String RUTA_ENEMIGOS = "/imagenes/hojasPersonajes/avatar-alone.png";
	
	public static Font FUENTE_MATRIX = CargadorRecursos.cargarFuente("/fuentes/matrix.ttf");
	
	public final static Color COLOR_BANNER_SUPERIOR = new Color(0xff0000);
}
