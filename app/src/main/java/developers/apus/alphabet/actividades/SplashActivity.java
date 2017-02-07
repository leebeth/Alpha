package developers.apus.alphabet.actividades;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;


import developers.apus.alphabet.R;
import developers.apus.alphabet.constantes.ImagenesId;
import developers.apus.alphabet.constantes.SonidosId;
import developers.apus.alphabet.utilidades.Splash;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        try
        {
            ProgressBar p1 = (ProgressBar)findViewById(R.id.progressBarSplash);
            p1.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
        catch (Exception e)
        {

        }

        new Thread(new Runnable() {
            public void run() {
                ImagenesId.init();
                SonidosId.init();
            }
        }).start();

        int tiempo = 3000; // en milisegundos
        Splash.mostrarSplash(tiempo, this, MainActivity.class);


    }
}
