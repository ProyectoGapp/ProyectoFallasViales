package sena.com.co.fallasviales.formulario;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import sena.com.co.fallasviales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuienesSomos extends Fragment {
    private ImageView imageView;
    private CardView cardView;

    public FragmentQuienesSomos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_fragment_quienes_somos, container, false);
        cardView = (CardView) v.findViewById(R.id.cardQ);

        return v;
    }

}
