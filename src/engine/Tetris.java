//classe controladora

package engine;

import graphics.LeTeclado;
import peças.Tetromino;
import peças.TetrominoT;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tetris {
	private Tetromino pecaTeste;
	private int maxX, maxY;
	private BufferedImage backgroundTile;
	
	public Tetris(int lin, int col) {
		pecaTeste = new TetrominoT();
		pecaTeste.x = 0;
		pecaTeste.y = 0;
		
		this.maxX = col;
		this.maxY = lin;
		
		try {
			this.backgroundTile = ImageIO.read(new FileInputStream("src/img/background/black.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stepRight() {
		pecaTeste.x++;
		if(pecaTeste.x > (this.maxX - pecaTeste.getSize()))
			pecaTeste.x = (this.maxX - pecaTeste.getSize());
	}	
	public void stepLeft() {
		pecaTeste.x--;
		if(pecaTeste.x < 0)
			pecaTeste.x = 0;
	}
	public void stepDown() {
		pecaTeste.y++;
		if(pecaTeste.y > (this.maxY - pecaTeste.getSize()))
			pecaTeste.y = (this.maxY - pecaTeste.getSize());
	}
	public void stepUp() {
		pecaTeste.y--;
		if(pecaTeste.y < 0)
			pecaTeste.y = 0;
	}
	public void rotateCW() {
		pecaTeste.rotacionar("CW");
	}
	public void rotateCCW() {
		pecaTeste.rotacionar("CCW");
	}
	
	public BufferedImage getCubeImg(int posX, int posY) {
		if(posX >= pecaTeste.x && posX <= (pecaTeste.x + pecaTeste.getSize() - 1)){
			if(posY >= pecaTeste.y && posY <= (pecaTeste.y + pecaTeste.getSize() - 1)) {
				//return this.pieceTile;
				
				if(pecaTeste.getCube(posX-pecaTeste.x, posY-pecaTeste.y)) {
					return pecaTeste.getImage();
				}else {
					return this.backgroundTile;
				}
			}else {
				return this.backgroundTile;
			}
		}else {
			return this.backgroundTile;
		}
	}
}
