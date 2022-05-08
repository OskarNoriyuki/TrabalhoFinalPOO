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

            // Botão de novo jogo
            buttonMenu = new JButton();
            //Posição na Matrix
            c.gridx = 0;
            c.gridy = 1;
            //Tamanho
            c.gridwidth=2;
            c.weightx=1;
            c.fill = GridBagConstraints.HORIZONTAL;
            //Espaçamento
            c.insets = new Insets(30,300,0,300);
            buttonMenu.setBackground(Color.GRAY);
            label.add(buttonMenu, c);
            // Ícone do botão
            buttonMenu.setMargin(new Insets(0,0,0,0));
            buttonMenu.setIcon(new ImageIcon("src/img/buttons/MenuButton.png"));
            //Definindo Ações do Botão
            buttonMenu.setActionCommand("menu");
            buttonMenu.addActionListener(this);

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
        SoundPlayer.tocarSom("buttonclick.wav");
        
        MenuWindow menu = new MenuWindow();
        
        janela.dispose();
    }
}