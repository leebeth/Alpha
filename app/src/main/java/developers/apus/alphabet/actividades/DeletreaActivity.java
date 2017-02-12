package developers.apus.alphabet.actividades;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import developers.apus.alphabet.R;
import developers.apus.alphabet.clases.Juego;
import developers.apus.alphabet.constantes.ImagenesId;
import developers.apus.alphabet.constantes.SonidosId;
import developers.apus.alphabet.excepciones.JuegoTerminadoException;
import developers.apus.alphabet.listeners.MediaPlayerListener;
import developers.apus.alphabet.utilidades.Util;

public class DeletreaActivity extends AppCompatActivity {

    private final static String TAG = "DeletreaActivity";

    private MediaPlayer teclado, correcto, imagen;
    private MediaPlayerListener mpListener;
    private static Juego juego;

    public static void setJuego(Juego juego) {
        DeletreaActivity.juego = juego;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletrea);

        mpListener = new MediaPlayerListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_li);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        catch (NullPointerException e){}

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletreaActivity.this.finish();
            }
        });

        LinearLayout layoutLetras = (LinearLayout)findViewById(R.id.matriz_letras);
        inicializarLayoutLetras(layoutLetras);
        TextView texto = (TextView) findViewById(R.id.texto);

        texto.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.font));
        actualizarImagenActual();
		ImageView imgActual = (ImageView)findViewById(R.id.imagen);
        if(imgActual != null){
            imgActual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(!imagen.isPlaying()){
                            imagen.start();
                        }
                    }catch (Exception e){
                        Log.e("DeletreaActivity", "Error al ejecutar sonido de imagen", e);
                    }
                }
            });
        }	 
    }

    private void actualizarImagenActual() {
        try {
            if (juego.getLetraActual() == null) {
                juego.getSiguienteImagenAleatoria();
            }
            ImageView imagen = (ImageView) findViewById(R.id.imagen);
            imagen.setImageResource(ImagenesId.getDrawableId(juego.getImagenActual().getNombre()));
            TextView texto = (TextView) findViewById(R.id.texto);
            texto.setText(juego.getEscrita());
			this.imagen = MediaPlayer.create( DeletreaActivity.this, SonidosId.getRawId(juego.getImagenActual().getNombre()));
        }
        catch (JuegoTerminadoException e) { }
        catch (Exception e2){
            Log.e("DeletreaActivity", "Error en actualizarImagenActual", e2);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            public void run() {
                teclado = MediaPlayer.create( DeletreaActivity.this, R.raw.burbuja );
                correcto = MediaPlayer.create( DeletreaActivity.this, R.raw.correcto );
            }
        }).start();
    }

    private void inicializarLayoutLetras(LinearLayout layoutLetras) {

        final String[] letras = {"letras_01", "letras_02", "letras_03", "letras_04", "letras_05", "letras_06",
                "letras_07", "letras_08", "letras_09", "letras_10", "letras_11", "letras_12", "letras_13",
                "letras_14", "letras_16", "letras_17", "letras_18", "letras_19", "letras_20",
                "letras_21", "letras_22", "letras_23", "letras_24", "letras_25", "letras_26", "letras_27"};

        final String[] nombres = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        int indice = 0;
        int numeroFilas =  getResources().getInteger(R.integer.numero_filas_juego_deletrea);
        int numeroColumnas =  getResources().getInteger(R.integer.numero_columnas_juego_deletrea);
        float alto = getResources().getDimension(R.dimen.alto_matriz);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) alto, 1.0f);

        for (int i = 0; i <numeroFilas; i++) {
            LinearLayout layoutAux = new LinearLayout(getApplicationContext());
            layoutAux.setOrientation(LinearLayout.HORIZONTAL);
            layoutAux.setWeightSum(numeroColumnas);
            for (int j = 0; j < numeroColumnas; j++) {
                ImageView letraAct = new ImageView(getApplicationContext());
                String nombre;
                if(indice < letras.length){
                    nombre = letras[indice];
                    agregarListener(nombres, indice, letraAct);
                }
                else{
                   nombre = "cuadro_blanco";
                }
                letraAct.setImageResource(ImagenesId.getDrawableId(nombre));
                letraAct.setLayoutParams(params);
                layoutAux.addView(letraAct);
                indice++;
            }
            layoutAux.setLayoutParams(params);
            layoutLetras.addView(layoutAux);
        }
    }

    private void agregarListener(final String[]nombres, final int finalIndice, ImageView letraAct) {
        letraAct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(juego.validarLetra(nombres[finalIndice])){
                    TextView texto = (TextView) findViewById(R.id.texto);
                    texto.setText(juego.getEscrita());
                }
                try {
                    DeletreaActivity.this.teclado.start();
                }
                catch (IllegalStateException e){}
                if(juego.verificarEscrito()){
                    DeletreaActivity.this.correcto.start();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                juego.getSiguienteImagenAleatoria();
                                actualizarImagenActual();

                            } catch (JuegoTerminadoException e) {
                                Toast.makeText(DeletreaActivity.this, "Has terminado el juego", Toast.LENGTH_SHORT)
                                        .show();
                                try {
                                    Util.playSound("celebracion", DeletreaActivity.this, mpListener, TAG);
                                } catch (IllegalStateException e2) { }
                                DeletreaActivity.this.finish();
                            }
                        }
                    }, 500);
                }
            }
        });
    }
}
