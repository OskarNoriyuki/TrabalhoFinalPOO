package pecas;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoL extends Tetromino{
	public TetrominoL() {
		this.cor = "laranja";
		this.ladoMatriz = 3;
		//todas as pe�as come�am deitadas
		this.matriz = new boolean[][] 
				{	{	false, 	false, 	false	},
					{	true, 	true, 	true	},
					{	true, 	false, 	false	}};
					/* Tetromino L:
										2| - # -
										1| - # -
										0| - # # 
										y|______
										 x 0 1 2
					 */
		this.angle = 0;
		this.reset();
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_orange.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
	
	public void reset() {
		while(this.angle > 0) {
			this.rotacionar("cw");
		}
		this.x = default_X + 1;
		this.y = default_Y;
		this.x_kick = 0;
		this.y_kick = 0;
	}
	
	public char toChar() {
		return 'L';
	}
}
