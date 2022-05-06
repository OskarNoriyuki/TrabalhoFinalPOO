package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class LeTeclado implements KeyListener{
	Tetris jogo;
	
	public LeTeclado(Tetris jogo) {
		this.jogo = jogo;
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		int teclaPressionada = e.getKeyCode();
		if(teclaPressionada == KeyEvent.VK_W)
			jogo.updateGame("goUp");
		if(teclaPressionada == KeyEvent.VK_S)
			jogo.updateGame("goDownExtra");
		if(teclaPressionada == KeyEvent.VK_A)
			jogo.updateGame("goLeft");
		if(teclaPressionada == KeyEvent.VK_D)
			jogo.updateGame("goRight");
		if(teclaPressionada == KeyEvent.VK_J)
			jogo.updateGame("rotateCCW");
		if(teclaPressionada == KeyEvent.VK_K)
			jogo.updateGame("rotateCW");
	}

	public void keyReleased(KeyEvent e) {

	}

}
