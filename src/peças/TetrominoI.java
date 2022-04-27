package pe�as;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoI extends Tetromino{
	
	public TetrominoI() {
		this.cor = "azul_claro";
		this.ladoMatriz = 4;
		//todas as pe�as come�am deitadas
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


}
