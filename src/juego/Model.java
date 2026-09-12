package juego;

import java.util.Random;

public class Model {
	private Tablero tablero;
	private int puntuacion;
	private int siguienteValor;

	public Model() {
		this.tablero = new Tablero();
		this.puntuacion = 0;
		this.siguienteValor = generarNumeroAleatoria();
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
}
