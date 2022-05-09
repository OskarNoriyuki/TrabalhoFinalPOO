/*
	Classe GameWindow
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.Tetris;
import players.Player;
import players.SaveLoad;
import sounds.SoundPlayer;
import tempo.Timer;

public class GameWindow implements KeyListener {
    JFrame frame;				// Janela do jogo
	//JLayeredPane layeredPane;
	//JPanel fieldPanel;
	Timer gameLoop;				// Loop do jogo
	Tetris game;				// Logica do jogo
	TetrisField tetrisField;	// Painel para desenhar o jogo
	StatsField statsField;		// Painel para escrever dados do jogo

	PauseWindow pause;

	// Construtor
    public GameWindow(int difficulty, String playerName, boolean load) {
    	// Verifica se eh preciso carregar um jogo
		if (load)
			this.game = SaveLoad.loadGame(playerName);
		else
			this.game = new Tetris(20,10, true, new Player(playerName));
		
		// Instancia timer do jogo
		Timer gameLoop = new Timer(game, 60, tetrisField, statsField, frame, difficulty); //60 fps
		
		gameLoop.iniciaTetris();	// Inicia
		
		// Tocar musica tema dependendo da dificuldade
		switch (difficulty) {
			case 1:
				SoundPlayer.tocarLoop("themeNoob.wav");
				break;
			case 2:
				SoundPlayer.tocarLoop("themeIntermediario.wav");
				break;
			case 3:
				SoundPlayer.tocarLoop("themePro.wav");
		}
		
		tetrisField = new TetrisField(game);
		statsField = new StatsField(game);
		
		frame = new JFrame();	// Instancia janela

		// Configuracoes da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Tetris");
		frame.setLayout(new FlowLayout());
		
		// Adiciona paineis
		frame.add(tetrisField);
		frame.add(statsField);

		// Redimensionamento e localizacao da janela
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);	// Deixa visivel
    }
    
    public void repaint() {
    	tetrisField.repaint();
    	statsField.repaint();
    }

	public void keyPressed(KeyEvent e) {
		//if(e.getKeyCode() == KeyEvent.VK_P)

	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
