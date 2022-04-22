package teste;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

import graphics.TetrisField;

import java.awt.event.*;

public class Aplicacao {
	public static void main (String[] args) {
		JFrame janela = new JFrame();
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setTitle("Tetris");
		
		TetrisField campoTeste = new TetrisField(20, 10);
		janela.add(campoTeste);
		
		campoTeste.iniciaTetris();
		
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}
}
