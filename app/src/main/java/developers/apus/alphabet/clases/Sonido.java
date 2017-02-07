package developers.apus.alphabet.clases;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Sonido {

    private String id;
    private String ruta;

    public Sonido(String id, String ruta) {
        this.id = id;
        this.ruta = ruta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
