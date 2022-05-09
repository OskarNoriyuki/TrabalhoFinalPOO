package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import sounds.SoundPlayer;

public class MenuWindow implements ActionListener {
    // Janela, background e opcoes do jogo
    private  JFrame frame;
    private JLabel background;
    private Options options;

    // Botoes do menu
    private JButton newGameButton;
    private JButton loadButton;
    private JButton rankingButton;
    private JButton optionsButton;
    private JButton exitButton;
    
    // Construtor
    public MenuWindow() {
        // Referencia auxiliar para carregamento das imagens
        BufferedImage image;

        // Instancia opcoes do jogo
        this.options = new Options();
        
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

        // Configuracoes dos botoes

        // Instanciacao dos botoes
        this.newGameButton = new JButton();
        this.loadButton = new JButton();
        this.rankingButton = new JButton();
        this.optionsButton = new JButton();
        this.exitButton = new JButton();

        // Define fundo cinza para os botoes
        this.newGameButton.setBackground(Color.GRAY); 
        this.loadButton.setBackground(Color.GRAY);
        this.rankingButton.setBackground(Color.GRAY);
        this.optionsButton.setBackground(Color.GRAY);
        this.exitButton.setBackground(Color.GRAY);

        // Retira margem dos botoes
        this.newGameButton.setMargin(new Insets(0,0,0,0)); 
        this.loadButton.setMargin(new Insets(0,0,0,0));
        this.rankingButton.setMargin(new Insets(0,0,0,0));
        this.optionsButton.setMargin(new Insets(0,0,0,0));
        this.exitButton.setMargin(new Insets(0,0,0,0));
        
        // Registra listener de cada botao
        this.newGameButton.addActionListener(this);
        this.newGameButton.setActionCommand("new game");
        this.loadButton.addActionListener(this);
        this.loadButton.setActionCommand("load");
        this.rankingButton.addActionListener(this);
        this.rankingButton.setActionCommand("ranking");
        this.optionsButton.addActionListener(this);
        this.optionsButton.setActionCommand("options");
        this.exitButton.addActionListener(this);
        this.exitButton.setActionCommand("exit");

        // Tenta carregar imagem de cada botao, avisando em uma janela nova caso nao consiga
        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/NewGameButton.png"));
            this.newGameButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir NewGameButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/LoadButton.png"));
            this.loadButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir LoadButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/RankingButton.png"));
            this.rankingButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir RankingButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/OptionsButton.png"));
            this.optionsButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir OptionsButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/ExitButton.png"));
            this.exitButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir ExitButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Constraints de GridBagLayout para organizacao dos botoes
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL; // Redimenzionamento horizontal

        // Define o espacamento e posicao de cada botao, seguido da adicao ao label
        c.insets = new Insets(250, 300, 0, 300); // Espacamento
        this.background.add(newGameButton, c);
        c.gridy = 1; // Localizacao
        c.insets = new Insets(30, 300, 0, 300);
        this.background.add(loadButton, c);
        c.gridy = 2;
        this.background.add(rankingButton, c);
        c.gridy = 3;
        this.background.add(optionsButton, c);
        c.gridy = 4;
        this.background.add(exitButton, c);
        
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

    public void actionPerformed(ActionEvent e){
        //Tocar som ao clicar
        SoundPlayer.tocarSom("buttonclick.wav");

		//Caso o botao clicado seja o de novo jogo
		if (e.getActionCommand().equals("new game")) {
            GameWindow game; // Janela do jogo

            // Trocar texto dos botoes na janela de entrada
            UIManager.put("OptionPane.cancelButtonText", "Cancelar");
            UIManager.put("OptionPane.okButtonText", "Jogar");

            // Abre uma janela de entrada e requisita nome do novo jogador
            String playerName = JOptionPane.showInputDialog(null, "Digite o nome do jogador: ", "Novo Jogo", JOptionPane.QUESTION_MESSAGE);
            
            if (playerName != null) {
                // Chama a janela do jogo
                game = new GameWindow(options.getDifficulty(), playerName);
            
                this.frame.dispose(); // Destroi a janela atual
            }
        }

        //Caso o botao clicado seja o de carregar jogo
		if (e.getActionCommand().equals("load")) {
            GameWindow game; // Janela do jogo

            // Trocar texto dos botoes na janela de entrada
            UIManager.put("OptionPane.cancelButtonText", "Cancelar");
            UIManager.put("OptionPane.okButtonText", "Carregar");

            // Abre uma janela de entrada e requisita nome do novo jogador
            String playerName = JOptionPane.showInputDialog(null, "Digite o nome do jogador: ", "Carregar", JOptionPane.QUESTION_MESSAGE);
            
            if (playerName != null) {
                File save = new File("src/players/" + playerName + ".sav");

                // Verifica se arquivo de salvamento existe
                if (save.exists()) {
                    // Chama a janela do jogo
                    game = new GameWindow(options.getDifficulty(), playerName); 
            
                    this.frame.dispose(); // Destroi a janela atual
                }
            }
        }

        //Caso o botao clicado seja o de ranking
		if (e.getActionCommand().equals("ranking")) {
            // Chama a janela do ranking
            RankingWindow ranking = new RankingWindow();
            
            this.frame.dispose(); // Destroi a janela atual
        }

        //Caso o botao clicado seja o de opcoes
		if (e.getActionCommand().equals("options")) 
            this.options.showOptions(); // Abre a janela de opcoes

        //Caso o botao clicado seja o de saida
		if (e.getActionCommand().equals("exit"))
            this.frame.dispose(); // Destroi a janela atual
    }
}