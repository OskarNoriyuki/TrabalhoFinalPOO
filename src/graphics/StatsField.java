/*
	Classe StatsField
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;

import javax.swing.JPanel;

import engine.Tetris;

public class StatsField extends JPanel {
	// Dimensoes
	private int sizeCube;
	private int sizeMiniCube;
	private int numColumns;
	private int numRows;
	private int numColumnsPreview;
	private int numRowsPreview;
	private int widthField;
	private int heightField;
	
	// Logica do jogo
	private Tetris game;

	// Elementos graficos
	private Label title, points, level, rows, dutyCycle, fillerText, next_0, next_1, next_2, next_3;
	
	// diversos
	String  m, threadCycle;
	GridBagConstraints c;
	Font TitleFont = new Font("SansSerif", Font.BOLD, 30);
	Font StatsFont;
	Font IndexFont;
	
	//construtor do painel auxiliar (mostrador de proximas pecas, points, level, etc)
	public StatsField(Tetris game) {
		// Instancia  
		this.game = game;
		
		//dimensoes
		this.sizeCube = game.getCubeSize();
		this.numColumns = game.getSizeX();
		this.numRows = game.getSizeY();
		this.widthField = sizeCube*numColumns/2; //painel auxiliar eh mais fino que o campo do jogo
		this.heightField = sizeCube*numRows;
		this.sizeMiniCube = game.getMiniCubeSize();
		this.numColumnsPreview = game.getPreviewSizeX();
		this.numRowsPreview = game.getPreviewSizeY();
		
		if(game.isLowResMode()) {
			this.IndexFont = new Font("Serif", Font.BOLD, 12);
			this.StatsFont = new Font("Monospaced", Font.BOLD, 16);
		}else {
			this.IndexFont = new Font("Serif", Font.BOLD, 16);
			this.StatsFont = new Font("Monospaced", Font.BOLD, 20);
		}
		
		//painel
		this.setPreferredSize(new Dimension(this.widthField, this.heightField));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

		
		this.title = new Label(" TETRIS");
		this.title.setForeground(Color.WHITE);
		this.title.setFont(TitleFont);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		this.add(title , c);

		if(game.isLowResMode()) c.weighty = 1;
			else c.weighty = 0.55;
		
		this.next_0 = new Label("Peca atual:        ");
		this.next_0.setForeground(Color.WHITE);
		this.next_0.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(next_0 , c);
		
		if(game.isLowResMode()) c.weighty = 1.8;
			else c.weighty = 1;
		
		this.next_1 = new Label("Proxima:        ");
		this.next_1.setForeground(Color.WHITE);
		this.next_1.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(next_1 , c);
		
		if(game.isLowResMode()) c.weighty = 1;
		else c.weighty = 0.8;
		
		this.next_2 = new Label("2nd:       ");
		this.next_2.setForeground(Color.WHITE);
		this.next_2.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(next_2 , c);


		
		this.next_3 = new Label("3rd:       ");
		this.next_3.setForeground(Color.WHITE);
		this.next_3.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(next_3 , c);
		
		this.fillerText = new Label("    ");
		this.fillerText.setForeground(Color.BLACK);
		this.fillerText.setFont(IndexFont);
		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(fillerText , c);

		c.weighty = 0;
		
		this.points = new Label();
		this.points.setForeground(Color.GREEN);
		this.points.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 6;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(points, c);
		
		this.rows = new Label();
		this.rows.setForeground(Color.GREEN);
		this.rows.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 7;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(rows, c);
		
		this.level = new Label();
		this.level.setForeground(Color.GREEN);
		this.level.setFont(StatsFont);
		c.gridx = 0;
		c.gridy = 8;
		c.anchor = GridBagConstraints.LINE_START;
		this.add(level, c);

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
		for(int x = 0; x < this.numColumnsPreview; x++) {
			for(int y = 0; y < this.numRowsPreview; y++) {
				g2.drawImage(game.getPreviewCubeImg(x, y), 40+x*sizeMiniCube, 100+ y*sizeMiniCube, sizeMiniCube, sizeMiniCube, null);
			}
		}
		if(game.isLowResMode()) {
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

		
		
		
		this.points.setText("Pontos: " + game.getScore()+ "      ");
		this.level.setText("level " + game.getLevel()+ "      ");
		this.rows.setText("rows: " + game.getLines()+ "      ");
		this.dutyCycle.setText("Thread: " + this.threadCycle+" %      ");
		g2.dispose();	//para n�o acumular lixo na mem�ria
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


