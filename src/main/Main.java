package main;

import java.awt.EventQueue;

import interfaz.ThreesGUI;

public class Main {

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

}
