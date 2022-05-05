package pecas;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TetrominoT extends Tetromino{
	public TetrominoT() {
		this.cor = "rosa";
		this.ladoMatriz = 3;
		//todas as pe�as come�am deitadas
		this.matriz = new boolean[][] 
				{	{	false, 	true, 	false	},
					{	true, 	true, 	false	},
					{	false, 	true, 	false	}};
					/* Tetromino T:
										2| - - - 
										1| # # #
										0| - # -
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
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_pink.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
	
	public void reset() {
		while(this.angle > 0) {
			this.rotacionar("cw");
		}
		this.x = default_X;
		this.y = default_Y;
		this.x_kick = 0;
		this.y_kick = 0;
	}
	
	public char toChar() {
		return 'T';
	}
}
