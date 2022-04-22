package graphics;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.*;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;

public class TetrisField extends JPanel implements Runnable{
	private final static int tamanhoCuboOriginal = 20; //20x20 pixels
	private final static int upScale = 2;				
	private final static int tamanhoCubo = tamanhoCuboOriginal*upScale; //20x20 pixels
	
	private int numColunas;
	private int numLinhas;
	private int larguraCampo;
	private int alturaCampo;
	
	Thread tetrisThread;
	LeTeclado comandoTeclado;
	
	//engine - teste
	private int posX;
	private int posY;
	private int velocidade;
	
	//timer - teste
	private final long targetFPS = 60;
	private final long loopInterval = 1000000000/targetFPS; //nanos
	
	public TetrisField(int lin, int col) {
		this.numColunas = col;
		this.numLinhas = lin;
		this.larguraCampo = tamanhoCubo*numColunas;
		this.alturaCampo = tamanhoCubo*numLinhas;
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		//entradas
		comandoTeclado = new LeTeclado();
		this.addKeyListener(comandoTeclado);
		this.setFocusable(true);
		
		//engine
		this.posX = 0;
		this.posY = 0;
		this.velocidade = 1;
	
	}

	public void iniciaTetris() {
		tetrisThread = new Thread(this);
		tetrisThread.start();
	}
	

	public void run() {
		while(tetrisThread != null) {
			
			long startTime = System.nanoTime();
			long endTime = startTime + this.loopInterval;
			//***********************trabalho útil do loop começa aqui********************************
			//teste
			System.out.println("Use as teclas W,S,A,D para controlar o cubo!");
			//System.out.println("X: "+this.posX+"    Y: "+this.posY);
			//processamento do jogo
			update();
			//atualizacao dos elementos graficos
			repaint();
			//**************************************loop_end*******************************************
			long sleepTime = (long)(endTime - System.nanoTime())/1000000;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		if(comandoTeclado.pressedDown) {
			this.posY += this.velocidade;
			if(posY > (this.numLinhas - 1)) {
				this.posY = (this.numLinhas - 1);
			}
		}
		if(comandoTeclado.pressedUp) {
			this.posY -= this.velocidade;
			if(posY < 0) {
				this.posY = 0;
			}
		}
		if(comandoTeclado.pressedRight) {
			this.posX += this.velocidade;
			if(posX > (this.numColunas - 1)) {
				this.posX = (this.numColunas - 1);
			}
		}
		if(comandoTeclado.pressedLeft) {
			this.posX -= this.velocidade;
			if(posX < 0) {
				this.posX = 0;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//faremos uso de alguns metodos importantes de Graphics2D
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(posX*this.tamanhoCubo, posY*this.tamanhoCubo, this.tamanhoCubo, this.tamanhoCubo);
		
		g2.dispose();	//para não acumular lixo na memória
		
	}
}
