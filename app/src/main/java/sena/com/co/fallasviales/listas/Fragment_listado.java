package sena.com.co.fallasviales.listas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sena.com.co.fallasviales.Entidades.Usuario;
import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.commons.AuxiliarSugar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_listado extends Fragment {
    RecyclerView recyclerView;
    List<Usuario> datosusuario = new ArrayList<>();

    public Fragment_listado() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_listado, container, false);
        recyclerView = (RecyclerView)vista.findViewById(R.id.id_listas_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        datosusuario = Usuario.listAll(Usuario.class);;
        System.out.println("lista"+datosusuario.size());
        AdaptadorLista adaptadorLista = new AdaptadorLista(datosusuario);
        recyclerView.setAdapter(adaptadorLista);
        return vista;
    }

}
