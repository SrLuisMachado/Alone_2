package principal.mapas;

import org.json.simple.JSONObject;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.dijkstra.Dijkstra;
import principal.entes.Enemigo;
import principal.entes.RegistroEnemigos;
import principal.herramientas.CalculadoraDistancia;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.inventario.ContenedorObjetos;
import principal.inventario.Objeto;
import principal.inventario.ObjetoUnicoTiled;
import principal.inventario.RegistroObjetos;
import principal.inventario.armas.Desarmado;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

import org.json.simple.parser.JSONParser;;

public class MapaTiled {
	
	private int anchoMapaTiles;
	private int altoMapaTiles;
	
	private Point puntoInicial;

	private ArrayList<CapaSprites> capasSprites;
	private ArrayList<CapaColisiones> capasColisiones;
	
	private ArrayList<Rectangle> areasColisionOriginales;
	private ArrayList<Rectangle> areasColisionPorActualizacion;
	
	private Sprite[] paletaSprites;
	
	private Dijkstra d;
	
	private ArrayList<ObjetoUnicoTiled> objetosMapa;
	private ArrayList<Enemigo> enemigosMapa;
	
	public MapaTiled(final String ruta) {
		String contenido = CargadorRecursos.leerArchivotxt(ruta);
		
		//ANCHO, ALTO
		JSONObject globalJSON = getObjetoJSON(contenido);
		anchoMapaTiles = getIntJSON(globalJSON, "width");
		altoMapaTiles =  getIntJSON(globalJSON, "height");

		//Punto de inicio (mitad del mapa en el json)
		JSONObject puntoInicial = getObjetoJSON(globalJSON.get("start").toString());
		this.puntoInicial = new Point(getIntJSON(puntoInicial, "x"), getIntJSON(puntoInicial, "y"));

		//Capas
		JSONArray capas = getArrayJSON(globalJSON.get("layers").toString());
		this.capasSprites = new ArrayList<>();
		this.capasColisiones = new ArrayList<>();
		
		//INICIAR CAPAS
		for(int i = 0; i < capas.size(); i++) {
			
			JSONObject datosCapa = getObjetoJSON(capas.get(i).toString());
			
			int anchoCapa = getIntJSON(datosCapa, "width");
			int altoCapa = getIntJSON(datosCapa, "height");
			int xCapa = getIntJSON(datosCapa, "x");
			int yCapa = getIntJSON(datosCapa, "y");
			String tipo = datosCapa.get("type").toString();
			
			switch(tipo){
			case "tilelayer":
				JSONArray sprites = getArrayJSON(datosCapa.get("data").toString());
				int[] spritesCapa = new int[sprites.size()];
				for(int j = 0; j < sprites.size(); j++) {
					int codigoSprite = Integer.parseInt(sprites.get(j).toString());
					spritesCapa[j] = codigoSprite - 1;
				}
				this.capasSprites.add(new CapaSprites(anchoCapa, altoCapa, xCapa, yCapa, spritesCapa));
				break;
			case "objectgroup":
				JSONArray rectangulos = getArrayJSON(datosCapa.get("objects").toString());
				Rectangle[] rectangulosCapa = new Rectangle[rectangulos.size()];
				for(int j = 0; j < rectangulos.size(); j++) {
					JSONObject datosRectangulo = getObjetoJSON(rectangulos.get(j).toString());
					
					int x = getIntJSON(datosRectangulo, "x");
					int y = getIntJSON(datosRectangulo, "y");
					int ancho = getIntJSON(datosRectangulo, "width");
					int alto = getIntJSON(datosRectangulo, "height");
					
					Rectangle rectangulo = new Rectangle(x, y, ancho, alto);
					rectangulosCapa[j] = rectangulo;
				}
				this.capasColisiones.add(new CapaColisiones(anchoCapa, altoCapa, xCapa, yCapa, rectangulosCapa));
				break;
			}
		}
		//Combinar colisiones
		areasColisionOriginales = new ArrayList<>();
		for(int i = 0; i < capasColisiones.size(); i++) {
			Rectangle[] rectangulos = capasColisiones.get(i).getColisionables();
			
			for(int j = 0; j < rectangulos.length; j++) {
				areasColisionOriginales.add(rectangulos[j]);
			}
		}
		
		d = new Dijkstra(new Point(10, 10), anchoMapaTiles, altoMapaTiles, areasColisionOriginales);
		
		//Averiguar total de sprites existentes en todas las capas
		JSONArray coleccionesSprites = getArrayJSON(globalJSON.get("tilesets").toString());
		int totalSprites = 0;
		for(int i = 0; i < coleccionesSprites.size(); i++) {
			JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
			totalSprites += getIntJSON(datosGrupo, "tilecount");
		}
		paletaSprites = new Sprite[totalSprites];
		//Asignar sprites necessarios a la paleta a partir de las capas
		for(int i = 0; i < coleccionesSprites.size(); i++) {
			JSONObject datosGrupo = getObjetoJSON(coleccionesSprites.get(i).toString());
			
			String nombreImagen = datosGrupo.get("image").toString();
			int anchoTiles = getIntJSON(datosGrupo, "tilewidth");
			int altoTiles = getIntJSON(datosGrupo, "tileheight");
			HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + nombreImagen, anchoTiles, altoTiles, false);
			
			int primerSpriteColeccion = getIntJSON(datosGrupo, "firstgid") - 1;
			int ultimoSpriteColeccion = primerSpriteColeccion + getIntJSON(datosGrupo, "tilecount") - 1;
			
			for(int j = 0; j < this.capasSprites.size(); j++) {
				CapaSprites capaActual = this.capasSprites.get(j);
				int[] spritesCapa = capaActual.getArraySprites();

				for(int k = 0; k < spritesCapa.length; k++) {
					int idSpriteActual = spritesCapa[k];
					if(idSpriteActual >= primerSpriteColeccion && idSpriteActual <= ultimoSpriteColeccion) { 
						if(paletaSprites[idSpriteActual] == null) {
							paletaSprites[idSpriteActual] = hoja.getSprite(idSpriteActual - primerSpriteColeccion);
							
						}
					}
				}
			}
		}
		//Obtener objetos
		objetosMapa = new ArrayList<>();
		JSONArray coleccionObjetos = getArrayJSON(globalJSON.get("objetos").toString());
		for(int i = 0; i < coleccionObjetos.size(); i++) {
			JSONObject datosObjeto = getObjetoJSON(coleccionObjetos.get(i).toString());
			
			int idObjeto = getIntJSON(datosObjeto, "id");
			int cantidadObjeto = getIntJSON(datosObjeto, "cantidad");
			int xObjeto = getIntJSON(datosObjeto, "x");
			int yObjeto = getIntJSON(datosObjeto, "y");
			
			Point posicionObjeto = new Point(xObjeto, yObjeto);
			Objeto objeto = RegistroObjetos.getObjeto(idObjeto);
			ObjetoUnicoTiled objetoUnico = new ObjetoUnicoTiled(posicionObjeto, objeto);
			objetosMapa.add(objetoUnico);
		}
		
		//Obtener Enemigos
		enemigosMapa = new ArrayList<>();
		JSONArray coleccionEnemigos = getArrayJSON(globalJSON.get("enemigos").toString());
		for(int i = 0; i < coleccionEnemigos.size(); i++) {
			JSONObject datosEnemigo = getObjetoJSON(coleccionEnemigos.get(i).toString());
			
			int idEnemigo = getIntJSON(datosEnemigo, "id");
			int xEnemigo = getIntJSON(datosEnemigo, "x");
			int yEnemigo = getIntJSON(datosEnemigo, "y");
			
			Point posicionEnemigo = new Point(xEnemigo, yEnemigo);
			Enemigo enemigo = RegistroEnemigos.getEnemigo(idEnemigo);
			enemigo.setPosicion(posicionEnemigo.x, posicionEnemigo.y);
			enemigosMapa.add(enemigo);
		}
		areasColisionPorActualizacion = new ArrayList<>();
	}
	
	public void actualizar() {
		actualizarAreasColision();
		actualizarRecogidaObjetos();
		actualizarEnemigos();
		actualizarAtaque();
		
		Point punto = new Point(ElementosPrincipales.jugador.getPosicionXInt(), ElementosPrincipales.jugador.getPosicionYInt());
		Point puntoCoincidente = d.getCoordenadasNodoCoincidente(punto);
		d.reiniciarYEvaluar(puntoCoincidente);
	}
	
	private void actualizarAtaque() {
		if(enemigosMapa.isEmpty() || ElementosPrincipales.jugador.getAlcanceActual().isEmpty() || ElementosPrincipales.jugador.getAlmacenEquipo().getArma() instanceof Desarmado) {
			return;
		}
		
		if(GestorControles.teclado.atacando) {
			ArrayList<Enemigo> enemigosAlcanzados = new ArrayList<>();
			
			if(ElementosPrincipales.jugador.getAlmacenEquipo().getArma().getPenetrante()) {
				for(Enemigo enemigo : enemigosMapa) {
					if(ElementosPrincipales.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {
						enemigosAlcanzados.add(enemigo);
					}
				}
			}else {
				Enemigo enemigoCercano = null;
				Double distanciaCercano = null;
				
				for (Enemigo enemigo : enemigosMapa) {
					if(ElementosPrincipales.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {
						Point puntoJugador = new Point(ElementosPrincipales.jugador.getPosicionXInt() /32, ElementosPrincipales.jugador.getPosicionYInt() /32);
						Point puntoEnemigo = new Point(enemigo.getPosicionXInt(), enemigo.getPosicionYInt());
						
						Double distanciaActual = CalculadoraDistancia.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);
						
						if(enemigoCercano == null) {
							enemigoCercano = enemigo;
							distanciaCercano = distanciaActual;
						}else if(distanciaActual < distanciaCercano) {
							enemigoCercano = enemigo;
							distanciaCercano = distanciaActual;
						}
					}
				}
				enemigosAlcanzados.add(enemigoCercano);
			}
			ElementosPrincipales.jugador.getAlmacenEquipo().getArma().atacar(enemigosAlcanzados);
		}
		
		Iterator<Enemigo> iterador = enemigosMapa.iterator();
		
		while(iterador.hasNext()) {
			Enemigo  enemigo = iterador.next();
			
			if(enemigo.getVidaActual() <= 0) {
				iterador.remove();
			}
		}
	}
	
	private void actualizarAreasColision() {
		if(!areasColisionPorActualizacion.isEmpty()) {
			areasColisionPorActualizacion.clear();
		}
		
		for(int i = 0; i < areasColisionOriginales.size(); i++) {
			Rectangle rInicial = areasColisionOriginales.get(i);
			
			int puntoX = rInicial.x - ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
			int puntoY = rInicial.y - ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;
			
			final Rectangle rFinal = new Rectangle(puntoX, puntoY, rInicial.width, rInicial.height);
			
			areasColisionPorActualizacion.add(rFinal);
		}
	}
	
	private void actualizarRecogidaObjetos() {
		if(!objetosMapa.isEmpty()) {
			final Rectangle areaJugador = new Rectangle(ElementosPrincipales.jugador.getPosicionXInt(), ElementosPrincipales.jugador.getPosicionYInt(), Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
			
			for(int i = 0; i < objetosMapa.size(); i++) {
				final ObjetoUnicoTiled objetoActual = objetosMapa.get(i);
				
				final Rectangle posicionObjetoActual = new Rectangle(objetoActual.getPosicion().x, objetoActual.getPosicion().y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
				
				if(areaJugador.intersects(posicionObjetoActual) && GestorControles.teclado.recoger) {
					ElementosPrincipales.inventario.recogerObjeto(objetoActual);
					objetosMapa.remove(i);
				}
			}
		}
	}
	
	private void actualizarEnemigos() {
		if(!enemigosMapa.isEmpty()) {
			for(Enemigo enemigo : enemigosMapa) {
				enemigo.setSiguienteNodo(d.encontrarSiguienteNodoParaEnemigo(enemigo));
				enemigo.actualizar(enemigosMapa);
			}
		}
	}
	
	public void dibujar(Graphics g) {
		for(int i = 0; i < capasSprites.size(); i++) {
			int[] spritesCapa = capasSprites.get(i).getArraySprites();
			
			for(int y = 0; y < altoMapaTiles; y++) {
				for(int x = 0; x < anchoMapaTiles; x++) {
					int idSpriteActual = spritesCapa[x + y * anchoMapaTiles];
					if(idSpriteActual != -1) {
						int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
						int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;
						
						if (puntoX < 0 - Constantes.LADO_SPRITE || puntoX > Constantes.ANCHO_JUEGO || puntoY < 0 - Constantes.LADO_SPRITE || puntoY > Constantes.ALTO_JUEGO - 65) {
								continue;
						}
						DibujoDebug.dibujarImagen(g, paletaSprites[idSpriteActual].getImagen(), puntoX, puntoY);
					}
				}
			}
		}
		for(int i = 0; i < objetosMapa.size(); i++) {
			ObjetoUnicoTiled objetoActual = objetosMapa.get(i);
			
			int puntoX = objetoActual.getPosicion().x - ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
			int puntoY = objetoActual.getPosicion().y - ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;
			
			if (puntoX < 0 - Constantes.LADO_SPRITE || puntoX > Constantes.ANCHO_JUEGO || puntoY < 0 - Constantes.LADO_SPRITE || puntoY > Constantes.ALTO_JUEGO - 65) {
				continue;
			}
			
			DibujoDebug.dibujarImagen(g, objetoActual.getObjeto().getSprite().getImagen(), puntoX,puntoY);
		}
		
		for(int i = 0; i < enemigosMapa.size(); i++) {
			Enemigo enemigo = enemigosMapa.get(i);
			
			int puntoX = enemigo.getPosicionXInt() - ElementosPrincipales.jugador.getPosicionXInt() + Constantes.MARGEN_X;
			int puntoY = enemigo.getPosicionYInt() - ElementosPrincipales.jugador.getPosicionYInt() + Constantes.MARGEN_Y;
			if (puntoX < 0 - Constantes.LADO_SPRITE || puntoX > Constantes.ANCHO_JUEGO || puntoY < 0 - Constantes.LADO_SPRITE || puntoY > Constantes.ALTO_JUEGO - 65) {
				continue;
			}
			enemigo.dibujar(g, puntoX, puntoY);
		}
	}
	
	private JSONObject getObjetoJSON(final String codigoJSON) {
		JSONParser lector = new JSONParser();
		JSONObject objetoJSON = null;
		
		try {
			Object recuperado = lector.parse(codigoJSON);
			objetoJSON = (JSONObject) recuperado;
		} catch(ParseException e) {
			System.out.println("Posicion: " + e.getPosition());
			System.out.println(e);
		}
		return objetoJSON;
	}
	
	private JSONArray getArrayJSON(final String codigoJSON) {
		JSONParser lector = new JSONParser();
		JSONArray arrayJSON = null;
		
		try {
			Object recuperado = lector.parse(codigoJSON);
			arrayJSON = (JSONArray) recuperado;
		} catch(ParseException e) {
			System.out.println("Posicion: " + e.getPosition());
			System.out.println(e);
		}
		return arrayJSON;
	}
	
	/**
	 * Devuelve el valor clave del JSONObject
	 * @param objetoJSON objeto donde buscamos la clave
	 * @param clave Nombre del parametro a buscar
	 * @return Devuelve el valor encontrado
	 */
	private int getIntJSON(final JSONObject objetoJSON, final String clave) {
		return Integer.parseInt(objetoJSON.get(clave).toString());
	}
	
	public Point getPosicionInicial() {
		return puntoInicial;
	}
	
	public ArrayList<Rectangle> getAreasColisionPorActualizacion() {
		return areasColisionPorActualizacion;
	}
	
	public Rectangle getBordes(final int posicionX, final int posicionY) {
		int x = Constantes.MARGEN_X - posicionX + ElementosPrincipales.jugador.getAncho();
		int y = Constantes.MARGEN_Y - posicionY + ElementosPrincipales.jugador.getAlto();
		int ancho = anchoMapaTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAncho() * 2;
		int alto = altoMapaTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAlto() * 2;

		return new Rectangle(x, y, ancho, alto);
	}
}
