package juego;

public class Tablero {
	private int cantidadDeFichas;
	private Ficha[][] matrizDeTablero;
	private int puntajeTotal;

	public Tablero() {
		this.matrizDeTablero = new Ficha[4][4];
		// Fichas iniciales
		agregarFicha(1, 1, new Ficha(1));
		agregarFicha(2, 2, new Ficha(2));
		
		this.cantidadDeFichas = 2;
	}

	public int getLargoTablero() {
		return matrizDeTablero.length;
	}
	
	public int getPuntajeTotal() {
		return this.puntajeTotal;
	}
	
	public void incrementarCantidadFichas() {
		this.cantidadDeFichas++;

	}
	
	public Ficha obtenerFicha(int i, int j) {
		return matrizDeTablero[i][j];
	}
	
	public int getCantidadDeFichasPresentes() {
		return this.cantidadDeFichas;
	}
	
	public void agregarFicha(int fila, int columna, Ficha ficha) {

		this.matrizDeTablero[fila][columna] = ficha;
	}
	
	public void moverDerecha() {

		for (int fila = 0; fila < getLargoTablero(); fila++) {

			for (int col = getLargoTablero() - 2; col >= 0; col--) {

				if (matrizDeTablero[fila][col] != null) {

					if (matrizDeTablero[fila][col + 1] == null) {
						matrizDeTablero[fila][col + 1] = matrizDeTablero[fila][col];
						matrizDeTablero[fila][col] = null;
					} else {
						
						combinarFichas(fila, col, fila, col + 1);
					}
				}
			}
		}
	}

	public void moverIzquierda() {
	
		for (int fila = 0; fila < getLargoTablero(); fila++) {

			for (int col = 1; col < getLargoTablero(); col++) {

				if (matrizDeTablero[fila][col] != null) {

					if (matrizDeTablero[fila][col - 1] == null) {
						matrizDeTablero[fila][col - 1] = matrizDeTablero[fila][col];
						matrizDeTablero[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila, col - 1);
					}
				}
			}
		}
	}

	public void moverArriba() {

		for (int fila = 1; fila < getLargoTablero(); fila++) {

			for (int col = 0; col < getLargoTablero(); col++) {

				if (matrizDeTablero[fila][col] != null) {

					if (matrizDeTablero[fila - 1][col] == null) {
						matrizDeTablero[fila - 1][col] = matrizDeTablero[fila][col];
						matrizDeTablero[fila][col] = null;
					} else {
						combinarFichas(fila, col, fila - 1, col);
					}
				}
			}
		}
	}

	public void moverAbajo() {
	
		for (int fila = getLargoTablero() - 2; fila >= 0; fila--) {

			for (int col = 0; col < getLargoTablero(); col++) {

				if (matrizDeTablero[fila][col] != null) {

					if (matrizDeTablero[fila + 1][col] == null) {
						matrizDeTablero[fila + 1][col] = matrizDeTablero[fila][col];
						matrizDeTablero[fila][col] = null;
					} else {
						
						combinarFichas(fila, col, fila + 1, col);
					}
				}
			}
		}
	}

	
	private void calcularPuntuacion(int valorFicha1, int valorFicha2) {
		int suma = valorFicha1 + valorFicha2;
		puntajeTotal+=suma;
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

	private void combinarFichas(int filaFichaOrigen, int columFichaOrigen, int filaFichaDestino, int columFichaDestino) {
		// usamos las coordenadas de los parametros para saber el valor de las fichas
		Ficha fichaOrigen = matrizDeTablero[filaFichaOrigen][columFichaOrigen];
		Ficha fichaDestino = matrizDeTablero[filaFichaDestino][columFichaDestino];

		if (sePuedeFusionar(fichaDestino, fichaOrigen)) {
			calcularPuntuacion(fichaDestino.getValor(), fichaOrigen.getValor());
			int nuevoValorFicha = fichaDestino.getValor() + fichaOrigen.getValor();
			fichaDestino.setValor(nuevoValorFicha);

			matrizDeTablero[filaFichaOrigen][columFichaOrigen] = null;

			this.cantidadDeFichas--;
			
			
		}
	}

}
