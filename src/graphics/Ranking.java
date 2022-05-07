package graphics;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ranking implements ActionListener{

    private JButton JButtonFechar;
    private JFrame janela;

    public Ranking(){
        JFrame janela = new JFrame();
        janela.setResizable(false);
        janela.setTitle("Tetris");

        JButtonFechar = new JButton("Fechar");
        janela.add(JButtonFechar);

        JButtonFechar.setActionCommand("Fechar");
        JButtonFechar.addActionListener(this);

        janela.pack();
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
		//Caso o botão clicado seja resetar
		if (e.getActionCommand().equals("Fechar")) {
            //Finaliza Jogo
            System.exit(0);
        }
    }

}


