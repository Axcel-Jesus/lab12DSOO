import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;
public class VideoJuego3{
    public static void main(String[] args) {
    // Se va a videoview
    videoview.main(args);
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
                JOptionPane.showMessageDialog(view.getVentana(), 
                    "Movimiento no válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
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