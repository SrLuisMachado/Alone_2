package principal.maquinaestado.estados.menuJuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Inventario;
import principal.inventario.Objeto;

public class MenuInventario extends SeccionMenu {

	public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, final EstructuraMenu em) {
		super(nombreSeccion, etiquetaMenu, em);

	}

	public void actualizar() {
		actualizarPosicionesMenu();
	}
	
	private void actualizarPosicionesMenu() {
		if(ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
			return;
		}
		
		for ( int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {
			final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
			
			int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();
			
			ElementosPrincipales.inventario.getObjeto(idActual).setPosicionMenu(new Rectangle(puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral), puntoInicial.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
			
		}
	}

	public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {
		dibujarLimitePeso(g);

		if (sd.getRaton().getRectanguloPosicion().intersects(EscaladorElementos.escalarRectanguloArriba(barraPeso))) {
			dibujarTooltipPeso(g, sd, em);
		}

		dibujarElementosConsumibles(g,em);
		dibujarPasoPagina(g, em);
	}

	private void dibujarItemsInventario(final Graphics g, EstructuraMenu em) {
		final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);

		g.setColor(Color.red);

		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 12; x++) {
				DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + x * (Constantes.LADO_SPRITE + margenGeneral), puntoInicial.y + y * (Constantes.LADO_SPRITE + margenGeneral), Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			}
		}
	}

	private void dibujarPasoPagina(final Graphics g, EstructuraMenu em) {
		final int anchoBoton = 32;
		final int altoBoton = 16;

		final Rectangle anterior = new Rectangle(em.FONDO.x + em.FONDO.width - margenGeneral * 2 - anchoBoton * 2 - 4, em.FONDO.y + em.FONDO.height - margenGeneral - altoBoton, anchoBoton, altoBoton);
		final Rectangle siguiente = new Rectangle(anterior.x + anterior.width + margenGeneral, anterior.y, anchoBoton, altoBoton);

		g.setColor(Color.blue);

		DibujoDebug.dibujarRectanguloRelleno(g, anterior);
		DibujoDebug.dibujarRectanguloRelleno(g, siguiente);
	}

	private void dibujarElementosConsumibles(final Graphics g, EstructuraMenu em) {

		if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
			return;
		}
		
		final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);
		
		for (int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {
			int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();
			Objeto objetoActual = ElementosPrincipales.inventario.getObjeto(idActual);

			DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(), objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

			DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - 12, puntoInicial.y + 32 - 8, 12, 8, Color.black);

			g.setColor(Color.white);

			String texto = "";

			if (objetoActual.getCantidad() < 10) {
				texto = "0" + objetoActual.getCantidad();
			} else {
				texto = "" + objetoActual.getCantidad();
			}

			g.setFont(g.getFont().deriveFont(10f));
			DibujoDebug.dibujarString(g, texto, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - MedidorString.medirAnchoPixeles(g, texto), puntoInicial.y + 31);
		}
		g.setFont(g.getFont().deriveFont(12f));
	}

	private void dibujarSpritesInventario(final Graphics g, EstructuraMenu em) {
		final Point puntoInicial = new Point(em.FONDO.x + 16, barraPeso.y + barraPeso.height + margenGeneral);

		for (int i = 0; i < ElementosPrincipales.inventario.objetos.size(); i++) {
			DibujoDebug.dibujarImagen(g, ElementosPrincipales.inventario.objetos.get(i).getSprite().getImagen(), puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral), puntoInicial.y);

			g.setColor(Color.black);
			DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - 12, puntoInicial.y + Constantes.LADO_SPRITE - 8, 12, 8);

			g.setColor(Color.white);
			String texto = "" + ElementosPrincipales.inventario.objetos.get(i).getCantidad();
			g.setFont(g.getFont().deriveFont(10f));
			DibujoDebug.dibujarString(g, texto, puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral) + Constantes.LADO_SPRITE - MedidorString.medirAnchoPixeles(g, texto), puntoInicial.y + Constantes.LADO_SPRITE - 1);

		}
		g.setFont(g.getFont().deriveFont(12f));
	}
}
