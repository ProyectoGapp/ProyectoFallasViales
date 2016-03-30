package sena.com.co.fallasviales.listas;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sena.com.co.fallasviales.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Preguntas_Frecuentes extends Fragment {


    public Fragment_Preguntas_Frecuentes() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi= inflater.inflate(R.layout.fragment__preguntas__frecuentes, container, false);

        Button btnpregunta1 = (Button)vi.findViewById(R.id.pregunta1);
        Button btnpregunta2 = (Button)vi.findViewById(R.id.btnpregunta2);
        Button btnpregunta3 = (Button)vi.findViewById(R.id.btnpregunta3);


        View panelProfile =vi.findViewById(R.id.panelProfile);
        panelProfile.setVisibility(vi.GONE);

        View panelSettings =vi.findViewById(R.id.panelSettings);
        panelSettings.setVisibility(vi.GONE);

        View paneltres =vi.findViewById(R.id.panel3);
        paneltres.setVisibility(vi.GONE);

        btnpregunta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View vi = inflater.inflate(R.layout.fragment__preguntas__frecuentes, container);

                View panelProfile = vi.findViewById(R.id.panelProfile);
                panelProfile.setVisibility(View.VISIBLE);

                View panelSettings = vi.findViewById(R.id.panelSettings);
                panelSettings.setVisibility(View.GONE);

                View panelPrivacy = vi.findViewById(R.id.panel3);
                panelPrivacy.setVisibility(View.GONE);


            }
        });

        btnpregunta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View vi= inflater.inflate(R.layout.fragment__preguntas__frecuentes,container);

                View panelProfile =vi.findViewById(R.id.panelProfile);
                panelProfile.setVisibility(View.GONE);

                View panelSettings =vi.findViewById(R.id.panelSettings);
                panelSettings.setVisibility(View.VISIBLE);

                View panelPrivacy = vi.findViewById(R.id.panel3);
                panelPrivacy.setVisibility(View.GONE);

//                View panelProfile =v.findViewById(R.id.panelProfile);
//                panelProfile.setVisibility(View.GONE);
//
//                View panelSettings =v.findViewById(R.id.panelSettings);
//                panelSettings.setVisibility(View.VISIBLE);

            }
        });


        btnpregunta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vi= inflater.inflate(R.layout.fragment__preguntas__frecuentes,container);

                View panelProfile =vi.findViewById(R.id.panelProfile);
                panelProfile.setVisibility(View.GONE);

                View panelSettings = vi.findViewById(R.id.panelSettings);
                panelSettings.setVisibility(View.GONE);

                View panelPrivacy = vi.findViewById(R.id.panel3);
                panelPrivacy.setVisibility(View.VISIBLE);
            }
        });

        return vi;
    }


}
