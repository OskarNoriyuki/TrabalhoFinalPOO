package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import teste.Aplicacao;

public class Menu extends JFrame implements ActionListener {
    private JButton JButtonPlay;
    private JPanel JPanelNorte;
    public Menu() {
        super("Tetris");
        Container container = getContentPane();
		container.setLayout(new BorderLayout());
        JPanelNorte = new JPanel();
        container.add(JPanelNorte,BorderLayout.NORTH);
        JButtonPlay = new JButton("Play!");
        JPanelNorte.add(JButtonPlay);
        this.pack();
        this.setVisible(true);
        JButtonPlay.setActionCommand("iniciar");
		JButtonPlay.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botão clicado seja resetar
		if (e.getActionCommand().equals("iniciar")) {
            //Aplicacao.IniciaTetris();
            GameWindow jogo = new GameWindow();
        }
    }

}
