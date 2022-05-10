/*
	Classe GameWindow
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import data.DataManager;
import engine.Player;
import engine.Tetris;
import sounds.SoundPlayer;
import tempo.Timer;

public class GameWindow extends JFrame {
	Timer gameLoop;				// Loop do jogo
	Tetris game;				// Logica do jogo
	TetrisField tetrisField;	// Painel para desenhar o jogo
	StatsField statsField;		// Painel para escrever dados do jogo

	PauseWindow pause;

	// Construtor
    public GameWindow(int difficulty, String playerName, boolean load) {
		super("Tetris");

		// Configuracoes da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new FlowLayout());

    	// Verifica se eh preciso carregar um jogo
		if (load)
			this.game = new Tetris(DataManager.loadGame(playerName));
		else
			this.game = new Tetris(20,10, true, new Player(playerName));
		
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
		
		// Adiciona paineis
		this.add(tetrisField);
		this.add(statsField);

		// Redimensionamento e localizacao da janela
		this.pack();
		this.setLocationRelativeTo(null);

		this.setVisible(true);	// Deixa visivel

		// Instancia timer do jogo
		Timer gameLoop = new Timer(game, 60, tetrisField, statsField, this, difficulty); //60 fps
		
		gameLoop.iniciaTetris();	// Inicia
    }
 
}
