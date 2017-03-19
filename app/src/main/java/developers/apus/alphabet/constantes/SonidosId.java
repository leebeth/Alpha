package developers.apus.alphabet.constantes;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.TreeMap;

import developers.apus.alphabet.R;

/**
 * Created by Miguel on 09/05/2016.
 */
public class SonidosId {
    private static TreeMap<String, Integer> ids;

    public static void init(){
        ids = new TreeMap<>();
        ArrayList<String> ignore = getListToIgnore();
        Field[] raws = R.raw.class.getFields();
        R.raw rawResources = new R.raw();
        for (Field f : raws) {
            try {
                if(!ignore.contains(f.getName()))
                {
                    ids.put(f.getName(),f.getInt(rawResources));
                }
            } catch (Exception e) {
                String name = f.getName();
                Log.e("SonidosId",name);
                Log.e("SonidosId",e.getMessage(),e);
            }
        }
    }

    private static ArrayList<String> getListToIgnore(){
        ArrayList<String> ignore = new ArrayList<>();
        ignore.add("$change");
        return ignore;
    }

    public static int getRawId(String nombre){
        return ids.get(nombre);
    }
}
