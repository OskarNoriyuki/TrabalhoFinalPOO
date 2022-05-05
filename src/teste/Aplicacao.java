package teste;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

import graphics.TetrisField;
import graphics.Menu;
import tempo.Timer;

import java.awt.event.*;

public class Aplicacao{

	static public void IniciaTetris() {

		JFrame janela = new JFrame();
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setTitle("Tetris");
		
		TetrisField campoTeste = new TetrisField(20, 10);
		janela.add(campoTeste);
		Timer gameLoop = new Timer(campoTeste, 60); //60 fps
		gameLoop.iniciaTetris();
		
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);

	}

	public static void main (String[] args) {
		Menu menu = new Menu();
	}
}
