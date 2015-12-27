package toxobotonera.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import toxobotonera.adapter.ButtonGridAdapter;
import toxobotonera.toxobotonera.R;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private GridView gridView;
    private AppBarLayout appBarLayout;
    private int active_background;

    public MainActivity(){
        this.active_background = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView i = (ImageView)findViewById(R.id.fondo);
        i.setBackgroundResource(R.drawable.f1);

        //AnimationDrawable pro = (AnimationDrawable)i.getBackground();
        //pro.start();

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ButtonGridAdapter(this));

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnClickListener(new AppBarLayout.OnClickListener(){

            @Override
            public void onClick(View v) {
                ImageView i = (ImageView)findViewById(R.id.fondo);

                active_background+=1;
                // SI SE CAMBIA LA CANTIDAD DE FONDOS, CAMBIAR ESTE NUMERO MAGICO
                if(active_background > 52){
                    active_background = 1;
                }

                int id = getResources().getIdentifier("f" + active_background, "drawable", getPackageName());
                i.setBackgroundResource(id);
            }
        });
        appBarLayout.setOnLongClickListener(new AppBarLayout.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(gridView.getVisibility() == View.VISIBLE){
                    gridView.setVisibility(View.INVISIBLE);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    /**
     * Al presionar el boton Back, se cierra la aplicacion
     */
    @Override
    public void onBackPressed() { //Boton BACK (triangulo abajo a la izquierda)
        Log.d("MainActivity", "Se presiono el boton Back");
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.te_queres_ir))
                .setNegativeButton(getResources().getString(R.string.no_me_voy), null)

                .setPositiveButton(getResources().getString(R.string.me_voy), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        displayText(getResources().getString(R.string.te_quedas_aca));
                    }
                }).create().show();
    }

    private void displayText(String s){
        Toast t = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        if(v != null) v.setGravity(Gravity.CENTER);
        t.show();
    }
}
