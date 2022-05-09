package graphics;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Options implements ActionListener{
    JPanel panel;   // Painel para conter os botoes
    int difficulty; // Dificuldade do jogo

    // Botoes para selecionar dificuldade
    private JRadioButton Difficulty1RadioButton;
    private JRadioButton Difficulty2RadioButton;
    private JRadioButton Difficulty3RadioButton;

    // Agrupamento dos botoes
    private ButtonGroup groupDifficultyButtons;

    public Options() {
        this.panel = new JPanel();  // Instanciacao do painel
                
        // Instanciacao dos botoes
        this.Difficulty1RadioButton = new JRadioButton("Noob");
        this.Difficulty2RadioButton = new JRadioButton("Intermediário");
        this.Difficulty3RadioButton = new JRadioButton("Profissional");

        // Registro do listener de cada botao
        this.Difficulty1RadioButton.addActionListener(this);
        this.Difficulty1RadioButton.setActionCommand("difficulty 1");
		this.Difficulty2RadioButton.addActionListener(this);
        this.Difficulty2RadioButton.setActionCommand("difficulty 2");
		this.Difficulty3RadioButton.addActionListener(this);
        this.Difficulty3RadioButton.setActionCommand("difficulty 3");

        this.groupDifficultyButtons = new ButtonGroup(); // Instanciacao do agrupamento

        // Adiciona botoes ao grupo
        this.groupDifficultyButtons.add(Difficulty1RadioButton);
        this.groupDifficultyButtons.add(Difficulty2RadioButton);
        this.groupDifficultyButtons.add(Difficulty3RadioButton);

        // Adiciona botoes ao painel
        this.panel.add(Difficulty1RadioButton);
        this.panel.add(Difficulty2RadioButton);
        this.panel.add(Difficulty3RadioButton);

        // Dificuldade noob como padrao
        this.difficulty = 1; 
        this.Difficulty1RadioButton.setSelected(true);
    }

    // Abre uma janela com os botoes para escolher a dificuldade
    public void showOptions(){
        UIManager.put("OptionPane.okButtonText", "Ok");
        JOptionPane.showMessageDialog(null, panel,  "Dificuldade", JOptionPane.QUESTION_MESSAGE);
    }

    // Getter
    public int getDifficulty(){
        return this.difficulty;
    }

    // Muda a dificuldade conforme a selecao do botao
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("difficulty 1"))
            this.difficulty = 1;
        else if (e.getActionCommand().equals("difficulty 2"))
            this.difficulty = 2;
        else if (e.getActionCommand().equals("difficulty 3"))
            this.difficulty = 3;
    }
}
