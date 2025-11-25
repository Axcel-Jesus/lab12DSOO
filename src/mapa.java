import java.awt.*;
import java.util.*;
import javax.swing.*;
public class mapa {
    private ArrayList<ArrayList<soldado>> mapa;
    private String territorio;
    private int equipo1;
    private String equipoUno;
    private int equipo2;
    private String equipoDos;
    private JPanel Tablero;
    public mapa(){
        

        Random rand = new Random();
        switch (rand.nextInt(5)) {
            case 0 :
                territorio = "Bosque";
                break;
            case 1 :
                territorio = "Campo abierto";
                break;
            case 2 :
                territorio = "Montaña";
                break;
            case 3 :
                territorio = "Desierto";
                break;
            case 4 :
                territorio = "Playa";
                break;
        }
        System.out.println("Territorio: " + territorio);
        mapa = new ArrayList<ArrayList<soldado>>();
        ArrayList<String> reinos = new ArrayList<String>();
        reinos.add("Inglaterra");
        reinos.add("Francia");
        reinos.add("Castilla-Aragon");
        reinos.add("Moros");
        reinos.add("Sacro Imperio Romano-Germanico");
        equipoUno = reinos.get(rand.nextInt(reinos.size()));
        reinos.remove(equipoUno);
        System.out.println("Equipo 1: " + equipoUno);
        equipoDos = reinos.get(rand.nextInt(reinos.size()));
        System.out.println("Equipo 2: " + equipoDos);
        if((territorio=="Bosque"&&equipoUno=="Inglaterra")||(territorio=="Campo abierto"&&equipoUno=="Francia")||(territorio=="Montaña"&&equipoUno=="Castilla-Aragon")||(territorio=="Desierto"&&equipoUno=="Moros")||((territorio=="Bosque"||territorio=="Playa"||territorio=="Campo abierto")&&equipoUno=="Sacro Imperio Romano-Germanico")){
            System.out.println("el equipo 1 gana uno de ataque gracias al terreno");
        }
        if((territorio=="Bosque"&&equipoDos=="Inglaterra")||(territorio=="Campo abierto"&&equipoDos=="Francia")||(territorio=="Montaña"&&equipoDos=="Castilla-Aragon")||(territorio=="Desierto"&&equipoDos=="Moros")||((territorio=="Bosque"||territorio=="Playa"||territorio=="Campo abierto")&&equipoDos=="Sacro Imperio Romano-Germanico")){
            System.out.println("el equipo 2 gana uno de ataque gracias al terreno");
        }
        for(int i = 0; i < 10; i++){
            ArrayList<soldado> lista = new ArrayList<soldado>();
            for(int j = 0; j < 10; j++){
                switch (rand.nextInt(4)) {
                    case 0 :
                        lista.add(new espadachin("espadachin"+(i+1) + "X" + (j+1),rand.nextInt(5)+1, rand.nextInt(5)+1,rand.nextInt(5)+1,0));
                        break;
                    case 1 :
                        lista.add(new caballero("caballero"+(i+1) + "X" + (j+1),rand.nextInt(5)+1, rand.nextInt(5)+1,rand.nextInt(5)+1,0));
                        break;
                    case 2 :
                        lista.add(new arquero("arquero"+(i+1) + "X" + (j+1),rand.nextInt(5)+1, rand.nextInt(5)+1,rand.nextInt(5)+1,0));
                        break;
                    case 3 :
                        lista.add(new lancero("lancero"+(i+1) + "X" + (j+1),rand.nextInt(5)+1, rand.nextInt(5)+1,rand.nextInt(5)+1,0));
                        break;
                }
            }
            mapa.add(lista);
        }
        equipo1 = rand.nextInt(10)+1;
        equipo2 = rand.nextInt(10)+1;
        for(int i = 0; i < equipo1; i++){
            int fila = rand.nextInt(10);
            int columna = rand.nextInt(10);
            soldado sold= mapa.get(fila).get(columna);
            if(sold.isActivo() == false){
                sold.setActivo(true);
                sold.setEquipo(1);
                sold.setPais(equipoUno);
                if(territorio.equals("Bosque")&&equipoUno.equals("Inglaterra")){
                    sold.setAtaque(sold.getAtaque()+1);
                }else if(territorio.equals("Campo abierto")&&equipoUno.equals("Francia")){
                    sold.setDefensa(sold.getDefensa()+1);
                }else if(territorio.equals("Montaña")&&equipoUno.equals("Castilla-Aragon")){
                    sold.setVidaactual(sold.getVidaactual()+1);
                }else if(territorio=="Desierto"&&equipoUno=="Moros"){
                    sold.setAtaque(sold.getAtaque()+1);
                }else if((territorio=="Bosque"||territorio=="Playa"||territorio=="Campo abierto")&&equipoUno=="Sacro Imperio Romano-Germanico"){
                    sold.setDefensa(sold.getDefensa()+1);
                }
            
            }else{
                i--;
            }
        }for(int i = 0; i < equipo2; i++){
            int fila = rand.nextInt(10);
            int columna = rand.nextInt(10);
            soldado sold= mapa.get(fila).get(columna);
            if(sold.isActivo() == false){
                sold.setActivo(true);
                sold.setEquipo(2);
                sold.setPais(equipoDos);
                if(territorio=="bosque"&&equipoDos=="Inglaterra"){
                    sold.setDefensa(sold.getDefensa()+1);
                }else if(territorio=="Campo abierto"&&equipoDos=="Francia"){
                    sold.setAtaque(sold.getAtaque()+1);
                }else if(territorio=="Montaña"&&equipoDos=="Castilla-Aragon"){
                    sold.setVidaactual(sold.getVidaactual()+1);
                }else if(territorio=="Desierto"&&equipoDos=="Moros"){
                    sold.setDefensa(sold.getDefensa()+1);
                }else if((territorio=="Bosque"||territorio=="Playa"||territorio=="Campo abierto")&&equipoDos=="Sacro Imperio Romano-Germanico"){
                    sold.setAtaque(sold.getAtaque()+1);
                }
            }else{
                i--;
            }
        }
    }
    public void setMapa(ArrayList<ArrayList<soldado>> mapa){
        this.mapa = mapa;
    }
    public JPanel getTablero(){
        JPanel Tablero = new JPanel(new GridLayout(10,10));
        Tablero.setBounds(20,20,600,600);

        celdaPanel[][] celdas = new celdaPanel[10][10];
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                celdas[i][j] = new celdaPanel();
                Tablero.add(celdas[i][j]);
            }
        }
        ImageIcon arquero1 = new ImageIcon("src/img/arquero1.png");
        ImageIcon caballero1 = new ImageIcon("src/img/caballero1.png");
        ImageIcon espadachin1 = new ImageIcon("src/img/espadachin1.png");
        ImageIcon lancero1 = new ImageIcon("src/img/lancero1.png");
        ImageIcon arquero2 = new ImageIcon("src/img/arquero2.png");
        ImageIcon caballero2 = new ImageIcon("src/img/caballero2.png");
        ImageIcon espadachin2 = new ImageIcon("src/img/espadachin2.png");
        ImageIcon lancero2 = new ImageIcon("src/img/lancero2.png");
        Image arc1 = arquero1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image cab1 = caballero1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image esp1 = espadachin1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image lan1 = lancero1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image arc2 = arquero2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image cab2 = caballero2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image esp2 = espadachin2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image lan2 = lancero2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(mapa.get(i).get(j).isActivo() == true){
                    if(mapa.get(i).get(j).getEquipo() == 1){
                        if(mapa.get(i).get(j).getClass().getName().equals("espadachin")){
                            celdas[i][j].setImagen(esp1);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("caballero")){
                            celdas[i][j].setImagen(cab1);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("arquero")){
                            celdas[i][j].setImagen(arc1);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("lancero")){
                            celdas[i][j].setImagen(lan1);
                        }
                    }else if(mapa.get(i).get(j).getEquipo() == 2){
                        if(mapa.get(i).get(j).getClass().getName().equals("espadachin")){
                            celdas[i][j].setImagen(esp2);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("caballero")){
                            celdas[i][j].setImagen(cab2);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("arquero")){
                            celdas[i][j].setImagen(arc2);
                        }else if(mapa.get(i).get(j).getClass().getName().equals("lancero")){
                            celdas[i][j].setImagen(lan2);
                        }
                    }
                }
            }
        }
        return Tablero;
    }
    public void getMapa(){
        for(int i = 0; i < 10; i++){
            System.out.println("----------------------------------------");
            for(int j = 0; j < 10; j++){
                if(mapa.get(i).get(j).isActivo() == true){
                    System.out.print(mapa.get(i).get(j).ID());
                }else{
                    System.out.print(" - ");
                }
            System.out.print("|");
            }
            System.out.println();
        }
    }
    public ArrayList<ArrayList<soldado>> geterMapa(){
        return mapa;
    }
    public String getTerritorio(){
        return territorio;
    }
    public int getEquipo1(){
        return equipo1;
    }
    public int getEquipo2(){
        return equipo2;
    }
    public soldado get(int fila, int columna){
        return mapa.get(fila).get(columna);
    }
    public void set(int fila, int columna, soldado soldado){
        mapa.get(fila).set(columna, soldado);
    }
}
