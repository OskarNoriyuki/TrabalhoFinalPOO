/*
	Classe RankingWindow
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import players.Ranking;
import sounds.SoundPlayer;

public class RankingWindow implements ActionListener{
    // Janela, background e ranking do jogo
    private JFrame frame;
    private JLabel background; 
    private Ranking ranking;    

    JTextArea textAreaRanking; // Area de texto com o ranking
    private JButton menuButton; // Botao para voltar ao menu

    // Construtor
    public RankingWindow() {
        // Referencia auxiliar para carregamento das imagens
        BufferedImage image;

        // Carrega ranking salvo
        this.ranking = new Ranking();
        
        // Instancia uma label que contem o background e componentes
        this.background = new JLabel();
        this.background.setLayout(new GridBagLayout());

        // Tenta carregar imagem do background, avisando em uma janela nova caso nao consiga
        try {
            image = ImageIO.read(new FileInputStream("src/img/background/TetrisMenu.png"));
            this.background.setIcon(new ImageIcon(image.getScaledInstance(800, 600, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir TetrisMenu.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Instanciacao da area de texto do ranking
        this.textAreaRanking = new JTextArea();

        // Configuracoes da area de texto do ranking
        this.textAreaRanking.setFont(textAreaRanking.getFont().deriveFont(20f));
        this.textAreaRanking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.textAreaRanking.setEditable(false);
        this.textAreaRanking.setOpaque(true);
        this.textAreaRanking.setText(this.ranking.toString());
        this.textAreaRanking.setForeground(Color.BLACK);
        this.textAreaRanking.setBackground(Color.WHITE);

        // Instanciacao do botão
        this.menuButton = new JButton();

        this.menuButton.setBackground(Color.GRAY);  // Define fundo cinza para o botao
        this.menuButton.setMargin(new Insets(0,0,0,0)); // Retira margem dos botoes

        // Registra listener
        this.menuButton.addActionListener(this);
        this.menuButton.setActionCommand("menu");

        // Tenta carregar imagem do botao, avisando em uma janela nova caso nao consiga
        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/MenuButton.png"));
            this.menuButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir MenuButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Constraints de GridBagLayout para organizacao dos botoes
        GridBagConstraints c = new GridBagConstraints();

        // Define o espacamento e posicao da area de texto e do botao, seguido da adicao ao label
        c.fill = GridBagConstraints.BOTH; // Redimensionamento
        c.insets = new Insets(130,80,0,80); // Espacamento
        background.add(textAreaRanking, c);
        c.gridy = 1; // Localizacao
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(30,300,0,300);
        this.background.add(menuButton, c);
        
        // Instanciacao da janela
        this.frame = new JFrame();
        
        // Configuracoes da janela
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(800, 600));
        this.frame.setResizable(false);
        this.frame.setTitle("Tetris");
        this.frame.setLayout(new GridLayout(0, 1));

        // Adiciona o background a janela
        this.frame.add(background);

        // Deixa visivel
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //Tocar som ao clicar
        SoundPlayer.tocarSom("buttonclick.wav");
        
        // Chama a janela do menu
        MenuWindow menu = new MenuWindow();
        
        this.frame.dispose(); // Destroi a janela atual
    }
}