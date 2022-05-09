/*
	Classe Tetrominoe
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package pecas;

import java.awt.image.BufferedImage;

public abstract class Tetrominoe {
	protected String cor;
	protected int ladoMatriz;
	protected boolean matriz[][]; //[x][y]
	protected final int default_Y = -5;
	protected final int default_X = 3;
	public int x;
	public int y;
	public int x_kick;
	public int y_kick;
	protected int angle;
	public BufferedImage cube;
	
	public void rotacionar(String sentido) {
		boolean matrizTmp[][] = new boolean[this.ladoMatriz][this.ladoMatriz]; 
		//realoca os elementos para a matriz auxiliar
		for(int x = 0; x < this.ladoMatriz; x++) {
			for(int y = 0; y< this.ladoMatriz; y++) {
				int xSym = this.ladoMatriz-1-x;
				int ySym = this.ladoMatriz-1-y;
				if(sentido.equals("CCW") || sentido.equals("ccw")|| sentido.equals("esq")|| sentido.equals("DIR")) {
					matrizTmp[y][xSym] = this.matriz[x][y];
					this.angle -= 90;
					if(angle == -90) 
						this.angle = 270;
				}
				if(sentido.equals("CW") || sentido.equals("cw")|| sentido.equals("dir")|| sentido.equals("DIR")) {
					matrizTmp[ySym][x] = this.matriz[x][y];
					this.angle += 90;
					if(angle == 360) 
						this.angle = 0;
				}
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
	public abstract void reset();
	public abstract char toChar();
}
