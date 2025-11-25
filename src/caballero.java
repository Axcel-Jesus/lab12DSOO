import java.util.Random;
public class caballero extends soldado {
    private boolean motando=new Random().nextBoolean();
    private String arma=(motando)?"lanza":"espada";
    public void desmontar(){
        if(motando==true){
            super.defender();
            String arma = "espada";
            motando=false;
        }
        else{
            super.defender();
            String arma = "espada";
            System.out.println("El caballero ya estaba desmontando");
        }
    }
    public void montar(){
        if(motando==false){
            super.atacar();
            String arma = "lanza";
            motando=true;
            envestir();
        }
        else{
            super.atacar();
            String arma = "lanza";
            System.out.println("El caballero ya estaba montando");
            envestir();
        }
    }
    public void envestir(){
    }
    public void ataque(){
        if(motando==true){
            super.ataque=super.ataque*3;
        }
    }
    public caballero(String nombre, int vida, int ataque, int defensa, int velocidad) {
        super(nombre, vida, ataque, defensa, velocidad);
    }
}
