/*
	Classe GameWindow
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import engine.Tetris;
import players.Player;
import players.SaveLoad;
import sounds.SoundPlayer;
import tempo.Timer;

public class GameWindow extends JFrame {
    JFrame frame;			// Janela do jogo
	Timer gameLoop;			// Loop do jogo
	Tetris game;			// Logica do jogo
	TetrisField tetrisField;	// Painel para desenhar o jogo
	StatsField statsField;	// Painel para escrever dados do jogo

    public GameWindow(int difficulty, String playerName, boolean load) {
    	JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Tetris");
		frame.setLayout(new FlowLayout());

		if (load)
			this.game = SaveLoad.loadGame(playerName);
		else
			this.game = new Tetris(20,10, true, new Player(playerName));
		
		tetrisField = new TetrisField(game);
		statsField = new StatsField(game);
		frame.add(tetrisField);
		frame.add(statsField);
		Timer gameLoop = new Timer(game, 60, tetrisField, statsField, frame, difficulty); //60 fps
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
    
    public void repaint() {
    	tetrisField.repaint();
    	statsField.repaint();
    }

	public void fecharJanela(){
		frame.setVisible(false);
	}
}
