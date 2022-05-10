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
		//cada tecla responde de forma diferente de acordo com o estado do jogo (pausado ou nao)
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				if(!game.isPaused()) {
					//game.updateGame("goUp");
				}else {
					game.moveArrow(false);
				}

				break;
			case KeyEvent.VK_S:
				if(!game.isPaused()) {
					game.updateGame("goDownExtra");
				}else {
					game.moveArrow(true);
				}
				break;
			case KeyEvent.VK_A:
				if(!game.isPaused()) {
					game.updateGame("goLeft");
				}else {
					game.moveArrow(false);
				}
				break;
			case KeyEvent.VK_D:
				if(!game.isPaused()) {
					game.updateGame("goRight");
				}else {
					game.moveArrow(true);
				}
				break;
			case KeyEvent.VK_J:
				if(!game.isPaused()) {
					game.updateGame("rotateCCW");
				}else {
					game.setEscape(true);
				}
				break;
			case KeyEvent.VK_K:
				if(!game.isPaused()) {
					game.updateGame("rotateCW");
				}else {
					game.setEscape(true);
				}
				break;
			case KeyEvent.VK_P:
				if(game.isPaused()) {
					game.setPause(false);
				}else {
					game.setPause(true);
				}
				break;
			case KeyEvent.VK_SPACE:
				if(game.isPaused()) {
					game.setEscape(true);
				}
				break;
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
