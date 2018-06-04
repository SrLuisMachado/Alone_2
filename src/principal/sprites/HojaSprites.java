package principal.sprites;

import java.awt.image.BufferedImage;

import principal.herramientas.CargadorRecursos;

public class HojaSprites {

	final private int anchoHojaPixeles;
	final private int altoHojaPixeles;

	final private int anchoHojaSprites;
	final private int altoHojaSprites;

	final private int anchoSprites;
	final private int altoSprites;

	final private Sprite[] sprites;

	public HojaSprites(final String ruta, final int sizeSprites, final boolean hojaOpaca) {
		BufferedImage imagen;

		if (hojaOpaca) {
			imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
		} else {
			imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
		}

		anchoHojaPixeles = imagen.getWidth();
		altoHojaPixeles = imagen.getHeight();

		anchoHojaSprites = anchoHojaPixeles / sizeSprites;
		altoHojaSprites = altoHojaPixeles / sizeSprites;

		anchoSprites = sizeSprites;
		altoSprites = sizeSprites;

		sprites = new Sprite[anchoHojaSprites * altoHojaSprites];
		
		rellenarSpritesImagen(imagen);
	}

	public HojaSprites(final String ruta, final int anchoSprites, final int altoSprites, final boolean hojaOpaca) {
		BufferedImage imagen;

		if (hojaOpaca) {
			imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
		} else {
			imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
		}

		anchoHojaPixeles = imagen.getWidth();
		altoHojaPixeles = imagen.getHeight();

		anchoHojaSprites = anchoHojaPixeles / anchoSprites;
		altoHojaSprites = altoHojaPixeles / altoSprites;

		this.anchoSprites = anchoSprites;
		this.altoSprites = altoSprites;

		sprites = new Sprite[anchoHojaSprites * altoHojaSprites];
		
		rellenarSpritesImagen(imagen);
	}

	//rellena el array sprites con el buffer "imagen"
	private void rellenarSpritesImagen(final BufferedImage imagen) {
		for(int y = 0; y < altoHojaSprites; y++) {
			for(int x = 0; x < anchoHojaSprites; x++) {
				final int posicionX = x * anchoSprites;
				final int posicionY = y * altoSprites;
				
				sprites[x+y * anchoHojaSprites] = new Sprite(imagen.getSubimage(posicionX, posicionY, anchoSprites, altoSprites));
			}
		}
	}
	
	public Sprite getSprite(final int indice) {
		return sprites[indice];
	}
	
	public Sprite getSprite(final int x, final int y) {
		return sprites[x+y * anchoHojaSprites];
	}
}
