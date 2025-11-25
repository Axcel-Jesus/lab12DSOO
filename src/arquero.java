import java.util.Random;
public class arquero extends soldado {
    private int flechas=new Random().nextInt(5,10);
    public void ataque(){
        if(flechas>0){
            flechas--;
            super.ataque=super.ataque+1;
        }
        else{
            System.out.println("No quedan flechas");
        }
    }
    public arquero(String nombre, int vida, int ataque, int defensa, int velocidad) {
        super(nombre, vida, ataque, defensa, velocidad);
        this.flechas = new Random().nextInt(5,10);
    }
}
