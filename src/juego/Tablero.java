package juego;

public class Tablero {
	private int cantidadDeFichas;
	private Ficha[][] matrizDeTablero;
	private boolean huboFusionParaPista = false;

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
	public void validacionDe(Enum<Direccion> direccion) {

	}

	private void procesarMovimiento(int filaActual, int colActual, int nuevaFila, int nuevaCol) {
		if (matrizDeTablero[filaActual][colActual] != null) {
			if (matrizDeTablero[nuevaFila][nuevaCol] == null) {
				matrizDeTablero[nuevaFila][nuevaCol] = matrizDeTablero[filaActual][colActual];
				matrizDeTablero[filaActual][colActual] = null;
			} else {
				combinarFichas(filaActual, colActual, nuevaFila, nuevaCol);
			}
		}
	}

	public void moverDerecha() {
		for (int fila = 0; fila < getLargoTablero(); fila++) {

			for (int col = getLargoTablero() - 2; col >= 0; col--) {
				procesarMovimiento(fila, col, fila, col+1);
			}
		}
	}

	public void moverIzquierda() {
		for (int fila = 0; fila < getLargoTablero(); fila++) {

			for (int col = 1; col < getLargoTablero(); col++) {
				procesarMovimiento(fila, col, fila, col-1);
			}
		}
	}

	public void moverArriba() {
		for (int fila = 1; fila < getLargoTablero(); fila++) {

			for (int col = 0; col < getLargoTablero(); col++) {
				procesarMovimiento(fila, col, fila-1, col);
			}
		}
	}

	public void moverAbajo() {
		for (int fila = getLargoTablero() - 2; fila >= 0; fila--) {

			for (int col = 0; col < getLargoTablero(); col++) {
				procesarMovimiento(fila, col, fila+1, col);
			}
		}
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

	private void combinarFichas(int filaFicha1, int columFicha1, int filaFicha2, int columFicha2) {
		// usamos las coordenadas de los parametros para saber el valor de las fichas
		Ficha fichaOrigen = matrizDeTablero[filaFicha1][columFicha1];
		Ficha fichaDestino = matrizDeTablero[filaFicha2][columFicha2];
	
		if (sePuedeFusionar(fichaDestino, fichaOrigen)) {
			matrizDeTablero[filaFicha1][columFicha1] = null;
			matrizDeTablero[filaFicha2][columFicha2] = null;
			agregarFicha(filaFicha2, columFicha2, new Ficha(fichaDestino.getValor() + fichaOrigen.getValor()));

			this.cantidadDeFichas--;

			this.huboFusionParaPista = true;
		}
	}
	
	public boolean huboFusion() {
		return this.huboFusionParaPista;
	}
	
	public void resetearBanderaFusion() {
		this.huboFusionParaPista = false;
	}

}
