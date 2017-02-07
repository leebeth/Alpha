package developers.apus.alphabet;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import developers.apus.alphabet.actividades.LetraInicialActivity;
import developers.apus.alphabet.clases.Imagen;
import developers.apus.alphabet.clases.Juego;
import developers.apus.alphabet.clases.Letra;
import developers.apus.alphabet.constantes.ImagenesId;
import developers.apus.alphabet.excepciones.JuegoTerminadoException;

public class PruebaActivity extends AppCompatActivity {

    public static List<Imagen> opciones;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_li);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PruebaActivity.this.finish();
            }
        });

        Button boton = (Button)findViewById(R.id.main_custom_button);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FogSansOutline.otf");
        boton.setTypeface(typeface);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarLayout();
            }
        });
    }

    private void actualizarLayout() {
        try {
            ImageView opcion1 = (ImageView) findViewById(R.id.opcion1);
            opcion1.setImageResource(ImagenesId.getDrawableId(opciones.get(i).getNombre()));
            i++;

            ImageView opcion2 = (ImageView) findViewById(R.id.opcion2);
            opcion2.setImageResource(ImagenesId.getDrawableId(opciones.get(i).getNombre()));
            i++;

            ImageView opcion3 = (ImageView) findViewById(R.id.opcion3);
            opcion3.setImageResource(ImagenesId.getDrawableId(opciones.get(i).getNombre()));
            i++;

            ImageView opcion4 = (ImageView) findViewById(R.id.opcion4);
            opcion4.setImageResource(ImagenesId.getDrawableId(opciones.get(i).getNombre()));
            i++;


        } catch (Exception e) {

        }
    }


}
