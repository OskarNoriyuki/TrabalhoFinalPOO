package graphics;

import engine.LeTeclado;
import engine.Tetris;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.*;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;

public class StatsField extends JPanel{
	private final static int tamanhoCuboOriginal = 20; //20x20 pixels
	private final static int upScale = 2;				
	private final static int tamanhoCubo = tamanhoCuboOriginal*upScale; //20x20 pixels
	
	private int numColunas;
	private int numLinhas;
	private int larguraCampo;
	private int alturaCampo;
	
	private Tetris jogo;
	
	//construtor do painel principal do jogo
	public StatsField(Tetris jogo) {

		this.numColunas = jogo.getSizeX();
		this.numLinhas = jogo.getSizeY();
		this.larguraCampo = tamanhoCubo*numColunas/2;
		this.alturaCampo = tamanhoCubo*numLinhas;
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
	
	}
	
	
	//configs graficas do painel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(int x = 0; x < this.numColunas; x++) {
			for(int y = 0; y < this.numLinhas; y++) {
				//g2.drawImage(jogoTeste.getCubeImg(x, y), x*tamanhoCubo, y*tamanhoCubo, tamanhoCubo, tamanhoCubo, null);
			}
		}
		
		g2.dispose();	//para n�o acumular lixo na mem�ria
	}
}