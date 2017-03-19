package developers.apus.alphabet.constantes;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeMap;

import developers.apus.alphabet.R;

/**
 * Created by Miguel on 08/03/2016.
 */
public class ImagenesId {

    private static TreeMap<String, Integer> ids;

    public static void init(){
        ids = new TreeMap<>();
        ArrayList<String> ignore = getListToIgnore();

        Field[] drawables = R.drawable.class.getFields();
        R.drawable drawableResources = new R.drawable();
        for (Field f : drawables) {
            try {
                if(!ignore.contains(f.getName()))
                {
                    ids.put(f.getName(),f.getInt(drawableResources));
                }
            } catch (Exception e) {
                String name = f.getName();
                Log.e("ImagenesId",name);
                Log.e("ImagenesId",e.getMessage(),e);
            }
        }
    }

    private static ArrayList<String> getListToIgnore(){
        ArrayList<String> ignore = new ArrayList<>();
        ignore.add("splash");
        ignore.add("$change");
        return ignore;
    }

    public static int getDrawableId(String nombre){
        try {
            return ids.get(nombre);
        }catch (Exception e){
            Log.i("ImagenesId","Error al obtener imagen: " + nombre, e);
        }
        return -1;
    }

}
