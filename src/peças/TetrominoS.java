package pe�as;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoS extends Tetromino{
	public TetrominoS() {
		this.cor = "verde";
		this.ladoMatriz = 3;
		//todas as pe�as come�am deitadas
		this.matriz = new boolean[][] 
				{	{	false, 	true, 	false	},
					{	false, 	true, 	true	},
					{	false, 	false, 	true	}};
					/* Tetromino S:
										2| - # #
										1| # # -
										0| - - - 
										y|______
										 x 0 1 2
					 */
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_green.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
}
