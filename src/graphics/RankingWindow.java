/*
	Classe RankingWindow
	Descricao: herda JFrame e implementa ActionListener para perceber cliques nos botoes. 
    Janela que contem componentes do menu de ranking. 
    Apresenta o ranking em um campo de texto.
    Permite abrir a janela do menu principal, seguido da destruicao da janela atual.
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
import javax.swing.SwingConstants;

import engine.Ranking;
import sounds.SoundPlayer;

public class RankingWindow extends JFrame implements ActionListener {
    // Background e ranking do jogo
    private JLabel background; 
    private Ranking ranking;    

    //JTextArea textAreaRanking;  // Area de texto com o ranking
    JLabel textRanking;
    private JButton menuButton; // Botao para voltar ao menu

    // Construtor
    public RankingWindow() {
        super("Tetris");

        // Configuracoes da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(800, 600));
        this.setResizable(false);
        this.setLayout(new GridLayout(0, 1));

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
            JOptionPane.showMessageDialog(null, "N??o foi poss??vel abrir TetrisMenu.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Instanciacao da texto do ranking
        textRanking = new JLabel(this.ranking.toString());
        this.textRanking.setFont(textRanking.getFont().deriveFont(20f));
        this.textRanking.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.textRanking.setHorizontalAlignment(SwingConstants.CENTER);
        this.textRanking.setOpaque(true);
        this.textRanking.setText(this.ranking.toString());
        this.textRanking.setForeground(Color.BLACK);
        this.textRanking.setBackground(Color.WHITE);

        // Instanciacao do bot??o
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
            JOptionPane.showMessageDialog(null, "N??o foi poss??vel abrir MenuButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Constraints de GridBagLayout para organizacao dos botoes
        GridBagConstraints c = new GridBagConstraints();

        // Define o espacamento e posicao da area de texto e do botao, seguido da adicao ao label
        c.fill = GridBagConstraints.BOTH; // Redimensionamento
        c.insets = new Insets(130,80,0,80); // Espacamento
        background.add(textRanking, c);
        c.gridy = 1; // Localizacao
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(30,300,0,300);
        this.background.add(menuButton, c);

        // Adiciona o background a janela
        this.add(background);

        // Deixa visivel
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //Tocar som ao clicar
        SoundPlayer.tocarSom("buttonclick.wav");
        
        // Chama a janela do menu
        MenuWindow menu = new MenuWindow();
        
        this.dispose(); // Destroi a janela atual
    }
}