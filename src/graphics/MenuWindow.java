package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.border.EmptyBorder;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import sounds.SoundPlayer;

import javax.swing.*;

import teste.Aplicacao;

public class MenuWindow implements ActionListener {

    private static final int MARGENS = 300;
    private static final int ESPACAMENTO = 30;
    private static final int CABECALHO = 250;

    private Opcoes opcoes;

    private JButton buttonNewGame;
    private JButton buttonLoad;
    private JButton buttonRanking;
    private JButton buttonOptions;
    private JButton buttonExit;

    public  JFrame janela = new JFrame();
    
    public MenuWindow() {
        
        opcoes = new Opcoes();

        //Configuracoes da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setPreferredSize(new Dimension(800, 600));
        janela.setResizable(false);
		janela.setTitle("Tetris");
        janela.setLayout(new GridLayout(0, 1));
        BufferedImage ImagemdeFundo=null;
        
        
        try {
            //LABEL
            //Criando Label com a Imagem de Fundo
            ImagemdeFundo = ImageIO.read(new FileInputStream("src/img/background/TetrisMenu.png"));
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(new ImageIcon(ImagemdeFundo).getImage().getScaledInstance(800, 600, ImagemdeFundo.SCALE_DEFAULT)));
            label.setLayout( new FlowLayout() );
            janela.add(label);
            //Layout de GridBagConstrains para Organizarmos os Botoes
            label.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            // Botao de novo jogo
            buttonNewGame = new JButton();
            //Posicao na Matrix
            c.gridx = 0;
            c.gridy = 0;
            //Tamanho
            c.gridwidth=2;
            c.weightx=1;
            c.fill = GridBagConstraints.HORIZONTAL;
            //Espacamento
            c.insets = new Insets(CABECALHO,MARGENS,0,MARGENS);
            buttonNewGame.setBackground(Color.GRAY);
            label.add(buttonNewGame, c);
            // icone do botao
            buttonNewGame.setMargin(new Insets(0,0,0,0));
            buttonNewGame.setIcon(new ImageIcon("src/img/buttons/NewGameButton.png"));
            //Definindo Acoes do BotÃ£o
            buttonNewGame.setActionCommand("new game");
            buttonNewGame.addActionListener(this);

            // Botao de carregamento
            this.buttonLoad = new JButton();
            //c.gridx = 0;
            c.gridy = 1;
            //c.gridwidth=2;
            c.insets = new Insets(ESPACAMENTO,MARGENS,0,MARGENS);
            buttonLoad.setBackground(Color.GRAY);
            label.add(buttonLoad, c);

            // Ã�cone do botao
            buttonLoad.setMargin(new Insets(0,0,0,0));
            buttonLoad.setIcon(new ImageIcon("src/img/buttons/LoadButton.png"));

            //Definindo acoes do BotÃ£o
            buttonLoad.setActionCommand("load");
            buttonLoad.addActionListener(this);

            // Botao de ranking
            buttonRanking = new JButton();
            //c.gridx = 0;
            c.gridy = 2;
            //c.gridwidth=2;
            //c.insets = new Insets(ESPACAMENTO,MARGENS,0,MARGENS);
            buttonRanking.setBackground(Color.GRAY);
            label.add(buttonRanking, c);

            // icone do botao
            buttonRanking.setMargin(new Insets(0,0,0,0));
            buttonRanking.setIcon(new ImageIcon("src/img/buttons/RankingButton.png"));

            //Definindo Acoµes do BotÃ£o
            buttonRanking.setActionCommand("ranking");
            buttonRanking.addActionListener(this);

            // Botao de opcoes
            buttonOptions = new JButton();
            //c.gridx = 0;
            c.gridy = 3;
            //c.gridwidth=2;
            //c.insets = new Insets(ESPACAMENTO,MARGENS,0,MARGENS);
            buttonOptions.setBackground(Color.GRAY);
            label.add(buttonOptions, c);
            
            // icone do botao
            buttonOptions.setMargin(new Insets(0,0,0,0));
            buttonOptions.setIcon(new ImageIcon("src/img/buttons/OptionsButton.png"));

            //Definindo Acoes do BotÃ£o
            buttonOptions.setActionCommand("options");
            buttonOptions.addActionListener(this);

            // Botao de saida
            buttonExit = new JButton();
            //c.gridx = 0;
            c.gridy = 4;
            //c.gridwidth=2;
            //c.insets = new Insets(ESPACAMENTO,MARGENS,0,MARGENS);
            buttonExit.setBackground(Color.GRAY);
            label.add(buttonExit, c);
            
            // icone do botao
            buttonExit.setMargin(new Insets(0,0,0,0));
            buttonExit.setIcon(new ImageIcon("src/img/buttons/ExitButton.png"));

            //Definindo Acoes do Botao
            buttonExit.setActionCommand("exit");
            buttonExit.addActionListener(this);    
            

            janela.add(label);
            janela.pack();
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

        
        
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botao clicado seja de novo jogo
		if (e.getActionCommand().equals("new game")) {
            
            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            String playerName = JOptionPane.showInputDialog("Digite o nome do jogador: ");
                
            GameWindow jogo = new GameWindow(opcoes.getDificuldade(), playerName);
            
            janela.dispose();
        }

        //Caso o botao clicado seja o ranking
		if (e.getActionCommand().equals("ranking")) {
            RankingWindow ranking = new RankingWindow();
            
            SoundPlayer.tocarSom("buttonclick.wav");

            janela.dispose();
        }

        //Caso o botao clicado seja o ranking
		if (e.getActionCommand().equals("ranking")) {

            //Tocar som

            RankingWindow rank = new RankingWindow();
            janela.dispose();
        }
        //Caso o botao clicado seja o opcoes
		if (e.getActionCommand().equals("options")) {

            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            opcoes.MostrarOpcoes();
        }
        //Caso o botao clicado seja o fechar
		if (e.getActionCommand().equals("exit")) {

            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            janela.dispose();
        }
    }

}
