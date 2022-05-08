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
	private Label titulo, pontuacao, nivel, linhas, dutyCycle;
	private JLabel previewBorder;
	private BufferedImage previewBorderImg;
	//diversos
	String  m, threadCycle;
	GridBagConstraints c;
	Font TetrisFont = new Font("Serif", Font.BOLD, 30);
	Font StatsFont = new Font("Serif", Font.BOLD, 20);
	
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
		
		//painel
		this.setPreferredSize(new Dimension(this.larguraCampo, this.alturaCampo));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        //preview
		try {
			this.previewBorderImg = ImageIO.read(new FileInputStream("src/img/map/previewBorder.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		this.previewBorder = new JLabel();
		previewBorder.setIcon(new ImageIcon(new ImageIcon(previewBorderImg).getImage().getScaledInstance(200, 600, previewBorderImg.SCALE_DEFAULT)));
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(previewBorder, c);
		
		//labels
		this.titulo = new Label("TETRIS");
		this.titulo .setForeground(Color.YELLOW);
		this.titulo .setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(titulo , c);
		
		this.pontuacao = new Label();
		this.pontuacao.setForeground(Color.GREEN);
		this.pontuacao.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(pontuacao, c);
		
		this.linhas = new Label();
		this.linhas.setForeground(Color.GREEN);
		this.linhas.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(linhas, c);
		
		this.nivel = new Label();
		this.nivel.setForeground(Color.GREEN);
		this.nivel.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(nivel, c);
	
		this.dutyCycle = new Label();
		this.dutyCycle.setForeground(Color.GREEN);
		this.dutyCycle.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 5 ;
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
		
		this.pontuacao.setText("Pontos: " + jogo.getScore()+ "      ");
		this.nivel.setText("Nivel " + jogo.getLevel()+ "      ");
		this.linhas.setText("Linhas: " + jogo.getLines()+ "      ");
		this.dutyCycle.setText("Thread: " + this.threadCycle+" %      ");
		g2.dispose();	//para não acumular lixo na memória
	}
}
