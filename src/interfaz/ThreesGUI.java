package interfaz;

import juego.Model;

import juego.Ficha;
import juego.Tablero;

import java.awt.EventQueue;
import java.util.Iterator;

import javax.swing.border.EmptyBorder;
import javax.swing.*;

import java.awt.*;
import java.awt.BorderLayout;

import java.awt.Color;

@SuppressWarnings("serial") // La declaración de la clase agregaba un warning del tipo serialID, por lo que suprimimosq el warning sin ensuciar el código principal
public class ThreesGUI extends JFrame{
	private JPanel contentPane;
	private JPanel panelGrilla;
	private JLabel[][] casillas;
	private JFrame frame;
	private JButton izqButton,derButton,arribaButton,abajoButton, resetButton;

	// Elementos para los objetivos opcionales y estado
	private JLabel lblPuntaje;
	private JLabel lblSiguienteFicha;
	private JLabel lblSugerencia;
	private boolean finMostrado = false, juegoGanado = false;
	Model model = new Model();

	public ThreesGUI()  { 
		// Configuración básica de la ventana (Respeta el límite de 1366 x 768)
		setTitle("Threes! - Trabajo Práctico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 784); 
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 15));

		// --- PANEL SUPERIOR: Información (Puntaje y Siguiente ficha) ---
		JPanel panelInfo = new JPanel();
		panelInfo.setOpaque(false);
		contentPane.add(panelInfo, BorderLayout.NORTH);
		panelInfo.setLayout(new GridLayout(1, 2, 10, 0));

		lblPuntaje = new JLabel("Puntaje: 0");
		lblPuntaje.setFont(new Font("Arial", Font.BOLD, 18));
		panelInfo.add(lblPuntaje);

		lblSiguienteFicha = new JLabel("Siguiente: ");
		lblSiguienteFicha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSiguienteFicha.setFont(new Font("Arial", Font.BOLD, 18));
		panelInfo.add(lblSiguienteFicha);

		// --- PANEL CENTRAL: Grilla 4x4 ---
		panelGrilla = new JPanel();
		panelGrilla.setBackground(new Color(200, 200, 200));
		panelGrilla.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(panelGrilla, BorderLayout.CENTER);

		// El GridLayout de 4 filas x 4 columnas, con 10px de separación entre celdas
		panelGrilla.setLayout(new GridLayout(4, 4, 10, 10)); 

		// Inicializamos la matriz de etiquetas (JLabels) para las 16 celdas
		casillas = new JLabel[4][4];

		for (int fila = 0; fila < 4; fila++) {
			for (int col = 0; col < 4; col++) {
				JLabel lblCelda = new JLabel("");
				lblCelda.setOpaque(true);
				lblCelda.setBackground(Color.blue);
				lblCelda.setHorizontalAlignment(SwingConstants.CENTER);
				lblCelda.setFont(new Font("Arial", Font.BOLD, 40));
				lblCelda.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2, true));

				casillas[fila][col] = lblCelda;
				panelGrilla.add(lblCelda);
			}
		}

		JPanel panelInferior = new JPanel();
		panelInferior.setOpaque(false);
		panelInferior.setLayout(new BorderLayout(0, 10)); // Separación de 10px vertical
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		// 1. Etiqueta de sugerencias (Arriba en el panel inferior)
		lblSugerencia = new JLabel("Sugerencia de jugada: (Ninguna)");
		lblSugerencia.setHorizontalAlignment(SwingConstants.CENTER);
		lblSugerencia.setFont(new Font("Arial", Font.ITALIC, 14));
		panelInferior.add(lblSugerencia, BorderLayout.NORTH);

		// 2. Contenedor para que la botonera no ocupe todo el ancho de la pantalla
		JPanel contenedorBotonera = new JPanel();
		contenedorBotonera.setOpaque(false);
		panelInferior.add(contenedorBotonera, BorderLayout.CENTER);

		// 3. Panel de la Botonera (Grilla 3x3 para forma de cruceta)
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(3, 3, 5, 5)); // 3 filas, 3 columnas, 5px de gap
		contenedorBotonera.add(panelBotones);
		resetButton = new JButton("Reiniciar");
		resetButton.setVisible(false);
		resetButton.addActionListener(e -> reiniciarPartida());
		panelInferior.add(resetButton, BorderLayout.SOUTH);

		// Inicializar los botones que ya tenías declarados
		arribaButton = new JButton("↑");
		izqButton = new JButton("←");
		derButton = new JButton("→");
		abajoButton = new JButton("↓");

		// Fila 1 de la grilla (Vacío, Arriba, Vacío)
		panelBotones.add(new JLabel(""));
		panelBotones.add(arribaButton);
		panelBotones.add(new JLabel(""));

		// Fila 2 de la grilla (Izquierda, Vacío, Derecha)
		panelBotones.add(izqButton);
		panelBotones.add(new JLabel("")); 
		panelBotones.add(derButton);

		// Fila 3 de la grilla (Vacío, Abajo, Vacío)
		panelBotones.add(new JLabel(""));
		panelBotones.add(abajoButton);
		panelBotones.add(new JLabel(""));

		configurarEventosBotones();
		configurarEventosTeclado();
		actualizarPantalla();

		// Esto es clave para que la ventana pueda detectar los eventos del teclado (las flechas)
		setFocusable(true);
		requestFocusInWindow();
	}

	private void configurarEventosBotones() {
		arribaButton.addActionListener(e -> moverFichaArriba());
		abajoButton.addActionListener(e -> moverFichaAbajo());
		izqButton.addActionListener(e -> moverFichaIzquierda());
		derButton.addActionListener(e -> moverFichaDerecha());
	}

	private void configurarEventosTeclado() {
		getRootPane().registerKeyboardAction(e -> moverFichaArriba(), KeyStroke.getKeyStroke("UP"), JComponent.WHEN_IN_FOCUSED_WINDOW);
		getRootPane().registerKeyboardAction(e -> moverFichaAbajo(), KeyStroke.getKeyStroke("DOWN"), JComponent.WHEN_IN_FOCUSED_WINDOW);
		getRootPane().registerKeyboardAction(e -> moverFichaIzquierda(), KeyStroke.getKeyStroke("LEFT"), JComponent.WHEN_IN_FOCUSED_WINDOW);
		getRootPane().registerKeyboardAction(e -> moverFichaDerecha(), KeyStroke.getKeyStroke("RIGHT"), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}





	// =========================================================================
	// MÉTODOS PÚBLICOS PARA QUE EL CONTROLADOR ACTUALICE LA VISTA
	// =========================================================================

	/**
	 * Actualiza el valor visual de una celda específica.
	 */
	public void actualizarCelda(int fila, int columna, String valor, Color colorFondo) {
		casillas[fila][columna].setText(valor);
		casillas[fila][columna].setBackground(colorFondo);
	}

	/**
	 * Actualiza el puntaje en la interfaz.
	 */
	public void actualizarPuntaje() {
		lblPuntaje.setText("Puntaje: " + model.getPuntuacion());
	}

	/**
	 * Muestra la siguiente ficha (Objetivo opcional 1).
	 */
	public void mostrarSiguienteFicha() {
		lblSiguienteFicha.setText("Siguiente: " + model.getSiguienteValor());
	}

	/**
	 * Muestra una sugerencia al usuario (Objetivo opcional 3).
	 */
	public void mostrarSugerencia(String sugerencia) {
		lblSugerencia.setText("Sugerencia de jugada: " + sugerencia);
	}

	public void moverFichaDerecha() {
		if (finMostrado) {
			return;
		}
		model.moverDerecha();
		actualizarPantalla();
		requestFocusInWindow();

	}
	public void moverFichaIzquierda() {
		if (finMostrado) {
			return;
		}
		model.moverIzquierda();
		actualizarPantalla();
		requestFocusInWindow();


	}
	public void moverFichaArriba() {
		if (finMostrado) {
			return;
		}
		model.moverArriba();
		actualizarPantalla();
		requestFocusInWindow();

	}
	public void moverFichaAbajo() {
		if (finMostrado) {
			return;
		}
		model.moverAbajo();
		actualizarPantalla();
		requestFocusInWindow();

	}

	private void verificarFinJuego() {
		if (!finMostrado && model.isJuegoTerminado()) {
			mostrarFinJuego();
			finMostrado = true;
		}
	}

	private void verificarJuegoGanado() {
		if (!finMostrado && model.isJuegoGanado()) {
			mostrarCartelGanador();
			juegoGanado = true;
		}

	}

	private void reiniciarPartida() {
		model.reiniciarJuego();
		finMostrado=false;
		resetButton.setVisible(false);
		actualizarPantalla();
		requestFocusInWindow();
	}

	private Color obtenerColorPorValor(int valor) {
		if (valor == 0) return new Color(200, 200, 200); // Celda vacía (Gris)
		if (valor == 1) return new Color(102, 204, 255); // Ficha 1 (Celeste)
		if (valor == 2) return new Color(255, 102, 102); // Ficha 2 (Roja)
		return Color.WHITE;                              // Fichas 3+ (Blancas)
	}

	public void actualizarPantalla() {
		Tablero tablero= model.getTablero();

		for(int fila=0;fila<tablero.getLargoTablero();fila++) {
			for(int col=0; col < tablero.getLargoTablero();col++) {
				Ficha ficha= tablero.obtenerFicha(fila, col);

				int valor=(ficha != null) ? ficha.getValor() : 0;

				String texto = (valor == 0) ? "" : String.valueOf(valor);
				Color colorFondo = obtenerColorPorValor(valor);

				actualizarCelda(fila, col, texto, colorFondo);
			}
		}	
		mostrarSiguienteFicha();
		actualizarPuntaje();
		verificarFinJuego();
		verificarJuegoGanado();
	}

	public void mostrarFinJuego() {
		resetButton.setVisible(true);
		resetButton.requestFocusInWindow();
		JOptionPane.showMessageDialog(this,
				"¡No hay más movimientos posibles!\nTu puntaje final es: " + model.getPuntuacion(),
				"Juego Terminado",
				JOptionPane.INFORMATION_MESSAGE);
		SwingUtilities.invokeLater(() -> {
			setFocusable(true);
			toFront();
			requestFocus();
			requestFocusInWindow();
			getRootPane().requestFocus();
		});
	}   
	
	public void mostrarCartelGanador() {
        resetButton.setVisible(true);
        resetButton.requestFocusInWindow();
        JOptionPane.showMessageDialog(this,
            "¡Felicidades! ¡Ganaste el juego al llegar a 500 puntos!",
            "Juego Terminado",
            JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            toFront();
            requestFocus();
            requestFocusInWindow();
            getRootPane().requestFocus();
        });
        
    }
}

