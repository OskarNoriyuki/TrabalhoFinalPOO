/*
	Classe PauseWindow
	Descricao:
	Autores: Allan Ferreira, Pedro Alves e Oskar Akama
*/

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Tetris;
import sounds.SoundPlayer;
import data.DataManager;

public class PauseWindow extends JPanel implements ActionListener {
    private JLabel background;
    private Tetris game;

    // Botoes do menu de pausa
    private JButton saveButton;
    private JButton menuButton;

    private boolean backToMenu; // Flag para avisar retorno ao menu

    // Construtor
    public PauseWindow(Tetris game) {
        // Referencia auxiliar para carregamento das imagens
        BufferedImage image;
        
        this.game = game;           // Instancia o jogo
        this.backToMenu = false;    // Nao voltar para o menu imediatamente

        // Instancia uma label que contem o background e componentes
        this.background = new JLabel();
        this.background.setLayout(new GridBagLayout());

        // Tenta carregar imagem do background, avisando em uma janela nova caso nao consiga
        try {
            image = ImageIO.read(new FileInputStream("src/img/background/TetrisMenu.png"));
            this.background.setIcon(new ImageIcon(image.getScaledInstance(200, 600, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir TetrisMenu.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Configuracoes dos botoes

        // Instanciacao dos botoes
        this.saveButton = new JButton();
        this.menuButton = new JButton();

        // Define fundo cinza para os botoes
        this.saveButton.setBackground(Color.GRAY); 
        this.menuButton.setBackground(Color.GRAY);

        // Retira margem dos botoes
        this.saveButton.setMargin(new Insets(0,0,0,0)); 
        this.menuButton.setMargin(new Insets(0,0,0,0));
        
        // Registra listener de cada botao
        this.saveButton.addActionListener(this);
        this.saveButton.setActionCommand("save");
        this.menuButton.addActionListener(this);
        this.menuButton.setActionCommand("menu");

        // Tenta carregar imagem de cada botao, avisando em uma janela nova caso nao consiga
        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/SaveButton.png"));
            this.saveButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir SaveButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            image = ImageIO.read(new FileInputStream("src/img/buttons/MenuButton.png"));
            this.menuButton.setIcon(new ImageIcon(image));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível abrir MenuButton.png!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        // Constraints de GridBagLayout para organizacao dos botoes
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL; // Redimenzionamento horizontal

        // Define o espacamento e posicao de cada botao, seguido da adicao ao label
        c.insets = new Insets(250, 300, 0, 300); // Espacamento
        this.background.add(saveButton, c);
        c.gridy = 1; // Localizacao
        c.insets = new Insets(30, 300, 0, 300);
        this.background.add(menuButton, c);
        
        // Configuracoes da janela
		this.setPreferredSize(new Dimension(200, 500));
		this.setLayout(new FlowLayout());
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);

        // Adiciona o background a janela
        this.add(background);
    }

    public void toggleMenu() {
        this.setVisible(false);
    }

    public void actionPerformed(ActionEvent e){
        //Tocar som ao clicar
        SoundPlayer.tocarSom("buttonclick.wav");

		//Caso o botao clicado seja o de salvar
		if (e.getActionCommand().equals("save")) {
            DataManager.saveGame(game);   // Salva o jogo

            // Abre uma janela e avisa que o jogo foi salvo
            JOptionPane.showMessageDialog(null, "Jogo salvo!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
        }

        //Caso o botao clicado seja o de voltar ao menu principal
		if (e.getActionCommand().equals("menu")) {
            this.backToMenu = true; // Avisa que deseja-se voltar ao menu principal
        }
    }
}
