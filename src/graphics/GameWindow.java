package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import teste.Aplicacao;
import tempo.Timer;

public class GameWindow extends JFrame{

    JFrame janela;
	Timer gameLoop;

    public GameWindow() {

		JFrame janela = new JFrame();
        //janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setTitle("Tetris");
		
		TetrisField campoTeste = new TetrisField(20, 10);
		janela.add(campoTeste);
		Timer gameLoop = new Timer(campoTeste, 60, janela); //60 fps
		gameLoop.iniciaTetris();
		
		janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);

    }

	public void fecharJanela(){
		janela.setVisible(false);
	}

}
