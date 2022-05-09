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
import players.Player;
import sounds.SoundPlayer;
import teste.Aplicacao;
import tempo.Timer;

public class GameWindow extends JFrame{
    JFrame frame;
	Timer gameLoop;
	Tetris game;
	TetrisField painelJogo;
	StatsField painelAux;
	private int difficulty;
	private Player player;

    public GameWindow(int difficulty, String playerName) {
		this.difficulty = difficulty;
    	JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Tetris");
		frame.setLayout(new FlowLayout());
		this.player = new Player(playerName);
		this.game = new Tetris(20,10, true, player);
		painelJogo = new TetrisField(game);
		painelAux = new StatsField(game);
		frame.add(painelJogo);
		frame.add(painelAux);
		Timer gameLoop = new Timer(game, 60, painelJogo, painelAux, frame, difficulty); //60 fps
		gameLoop.iniciaTetris();
		
		//tocarMúsicaTema
		if(difficulty == 1)
			SoundPlayer.tocarLoop("themeNoob.wav");
		else if (difficulty == 2)
			SoundPlayer.tocarLoop("themeIntermediario.wav");
		else if (difficulty == 3)
			SoundPlayer.tocarLoop("themePro.wav");

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }

	public GameWindow(int difficulty, String playerName, boolean load) {
		this(difficulty, playerName);

		if (load)
			this.game.load();
    }
    
    public void repaint() {
    	painelJogo.repaint();
    	painelAux.repaint();
    }

	public void fecharJanela(){
		frame.setVisible(false);
	}
}
