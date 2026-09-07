package juego;

import java.util.Random;

public class Model {
	Tablero tablero = new Tablero();
	int puntuación = 0;
	boolean juegoTerminado = false;

	private int siguienteValor;

	public Model() {
		this.siguienteValor = generarFichaAleatoria();
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
	
	public int generarFichaAleatoria() {
		Random random = new Random();

		int[] posiblesValoresFichas = {1,2,3};

		int indice = random.nextInt(posiblesValoresFichas.length);

		int fichaAleatoria = posiblesValoresFichas[indice];

		return fichaAleatoria;
	}

	public int getSiguientValor() {
		return siguienteValor;
	}

	public void isJuegoTerminado() {
		if(tablero.cantidadDeFichasPresentes() < 16) {
			juegoTerminado = false;
			return;
		}
		boolean sinCombinacionesVerticales = true;
		boolean sinCombinacionesHorizontales = true;

		for (int i = 0; i < tablero.obtenerLargoTablero()-1; i++) {
			for(int j = 0; j < tablero.obtenerLargoTablero(); j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i+1, j))){
					sinCombinacionesVerticales = false;

				}
			}
		}

		for (int i = 0; i < tablero.obtenerLargoTablero(); i++) {
			for(int j = 0; j < tablero.obtenerLargoTablero()-1; j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i, j+1))){
					sinCombinacionesHorizontales = false;

				}
			}
		}

		juegoTerminado = sinCombinacionesVerticales && sinCombinacionesHorizontales;
	}
}
