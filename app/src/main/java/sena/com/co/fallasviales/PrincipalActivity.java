package sena.com.co.fallasviales;


import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import sena.com.co.fallasviales.fragments_mapa.Fragment_ubicacion;
import sena.com.co.fallasviales.listas.Fragment_listado;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager manager;
    Fragment_ubicacion fragment_ubicacion = new Fragment_ubicacion();
    Fragment_listado fragment_listado = new Fragment_listado();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.framelayout_contenedor,fragment_listado).commit();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
        //intent.putExtra("enabled", true);
        //sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.id_mapas) {
           // manager.beginTransaction().add(R.id.framelayout_contenedor, fragment_ubicacion).commit();
            manager.beginTransaction().replace(R.id.framelayout_contenedor,fragment_ubicacion).commit();

            // Handle the camera action
        } else if (id == R.id.id_opciones) {

            manager.beginTransaction().replace(R.id.framelayout_contenedor,fragment_listado).commit();

        }

    //manager.beginTransaction().add(fragment_lista,"FRAGMENT_LISTA");

 //   manager.beginTransaction().replace(R.id.framelayout_contenedor, fragment_lista, "key").commit();



        //manager.beginTransaction().add(R.id.framelayout_contenedor, fragment_lista).commit();*/


       /* if (id == R.id.id_mapas) {
            if (manager.findFragmentByTag("FRAGMENT_UBICACION") == null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(fragment_listado);
                transaction.add(R.id.framelayout_contenedor, fragment_ubicacion, "FRAGMENT_UBICACION");
                transaction.commit();
            } else {
                FragmentTransaction transaction = manager.beginTransaction();
                fragment_ubicacion = (Fragment_ubicacion) manager.findFragmentByTag("FRAGMENT_UBICACION");
                transaction.hide(fragment_listado);
                transaction.show(fragment_ubicacion);
                transaction.commit();

            }
        } else if (id == R.id.id_opciones) {
            if (manager.findFragmentByTag("FRAGMENT_LISTA") == null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(fragment_ubicacion);
                transaction.add(R.id.framelayout_contenedor, fragment_listado, "FRAGMENT_LISTA");
                transaction.commit();
            } else {
                FragmentTransaction transaction = manager.beginTransaction();
                fragment_listado = (Fragment_listado) manager.findFragmentByTag("FRAGMENT_LISTA");
                transaction.hide(fragment_ubicacion);
                transaction.show(fragment_listado);
                transaction.commit();
            }
        }*/








        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

