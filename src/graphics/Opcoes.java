package graphics;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Opcoes implements ActionListener{
    
    JPanel panel;
    int opcaoRadioButton=1;
    private ButtonGroup grupoBotoes;
    private JRadioButton Dificuldade1;
    private JRadioButton Dificuldade2;
    private JRadioButton Dificuldade3;

    public Opcoes(){

        this.panel = new JPanel();
        
        Dificuldade1 = new JRadioButton("Noob");
        Dificuldade1.setActionCommand("Dificuldade1");

        Dificuldade2 = new JRadioButton("Intermediário");
        Dificuldade2.setActionCommand("Dificuldade2");

        Dificuldade3 = new JRadioButton("Profissional");
        Dificuldade3.setActionCommand("Dificuldade3");

        Dificuldade1.addActionListener(this);
		Dificuldade2.addActionListener(this);
		Dificuldade3.addActionListener(this);

        grupoBotoes = new ButtonGroup();
        grupoBotoes.add(Dificuldade1);
        grupoBotoes.add(Dificuldade2);
        grupoBotoes.add(Dificuldade3);

        panel.add(Dificuldade1);
        panel.add(Dificuldade2);
        panel.add(Dificuldade3);

        Dificuldade1.setSelected(true);


    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Dificuldade1")) {
            opcaoRadioButton=1;
        }
        else if (e.getActionCommand().equals("Dificuldade2")) {
            opcaoRadioButton=2;
        }
        else if (e.getActionCommand().equals("Dificuldade3")) {
            opcaoRadioButton=3;
        }

    }

    void MostrarOpcoes(){

        if(opcaoRadioButton==1)
            Dificuldade1.setSelected(true);
        else if(opcaoRadioButton==2)
            Dificuldade2.setSelected(true);
        else if(opcaoRadioButton==3)
            Dificuldade3.setSelected(true);

        JOptionPane.showMessageDialog(null, panel);
    }

    public int getDificuldade(){
        return(opcaoRadioButton);
    }
}
