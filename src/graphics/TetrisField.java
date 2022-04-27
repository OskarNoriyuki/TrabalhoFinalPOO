package graphics;

import engine.Tetris;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.*;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;

public class TetrisField extends JPanel{
	private final static int tamanhoCuboOriginal = 20; //20x20 pixels
	private final static int upScale = 2;				
	private final static int tamanhoCubo = tamanhoCuboOriginal*upScale; //20x20 pixels
	
	private int numColunas;
	private int numLinhas;
	private int larguraCampo;
	private int alturaCampo;
	
	//Thread tetrisThread;
	LeTeclado comandoTeclado;
	
	//engine - teste da peça
	private Tetris jogoTeste;
	
	//construtor do painel principal do jogo
	public TetrisField(int lin, int col) {
		this.numColunas = col;
		this.numLinhas = lin;
		this.larguraCampo = tamanhoCubo*numColunas;
		this.alturaCampo = tamanhoCubo*numLinhas;
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		//engine
		jogoTeste = new Tetris(lin, col);
		
		//entradas
		comandoTeclado = new LeTeclado(jogoTeste);
		this.addKeyListener(comandoTeclado);
		this.setFocusable(true);
	
	}
	
	//teste, jogo
	public void updateT() {
		if(comandoTeclado.pressedDown) {
			jogoTeste.stepDown();
		}
		if(comandoTeclado.pressedUp) {
			jogoTeste.stepUp();
		}
		if(comandoTeclado.pressedLeft) {
			jogoTeste.stepLeft();
		}
		if(comandoTeclado.pressedRight) {
			jogoTeste.stepRight();
		}
	}
	
	//configs graficas do painel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//faremos uso de alguns metodos importantes de Graphics2D
		Graphics2D g2 = (Graphics2D)g;

		//teste do tetromino T:
		for(int x = 0; x < this.numColunas; x++) {
			for(int y = 0; y < this.numLinhas; y++) {
				g2.drawImage(jogoTeste.getCubeImg(x, y), x*tamanhoCubo, y*tamanhoCubo, tamanhoCubo, tamanhoCubo, null);
			}
		}
		
		
		g2.dispose();	//para não acumular lixo na memória
	}
}
