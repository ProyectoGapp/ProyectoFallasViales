package sena.com.co.fallasviales.fragments_mapa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


import sena.com.co.fallasviales.R;
import sena.com.co.fallasviales.listas.ListaDatosusuario;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_mapa_listadetalle extends Fragment implements OnMapReadyCallback {

//    List<ListaDatosusuario> listaDatosusuarios;
//
//    public Fragment_mapa_listadetalle(List<ListaDatosusuario> listaDatosusuarios) {
//        this.listaDatosusuarios = listaDatosusuarios;
//    }

    public Fragment_mapa_listadetalle() {
        // Required empty public constructor
    }
    FragmentManager manager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_mapa_listadetalle, container, false);

        /*SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idfragment_mapas);
        mapFragment.getMapAsync(this);*/
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.idfragment_mapas_lista);
        mapFragment.getMapAsync(this);

        LocationManager locManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> listaProviders = locManager.getAllProviders();
        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            manager = getFragmentManager();
            DialogoAlerta dialogo = new DialogoAlerta();
            dialogo.show(manager,"tagAlerta");

        }


        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        if(ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

               /*String lat = String.valueOf(location.getLatitude());
                String lon = String.valueOf(location.getLongitude());

                String latss = ListaDatosusuario.latitud;
                String lonss = ListaDatosusuario.longitud;*/


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(ListaDatosusuario.latitud), Double.parseDouble(ListaDatosusuario.longitud)), 16));
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(Double.parseDouble(ListaDatosusuario.latitud), Double.parseDouble(ListaDatosusuario.longitud))));

                // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                //googleMap.clear();
                // googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(Double.parseDouble(ListaDatosusuario.latitud), Double.parseDouble(ListaDatosusuario.longitud))));

               /* googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().title("Fallas Viales").position(new LatLng(location.getLatitude(), location.getLongitude())));*/


            }
        });

    }

    public void onProviderDisabled(String provider)
    {
        Toast.makeText(getContext(),"Gps Desactivado", Toast.LENGTH_SHORT ).show();
    }




}
