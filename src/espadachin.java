import java.util.Random;
public class espadachin extends soldado {
    private int longitudDeEspada;
    public void crearMuro(){
    }
    public espadachin(String nombre, int vida, int ataque, int defensa, int velocidad) {
        super(nombre, vida, ataque, defensa, velocidad);
        this.longitudDeEspada = new Random().nextInt(5,10);
    }
}
