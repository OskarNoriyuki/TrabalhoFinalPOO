package graphics;

import engine.LeTeclado;
import engine.Tetris;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class StatsField extends JPanel{
	//dimensoes
	private int tamanhoCubo;
	private int tamanhoMiniCubo;
	private int numColunas;
	private int numLinhas;
	private int numColunasPreview;
	private int numLinhasPreview;
	private int larguraCampo;
	private int alturaCampo;
	//ref para o game
	private Tetris jogo;
	//elementos graficos
	private Label titulo, pontuacao, nivel, linhas, dutyCycle, fillerText, prox_0, prox_1, prox_2, prox_3;
	//diversos
	String  m, threadCycle;
	GridBagConstraints c;
	Font TitleFont = new Font("SansSerif", Font.BOLD, 30);
	Font StatsFont;
	Font IndexFont;
	
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
		this.tamanhoMiniCubo = jogo.getMiniCubeSize();
		this.numColunasPreview = jogo.getPreviewSizeX();
		this.numLinhasPreview = jogo.getPreviewSizeY();
		
		if(jogo.isLowResMode()) {
			this.IndexFont = new Font("Serif", Font.BOLD, 12);
			this.StatsFont = new Font("Monospaced", Font.BOLD, 16);
		}else {
			this.IndexFont = new Font("Serif", Font.BOLD, 16);
			this.StatsFont = new Font("Monospaced", Font.BOLD, 20);
		}
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

		
		this.titulo = new Label(" TETRIS");
		this.titulo.setForeground(Color.WHITE);
		this.titulo.setFont(TitleFont);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(titulo , c);

		if(jogo.isLowResMode()) c.weighty = 1;
			else c.weighty = 0.55;
		
		this.prox_0 = new Label("Peca atual:        ");
		this.prox_0.setForeground(Color.WHITE);
		this.prox_0.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(prox_0 , c);
		
		if(jogo.isLowResMode()) c.weighty = 1.8;
			else c.weighty = 1;
		
		this.prox_1 = new Label("Proxima:        ");
		this.prox_1.setForeground(Color.WHITE);
		this.prox_1.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(prox_1 , c);
		
		if(jogo.isLowResMode()) c.weighty = 1;
		else c.weighty = 0.8;
		
		this.prox_2 = new Label("2nd:       ");
		this.prox_2.setForeground(Color.WHITE);
		this.prox_2.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(prox_2 , c);


		
		this.prox_3 = new Label("3rd:       ");
		this.prox_3.setForeground(Color.WHITE);
		this.prox_3.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(prox_3 , c);
		
		this.fillerText = new Label("    ");
		this.fillerText.setForeground(Color.BLACK);
		this.fillerText.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(fillerText , c);

		c.weighty = 0;
		
		this.pontuacao = new Label();
		this.pontuacao.setForeground(Color.GREEN);
		this.pontuacao.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(pontuacao, c);
		
		this.linhas = new Label();
		this.linhas.setForeground(Color.GREEN);
		this.linhas.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(linhas, c);
		
		this.nivel = new Label();
		this.nivel.setForeground(Color.GREEN);
		this.nivel.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 8;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(nivel, c);

		c.weighty = 0.2;
	
		this.dutyCycle = new Label();
		this.dutyCycle.setForeground(Color.GREEN);
		this.dutyCycle.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 9;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(dutyCycle, c);
	
		
	}
	
	//setters
	public void setThreadCycleIndicator(String dutyCycle) {
		this.threadCycle = dutyCycle;
	}
	
	
	//configs graficas do painel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(int x = 0; x < this.numColunasPreview; x++) {
			for(int y = 0; y < this.numLinhasPreview; y++) {
				g2.drawImage(jogo.getPreviewCubeImg(x, y), 40+x*tamanhoMiniCubo, 100+ y*tamanhoMiniCubo, tamanhoMiniCubo, tamanhoMiniCubo, null);
			}
		}
		if(jogo.isLowResMode()) {
			//retangulo interno, destaque da peca atual
			this.fillHollowRect(g2, Color.WHITE, 40, 105, 90, 65, 4);
			//retangulo externo, destaque da peca atual
			this.fillHollowRect(g2, Color.GRAY, 37, 102, 96, 71, 3);
		}else {
			//retangulo interno, destaque da peca atual
			this.fillHollowRect(g2, Color.WHITE, 40, 110, 120, 80, 5);
			//retangulo externo, destaque da peca atual
			this.fillHollowRect(g2, Color.GRAY, 36, 106, 128, 88, 4);
		}

		
		
		
		this.pontuacao.setText("Pontos: " + jogo.getScore()+ "      ");
		this.nivel.setText("Nivel " + jogo.getLevel()+ "      ");
		this.linhas.setText("Linhas: " + jogo.getLines()+ "      ");
		this.dutyCycle.setText("Thread: " + this.threadCycle+" %      ");
		g2.dispose();	//para não acumular lixo na memória
	}
	
	private void fillHollowRect(Graphics g, Color color, int x, int y, int  width, int  height, int thickness) {
		int w = width;
		int h = height;
		int t = thickness;
		
		g.setColor(color);
		//preeenche 4 retangulos criando um retangulo oco
		//horizontais
		g.fillRect(x + t, y, w - 2*t, t);
		g.fillRect(x + t, y + h - t, w - 2*t, t);
		//verticais
		g.fillRect(x, y, t, h);
		g.fillRect(x + w - t, y, t, h);
		
	}
}


