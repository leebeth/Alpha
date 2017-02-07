package developers.apus.alphabet.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import developers.apus.alphabet.PruebaActivity;
import developers.apus.alphabet.R;
import developers.apus.alphabet.adapters.ItemAdapter;
import developers.apus.alphabet.adapters.Juegos;
import developers.apus.alphabet.clases.Imagen;
import developers.apus.alphabet.clases.Juego;
import developers.apus.alphabet.clases.Letra;
import developers.apus.alphabet.constantes.TipoJuego;
import developers.apus.alphabet.interfaces.IAdapterComunication;
import developers.apus.alphabet.utilidades.Json;
import developers.apus.alphabet.utilidades.Util;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Miguel on 24/02/2016.
 */
public class CatalogoJuegosFragment extends Fragment implements IAdapterComunication {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private static InterstitialAd interstitial;
    private static AdView adView;
    private Juego juego;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.catalogo_fragment, container, false);

        adView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice( AdRequest.DEVICE_ID_EMULATOR )
                .build();
        adView.loadAd(adRequest);

        // Create the interstitial.
        interstitial = new InterstitialAd( getActivity( ) );
        interstitial.setAdUnitId(getString(R.string.intersticial));

        // Create ad request.
        AdRequest adRequestI = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequestI);


        JSONObject diccionario = null;
        try {
            String rutaDiccionario = getResources().getString(R.string.diccionario);
            diccionario = new JSONObject(Util.leerArchivo(rutaDiccionario, rootView.getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Obtener el Recycler
        recycler = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(rootView.getContext());
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        String json = Util.leerArchivo(getResources().getString(R.string.juegos), rootView.getContext());

        try {
            Juegos juegos = Json.json2Object(json, Juegos.class);
            adapter = new ItemAdapter(CatalogoJuegosFragment.this, juegos.getJuegos());
            recycler.setAdapter(adapter);

            juego = new Juego(diccionario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void callBack(String juego) {
        Intent intent = null;
        this.juego.reiniciar();

        if(juego.toUpperCase().equals(TipoJuego.LETRA_INICIAL)){
            LetraInicialActivity.setJuego(this.juego);
            intent = new Intent(getActivity(), LetraInicialActivity.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.DELETREA)){
            DeletreaActivity.setJuego(this.juego);
            intent = new Intent(getActivity(), DeletreaActivity.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.APENDICE)){
            ApendiceFragment.setJuego(this.juego);
            intent = new Intent(getActivity(), ApendiceFragment.class);
        }
        else if(juego.toUpperCase().equals(TipoJuego.Item_de_prueba)){
            ArrayList<Imagen> imagenes = new ArrayList<>();

            List<Letra> letras = this.juego.getLetras();
            for (Letra l: letras ) {
                imagenes.addAll(l.getImagenes());
            }
            PruebaActivity.opciones = imagenes;
            intent = new Intent(getActivity(), PruebaActivity.class);
        }
        if( intent != null) {
            startActivity(intent);
            if (interstitial.isLoaded())
            {
                interstitial.show();
            }
        }
    }

    @Override
    public void onPause()
    {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume()
    {
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        adView.destroy();
        super.onDestroy();
    }

}