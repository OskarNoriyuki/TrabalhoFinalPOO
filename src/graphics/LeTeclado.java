package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.Tetris;


public class LeTeclado implements KeyListener{
	
	public boolean pressedUp, pressedDown, pressedLeft, pressedRight, pressedRL, pressedRR; 
	Tetris jogoTeste;
	
	public LeTeclado(Tetris jogoTeste) {
		this.pressedDown = false;
		this.pressedUp = false;
		this.pressedLeft = false;
		this.pressedRight = false;
		this.pressedRL = false;
		this.pressedRR = false;
		
		this.jogoTeste = jogoTeste;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int teclaPressionada = e.getKeyCode();
		
		if(teclaPressionada == KeyEvent.VK_W) {
			pressedUp = true;
		}
		if(teclaPressionada == KeyEvent.VK_S) {
			pressedDown = true;
		}
		if(teclaPressionada == KeyEvent.VK_A) {
			pressedLeft = true;
		}
		if(teclaPressionada == KeyEvent.VK_D) {
			pressedRight = true;
		}
		if(teclaPressionada == KeyEvent.VK_J) {
			jogoTeste.rotateCW();
		}
		if(teclaPressionada == KeyEvent.VK_K) {
			jogoTeste.rotateCCW();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int teclaPressionada = e.getKeyCode();
		
		if(teclaPressionada == KeyEvent.VK_W) {
			pressedUp = false;
		}
		if(teclaPressionada == KeyEvent.VK_S) {
			pressedDown = false;
		}
		if(teclaPressionada == KeyEvent.VK_A) {
			pressedLeft = false;
		}
		if(teclaPressionada == KeyEvent.VK_D) {
			pressedRight = false;
		}
	}

}
