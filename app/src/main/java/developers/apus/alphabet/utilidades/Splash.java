package developers.apus.alphabet.utilidades;

/**
 * Created by Miguel on 23/02/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;


/**
 * Clase que implemnta el SplashActivity
 * normalmente conocida asi por ser un actividad que
 * dura pocos segundos en ejecucion y que sirve como
 * bienvenida al usuario, normalmente mostrandole la marca
 * de la aplicacion
 */
public class Splash
{

    /**
     * Metodo que permite mostrar la actividad de splash
     * @param tiempoTotal - tiempo total que se quiere mostrar la actividad
     * @param activity - actividad splash
     * @param clase - clase(actividad) a la que se quiere ir despues de mostrar el splash
     */
    public static void mostrarSplash(int tiempoTotal, final Activity activity, final Class<?> clase)
    {
        tiempoTotal = tiempoTotal >= 1000 ? tiempoTotal : 1000;
        final CountDownTimer tiempo = new CountDownTimer(tiempoTotal, tiempoTotal)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {

            }

            @Override
            public void onFinish()
            {
                Intent intent = new Intent(activity, clase);
                activity.startActivity(intent);
                activity.finish();
            }
        };
        tiempo.start();
    }
}
