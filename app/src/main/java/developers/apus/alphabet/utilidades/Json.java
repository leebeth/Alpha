package developers.apus.alphabet.utilidades;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Miguel on 24/02/2016.
 */
public class Json {

    private static final String TAG = "Json-Utils";

    /**
     * Metodo para convertir cualquier objeto a un string en formato json
     * @param object objeto del que se quiere generar el json
     * @return json
     * @throws Exception
     */
    public static String obtect2Json(final Object object) throws Exception {
        String json;
        final Gson gson = new Gson();
        json = gson.toJson(object);
        return json;
    }

    /**
     * Metodo para crear un objeto a partir de un json
     * @param json json del que se quiere crear el objeto
     * @param clase clase del objeto que sera creado
     * @return objeto del tipo especificado por parametro que tiene la informacion del json
     * @throws Exception
     */
    public static <T>T json2Object(final String json, Class<T> clase) throws Exception {
        try {
            T object;
            Gson gson = new Gson();
            object = gson.fromJson(json, clase);
            return object;
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage(),e);
            throw e;
        }
    }
}
