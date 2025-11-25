import java.awt.*;
import javax.swing.*;
public class videoview {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Juego de Videojuego 3");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);

        // usar layout null para respetar setBounds en Tablero
        JPanel contentPane = new JPanel(null);
        ventana.setContentPane(contentPane);

        JPanel Tablero = new JPanel(new GridLayout(10,10));
        Tablero.setBounds(20,20,600,600);
        contentPane.add(Tablero);

        celdaPanel[][] celdas = new celdaPanel[10][10];
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                celdas[i][j] = new celdaPanel();
                Tablero.add(celdas[i][j]);
            }
        }

        // cargar imagen de forma síncrona y escalarla al tamaño de cada celda
        ImageIcon arquero = new ImageIcon("src/img/arquero.png");
        ImageIcon caballero = new ImageIcon("src/img/caballero.png");
        ImageIcon espadachin = new ImageIcon("src/img/espadachin.jpg");
        ImageIcon lancero = new ImageIcon("src/img/lancero.png");
        Image arc = arquero.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image cab = caballero.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image esp = espadachin.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image lan = lancero.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        celdas[2][3].setImagen(arc);
        celdas[4][5].setImagen(cab);
        celdas[6][7].setImagen(esp);
        celdas[8][9].setImagen(lan);


        ventana.setVisible(true);
    }
}

