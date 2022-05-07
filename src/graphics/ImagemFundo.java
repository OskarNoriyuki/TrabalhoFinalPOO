package graphics;

import javax.swing.JComponent;

import java.awt.Image;

import java.awt.Graphics;

public class ImagemFundo extends JComponent {
    private Image image;
    public ImagemFundo(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,800,600,this);
    }
    
}
