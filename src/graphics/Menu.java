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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import teste.Aplicacao;

public class Menu implements ActionListener {
    
    private JButton JButtonPlay;
    private JButton JButtonRanking;
    private JButton JButtonOpcoes;
    private JButton JButtonSair;

    public  JFrame janela = new JFrame();
    
    public Menu() {
        
        //Configurações da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setPreferredSize(new Dimension(800, 600));
        janela.setResizable(false);
		janela.setTitle("Tetris");
        janela.setLayout(new GridLayout(0, 1));
        BufferedImage myImage;
        try {
            myImage = ImageIO.read(new FileInputStream("src/img/background/TetrisMenu.jpg"));
            //janela.setContentPane(new ImagemFundo(myImage));
            JLabel label = new JLabel( new ImageIcon(myImage));
            label.setLayout( new FlowLayout() );


            janela.add(label);
            label.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

        JPanel panelPlay = new JPanel();
        JButtonPlay = new JButton("Play!");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth=2;
        c.fill = GridBagConstraints.HORIZONTAL;
        //Código para os Panels ocuparem todo o espaço disponível
        c.insets = new Insets(250,300,0,300);
        c.weightx=1;
        c.weighty=1;
        c.fill=GridBagConstraints.BOTH;
        JButtonPlay.setBackground(Color.green);
        label.add(JButtonPlay, c);

        JPanel panelRanking = new JPanel();
        JButtonRanking = new JButton("Ranking!");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth=2;
        c.insets = new Insets(50,300,0,300);
        label.add(JButtonRanking, c);

        JButtonOpcoes = new JButton("Opções!");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth=1;
        c.insets = new Insets(50,300,100,0);
        label.add(JButtonOpcoes, c);

        JButtonSair = new JButton("Fechar");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth=1;
        c.insets = new Insets(50,0,100,300);
        label.add(JButtonSair, c);


        JButtonPlay.setActionCommand("iniciar");
		JButtonRanking.setActionCommand("ranking");
		 
		JButtonPlay.addActionListener(this);
		JButtonRanking.addActionListener(this);
        
        janela.add(label);
        janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
        
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

        
        
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
