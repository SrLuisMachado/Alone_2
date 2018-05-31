package principal.herramientas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;

public class GeneradorTooltip {
	
	public static Point generarTooltip(final Point pinicial) {
		
		final int x = pinicial.x;
		final int y = pinicial.y;
		
		final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);
		
		final Point centroCanvasEscalado = new Point(EscaladorElementos.escalarPuntoArriba(centroCanvas).x, EscaladorElementos.escalarPuntoArriba(centroCanvas).y);
		
		final int margenCursor = 5;
		
		final Point pfinal = new Point();
		
		if(x <= centroCanvasEscalado.x) {
			if(y <= centroCanvasEscalado.y) {
				pfinal.x = x + Constantes.LADO_CURSOR + margenCursor;
				pfinal.y = y + Constantes.LADO_CURSOR + margenCursor;
			} else {
				pfinal.x = x + Constantes.LADO_CURSOR + margenCursor;
				pfinal.y = y - Constantes.LADO_CURSOR - margenCursor;
			}
		} else {
			if(y <= centroCanvasEscalado.y) {
				pfinal.x = x - Constantes.LADO_CURSOR - margenCursor;
				pfinal.y = y + Constantes.LADO_CURSOR + margenCursor;
			} else {
				pfinal.x = x - Constantes.LADO_CURSOR - margenCursor;
				pfinal.y = y - Constantes.LADO_CURSOR - margenCursor;
			}
		}
		
		return pfinal;
	}
	
	public static String getPosicionTooltip(final Point pinicial) {
		final int x = pinicial.x;
		final int y = pinicial.y;
		
		final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);
		
		final Point centroCanvasEscalado = new Point(EscaladorElementos.escalarPuntoArriba(centroCanvas).x, EscaladorElementos.escalarPuntoArriba(centroCanvas).y);
		
		String posicion = "";
		
		if(x <= centroCanvasEscalado.x) {
			if(y <= centroCanvasEscalado.y) {
				posicion = "no";
			} else {
				posicion = "so";
			}
		} else {
			if(y <= centroCanvasEscalado.y) {
				posicion = "ne";
			} else {
				posicion = "se";
			}
		}
		return posicion;
	}

}
