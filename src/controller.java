import java.util.ArrayList;
import javax.swing.*;
public class controller {
    private videoview view;
    private mapa mapaActual;
    private ArrayList<String> log = new ArrayList<>();

    public controller(videoview view) {
        this.view = view;
        this.mapaActual = null;
        setupListeners();
    }

    private void setupListeners() {
        view.getNuevo().addActionListener(e -> generarMapa());
        view.getGuardarlog().addActionListener(e -> {
            try {
                guardarInfoComoTxt(view);
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Log guardado correctamente en .txt.", 
                    "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Error al guardar: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        view.getAbrirlog().addActionListener(e -> {
            try {
                abrirInfoComoTxt();
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Log cargado correctamente.", 
                    "Abierto", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Error al abrir: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        view.getGuardar().addActionListener(e -> {
            try {
                if (mapaActual == null) {
                    JOptionPane.showMessageDialog(view.getVentana(), 
                        "No hay mapa generado. Presiona 'Nuevo' primero.", 
                        "Sin mapa", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                videoview.writeMapa(mapaActual);
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Mapa guardado correctamente.", 
                    "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Error al guardar: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        view.getAbrir().addActionListener(e -> {
            try {
                mapa m = videoview.readMapa();
                mapaActual = m;
                mostrarMapa(m);
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Mapa cargado correctamente.", 
                    "Abierto", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Error al abrir: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getSalir().addActionListener(e -> view.getVentana().dispose());

        view.getMostrar().addActionListener(e -> {
            if (mapaActual == null) {
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "No hay mapa generado. Presiona 'Nuevo' primero.", 
                    "Sin mapa", JOptionPane.WARNING_MESSAGE);
                return;
            }
            mapaActual.getMapa();
            JOptionPane.showMessageDialog(view.getVentana(), 
                "Salida de consola impresa en terminal (getMapa).", 
                "Consola", JOptionPane.INFORMATION_MESSAGE);
        });

        view.getSobre().addActionListener(e -> {
            if (mapaActual == null) {
                JOptionPane.showMessageDialog(view.getVentana(),
                    "No hay un tablero generado. Presiona 'Nuevo' primero.",
                    "Sin tablero",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Equipo 1: ").append(mapaActual.getEquipo1()).append("\n\n");
            sb.append("Equipo 2: ").append(mapaActual.getEquipo2()).append("\n\n");
            sb.append(VideoJuego3.BuscarSoldadoVida(mapaActual, 1)).append("\n\n");
            sb.append(VideoJuego3.BuscarSoldadoVida(mapaActual, 2)).append("\n\n");
            sb.append(VideoJuego3.PromediodeVida(mapaActual,1)).append("\n\n");
            sb.append(VideoJuego3.PromediodeVida(mapaActual,2)).append("\n\n");
            sb.append(VideoJuego3.MostrardatosEquipo(mapaActual,1)).append("\n");
            sb.append(VideoJuego3.MostrardatosEquipo(mapaActual,2)).append("\n");
            sb.append(VideoJuego3.RankingDeSoldadosDeEquipo(mapaActual,1)).append("\n");
            sb.append(VideoJuego3.RankingDeSoldadosDeEquipo(mapaActual,2)).append("\n");

            JTextArea infoAreaDialog = new JTextArea(sb.toString());
            infoAreaDialog.setEditable(false);
            infoAreaDialog.setLineWrap(true);
            infoAreaDialog.setWrapStyleWord(true);
            infoAreaDialog.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            JScrollPane infoScrollDialog = new JScrollPane(infoAreaDialog);
            infoScrollDialog.setPreferredSize(new java.awt.Dimension(600, 400));

            JOptionPane.showMessageDialog(view.getVentana(), infoScrollDialog, 
                "Datos del tablero", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void generarMapa() {
        mapa m = new mapa();
        m.getMapa();
        mapaActual = m;
        mostrarMapa(m);
    }

    private void mostrarMapa(mapa m) {
        view.limpiarCentro();
        view.mostrarTablero(m);
        view.actualizarInfo(m);
    }
    private void guardarInfoComoTxt(videoview view) throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar información de la partida");
        chooser.setSelectedFile(new java.io.File("partida_log.txt"));
        int opcion = chooser.showSaveDialog(view.getVentana());
        if (opcion == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(file))) {
                // Información básica del panel lateral
                bw.write(view.getInfoArea().getText());
                bw.write("\n\n");
                bw.write("================== INFORMACIÓN DETALLADA ==================\n\n");
                
                // Información completa (como en "Sobre")
                if (mapaActual != null) {
                    bw.write("Equipo 1: " + mapaActual.getEquipo1() + "\n\n");
                    bw.write("Equipo 2: " + mapaActual.getEquipo2() + "\n\n");
                    bw.write(VideoJuego3.BuscarSoldadoVida(mapaActual, 1) + "\n\n");
                    bw.write(VideoJuego3.BuscarSoldadoVida(mapaActual, 2) + "\n\n");
                    bw.write(VideoJuego3.PromediodeVida(mapaActual, 1) + "\n\n");
                    bw.write(VideoJuego3.PromediodeVida(mapaActual, 2) + "\n\n");
                    bw.write(VideoJuego3.RankingDeSoldadosDeEquipo(mapaActual, 1) + "\n");
                    bw.write(VideoJuego3.RankingDeSoldadosDeEquipo(mapaActual, 2) + "\n");
                }
            }
        }
    }
    private void abrirInfoComoTxt() throws Exception {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Abrir información de la partida");
        int opcion = chooser.showOpenDialog(view.getVentana());
        if (opcion == JFileChooser.APPROVE_OPTION) {
            java.io.File file = chooser.getSelectedFile();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
                StringBuilder contenido = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }

                JTextArea logArea = new JTextArea(contenido.toString());
                logArea.setEditable(false);
                logArea.setLineWrap(true);
                logArea.setWrapStyleWord(true);
                logArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
                JScrollPane logScroll = new JScrollPane(logArea);
                logScroll.setPreferredSize(new java.awt.Dimension(600, 400));

                JOptionPane.showMessageDialog(view.getVentana(), logScroll, 
                    "Log de la partida", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}