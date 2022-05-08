package graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sounds.SoundPlayer;
import players.Ranking;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RankingWindow implements ActionListener{

    private JButton buttonMenu;
    private JFrame janela;

    Ranking ranking;

    public RankingWindow(){
        this.ranking = new Ranking();
        
        //Configurações da janela
        janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setPreferredSize(new Dimension(800, 600));
        janela.setResizable(false);
		janela.setTitle("Tetris");
        janela.setLayout(new GridLayout(0, 1));
        BufferedImage ImagemdeFundo=null;

        try {
            //LABEL
            //Criando Label com a Imagem de Fundo
            ImagemdeFundo = ImageIO.read(new FileInputStream("src/img/background/TetrisRanking.jpg"));
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(new ImageIcon(ImagemdeFundo).getImage().getScaledInstance(800, 600, ImagemdeFundo.SCALE_DEFAULT)));
            label.setLayout( new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            janela.add(label);

            //PANEL DE LEITURA DO RANKING
            JTextArea TextoRanking = new JTextArea();
            TextoRanking.setFont(TextoRanking.getFont().deriveFont(20f));
            TextoRanking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            TextoRanking.setEditable(false);
            TextoRanking.setOpaque(false);
            TextoRanking.setText(this.ranking.toString());
            TextoRanking.setForeground(Color.white);
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 1;
            c.weightx = 1;
            c.gridwidth=2;
            c.gridheight=1;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(40,80,0,80);  //top padding
            label.add(TextoRanking, c);

            //Botão para voltar ao Menu
            JButton menu  = new JButton("Menu");
            c.gridx = 0;
            c.gridy = 1;
            c.weighty = 0;
            c.weightx = 0.5;
            c.gridwidth=1;
            c.gridheight=1;
            c.insets = new Insets(20,120,20,20);
            label.add(menu, c);
            menu.setActionCommand("Menu");
            menu.addActionListener(this);

            // Botão de novo jogo
            buttonNewGame = new JButton();
            //Posição na Matrix
            c.gridx = 0;
            c.gridy = 0;
            //Tamanho
            c.gridwidth=2;
            c.weightx=1;
            c.fill = GridBagConstraints.HORIZONTAL;
            //Espaçamento
            c.insets = new Insets(CABECALHO,MARGENS,0,MARGENS);
            buttonNewGame.setBackground(Color.GRAY);
            label.add(buttonNewGame, c);
            // Ícone do botão
            buttonNewGame.setMargin(new Insets(0,0,0,0));
            buttonNewGame.setIcon(new ImageIcon("src/img/buttons/NewGameButton.png"));
            //Definindo Ações do Botão
            buttonNewGame.setActionCommand("new game");
            buttonNewGame.addActionListener(this);

            //Botão para Fechar
            JButton Fechar  = new JButton("Sair");
            c.gridx = 1;
            c.gridy = 1;
            c.weighty = 0;
            c.weightx = 0.5;
            c.gridwidth=1;
            c.gridheight=1;
            c.insets = new Insets(20,20,20,120);  //top padding
            label.add(Fechar, c);
            Fechar.setActionCommand("Fechar");
            Fechar.addActionListener(this);

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
		//Caso o botão clicado seja resetar
		if (e.getActionCommand().equals("Fechar")) {
            SoundPlayer.tocarSom("buttonclick.wav");
            //Finaliza Jogo
            System.exit(0);
        }
        if (e.getActionCommand().equals("Menu")) {
            SoundPlayer.tocarSom("buttonclick.wav");
            //Volta ao Menu
            MenuWindow menu = new MenuWindow();
            janela.dispose();
        }
    }
}