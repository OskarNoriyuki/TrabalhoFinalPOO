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
	//dimensoes
	private int tamanhoCubo;
	private int numColunas;
	private int numLinhas;
	private int larguraCampo;
	private int alturaCampo;
	//ref para o game
	private Tetris jogo;
	//elementos graficos
	Label pontuacao, nivel, linhas;
	//construtor do painel auxiliar (mostrador de proximas pecas, pontuacao, nivel, etc)
	public StatsField(Tetris jogo) {
		//engine 
		this.jogo = jogo;
		
		//dimensoes
		this.tamanhoCubo = jogo.getCubeSize();
		this.numColunas = jogo.getSizeX();
		this.numLinhas = jogo.getSizeY();
		this.larguraCampo = tamanhoCubo*numColunas/2; //painel auxiliar eh mais fino que o campo do jogo
		this.alturaCampo = tamanhoCubo*numLinhas;
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setLayout(new FlowLayout());
		
		//elementos diversos
		this.pontuacao = new Label();
		this.nivel = new Label();
		this.linhas = new Label();
		
		this.pontuacao.setForeground(Color.YELLOW);
		this.nivel.setForeground(Color.YELLOW);
		this.linhas.setForeground(Color.YELLOW);
		
		this.add(pontuacao);
		this.add(linhas);
		this.add(nivel);
	
		
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
		
		this.pontuacao.setText(jogo.getScore() + " pontos");
		this.nivel.setText("Nivel " + jogo.getLevel());
		this.linhas.setText(jogo.getLines() + " linhas");
		
		g2.dispose();	//para não acumular lixo na memória
	}
}
