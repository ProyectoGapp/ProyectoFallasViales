package sena.com.co.fallasviales.fragments_mapa;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.formulario.FormularioActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_ubicacion extends Fragment implements OnMapReadyCallback {


    Button btnenviarformulario;
    String longitud, latitud;


    FormularioActivity formularioActivity = new FormularioActivity();


    public Fragment_ubicacion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idfragment_mapas);
        mapFragment.getMapAsync(this);


        btnenviarformulario = (Button) v.findViewById(R.id.idbtnenviarformulario);

        btnenviarformulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                * Se capturan los datos correspondientes (longitud,latitud)
                * que se envian a la actividad siguiente.
                * */
                Intent intent = new Intent(getContext(), FormularioActivity.class);
                Bundle datosmapa = new Bundle();

                datosmapa.putString("longitud", longitud);
                datosmapa.putString("latitud", latitud);

                intent.putExtras(datosmapa);
                startActivity(intent);

            }
        });

        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("marcador gapp"));

        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {



                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));
                longitud = " " + location.getLongitude();
                latitud = " " + location.getLatitude();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));

                longitud = " " + location.getLongitude();
                latitud = " " + location.getLatitude();


               /* googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));
                longitud = " " + location.getLongitude();
                latitud = " " + location.getLatitude();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));

                longitud = " " + location.getLongitude();
                latitud = " " + location.getLatitude();*/


            }
        });

    }
}
