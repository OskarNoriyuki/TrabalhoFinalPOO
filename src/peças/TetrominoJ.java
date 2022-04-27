package peças;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoJ extends Tetromino{
	public TetrominoJ() {
		this.cor = "azul_escuro";
		this.ladoMatriz = 3;
		//todas as peças começam deitadas
		this.matriz = new boolean[][] 
				{	{	true, 	false, 	false	},
					{	true, 	true, 	true	},
					{	false,  false, 	false	}};
					/* Tetromino J:
										2| - # -
										1| - # -
										0| # # - 
										y|______
										 x 0 1 2
					 */
		this.getImage();
	}

	@Override
	public BufferedImage getImage() {
		try {
			this.cube = ImageIO.read(new FileInputStream("src/img/cubes/cube_blue.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return this.cube;
	}
}
