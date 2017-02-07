package developers.apus.alphabet.adapters;

/**
 * Created by Lorena on 15/05/2016.
 */
public class ItemLetra
{
    private String nombre;
    private String nombreOriginal;

    public ItemLetra(String nombre, String portada) {
        this.nombre = nombre;
        this.nombreOriginal = portada;
    }

    public ItemLetra(String nombre) {
        this.nombre = nombre;
        this.nombreOriginal = nombre;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
