package peças;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoO extends Tetromino{
	public TetrominoO() {
		this.cor = "amarelo";
		this.ladoMatriz = 2;
		//todas as peças começam deitadas
		this.matriz = new boolean[][] 
				{	{	true, 	true 	},
					{	true, 	true 	}};
					/* Tetromino O:
										1| # #
										0| # # 
										y|____
										 x 0 1 
					 */
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_yellow.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
}
