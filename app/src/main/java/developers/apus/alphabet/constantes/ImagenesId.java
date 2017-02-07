package developers.apus.alphabet.constantes;

import java.lang.reflect.Field;
import java.util.TreeMap;

import developers.apus.alphabet.R;

/**
 * Created by Miguel on 08/03/2016.
 */
public class ImagenesId {

    private static TreeMap<String, Integer> ids;

    public static void init(){
        ids = new TreeMap<>();
        Field[] drawables = R.drawable.class.getFields();
        R.drawable drawableResources = new R.drawable();
        for (Field f : drawables) {
            try {
                if(!f.getName().equals("splash"))
                {
                    ids.put(f.getName(),f.getInt(drawableResources));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDrawableId(String nombre){
        return ids.get(nombre);
    }

}
