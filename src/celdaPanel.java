import java.awt.*;
import javax.swing.*;
public class celdaPanel extends JPanel{
    private Image imagen;
    private Color fondo=Color.WHITE;
    public void setImagen(Image imagen){
        this.imagen=imagen;
        repaint();
    }
    public void setFondo(Color fondo){
        this.fondo=fondo;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(fondo);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(imagen!=null){
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }
    
}
