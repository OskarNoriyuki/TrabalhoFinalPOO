package peças;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoI extends Tetromino{
	
	public TetrominoI() {
		this.cor = "azul_claro";
		this.ladoMatriz = 4;
		//todas as peças começam deitadas
		this.matriz = new boolean[][] 
				{	{	false, 	true, 	false, 	false	},
					{	false, 	true, 	false, 	false	},
					{	false, 	true, 	false, 	false	},
					{	false,	true, 	false, 	false	}};
					/* Tetromino I:
										3| - - - -
										2| - - - -
										1| # # # #
										0| - - - -
										y|________
										 x 0 1 2 3
					 */
		this.angle = 0;
		this.reset();
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_cyan.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
	
	public void reset() {
		while(this.angle > 0) {
			this.rotacionar("cw");
		}
		this.x = default_X - 1;
		this.y = default_Y;
		this.x_kick = 0;
		this.y_kick = 0;
	}
	
	public char toChar() {
		return 'I';
	}

}
