package data;

import java.io.Serializable;

import engine.Player;

public class TetrisInfo implements Serializable{
	public char map[][];
	public int maxX, maxY;
	public boolean lowRes;
	public Player player;
	public int pontuacao, linhas, nivel;
	public int proximaPeca[];
	public int pecaX;
	public int pecaY;
	
	public TetrisInfo(int lin, int col, boolean lowRes, Player player) {
		this.map = new char[lin][col];
		this.lowRes = lowRes;
		this.player = player;
		this.proximaPeca = new int[7];

	}
}
