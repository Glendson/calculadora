package br.com.glendson.calc.visao;

import javax.swing.JFrame;

public class Calculadora extends JFrame {
    
    public Calculadora() {
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(232, 322);
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
