package peças;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoZ extends Tetromino{
	public TetrominoZ() {
		this.cor = "vermelho";
		this.ladoMatriz = 3;
		//todas as peças começam deitadas
		this.matriz = new boolean[][] 
				{	{	false, 	false, 	true	},
					{	false, 	true, 	true	},
					{	false, 	true, 	false	}};
					/* Tetromino Z:
										2| # # -
										1| - # #
										0| - - - 
										y|______
										 x 0 1 2
					 */
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_red.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
}