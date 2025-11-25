public class soldado {
    private String nombre;
    public int ataque;
    public int defensa;
    private int vida;
    private int vidaactual;
    private int velocidad;
    private String actitud;
    private int equipo;
    private String pais;
    public String id="";
    private boolean activo = false;//muestra si esta en el tablero o no, asi como si esta vivo o no
    public soldado() {
    }
    public soldado(String nombre, int vida, int equipo) {
        this.nombre = nombre;
        this.vida = vida;
        this.equipo = equipo;
    }
    public soldado(String nombre, int vida) {
        this.nombre = nombre;
        this.vida = vida;
    }
    public soldado(String nombre, int vida, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.vidaactual = vida;
        this.activo = false;
    }
    public String ID(){
        return (" "+nombre.substring(0, 1).toUpperCase()+equipo);
    }
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public int getAtaque() {
        return ataque;
    }
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    public int getDefensa() {
        return defensa;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    public int getVelocidad() {
        return velocidad;
    }
    public void setActitud(String actitud) {
        this.actitud = actitud;
    }
    public String getActitud() {
        return actitud;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public void setVidaactual(int vidaactual) {
        this.vidaactual = vidaactual;
    }
    public int getVidaactual() {
        return vidaactual;
    }
    public int getEquipo() {
        return equipo;
    }                                              
    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public void atacar(){
        if(activo){
            actitud = "Atacando";
            velocidad +=1;

        }
    }
    public void defender(){
        if(activo){
            actitud = "Defendiendo";
            velocidad = 0;
        }
    }
    public void huir(){
        if(activo){
            actitud = "Huyendo";
            velocidad += 2;
        }
    }
    public void retroceder(){
        if(activo){
            if(velocidad > 0){
                velocidad = 0;
                actitud = "Defendiendo";
            }else{
                velocidad -= 1;
                actitud = "Huyendo";
            }
        }
    }
    public void avanzar(){
        if(activo){
            velocidad += 1;
            actitud = "Atacando";
        }
    }
    public void serAtacado(){
        if(activo){
            actitud = "Defendiendo";
            velocidad = 0;
            vida -= 1;
        }
    }
    public void morir(){
        if(activo){
            vida = 0;
            activo = false;
        }
    }
}
