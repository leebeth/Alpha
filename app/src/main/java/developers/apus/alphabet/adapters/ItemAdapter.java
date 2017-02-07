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
 * Created by Miguel on 23/02/2016.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.JuegoViewHolder>  {

    private List<Item> items;
    private static IAdapterComunication listener;

    public ItemAdapter(IAdapterComunication listener, List<Item> juegos){
        ItemAdapter.listener = listener;
        this.items = juegos;
    }

    @Override
    public JuegoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        return new JuegoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JuegoViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(ImagenesId.getDrawableId(items.get(i).getPortada()));
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class JuegoViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;

        public JuegoViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.callBack(nombre.getText().toString());
                }
            });

            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion);
        }
    }
}
