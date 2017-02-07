package developers.apus.alphabet.clases;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Letra {

    private String id;

    private Sonido sonido;

    private List<Imagen> imagenes;

    private Set<Integer> mostradas;

    private boolean mostrada;

    public Letra(String id, Sonido sonido, List<Imagen> imagenes) {
        this.id = id;
        this.sonido = sonido;
        this.imagenes = imagenes;
        init();
    }

    public void init() {
        mostradas = new TreeSet<>();
        mostrada = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sonido getSonido() {
        return sonido;
    }

    public void setSonido(Sonido sonido) {
        this.sonido = sonido;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Set<Integer> getMostradas() {
        return mostradas;
    }

    public void setMostradas(Set<Integer> mostradas) {
        this.mostradas = mostradas;
    }

    public Imagen getImagenAleatoria(){
        Random aleatorio = new Random();
        int numero = aleatorio.nextInt(imagenes.size());
        Imagen imagen = imagenes.get(numero);
        imagen.setMostrada(true);
        mostradas.add(numero);
        return imagen;
    }

    public void mostrar(){
        mostrada = true;
    }

    public boolean isMostrada() {
        return mostrada;
    }

    @Override
    public String toString() {
        return id;
    }
}
