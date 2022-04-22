package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class LeTeclado implements KeyListener{
	
	public boolean pressedUp, pressedDown, pressedLeft, pressedRight; 
	
	public void LeTeclado() {
		this.pressedDown = false;
		this.pressedUp = false;
		this.pressedLeft = false;
		this.pressedRight = false;
		
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
