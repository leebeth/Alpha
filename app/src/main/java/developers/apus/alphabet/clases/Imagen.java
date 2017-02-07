package developers.apus.alphabet.clases;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Imagen {

    private String nombre;

    private boolean mostrada;

    private Sonido sonido;

    public Imagen(String nombre, Sonido sonido) {
        this.nombre = nombre;
        this.sonido = sonido;
        init();
    }

    public void init(){
        mostrada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMostrada() {
        return mostrada;
    }

    public void setMostrada(boolean mostrada) {
        this.mostrada = mostrada;
    }

    public Sonido getSonido() {
        return sonido;
    }

    public void setSonido(Sonido sonido) {
        this.sonido = sonido;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
