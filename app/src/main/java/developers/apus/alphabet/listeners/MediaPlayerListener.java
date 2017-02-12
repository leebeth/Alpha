package developers.apus.alphabet.listeners;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Lore on 29/01/2017.
 */
public class MediaPlayerListener implements MediaPlayer.OnCompletionListener {
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("Media Player","Llamado al onCompletion clase MediaPlayerListener");

        try {
            mediaPlayer.stop();
            Log.d("Media Player","Stop llamado");
            mediaPlayer.release();
            Log.d("Media Player","Release llamado");
        }
        catch(IllegalStateException ilegalState){
            Log.e("Media Player","Error en el llamado al onCompletion clase MediaPlayerListener", ilegalState);
        }
    }
}
