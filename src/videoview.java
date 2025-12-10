import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.*;
public class videoview {
    private JFrame ventana;
    private JPanel contentPane;
    private JTextArea infoArea;
    private JScrollPane infoScroll;
    private JMenuItem nuevo,guardarlog,abrirlog, guardar, abrir, salir, mostrar, sobre;

    public JFrame getVentana() { return ventana; }
    public JMenuItem getNuevo() { return nuevo; }
    public JMenuItem getGuardar() { return guardar; }
    public JMenuItem getAbrir() { return abrir; }
    public JMenuItem getSalir() { return salir; }
    public JMenuItem getMostrar() { return mostrar; }
    public JMenuItem getSobre() { return sobre; }
    public JMenuItem getGuardarlog() { return guardarlog; }
    public JMenuItem getAbrirlog() { return abrirlog; }
    public JTextArea getInfoArea() { return infoArea; }

    public videoview() {
        ventana = new JFrame("Juego de Videojuego 3 - Viewer");
        ventana.setSize(1300, 800);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        ventana.setJMenuBar(menuBar);
        JMenu Archivo = new JMenu("Archivo");
        JMenu Ver = new JMenu("Ver");
        JMenu Ayuda = new JMenu("Ayuda");
        menuBar.add(Archivo);
        menuBar.add(Ver);
        menuBar.add(Ayuda);

        nuevo = new JMenuItem("Nuevo");
        salir = new JMenuItem("Salir");
        guardar = new JMenuItem("Guardar");
        abrir = new JMenuItem("Abrir");
        guardarlog = new JMenuItem("Guardar log");
        abrirlog = new JMenuItem("Abrir log");
        Archivo.add(nuevo);
        Archivo.add(guardarlog);
        Archivo.add(abrirlog);
        Archivo.add(abrir);
        Archivo.add(guardar);
        Archivo.add(salir);

        mostrar = new JMenuItem("Mostrar consola");
        Ver.add(mostrar);
        sobre = new JMenuItem("Sobre el juego");
        Ayuda.add(sobre);

        contentPane = new JPanel(new BorderLayout());
        ventana.setContentPane(contentPane);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        infoScroll = new JScrollPane(infoArea);
        infoScroll.setPreferredSize(new Dimension(320, 600));
        contentPane.add(infoScroll, BorderLayout.EAST);

        // Iniciar con mapa
        mapa m = new mapa();
        m.getMapa();
        mostrarTablero(m);
        actualizarInfo(m);

        ventana.setVisible(true);
    }
    public void limpiarCentro() {
        contentPane.removeAll();
        contentPane.add(infoScroll, BorderLayout.EAST);
    }

    public void mostrarTablero(mapa m) {
        limpiarCentro();

        JPanel Tablero = m.getTablero();
        Dimension tableroSize = new Dimension(600, 600);
        Dimension tableroScrollSize = new Dimension(620, 620);

        Tablero.setPreferredSize(tableroSize);
        Tablero.setMinimumSize(tableroSize);
        Tablero.setMaximumSize(tableroSize);

        JScrollPane tableroScroll = new JScrollPane(Tablero);
        tableroScroll.setPreferredSize(tableroScrollSize);
        tableroScroll.setMinimumSize(tableroScrollSize);
        tableroScroll.setMaximumSize(tableroScrollSize);
        tableroScroll.getViewport().setPreferredSize(tableroSize);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(tableroSize);
        wrapper.setMinimumSize(tableroSize);
        wrapper.setMaximumSize(tableroSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        wrapper.add(tableroScroll, gbc);

        contentPane.add(wrapper, BorderLayout.CENTER);
        ventana.revalidate();
        ventana.repaint();
    }

    public void actualizarInfo(mapa m) {
        String info = "";
        info += "Territorio: " + m.getTerritorio() + "\n\n";
        info += "Equipo 1 (activos): " + m.getEquipo1() + "\n";
        info += "Equipo 2 (activos): " + m.getEquipo2() + "\n\n";
        info += VideoJuego3.MostrardatosEquipo(m, 1) + "\n";
        info += VideoJuego3.MostrardatosEquipo(m, 2) + "\n";
        infoArea.setText(info);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            videoview view = new videoview();
            new controller(view);
        });
    }
    public static void writeMapa(mapa mapa) throws Exception {
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream("mapa.bin"))) {
            // Guardar info del mapa
            salida.writeUTF(mapa.getTerritorio() == null ? "" : mapa.getTerritorio());
            salida.writeInt(mapa.getEquipo1());
            salida.writeInt(mapa.getEquipo2());

            // Guardar tablero completo (10x10)
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    soldado s = mapa.get(i, j);
                    // Si por alguna razón la celda fuera null, escribir valores por defecto
                    if (s == null) {
                        salida.writeBoolean(false); // activo
                        salida.writeUTF(""); // nombre
                        salida.writeInt(0); // vida
                        salida.writeInt(0); // vidaactual
                        salida.writeInt(0); // ataque
                        salida.writeInt(0); // defensa
                        salida.writeInt(0); // velocidad
                        salida.writeInt(0); // equipo
                        salida.writeUTF(""); // pais
                        salida.writeUTF(""); // tipo
                        continue;
                    }

                    salida.writeBoolean(s.isActivo());
                    salida.writeUTF(s.getNombre() == null ? "" : s.getNombre());
                    salida.writeInt(s.getVida());
                    salida.writeInt(s.getVidaactual());
                    salida.writeInt(s.getAtaque());
                    salida.writeInt(s.getDefensa());
                    salida.writeInt(s.getVelocidad());
                    salida.writeInt(s.getEquipo());
                    salida.writeUTF(s.getPais() == null ? "" : s.getPais());
                    String tipo = s.getClass().getSimpleName();
                    salida.writeUTF(tipo == null ? "" : tipo);
                }
            }
        }
    }
    public static mapa readMapa() throws Exception {
        DataInputStream entrada = new DataInputStream(new FileInputStream("mapa.bin"));

        // Leer info del mapa
        String territorio = entrada.readUTF();
        int equipo1Count = entrada.readInt();
        int equipo2Count = entrada.readInt();

        // Crear mapa vacío (sin inicializar aleatoriamente)
        mapa m = new mapa(true);

        // Leer tablero completo
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                boolean activo = entrada.readBoolean();
                String nombre = entrada.readUTF();
                int vida = entrada.readInt();
                int vidaActual = entrada.readInt();
                int ataque = entrada.readInt();
                int defensa = entrada.readInt();
                int velocidad = entrada.readInt();
                int equipo = entrada.readInt();
                String pais = entrada.readUTF();
                if (pais.isEmpty()) pais = null;
                String tipo = entrada.readUTF();

                // Recrear el soldado según su tipo
                soldado s;
                if(tipo.equals("espadachin")){
                    s = new espadachin(nombre, vida, ataque, defensa, velocidad);
                }else if(tipo.equals("caballero")){
                    s = new caballero(nombre, vida, ataque, defensa, velocidad);
                }else if(tipo.equals("arquero")){
                    s = new arquero(nombre, vida, ataque, defensa, velocidad);
                }else if(tipo.equals("lancero")){
                    s = new lancero(nombre, vida, ataque, defensa, velocidad);
                }else{
                    s = new soldado(nombre, vida, ataque, defensa, velocidad);
                }

                s.setActivo(activo);
                s.setEquipo(equipo);
                s.setPais(pais);
                s.setVidaactual(vidaActual);
                m.set(i, j, s);
            }
        }
        m.setTerritorio(territorio);
        m.setEquipo1(equipo1Count);
        m.setEquipo2(equipo2Count);
        entrada.close();
        return m;
    }
    public static void writeLog(ArrayList<String> log) throws Exception {
        try (DataOutputStream salida = new DataOutputStream(new FileOutputStream("log.bin"))) {
            // Guardar info del mapa
            salida.writeInt(log.size());
            for (String s : log) {
                salida.writeUTF(s);
            }
        }
    }
    public static ArrayList<String> readLog() throws Exception {
        DataInputStream entrada = new DataInputStream(new FileInputStream("log.bin"));

        // Leer info del mapa
        int logSize = entrada.readInt();
        ArrayList<String> log = new ArrayList<>();
        for (int i = 0; i < logSize; i++) {
            log.add(entrada.readUTF());
        }
        entrada.close();
        return log;
    }
}

