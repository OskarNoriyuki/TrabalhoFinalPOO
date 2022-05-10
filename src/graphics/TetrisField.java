/*
	Classe TetrisField
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import engine.ReadKeyboard;
import engine.Tetris;

public class TetrisField extends JPanel {
	// Dimensoes
	private int sizeCube;
	private int numColumns;
	private int numRows;
	private int widthField;
	private int heightField;
	
	// Listener;
	//private ReadKeyboard keyboardCommand;
	
	// Logica do jogo
	private Tetris game;
	
	//atributos importantes para o menu de pausa
	private BufferedImage TitleImg;
	private BufferedImage Option0Img;
	private BufferedImage Option1Img;
	private BufferedImage ArrowImg;
	
	// Construtor
	public TetrisField(Tetris game) {
		// Instanciacao do jogo
		this.game = game;
		
		//imagenns do menu de pausa
		try {
			this.ArrowImg = ImageIO.read(new FileInputStream("src/img/MenuPause/Arrow.png"));
			this.TitleImg = ImageIO.read(new FileInputStream("src/img/MenuPause/Title.png"));
			this.Option0Img = ImageIO.read(new FileInputStream("src/img/buttons/SaveButton.png"));
			this.Option1Img = ImageIO.read(new FileInputStream("src/img/buttons/MenuButton.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		// Instanciacao das dimensoes
		this.sizeCube = this.game.getCubeSize();
		this.numColumns = this.game.getSizeX();
		this.numRows = this.game.getSizeY();
		this.widthField = sizeCube*numColumns;
		this.heightField = sizeCube*numRows;

		// Configuracoes da janela
		this.setPreferredSize(new Dimension(this.widthField, this.heightField));
		this.setLayout(new FlowLayout());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
		// Registra listener
		//keyboardCommand = new ReadKeyboard(game);
		//this.addKeyListener(keyboardCommand);
		this.addKeyListener(new ReadKeyboard(game));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		
		// Desenha o jogo
		for(int x = 0; x < this.numColumns; x++)
			for(int y = 0; y < this.numRows; y++)
				g2.drawImage(game.getCubeImg(x, y), x*sizeCube, y*sizeCube, sizeCube, sizeCube, null);
		
		if(game.isPaused()) {
			g2.drawImage(this.TitleImg, 0, sizeCube*5, sizeCube*10, sizeCube, null);
			g2.drawImage(this.Option0Img, sizeCube, sizeCube*6, sizeCube*8, sizeCube, null);
			g2.drawImage(this.Option1Img, sizeCube, sizeCube*7, sizeCube*8, sizeCube, null);
			if(game.getOption() == 0) {
				g2.drawImage(this.ArrowImg, 0, sizeCube*6, sizeCube, sizeCube, null);
			}else if(game.getOption() == 1) {
				g2.drawImage(this.ArrowImg, 0, sizeCube*7, sizeCube, sizeCube, null);
			}
		}
		g2.dispose(); // Para nao acumular lixo na memoria
	}
}