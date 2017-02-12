package developers.apus.alphabet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import developers.apus.alphabet.R;
import developers.apus.alphabet.constantes.ImagenesId;
import developers.apus.alphabet.interfaces.IAdapterComunication;

/**
 * Created by Lorena on 15/05/2016.
 */
public class ItemAdapterLetra extends RecyclerView.Adapter<ItemAdapterLetra.LetraViewHolder> {

    private List<ItemLetra> items;
    private static IAdapterComunication listener;

    public ItemAdapterLetra(IAdapterComunication listener, List<ItemLetra> juegos) {
        ItemAdapterLetra.listener = listener;
        this.items = juegos;
    }

    @Override
    public LetraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_letra, parent, false);
        return new LetraViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LetraViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(ImagenesId.getDrawableId(items.get(i).getNombre()));
        viewHolder.original = items.get(i).getNombreOriginal();
        String nombre;
        String nombreJson = items.get(i).getNombre();
        switch (nombreJson) {
            case "wood_fire":
                nombre = "Wood fire";
                break;
            case "bottle_baby":
                nombre = "Bottle baby";
                break;
            case "cooking_pot":
                nombre = "Cooking pot";
                break;
            case "magnifying_glass":
                nombre = "Magnifying glass";
                break;
            case "wild_pig":
                nombre = "Wild pig";
                break;
            case "zig_zag":
                nombre = "zig zag";
                break;
            case "yo0yo":
                nombre = "yo-yo";
                break;
            case "x0rays":
                nombre = "x-rays";
                break;
            case "wall0e":
                nombre = "wall-e";
                break;
            default:
                nombre = String.valueOf(nombreJson.charAt(0)).toUpperCase().concat(nombreJson.substring(1));
        }
        viewHolder.nombre.setText(nombre);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class LetraViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre;
        public String original;

        public LetraViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBack(original);
                }
            });
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
        }
    }
}