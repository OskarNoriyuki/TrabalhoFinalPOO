package graphics;

import engine.LeTeclado;
import engine.Tetris;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.*;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;

public class TetrisField extends JPanel{
	//dimensoes
	private int tamanhoCubo;
	private int numColunas;
	private int numLinhas;
	private int larguraCampo;
	private int alturaCampo;
	
	//Thread tetrisThread;
	LeTeclado comandoTeclado;
	
	//engine
	private Tetris jogo;
	
	//construtor do painel principal do jogo
	public TetrisField(Tetris jogo) {
		//engine
		this.jogo = jogo;
		
		//dimensoes
		this.tamanhoCubo = this.jogo.getCubeSize();
		this.numColunas = this.jogo.getSizeX();
		this.numLinhas = this.jogo.getSizeY();
		this.larguraCampo = tamanhoCubo*numColunas;
		this.alturaCampo = tamanhoCubo*numLinhas;

		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setLayout(new FlowLayout());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		//entradas
		comandoTeclado = new LeTeclado(jogo);
		this.addKeyListener(comandoTeclado);
		this.setFocusable(true);
	
	}
	
	//configs graficas do painel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(int x = 0; x < this.numColunas; x++) {
			for(int y = 0; y < this.numLinhas; y++) {
				g2.drawImage(jogo.getCubeImg(x, y), x*tamanhoCubo, y*tamanhoCubo, tamanhoCubo, tamanhoCubo, null);
			}
		}
		g2.dispose();	//para não acumular lixo na memória
	}
}
