import java.util.Random;
public class lancero extends soldado {
    private int longitudDeLanza;
    public void schiltrom(){
        super.defensa=super.defensa+1;
    }
    public lancero(String nombre, int vida, int ataque, int defensa, int velocidad) {
        super(nombre, vida, ataque, defensa, velocidad);
        this.longitudDeLanza = new Random().nextInt(5,10);
    }
}
