package Juego;

public class Model {
//	new Tablero tablero = new Tablero();
	int puntuación = 0;
	boolean juegoTerminado = false;
	
	public void isJuegoTerminado() {
		if (tablero.obtenerElementos == 14) {
			juegoTerminado = true;
		}
}
