/*
	Classe ReadKeyboard
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ReadKeyboard implements KeyListener {
	private Tetris game;
	
	// Construtor
	public ReadKeyboard(Tetris game) {
		this.game = game;
	}

	public void keyPressed(KeyEvent e) {
		// Comandos para cada tecla pressionada
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				game.updateGame("goUp");
				break;
			case KeyEvent.VK_S:
				game.updateGame("goDownExtra");
				break;
			case KeyEvent.VK_A:
				game.updateGame("goLeft");
				break;
			case KeyEvent.VK_D:
				game.updateGame("goRight");
				break;
			case KeyEvent.VK_J:
				game.updateGame("rotateCCW");
				break;
			case KeyEvent.VK_K:
				game.updateGame("rotateCW");
				break;
			case KeyEvent.VK_P:
				game.updateGame("pause");
		}

		/*int teclaPressionada = e.getKeyCode();
		if(teclaPressionada == KeyEvent.VK_W)
			game.updateGame("goUp");
		if(teclaPressionada == KeyEvent.VK_S)
			game.updateGame("goDownExtra");
		if(teclaPressionada == KeyEvent.VK_A)
			game.updateGame("goLeft");
		if(teclaPressionada == KeyEvent.VK_D)
			game.updateGame("goRight");
		if(teclaPressionada == KeyEvent.VK_J)
			game.updateGame("rotateCCW");
		if(teclaPressionada == KeyEvent.VK_K)
			game.updateGame("rotateCW");*/
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
