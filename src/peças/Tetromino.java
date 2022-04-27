package peças;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Tetromino {
	protected String cor;
	protected int ladoMatriz;
	protected boolean matriz[][]; //[x][y]
	public int x;
	public int y;
	public BufferedImage cube;
	
	public void rotacionar(String sentido) {
		boolean matrizTmp[][] = new boolean[this.ladoMatriz][this.ladoMatriz]; 
		//realoca os elementos para a matriz auxiliar
		for(int x = 0; x < this.ladoMatriz; x++) {
			for(int y = 0; y< this.ladoMatriz; y++) {
				int xSym = this.ladoMatriz-1-x;
				int ySym = this.ladoMatriz-1-y;
				if(sentido.equals("CW") || sentido.equals("cw")|| sentido.equals("dir")|| sentido.equals("DIR"))
					matrizTmp[y][xSym] = this.matriz[x][y];
				if(sentido.equals("CCW") || sentido.equals("ccw")|| sentido.equals("esq")|| sentido.equals("DIR"))
					matrizTmp[ySym][x] = this.matriz[x][y];
			}
		}
		//copia para a matriz principal
		for(int x = 0; x < this.ladoMatriz; x++)
			for(int y = 0; y< this.ladoMatriz; y++)
				this.matriz[x][y] = matrizTmp[x][y];

	}
	
	public int getSize() {
		return this.ladoMatriz;
	}
	
	public boolean getCube(int posX, int posY) {
		if(this.matriz[posX][posY]) {
			return true;
		}else {
			return false;
		}
	}
	
	public abstract BufferedImage getImage();
}
