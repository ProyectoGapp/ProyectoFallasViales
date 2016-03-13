package sena.com.co.fallasviales.listas;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.squareup.picasso.Picasso;

import sena.com.co.fallasviales.R;

public class Activity_lista_detalle extends AppCompatActivity {
    ImageView imageView;
    EditText edit_nombre,edit_apellido,edit_email;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_detalle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Bundle bundle = this.getIntent().getExtras();
        //imageView = (ImageView)findViewById(R.id.idimagen_detallelista);
        edit_nombre = (EditText)findViewById(R.id.idedit_nombre);
        edit_apellido = (EditText)findViewById(R.id.idedit_apellido);
        edit_email = (EditText)findViewById(R.id.idedit_correo);
        btn = (Button)findViewById(R.id.id_button_imagen_dialogo);


       // Picasso.with(this).load(bundle.getString("vurlfoto")).into(imageView);
        edit_nombre.setText(bundle.getString("vnombre"));
        edit_apellido.setText(bundle.getString("vapellidos"));
        edit_email.setText(bundle.getString("vemail"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_lista_detalle.this);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog dialog = alert.create();
                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.layout_dialog_imagen, null);
                ImageView imagen = (ImageView) view1.findViewById(R.id.id_imagen_dialog);
                Picasso.with(Activity_lista_detalle.this).load(bundle.getString("vurlfoto")).into(imagen);
                dialog.setView(view1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {


                    }
                });


               /* AlertDialog.Builder alert = new AlertDialog.Builder(Activity_lista_detalle.this);
                LayoutInflater factory = LayoutInflater.from(Activity_lista_detalle.this);
                final View view = factory.inflate(R.layout.layout_dialog_imagen, null);
                ImageView imagen = (ImageView) view.findViewById(R.id.id_imagen_dialog);
                Picasso.with(Activity_lista_detalle.this).load(bundle.getString("vurlfoto")).into(imagen);
                alert.setView(view);


                alert.show();
                //alert.show().getWindow().setLayout(600,500);
                alert.setNeutralButton("cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/

            }
        });
    }
    /*
preguntar sobre este error
getSlotFromBufferLocked: unknown buffer: 0xabe286d0*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                System.out.println("estas dando aqui");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
