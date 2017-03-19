package developers.apus.alphabet.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import developers.apus.alphabet.R;
import developers.apus.alphabet.adapters.ItemAdapterLetra;
import developers.apus.alphabet.adapters.ItemLetra;
import developers.apus.alphabet.clases.Imagen;
import developers.apus.alphabet.clases.Juego;
import developers.apus.alphabet.clases.Letra;
import developers.apus.alphabet.interfaces.IAdapterComunication;
import developers.apus.alphabet.listeners.MediaPlayerListener;
import developers.apus.alphabet.utilidades.Util;

public class ApendiceFragment extends AppCompatActivity implements IAdapterComunication {

    private final static String TAG = "ApendiceFragment";

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private MediaPlayerListener mpListener;
    private static Juego juego;

    public static void setJuego(Juego juego) {
        if(ApendiceFragment.juego == null)
            ApendiceFragment.juego = juego;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mpListener = new MediaPlayerListener();

        setContentView(R.layout.activity_apendice);
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
                ApendiceFragment.this.finish();
            }
        });
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recicladorApendice);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        try {
            List<Letra> letras = juego.getLetras();
            List<ItemLetra> itemLetras = new ArrayList<>();
            for(int i=0; i<letras.size(); i++)
            {
                Letra letra = letras.get(i);
                ItemLetra item = new ItemLetra(letra.getId());
                itemLetras.add(item);
                for (int j =0; j<letra.getImagenes().size(); j++ )
                {
                    Imagen image = letra.getImagenes().get(j);
                    item = new ItemLetra(image.getNombre());
                    itemLetras.add(item);
                }
            }
            adapter = new ItemAdapterLetra(ApendiceFragment.this, itemLetras );
            recycler.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   @Override
    public void callBack(String juego) {
       Util.playSound(juego, ApendiceFragment.this.getApplicationContext(), mpListener, TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
