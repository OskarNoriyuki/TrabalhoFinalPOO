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

import javax.swing.*;
import java.awt.event.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import teste.Aplicacao;

public class Menu implements ActionListener {
    
    private JButton JButtonPlay;
    private JButton JButtonRanking;
    private JButton JButtonOpcoes;
    private JButton JButtonSair;

    public  JFrame janela = new JFrame();
    GridBagConstraints gbc = new GridBagConstraints();
    
    public Menu() {
        
        //Configurações da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setPreferredSize(new Dimension(800, 600));
        janela.setResizable(false);
		janela.setTitle("Tetris");
        janela.setLayout(new GridBagLayout());
        
        //Configurações do Play
        JButtonPlay = new JButton("Play!");
        gbc.gridx=0;
        gbc.gridy=0;
        janela.add(JButtonPlay, gbc);

        //Configurações do Ranking
        JButtonRanking = new JButton("Ranking");
        gbc.gridx=0;
        gbc.gridy=1;
        janela.add(JButtonRanking, gbc);

        //Configurações do Opcoes
        JButtonOpcoes = new JButton("Opções");
        gbc.gridx=0;
        gbc.gridy=3;
        janela.add(JButtonOpcoes, gbc);

        //Configurações do Sair
        JButtonSair = new JButton("Sair");
        gbc.gridx=0;
        gbc.gridy=3;
        janela.add(JButtonSair, gbc);


        janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);

        JButtonPlay.setActionCommand("iniciar");
		JButtonPlay.addActionListener(this);

        
        
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botão clicado seja resetar
		if (e.getActionCommand().equals("iniciar")) {
            //Aplicacao.IniciaTetris();
            
            GameWindow jogo = new GameWindow();
            janela.dispose();
        }
    }

    public void fecharJanela(){
        janela.dispose();
    }

}
