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
            case "avion":
                nombre = "Avión";
                break;
            case "buho":
                nombre = "Búho";
                break;
            case "iglu":
                nombre = "Iglú";
                break;
            case "jabali":
                nombre = "Jabalí";
                break;
            case "jabon":
                nombre = "Jabón";
                break;
            case "leon":
                nombre = "León";
                break;
            case "mama":
                nombre = "Mamá";
                break;
            case "ni_a":
                nombre = "Niña";
                break;
            case "ni_o":
                nombre = "Niño";
                break;
            case "_":
                nombre = "Ñ";
                break;
            case "_andu":
                nombre = "Ñandú";
                break;
            case "_o_o":
                nombre = "Ñoño";
                break;
            case "papa":
                nombre = "Papá";
                break;
            case "raton":
                nombre = "Ratón";
                break;
            case "telefono":
                nombre = "Teléfono";
                break;
            case "trebol":
                nombre = "Trébol";
                break;
            case "u_a":
                nombre = "Uña";
                break;
            case "xilofono":
                nombre = "Xilófono";
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