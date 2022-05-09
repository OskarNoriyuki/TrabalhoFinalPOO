/*
	Classe TetrominoeO
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package pecas;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TetrominoeO extends Tetrominoe {
	public TetrominoeO() {
		this.cor = "amarelo";
		this.ladoMatriz = 2;
		//todas as pe�as come�am deitadas
		this.matriz = new boolean[][] 
				{	{	true, 	true 	},
					{	true, 	true 	}};
					/* Tetromino O:
										1| # #
										0| # # 
										y|____
										 x 0 1 
					 */
		this.angle = 0;
		this.reset();
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
	
	public void reset() {
		while(this.angle > 0) {
			this.rotacionar("cw");
		}
		this.x = default_X + 2;
		this.y = default_Y;
		this.x_kick = 0;
		this.y_kick = 0;
	}
	
	public char toChar() {
		return 'O';
	}
}
