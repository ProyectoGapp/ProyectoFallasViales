package sena.com.co.fallasviales.listas;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.R;

/**
 * Created by Mobilelab18 on 5/03/2016.
 */
public class AdaptadorLista extends  RecyclerView.Adapter<AdaptadorLista.Usuariosclass>{
    List<Usuario>datosusuario;

    public AdaptadorLista(List<Usuario> datosusuario) {
        this.datosusuario = datosusuario;
    }

    public class Usuariosclass extends RecyclerView.ViewHolder {
        CardView v_cardview;
        TextView v_txtnombre;
        TextView v_txtapellido;
        ImageView v_imagen;
        int v_posicion;


        public Usuariosclass(View itemView) {
            super(itemView);

            v_cardview = (CardView)itemView.findViewById(R.id.idlistas_cardview);
            v_txtnombre = (TextView)itemView.findViewById(R.id.id_listas_nombre);
            v_txtapellido = (TextView)itemView.findViewById(R.id.id_listas_apellidos);
            v_imagen = (ImageView)itemView.findViewById(R.id.id_listas_imagen);

        }
    }


    @Override
    public Usuariosclass onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listas_item_cardview,parent,false);
        Usuariosclass pvh = new Usuariosclass(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(Usuariosclass holder, int position) {
        String varia = datosusuario.get(position).getNombre().toString();

        holder.v_txtnombre.setText(datosusuario.get(position).getNombre().toString());
        holder.v_txtapellido.setText(datosusuario.get(position).getApellidos().toString());
        Picasso.with(holder.itemView.getContext()).load(datosusuario.get(position).getUrlFoto().toString()).into(holder.v_imagen);
        holder.v_posicion = position;


    }

    @Override
    public int getItemCount() {
        return datosusuario.size();
    }


}
