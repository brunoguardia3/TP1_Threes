package juego;

import java.util.Random;

public class Model {
	private Tablero tablero;
	private int puntuacion;
	private int siguienteValor;
	
	private String sugerenciaActual;
	private final String[] listaSugerencias = {
			"Mantene tu ficha de mayor valor fija en una de las esquinas del tablero.",
		    "Elegí una dirección 'prohibida' (como arriba) y evitá usarla para no desarmar tu esquina.",
		    "Prestá mucha atención a la próxima ficha; te permite planear un paso por adelantado.",
		    "Los números 1 y 2 ocupan espacio vital, ¡tratá de fusionarlos ni bien aparezcan!",
		    "Intentá alinear tus fichas en orden (de mayor a menor) para facilitar fusiones en cadena.",
		    "Evitá que las fichas de valor bajo (1, 2 o 3) queden atrapadas entre fichas gigantes.",
		    "Tu prioridad número uno siempre debe ser mantener la mayor cantidad de celdas vacías posibles.",
		    "Pensá tus movimientos en cascada: un 3 forma un 6, que luego forma un 12...",
		    "Mover todo hacia una esquina es mucho más seguro que dejar las fichas grandes en el centro.",
		    "La paciencia es clave; a veces es mejor postergar una fusión si eso rompe tu estructura.",
		    "Agrupá las fichas medianas cerca de tu ficha más grande para alimentarla rápidamente.",
		    "Si el tablero se llena mucho, enfocate exclusivamente en hacer fusiones chicas para liberar espacio.",
		    "No te apures en deslizar. Evaluá si un movimiento te va a bloquear futuros emparejamientos.",
		    "Intentá que las nuevas fichas nazcan en la zona donde tenés los números más bajos.",
		    "Mantener un patrón de 'serpiente' con tus valores más altos te asegura llegar lejos."
	};

	public Model() {
		this.tablero = new Tablero();
		this.puntuacion = 0;
		this.siguienteValor = generarNumeroAleatoria();
		
		this.sugerenciaActual = generarSugerenciaAleatoria();
	}

	public Tablero getTablero() {
		return tablero;
	}

	public int getPuntuacion() {
		return this.puntuacion = tablero.getPuntajeTotal();
	}

	public int getSiguienteValor() {
		return siguienteValor;
	}

	public void setPuntuacion(int nuevaPuntuacion) {
		this.puntuacion = nuevaPuntuacion;
	}
	
	public String getSugerenciaActual() {
		return sugerenciaActual; 
	}

	public void reiniciarJuego() {
		this.tablero = new Tablero();
		this.puntuacion = 0;
		
		this.sugerenciaActual = generarSugerenciaAleatoria();

	}
	public void moverDerecha() {
		tablero.moverDerecha();
		generarFichaAleatoria("derecha");
	}

	public void moverIzquierda() {
		tablero.moverIzquierda();
		generarFichaAleatoria("izquierda");
	}

	public void moverArriba() {
		tablero.moverArriba();
		generarFichaAleatoria("arriba");
	}

	public void moverAbajo() {
		tablero.moverAbajo();
		generarFichaAleatoria("abajo");
	}

	private int generarNumeroAleatoria() {
		Random random = new Random();

		int[] posiblesValoresFichas = { 1, 2, 3 };

		int indice = random.nextInt(posiblesValoresFichas.length);

		int nroAleatoria = posiblesValoresFichas[indice];

		return nroAleatoria;
	}

	public void generarFichaAleatoria(String direccion) {
		Random random = new Random();

		int[] posFilasVacias = new int[4];
		int[] posColumnasVacias = new int[4];

		int cantCeldasVacias = 0;

		int borde = tablero.getLargoTablero() - 1;

		// Se Busca celdas vacías en el borde contrario al movimiento
		if (direccion.equals("derecha")) {
			for (int fila = 0; fila <= borde; fila++) {
				if (tablero.obtenerFicha(fila, 0) == null) {
					posFilasVacias[cantCeldasVacias] = fila;
					posColumnasVacias[cantCeldasVacias] = 0;
					cantCeldasVacias++;
				}
			}
		} else if (direccion.equals("izquierda")) {
			for (int fila = 0; fila <= borde; fila++) {
				if (tablero.obtenerFicha(fila, borde) == null) {
					posFilasVacias[cantCeldasVacias] = fila;
					posColumnasVacias[cantCeldasVacias] = borde;
					cantCeldasVacias++;
				}
			}
		} else if (direccion.equals("arriba")) {
			for (int col = 0; col <= borde; col++) {
				if (tablero.obtenerFicha(borde, col) == null) {
					posFilasVacias[cantCeldasVacias] = borde;
					posColumnasVacias[cantCeldasVacias] = col;
					cantCeldasVacias++;
				}
			}
		} else if (direccion.equals("abajo")) {
			for (int col = 0; col <= borde; col++) {
				if (tablero.obtenerFicha(0, col) == null) {
					posFilasVacias[cantCeldasVacias] = 0;
					posColumnasVacias[cantCeldasVacias] = col;
					cantCeldasVacias++;
				}
			}
		}

		if (cantCeldasVacias > 0) {

			int indiceRandom = random.nextInt(cantCeldasVacias);

			int filaNuevaFicha = posFilasVacias[indiceRandom];
			int columnaNuevaFicha = posColumnasVacias[indiceRandom];

			// Se coloca nueva ficha en la nueva posicion con el siguiente valor

			tablero.agregarFicha(filaNuevaFicha, columnaNuevaFicha, new Ficha(siguienteValor));
			tablero.incrementarCantidadFichas();
			siguienteValor = generarNumeroAleatoria();

		}
	}
	
	
	private String generarSugerenciaAleatoria() {
	    Random random = new Random();
	    int indice = random.nextInt(listaSugerencias.length);
	    return listaSugerencias[indice];
	}
	
	public void actualizarSugerenciaAleatoria() {
		this.sugerenciaActual = generarSugerenciaAleatoria();
	}

	public boolean isJuegoTerminado() {
		if (tablero.getCantidadDeFichasPresentes() < 16) {
			return false;
		}

		boolean sinCombinacionesVerticales = true;
		boolean sinCombinacionesHorizontales = true;

		for (int i = 0; i < tablero.getLargoTablero() - 1; i++) {
			for (int j = 0; j < tablero.getLargoTablero(); j++) {
				sinCombinacionesVerticales = sinCombinacionesVerticales && !tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i + 1, j));				
			}
		}

		for (int i = 0; i < tablero.getLargoTablero(); i++) {
			for (int j = 0; j < tablero.getLargoTablero() - 1; j++) {
				sinCombinacionesHorizontales = sinCombinacionesHorizontales && !tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i, j + 1));		
			}
		}


		return sinCombinacionesVerticales && sinCombinacionesHorizontales;
	}

	public boolean isJuegoGanado() {
		if(this.puntuacion>=500) {
			return true;
		}
		return false;
	}
}
