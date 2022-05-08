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

public class Menu implements ActionListener {

    private static final int MARGENS = 300;
    private static final int ESPACAMENTO = 30;
    private static final int CABECALHO = 300;
    private static final int RODAPE = 130;

    private Opcoes opcoes;

    private JButton JButtonPlay;
    private JButton JButtonRanking;
    private JButton JButtonOpcoes;
    private JButton JButtonSair;

    public  JFrame janela = new JFrame();
    
    public Menu() {
        
        //Configurações da janela
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
            //Layout de GridBagConstrains para Organizarmos os Botões
            label.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

        //BOTÃO PLAY
        JButtonPlay = new JButton();
        //Posição na Matrix
        c.gridx = 0;
        c.gridy = 0;
        //Tamanho
        c.gridwidth=2;
        c.weightx=1;
        c.fill = GridBagConstraints.HORIZONTAL;
        //Espaçamento
        c.insets = new Insets(CABECALHO,MARGENS,0,MARGENS);
        JButtonPlay.setBackground(Color.GRAY);
        label.add(JButtonPlay, c);
        //Definindo Ações do Botão
        JButtonPlay.setActionCommand("iniciar");
        JButtonPlay.addActionListener(this);

        //BOTÃO RANKING
        JButtonRanking = new JButton();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth=2;
        c.insets = new Insets(ESPACAMENTO,MARGENS,0,MARGENS);
        JButtonRanking.setBackground(Color.GRAY);
        label.add(JButtonRanking, c);
        //Definindo Ações do Botão
        JButtonRanking.setActionCommand("ranking");
        JButtonRanking.addActionListener(this);
        

        //BOTÃO OPÇÕES
        JButtonOpcoes = new JButton("Opções!");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth=1;
        c.insets = new Insets(ESPACAMENTO,MARGENS,RODAPE,0);
        label.add(JButtonOpcoes, c);
        //Definindo Ações do Botão
        JButtonOpcoes.setActionCommand("opcoes");
        JButtonOpcoes.addActionListener(this);

        //BOTÃO SAIR
        JButtonSair = new JButton("Fechar");
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth=1;
        c.insets = new Insets(ESPACAMENTO,0,RODAPE,MARGENS);
        label.add(JButtonSair, c);
        //Definindo Ações do Botão
        JButtonSair.setActionCommand("fechar");
        JButtonSair.addActionListener(this);

        janela.add(label);
        janela.pack();
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);

        //BOTÃO PLAY
        //carregamento do ícone
        ImageIcon playicon = new ImageIcon("src/img/buttons/PlayBotao.png");
        JButtonPlay.setMargin(new Insets(0,0,0,0));
        JButtonPlay.setIcon(playicon);

        //BOTÃO RANKING
        //carregamento do ícone
        ImageIcon rankingicon = new ImageIcon("src/img/buttons/RankingBotao.png");
        JButtonRanking.setIcon(rankingicon);
        JButtonRanking.setMargin(new Insets(0,0,0,0));        
        
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

        
        
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botão clicado seja o play
		if (e.getActionCommand().equals("iniciar")) {
            
            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            if(opcoes==null){
                GameWindow jogo = new GameWindow(1);
            }
            else{
                GameWindow jogo = new GameWindow(opcoes.getDificuldade());
            }
            
            janela.dispose();
        }
        //Caso o botão clicado seja o ranking
		if (e.getActionCommand().equals("ranking")) {

            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            Ranking rank = new Ranking();
            janela.dispose();
        }
        //Caso o botão clicado seja o opcoes
		if (e.getActionCommand().equals("opcoes")) {

            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            if(opcoes==null) {
                opcoes = new Opcoes();
            }
            else{
                opcoes.MostrarOpcoes();
            }
        }
        //Caso o botão clicado seja o fechar
		if (e.getActionCommand().equals("fechar")) {

            //Tocar som
            SoundPlayer.tocarSom("buttonclick.wav");

            janela.dispose();
        }
    }

}
