package developers.apus.alphabet.utilidades;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import developers.apus.alphabet.constantes.SonidosId;

/**
 * Created by Miguel on 23/02/2016.
 */
public class Util {

    public static String leerArchivo(String fileName, Context context){
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }
	
	public static <T> List<T> iteratorToList(Iterator<T> iterator){
        List<T> list = new ArrayList<>();
        if(iterator != null){
            while (iterator.hasNext()){
                list.add(iterator.next());
            }
        }
        return list;
    }

    public static void playSound(String soundName, Context context, MediaPlayer.OnCompletionListener listener, String tag){
        playSound(new String[]{soundName}, context, listener, tag);
    }

    public static void playSound(String[] soundNames, Context context, MediaPlayer.OnCompletionListener listener, String tag){
        try
        {
            MediaPlayer sound;

            if(soundNames != null && soundNames.length > 0){
                for(String soundName : soundNames) {
                    sound = MediaPlayer.create(context, SonidosId.getRawId(soundName));
                    sound.setOnCompletionListener(listener);
                    sound.start();

                    while (soundNames.length > 1 && sound.isPlaying()){}
                }
            }
        }
        catch (Exception e)
        {
            Log.e(tag,"Error iniciando sonido", e);
        }
    }
	
}
