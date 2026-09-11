package juego;

import java.util.Random;

public class Tablero {
	private int cantidadDeFichas;
	private Ficha[][] matrizDeJuego;

	public Tablero() {
		this.matrizDeJuego = new Ficha[4][4];
		// Fichas iniciales
		this.matrizDeJuego[1][1] = new Ficha(1);
		this.matrizDeJuego[2][2] = new Ficha(2);

		this.cantidadDeFichas = 2;
		this.matrizDeJuego = new Ficha[4][4];
	}

	public void moverDerecha() {
		// recorremos las filas de abajo para arriba y las columnas de derecha a
		// izquierda
		for (int fila = 0; fila < obtenerLargoTablero(); fila++) {

			for (int col = obtenerLargoTablero() - 2; col >= 0; col--) {

				if (matrizDeJuego[fila][col] != null) {

					if (matrizDeJuego[fila][col + 1] == null) {
						matrizDeJuego[fila][col + 1] = matrizDeJuego[fila][col];
						matrizDeJuego[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila, col + 1);
					}
				}
			}
		}
	}

	public void moverIzquierda() {
		// recorremos las filas de abajo para arriba y las columnas de izquierda a
		// derecha
		for (int fila = 0; fila < obtenerLargoTablero(); fila++) {

			for (int col = 1; col < obtenerLargoTablero(); col++) {

				if (matrizDeJuego[fila][col] != null) {

					if (matrizDeJuego[fila][col - 1] == null) {
						matrizDeJuego[fila][col - 1] = matrizDeJuego[fila][col];
						matrizDeJuego[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila, col - 1);
					}
				}
			}
		}
	}

	public void moverArriba() {
		// recorremos las filas desde abajo hacia arriba y las columnas desde izquierda
		// a derecha
		for (int fila = 1; fila < obtenerLargoTablero(); fila++) {

			for (int col = 0; col < obtenerLargoTablero(); col++) {

				if (matrizDeJuego[fila][col] != null) {

					if (matrizDeJuego[fila - 1][col] == null) {
						matrizDeJuego[fila - 1][col] = matrizDeJuego[fila][col];
						matrizDeJuego[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila - 1, col);
					}
				}
			}
		}
	}

	public void moverAbajo() {
		// recorremos las filas desde arriba hacia abajo y las columnas desde izquierda
		// a derecha
		for (int fila = obtenerLargoTablero() - 2; fila >= 0; fila--) {

			for (int col = 0; col < obtenerLargoTablero(); col++) {

				if (matrizDeJuego[fila][col] != null) {

					if (matrizDeJuego[fila + 1][col] == null) {
						matrizDeJuego[fila + 1][col] = matrizDeJuego[fila][col];
						matrizDeJuego[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila + 1, col);
					}
				}
			}
		}
	}

	private boolean validarSiHayBorde(int fila, int columna, String direccion) {
		// traemos el largo de fila y columna
		int maximoMatriz = matrizDeJuego.length - 1;

		if (direccion.equals("arriba") && fila == 0) {
			return true;
		} else if (direccion.equals("abajo") && fila == maximoMatriz) {
			return true;
		} else if (direccion.equals("izquierda") && columna == 0) {
			return true;
		} else if (direccion.equals("derecha") && columna == maximoMatriz) {
			return true;
		} else {
			return false;
		}
	}

	public int obtenerLargoTablero() {
		return matrizDeJuego.length;
	}

	public boolean posicionLibre(int nFila, int nCol) {
		if (matrizDeJuego[nFila][nCol] == null) {
			return true;
		} else {
			return false;
		}
	}

	public void combinarFichas(int filaFichaOrigen, int columFichaOrigen, int filaFichaDestino, int columFichaDestino) {
		// usamos las coordenadas de los parametros para saber el valor de las fichas
		Ficha fichaOrigen = matrizDeJuego[filaFichaOrigen][columFichaOrigen];
		Ficha fichaDestino = matrizDeJuego[filaFichaDestino][columFichaDestino];

		if (sePuedeFusionar(fichaDestino, fichaOrigen)) {
			int nuevoValorFicha = fichaDestino.getValor() + fichaOrigen.getValor();
			fichaDestino.setValor(nuevoValorFicha);

			matrizDeJuego[filaFichaOrigen][columFichaOrigen] = null;

			this.cantidadDeFichas--;
		}
	}

	public Ficha obtenerFicha(int i, int j) {
		return matrizDeJuego[i][j];
	}

	public int cantidadDeFichasPresentes() {
		return this.cantidadDeFichas;
	}

	public void agregarFicha(int fila, int columna, int nuevovalor) {

		this.matrizDeJuego[fila][columna] = new Ficha(nuevovalor);

	}

	public boolean sePuedeFusionar(Ficha ficha1, Ficha ficha2) {
		if (ficha1 == null | ficha2 == null) {
			return false;
		}

		int valor1 = ficha1.getValor();
		int valor2 = ficha2.getValor();

		if ((valor1 == 1 && valor2 == 2) || (valor1 == 2 && valor2 == 1)) {
			return true;
		} else if (valor1 == valor2 && valor1 >= 3) {
			return true;
		} else {
			return false;
		}
	}

	public void agregarCantidadFichas() {
		this.cantidadDeFichas++;

	}

}
