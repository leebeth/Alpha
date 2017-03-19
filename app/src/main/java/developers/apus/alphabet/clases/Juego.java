package developers.apus.alphabet.clases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import developers.apus.alphabet.constantes.TipoJuego;
import developers.apus.alphabet.excepciones.JuegoTerminadoException;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Juego {

    private List<Letra> letras;

    private TipoJuego juego;

    private Letra letraActual;

    private Imagen imagenActual;

    private List<Imagen> opciones;

    private String escrita;

    private int indice;

    public Juego(JSONObject diccionario){
        letras = new ArrayList<>();
        escrita = "";
        Iterator<String> keys = diccionario.keys();
        while( keys.hasNext()){
            String key = keys.next();
            List<Imagen> imagenes = new ArrayList<>();
            try {
                JSONArray array = diccionario.getJSONArray(key);
                for (int i = 0; i < array.length(); i++){
                    Imagen imagen = new Imagen(array.getString(i), null);//TODO hacer set del sonido
                    imagenes.add(imagen);
                }
                Letra letra = new Letra(key, null, imagenes);//TODO hacer set del sonido
                letras.add(letra);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(letras, new Comparator<Letra>() {
            @Override
            public int compare(Letra a, Letra b) {
                String id = "ni";
                if(a.getId().equals("_")){
                    return id.compareTo(b.getId());
                }
                else if(b.getId().equals("_")){
                    return a.getId().compareTo(id);
                }
                else{
                    return a.getId().compareTo(b.getId());
                }
            }
        });
    }

    public Letra getLetraAleatoria() throws JuegoTerminadoException {
        List<Letra> copia =new ArrayList<>();
        Collections.copy(copia, letras);
        Letra letra;

        boolean encontrada = false;
        Random random = new Random();

        do {
            int id  = random.nextInt(letras.size());
            letra = letras.get(id);
            if(!letra.isMostrada()){
                encontrada = true;
            }
            else {
                copia.remove(id);
            }
        }
        while(!encontrada && !copia.isEmpty());
        if(encontrada) {
            letraActual = letra;
            return letra;
        }
        else
            throw new JuegoTerminadoException();

    }

    public Letra getSiguienteLetra() throws JuegoTerminadoException {
        Letra letra = null;
        boolean encontrada = false;
        for (int i = 0; i < letras.size() && !encontrada; i++) {
            if(!letras.get(i).isMostrada()){
                letra = letras.get(i);
                encontrada = true;
            }
        }
        if(encontrada){
            letraActual = letra;
            return letra;
        }
        else{
            throw new JuegoTerminadoException();
        }
    }

    public List<Imagen> generarOpciones() {
        opciones = new ArrayList<>();

        opciones.add(letraActual.getImagenAleatoria());
        Random random = new Random();
        boolean completo = false;
        ArrayList<Integer> sacados = new ArrayList<>();
        while(!completo){
            int i = random.nextInt(letras.size());
            Letra act = letras.get(i);
            if( !act.getId().equals(letraActual.getId()) && !sacados.contains(i) ){
                opciones.add(act.getImagenAleatoria());
                sacados.add(i);
            }
            if(sacados.size()== 3){
                completo = true;
            }
        }
        Collections.shuffle(opciones);
        return opciones;
    }

    public boolean verificarRespuesta(int imagen){

        boolean correcto = opciones.get(imagen).getNombre().startsWith(letraActual.getId());
        if(correcto){
            letraActual.mostrar();
        }
        return correcto;
    }

    public boolean validarLetra(String letra){
        if(indice < imagenActual.getNombre().length()){
            StringBuilder aStringBuilder = new StringBuilder(escrita);

            char caracterActual = imagenActual.getNombre().charAt(indice);

            if(caracterActual == '0'){
                aStringBuilder.replace(indice*2, (indice*2) + 1, "-" );
                indice++;
            }
            else if(caracterActual == '_'){
                aStringBuilder.replace(indice*2, (indice*2) + 1, " " );
                indice++;
            }

            boolean correcto = imagenActual.getNombre().charAt(indice) == letra.charAt(0);

            if(correcto){
                aStringBuilder.replace(indice*2, (indice*2) + 1, letra);
                escrita = aStringBuilder.toString();
                indice++;
            }
            return correcto;
        }
        return false;
    }

    public boolean verificarEscrito(){
        boolean ok = imagenActual.getNombre().replaceAll("[_0]", "").equals(escrita.replaceAll("[ -]", ""));
        if(ok){
            letraActual.mostrar();
        }
        return ok;
    }

    public List<Letra> getLetras() {
        return letras;
    }

    public void reiniciar(){
        letraActual = null;
        opciones = null;
        indice = 0;
        for (Letra letra :
                letras) {
            letra.init();
        }
    }

    public Letra getLetraActual() {
        return letraActual;
    }

    public List<Imagen> getOpciones() {
        return opciones;
    }

    public Imagen getImagenActual() {
        return imagenActual;
    }

    public Imagen getSiguienteImagenAleatoria() throws JuegoTerminadoException {
        imagenActual = getSiguienteLetra().getImagenAleatoria();
        escrita = "";
        indice = 0;
        for (int i = 0; i < imagenActual.getNombre().length() - 1; i++) {
            char charActual = imagenActual.getNombre().charAt(i);
            if(charActual == '_'){
                escrita += "  ";
            }
            else if(charActual == '0'){
                escrita += "- ";
            }
            else{
                escrita += "_ ";
            }
        }
        escrita += "_";

        return imagenActual;
    }

    public String getEscrita() {
        return escrita;
    }
}
