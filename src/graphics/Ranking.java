package graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
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

public class Ranking implements ActionListener{

    private JButton JButtonFechar;
    private JFrame janela;

    String nomeJogador;

    public Ranking(String nomeJogador){
        this.nomeJogador=nomeJogador;
        BuildRanking();
    }
    public Ranking(){
        BuildRanking();
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botão clicado seja resetar
		if (e.getActionCommand().equals("Fechar")) {
            //Finaliza Jogo
            System.exit(0);
        }
        if (e.getActionCommand().equals("Menu")) {
            //Volta ao Menu
            Menu menu = new Menu();
            janela.dispose();

        }
    }

    private void BuildRanking(){
        //Configurações da janela
        janela = new JFrame();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setPreferredSize(new Dimension(800, 600));
        janela.setResizable(false);
		janela.setTitle("Tetris");
        janela.setLayout(new GridLayout(0, 1));
        BufferedImage ImagemdeFundo=null;
        String StringRanking = "";

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
            TextoRanking.setEditable(false);
            TextoRanking.setOpaque(false);
            TextoRanking.setText(StringRanking);;
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

}


