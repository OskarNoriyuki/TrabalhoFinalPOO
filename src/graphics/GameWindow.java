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

import engine.Tetris;
import sounds.SoundPlayer;
import teste.Aplicacao;
import tempo.Timer;

public class GameWindow extends JFrame{

    JFrame janela;
	Timer gameLoop;
	Tetris jogo;
	TetrisField painelJogo;
	StatsField painelAux;
	int dificuldade;

    public GameWindow(int dificuldade) {

		this.dificuldade=dificuldade;
    	JFrame janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setTitle("Tetris");
		janela.setLayout(new FlowLayout());
		this.jogo = new Tetris(20,10, true);
		painelJogo = new TetrisField(jogo);
		painelAux = new StatsField(jogo);
		janela.add(painelJogo);
		janela.add(painelAux);
		Timer gameLoop = new Timer(jogo, 60, painelJogo, painelAux, janela, dificuldade); //60 fps
		gameLoop.iniciaTetris();
		
		//tocarMúsicaTema
		if(dificuldade==1)
			SoundPlayer.tocarLoop("themeNoob.wav");
		else if (dificuldade==2)
			SoundPlayer.tocarLoop("themeIntermediario.wav");
		else if (dificuldade==3)
			SoundPlayer.tocarLoop("themePro.wav");

		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);

    }
    
    public void repaint() {
    	painelJogo.repaint();
    	painelAux.repaint();
    }

	public void fecharJanela(){
		janela.setVisible(false);
	}

}
