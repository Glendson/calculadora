package br.com.glendson.calc.visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.com.glendson.calc.modelo.Memoria;
import br.com.glendson.calc.style.Colors;

public class Teclado extends JPanel implements ActionListener {

    public Teclado() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(layout);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        // Linha 1
        c.gridwidth = 3;
        adicionarBotao("AC", Colors.COR_CINZA_ESCURO, c, 0, 0);
        c.gridwidth = 1;
        adicionarBotao("/", Colors.COR_LARANJA, c, 3, 0);

        // Linha 2
        adicionarBotao("7", Colors.COR_CINZA_CLARO, c, 0, 1);
        adicionarBotao("8", Colors.COR_CINZA_CLARO, c, 1, 1);
        adicionarBotao("9", Colors.COR_CINZA_CLARO, c, 2, 1);
        adicionarBotao("*", Colors.COR_LARANJA, c, 3, 1);

        // Linha 3
        adicionarBotao("4", Colors.COR_CINZA_CLARO, c, 0, 2);
        adicionarBotao("5", Colors.COR_CINZA_CLARO, c, 1, 2);
        adicionarBotao("6", Colors.COR_CINZA_CLARO, c, 2, 2);
        adicionarBotao("-", Colors.COR_LARANJA, c, 3, 2);

        // Linha 4
        adicionarBotao("1", Colors.COR_CINZA_CLARO, c, 0, 3);
        adicionarBotao("2", Colors.COR_CINZA_CLARO, c, 1, 3);
        adicionarBotao("3", Colors.COR_CINZA_CLARO, c, 2, 3);
        adicionarBotao("+", Colors.COR_LARANJA, c, 3, 3);

        // Linha 5
        c.gridwidth = 2;
        adicionarBotao("0", Colors.COR_CINZA_CLARO, c, 0, 4);
        c.gridwidth = 1;
        adicionarBotao(",", Colors.COR_CINZA_CLARO, c, 2, 4);
        adicionarBotao("=", Colors.COR_LARANJA, c, 3, 4);

    }

    private void adicionarBotao(String texto, Color cor, GridBagConstraints c, int x, int y) {
        Botao botao = new Botao(texto, cor);
        botao.addActionListener(this);
        c.gridx = x;
        c.gridy = y;
        add(botao, c);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton botao = (JButton) e.getSource();
            Memoria.getInstancia().setTextoAtual(botao.getText());
        }

    }
}
