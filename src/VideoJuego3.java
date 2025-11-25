import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
public class VideoJuego3{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        JFrame ventana = new JFrame("Juego de Videojuego 3");
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
        JMenuItem nuevo = new JMenuItem("nuevo");
        JMenuItem abrir = new JMenuItem("abrir");
        JMenuItem guardar = new JMenuItem("guardar");
        JMenuItem salir = new JMenuItem("salir");
        Archivo.add(nuevo);

        // Referencia al mapa actual (puede ser null si no se creó)
        final mapa[] mapaActual = new mapa[1];

        // Panel principal reutilizable donde se colocará el tablero (centro) y opcionalmente otros componentes
        final JPanel contentPane = new JPanel(new java.awt.BorderLayout());
        ventana.setContentPane(contentPane);

        nuevo.addActionListener(e -> {
            // Generar mapa y guardarlo en la referencia
            mapa m = new mapa();
            m.getMapa(); // inicializa/genera el mapa
            mapaActual[0] = m;

            // Limpiar contenido previo y poner solo el tablero en el centro
            contentPane.removeAll();

            // Obtener el panel del tablero y forzar tamaño fijo
            JPanel Tablero = m.getTablero();
            java.awt.Dimension tableroSize = new java.awt.Dimension(600, 600); // AJUSTA TAMAÑO AQUÍ
            java.awt.Dimension tableroScrollSize = new java.awt.Dimension(620, 620); // AJUSTA TAMAÑO AQUÍ

            // Asegurar que el panel del tablero respete un tamaño fijo
            Tablero.setPreferredSize(tableroSize);
            Tablero.setMinimumSize(tableroSize);
            Tablero.setMaximumSize(tableroSize);

            // Poner el tablero dentro de un JScrollPane y fijar el tamaño del viewport
            JScrollPane tableroScroll = new JScrollPane(Tablero);
            tableroScroll.setPreferredSize(tableroScrollSize);
            tableroScroll.setMinimumSize(tableroScrollSize);
            tableroScroll.setMaximumSize(tableroScrollSize);
            tableroScroll.getViewport().setPreferredSize(tableroSize);

            // Wrapper que evita que BorderLayout estire el componente
            JPanel wrapper = new JPanel(new java.awt.GridBagLayout());
            wrapper.setOpaque(false);
            wrapper.setPreferredSize(tableroSize);
            wrapper.setMinimumSize(tableroSize);
            wrapper.setMaximumSize(tableroSize);

            java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.fill = java.awt.GridBagConstraints.NONE;
            wrapper.add(tableroScroll, gbc);

            // Añadir el wrapper al centro; el tablero mantendrá su tamaño fijo
            contentPane.add(wrapper, java.awt.BorderLayout.CENTER);

            // Forzar actualización de la ventana visible
            ventana.revalidate();
            ventana.repaint();
            ventana.validate();
        });
        salir.addActionListener(e -> {
            System.out.println("Saliendo del juego");
            ventana.dispose();
        });
        Archivo.add(abrir);
        Archivo.add(guardar);
        Archivo.add(salir);
        
        JMenuItem mostrar = new JMenuItem("Mostrar consola");
        Ver.add(mostrar);
        JMenuItem sobre = new JMenuItem("Sobre el juego");
        Ayuda.add(sobre);

        // Cuando se pida "Sobre el juego" verificar si hay tablero; si no, avisar; si sí, mostrar datos
        sobre.addActionListener(e -> {
            if (mapaActual[0] == null) {
                JOptionPane.showMessageDialog(ventana,
                    "No hay un tablero generado. Presiona 'nuevo' primero.",
                    "Sin tablero",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            mapa m = mapaActual[0];

            StringBuilder sb = new StringBuilder();
            sb.append("Equipo 1: ").append(m.getEquipo1()).append("\n\n");
            sb.append("Equipo 2: ").append(m.getEquipo2()).append("\n\n");
            sb.append(BuscarSoldadoVida(m, 1)).append("\n\n");
            sb.append(BuscarSoldadoVida(m, 2)).append("\n\n");
            sb.append(PromediodeVida(m,1)).append("\n\n");
            sb.append(PromediodeVida(m,2)).append("\n\n");
            sb.append(MostrardatosEquipo(m,1)).append("\n");
            sb.append(MostrardatosEquipo(m,2)).append("\n");
            sb.append(RankingDeSoldadosDeEquipo(m,1)).append("\n");
            sb.append(RankingDeSoldadosDeEquipo(m,2)).append("\n");

            JTextArea infoAreaDialog = new JTextArea(sb.toString());
            infoAreaDialog.setEditable(false);
            infoAreaDialog.setLineWrap(true);
            infoAreaDialog.setWrapStyleWord(true);
            infoAreaDialog.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            JScrollPane infoScroll = new JScrollPane(infoAreaDialog);
            infoScroll.setPreferredSize(new java.awt.Dimension(600, 400));

            JOptionPane.showMessageDialog(ventana, infoScroll, "Datos del tablero", JOptionPane.INFORMATION_MESSAGE);
        });

        // Panel lateral: usar JTextArea con saltos de línea y JScrollPane
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

        ventana.setVisible(true);
        /*
        System.out.println("el juego a comenzado");
        System.out.println("que equipo quieres jugar?");
        int equipo = sc.nextInt();
        if(equipo == 1){
            equipo = 1;
        }else if(equipo == 2){
            equipo = 2;
        }
        while(true){
            System.out.println("Elige un soldado por casillas, primero fila y luego columna");
            int fila = sc.nextInt()-1;
            int columna = sc.nextInt()-1;
            if(mapa.get(fila, columna).isActivo() == true){
                if(mapa.get(fila, columna).getEquipo() == equipo){
                    System.out.println("El soldado Existe");
                }else{
                    System.out.println("El soldado Existe pero no es del equipo " + equipo);
                    continue;
                }
            }else{
                System.out.println("No existe soldado en esta casilla");
                continue;
            }
            System.out.println("Que tipo de movimiento quieres hacer?");
            System.out.println("1. Arriba");
            System.out.println("2. Abajo");
            System.out.println("3. Izquierda");
            System.out.println("4. Derecha");
            int movimiento = sc.nextInt();
            switch (VerificarEspacio(mapa, fila, columna, movimiento)) {
                case 0 :
                    System.out.println("No hay espacio disponible en ese lugar.");
                    continue;
                case 1 :
                    System.out.println("No puedes mover a un soldado que tiene el mismo equipo.");
                    continue;
                case 2 :
                    System.out.println("Hay un soldado enemigo... comienza la batalla");
                    if(ProbabilidadDeBatalla(mapa, fila, columna, movimiento, rand)){
                        cambiarposicion(mapa, fila, columna, movimiento);
                        if(equipo == 1){
                            equipo = 2;
                        }else{
                            equipo = 1;
                        }
                    }
                    else{
                        mapa.get(fila,columna).setActivo(false);
                    }
                    break;
                case 3 :
                    System.out.println("se cambio la posicion");
                    cambiarposicion(mapa, fila, columna, movimiento);
                    if(equipo == 2){
                        equipo = 1;
                    }else{
                        equipo = 2;
                    }
                    break;
            
                case 4 :
                    System.out.println("Ocurrio un error inesperado");
                    continue;
                }
            mapa.getMapa();
        }
        */
    }
    public static String BuscarSoldadoVida(mapa mapa, int equipo){
        soldado masVida = new soldado();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mapa.get(i,j).isActivo() == true){
                    if(mapa.get(i,j).getEquipo() == equipo){
                        if(mapa.get(i,j).getVida() > masVida.getVida()){
                            masVida = mapa.get(i,j);
                        }
                    }
                }
            }
        }
        if(masVida.getVida()==0){
            return "El equipo " + equipo + " no tiene soldados activos";
        } else {
            return "El soldado con mas vida del equipo " + equipo + " es: " + masVida.getNombre() + " con " + masVida.getVida() + " de vida";
        }
    }
    public static String PromediodeVida(mapa mapa, int equipo){
        double promedio = 0;
        int cantidad = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mapa.get(i,j).isActivo() == true){
                    if(mapa.get(i,j).getEquipo() == equipo){
                        promedio += mapa.get(i,j).getVida();
                        cantidad++;
                    }
                }
            }
        }
        promedio /= cantidad;
        return "El equipo " + equipo + " tiene un promedio de vida de: " + promedio;
    }
    public static String MostrardatosEquipo(mapa mapa, int equipo){
        String mensaje = "";
        mensaje+= "\n";
        mensaje+= "Equipo " + equipo + "\n";
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mapa.get(i,j).isActivo() == true){
                    if(mapa.get(i,j).getEquipo() == equipo){
                        mensaje+=mapa.get(i,j).getNombre() + " con vida: " + mapa.get(i,j).getVida() + "\n";
                    }
                }
            }
        }
        return mensaje;
    }
    public static String RankingDeSoldadosDeEquipo(mapa mapa, int equipo){
        String mensaje = "";
        ArrayList<soldado> lista = new ArrayList<soldado>();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mapa.get(i,j).isActivo() == true){
                    if(mapa.get(i,j).getEquipo() == equipo){
                        lista.add(mapa.get(i,j));
                    }
                }
            }
        }
        int cambios=1;
        while (cambios==0) { 
            cambios = 0;
            // Ordenar
            for (int i = 0; i < lista.size() - 1; i++) {
                if (lista.get(i).getVida() < lista.get(i + 1).getVida()) {
                    // intercambiar
                    soldado aux = lista.get(i);
                    lista.set(i, lista.get(i + 1));
                    lista.set(i + 1, aux);
                    cambios++;
                }
            }
        }
        
        int posicion = 1;
        mensaje += "\n";
        mensaje += "Ranking de soldados del equipo " + equipo + "\n";
        for(int i = 0; i < lista.size(); i++){
            mensaje+=posicion + ". " + lista.get(i).getNombre() + " con vida: " + lista.get(i).getVida() + "\n";
            posicion++;
        }
        return mensaje;
    }
}

/*   public static int VerificarEspacio(mapa mapa, int fila, int columna, int movimiento){
        switch (movimiento) {
            case 1 :
                if(fila-1<0){
                    return 0;
                }
                else if(mapa.get(fila-1,columna).isActivo() == true){
                    if(mapa.get(fila-1,columna).getEquipo() == mapa.get(fila,columna).getEquipo()){
                        return 1;
                    }else{
                        return 2;
                    }
                }else{
                    return 3;
                }
            case 2 :
                if(fila+1>=10){
                    return 0;
                }
                else if(mapa.get(fila+1,columna).isActivo() == true){
                    if(mapa.get(fila+1,columna).getEquipo() == mapa.get(fila,columna).getEquipo()){
                        return 1;
                    }else{
                        return 2;
                    }
                }
                else{
                    return 3;
                }
            case 3 :
                if(columna-1<0){
                    return 0;
                }
                else if(mapa.get(fila,columna-1).isActivo() == true){
                    if(mapa.get(fila,columna-1).getEquipo() == mapa.get(fila,columna).getEquipo()){
                        return 1;
                    }else{
                        return 2;
                    }
                }
                else{
                    return 3;
                }
            case 4 :
                if(columna+1>=10){
                    return 0;
                }
                else if(mapa.get(fila,columna+1).isActivo() == true){
                    if(mapa.get(fila,columna+1).getEquipo() == mapa.get(fila,columna).getEquipo()){
                        return 1;
                    }else{
                        return 2;
                    }
                }
                else{
                    return 3;
                }
            default:
                return 4;
        }
    }
    public static void cambiarposicion(mapa mapa, int fila, int columna, int movimiento){
        int nuevaFila = fila;
        int nuevaColumna = columna;
        switch (movimiento) {
            case 1: // Arriba
                nuevaFila = fila - 1;
                break;
            case 2: // Abajo
                nuevaFila = fila + 1;
                break;
            case 3: // Izquierda
                nuevaColumna = columna - 1;
                break;
            case 4: // Derecha
                nuevaColumna = columna + 1;
                break;
            default:
                System.out.println("Movimiento no válido");
                return;
        }
        if (nuevaFila >= 0 && nuevaFila < 10 && nuevaColumna >= 0 && nuevaColumna < 10) {
            soldado temp = mapa.get(fila,columna);
            mapa.set(fila,columna, mapa.get(nuevaFila,nuevaColumna));
            mapa.set(nuevaFila,nuevaColumna, temp);
            System.out.println("Movimiento realizado.");
        } else {
            System.out.println("Movimiento fuera de los límites del mapa.");
        }
    }
    public static boolean ProbabilidadDeBatalla(mapa mapa, int fila, int columna, int movimiento, Random rand){
        int vida1=mapa.get(fila,columna).getVida();
        System.out.println("Vida de tu soldado: " + vida1);
        int vida2=0;
        switch (movimiento) {
            case 1 :
                vida2=mapa.get(fila-1,columna).getVida();
                break;
            case 2 :
                vida2=mapa.get(fila+1,columna).getVida();
                break;
            case 3 :
                vida2=mapa.get(fila,columna-1).getVida();
                break;
            case 4 :
                vida2=mapa.get(fila,columna+1).getVida();
            System.out.println("Tu soldado perdio porque el numero random es: " + probabilidad+" y tu vida es: " + vida1+ " siendo tu vida menor a la probabilidad");
            return false;
            em.out.println("Vida de el enemigo: " + vida2);
        }nt probabilidad = rand.nextInt(vida2+vida1);
    }   System.out.println("Probabilidad de ganar es: " + vida1*100/(vida1+vida2)+"%");
}       System.out.println("numero random: " + probabilidad);
      if(vida1>probabilidad){
            System.out.println("El soldado " + mapa.get(fila,columna).getNombre() + " ha ganado");
            System.out.println("Tu soldado gano porque el numero random es: " + probabilidad+" y tu vida es: " + vida1+ " siendo tu vida mayor a la probabilidad");
            return true;
        }else{
            System.out.println("El soldado " + mapa.get(fila,columna).getNombre() + " ha perdido");
            System.out.println("Tu soldado perdio porque el numero random es: " + probabilidad+" y tu vida es: " + vida1+ " siendo tu vida menor a la probabilidad");
            return false;
            
        }
    }
}
*/