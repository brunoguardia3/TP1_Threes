package interfaz;

import Juego.Model;
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
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
    
    // Elementos para los objetivos opcionales y estado
    private JLabel lblPuntaje;
    private JLabel lblSiguienteFicha;
    private JLabel lblSugerencia;

    /**
     * Launch the application. (Solo para probar la interfaz aislada)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ThreesGUI frame = new ThreesGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public ThreesGUI() {
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

        lblSiguienteFicha = new JLabel("Siguiente: ?");
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

        // --- PANEL INFERIOR: Mensajes / Sugerencias ---
        lblSugerencia = new JLabel("Sugerencia de jugada: (Ninguna)");
        lblSugerencia.setHorizontalAlignment(SwingConstants.CENTER);
        lblSugerencia.setFont(new Font("Arial", Font.ITALIC, 14));
        contentPane.add(lblSugerencia, BorderLayout.SOUTH);
        
        // Esto es clave para que la ventana pueda detectar los eventos del teclado (las flechas)
        setFocusable(true);
        requestFocusInWindow();
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
    public void actualizarPuntaje(int puntaje) {
        lblPuntaje.setText("Puntaje: " + puntaje);
    }
    
    /**
     * Muestra la siguiente ficha (Objetivo opcional 1).
     */
    public void mostrarSiguienteFicha(String valor) {
        lblSiguienteFicha.setText("Siguiente: " + valor);
    }
    
    /**
     * Muestra una sugerencia al usuario (Objetivo opcional 3).
     */
    public void mostrarSugerencia(String sugerencia) {
        lblSugerencia.setText("Sugerencia de jugada: " + sugerencia);
    }
    
    
    }
