package juego;

public class Model {
	Tablero tablero = new Tablero();
	int puntuación = 0;
	boolean juegoTerminado = false;

	public void isJuegoTerminado() {
		if(tablero.cantidadDeFichas() < 16) {
			juegoTerminado = false;
			return;
		}
		boolean sinCombinacionesVerticales = true;
		boolean sinCombinacionesHorizontales = true;

		for (int i = 0; i < tablero.obtenerLargo()-1; i++) {
			for(int j = 0; j < tablero.obtenerLargo(); j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i+1, j))){
					sinCombinacionesVerticales = false;

				}
			}
		}

		for (int i = 0; i < tablero.obtenerLargo(); i++) {
			for(int j = 0; j < tablero.obtenerLargo()-1; j++) {
				if (tablero.sePuedeFusionar(tablero.obtenerFicha(i, j), tablero.obtenerFicha(i, j+1))){
					sinCombinacionesHorizontales = false;

				}
			}
		}

		juegoTerminado = sinCombinacionesVerticales && sinCombinacionesHorizontales;
	}
}
