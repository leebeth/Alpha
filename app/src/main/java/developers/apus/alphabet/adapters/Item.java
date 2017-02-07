package developers.apus.alphabet.adapters;

/**
 * Created by Miguel on 24/02/2016.
 */
public class Item {

    private String nombre;

    private String descripcion;

    private String portada;

    public Item(){

    }

    public Item(String nombre, String descripcion, String portada) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.portada = portada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
