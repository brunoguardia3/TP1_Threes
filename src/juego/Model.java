package juego;

import java.util.Random;

public class Model {
	Tablero tablero = new Tablero();
	int puntuación = 0;
	boolean juegoTerminado = false;

	private int siguienteValor;

	public Model() {
		this.siguienteValor = generarNumeroAleatoria();
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void moverDerecha() {
		tablero.moverDerecha();
	}

	public void moverIzquierda() {
		tablero.moverIzquierda();
	}

	public void moverArriba() {
		tablero.moverArriba();
	}

	public void moverAbajo() {
		tablero.moverAbajo();
	}

	public int getPuntuacion() {
		return puntuación;
	}

	public void generarFichaAleatoria(String direccion, int siguienteValor) {
		Random random = new Random();

		int[] posFilasVacias = new int[4];
		int[] posColumnasVacias = new int[4];

		int cantCeldasVacias = 0;

		int borde = tablero.obtenerLargoTablero() - 1;

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
			tablero.agregarFicha(filaNuevaFicha, columnaNuevaFicha, siguienteValor);

			tablero.agregarCantidadFichas();

		}
	}

	// Ese metodo genera un numero aleatorio de 1 a 3 para las nuevas fichas
	public int generarNumeroAleatoria() {
		Random random = new Random();

		int[] posiblesValoresFichas = { 1, 2, 3 };

		int indice = random.nextInt(posiblesValoresFichas.length);

		int nroAleatoria = posiblesValoresFichas[indice];

		return nroAleatoria;
	}

	public int getSiguientValor() {
		return siguienteValor;
	}

	public void isJuegoTerminado() {
		if (tablero.cantidadDeFichasPresentes() < 16) {
			juegoTerminado = false;
			return;
		}
		boolean sinCombinacionesVerticales = true;
		boolean sinCombinacionesHorizontales = true;

		for (int i = 0; i < tablero.obtenerLargoTablero() - 1; i++) {
			for (int j = 0; j < tablero.obtenerLargoTablero(); j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i + 1, j))) {
					sinCombinacionesVerticales = false;

				}
			}
		}

		for (int i = 0; i < tablero.obtenerLargoTablero(); i++) {
			for (int j = 0; j < tablero.obtenerLargoTablero() - 1; j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i, j + 1))) {
					sinCombinacionesHorizontales = false;

				}
			}
		}

		juegoTerminado = sinCombinacionesVerticales && sinCombinacionesHorizontales;
	}
}
