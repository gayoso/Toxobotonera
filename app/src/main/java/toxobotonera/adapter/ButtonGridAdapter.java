package toxobotonera.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import toxobotonera.toxobotonera.R;

/**
 * Adaptador para la grilla de archivos.
 * Establece el ícono de los archivos y sus nombres.
 */
public class ButtonGridAdapter extends BaseAdapter {
    private Context context;
    private String[] botones;
    private Integer[] ids_sonidos;
    private MediaPlayer mp;
    private String playing_sound;

    private ArrayList<Button> botones_invisibles;
    private String[] mensajes_desaparecen_botones;
    private String[] mensajes_bardo;

    public ButtonGridAdapter(Context context) {
        this.context = context;

        ArrayList temp_botones = new ArrayList();
        temp_botones.add(context.getString(R.string.s_bauty_tarde));
        temp_botones.add(context.getString(R.string.s_duro_diamante));
        temp_botones.add(context.getString(R.string.s_gabo_para_nada));
        temp_botones.add(context.getString(R.string.s_lauty_sos_chileno));
        temp_botones.add(context.getString(R.string.s_murloc));
        temp_botones.add(context.getString(R.string.s_punta_lauty_humo_corto));
        temp_botones.add(context.getString(R.string.s_punta_lauty_humo_largo));
        temp_botones.add(context.getString(R.string.s_turco_bardo_mati));
        temp_botones.add(context.getString(R.string.s_turco_tarde_1));
        temp_botones.add(context.getString(R.string.s_turco_tarde_2));
        temp_botones.add(context.getString(R.string.s_turco_tarde_3));
        temp_botones.add(context.getString(R.string.s_turco_tarde_4));
        temp_botones.add(context.getString(R.string.s_turco_y_facu));
        this.botones = (String[]) temp_botones.toArray(new String[temp_botones.size()]);

        for(int i = 0; i < botones.length; ++i){
            Log.d("GridAdapter", "String de boton: " + botones[i]);
        }

        this.playing_sound = "";
        this.botones_invisibles = new ArrayList<Button>();

        ArrayList temp_sonidos = new ArrayList();
        temp_sonidos.add(R.raw.bauty_copion_tarde_a_futbol);
        temp_sonidos.add(R.raw.mas_duro_que_el_diamante);
        temp_sonidos.add(R.raw.gabo_para_nada);
        temp_sonidos.add(R.raw.lauty_sos_un_chileno);
        temp_sonidos.add(R.raw.murloc);
        temp_sonidos.add(R.raw.punta_lauty_humo_corto);
        temp_sonidos.add(R.raw.punta_lauty_humo_largo);
        temp_sonidos.add(R.raw.turco_bardo_a_mati_por_pelota);
        temp_sonidos.add(R.raw.turco_tarde_a_futbol_1);
        temp_sonidos.add(R.raw.turco_tarde_a_futbol_2);
        temp_sonidos.add(R.raw.turco_tarde_a_futbol_3);
        temp_sonidos.add(R.raw.turco_tarde_a_futbol_4);
        temp_sonidos.add(R.raw.turco_se_cruza_a_facu);
        this.ids_sonidos = (Integer[]) temp_sonidos.toArray(new Integer[temp_sonidos.size()]);

        for(int i = 0; i < ids_sonidos.length; ++i){
            Log.d("GridAdapter", "Sonido de boton: " + ids_sonidos[i]);
        }

        mensajes_desaparecen_botones = context.getResources().getStringArray(R.array.desaparecen_botones);
        mensajes_bardo = context.getResources().getStringArray(R.array.bardos);
    }

    private void displayText(String s){
        Toast t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        if(v != null) v.setGravity(Gravity.CENTER);
        t.show();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View gridView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.button, null);

        } else {
            gridView =  convertView;
        }

        final Button button = (Button) gridView.findViewById(R.id.label);

        button.setText(botones[position]);
        button.setPadding(5, 5, 5, 5);
        button.setHeight(150);
        if(botones[position] == playing_sound){
            button.setBackgroundResource(R.drawable.pressedbuttonshape);
        } else {
            button.setBackgroundResource(R.drawable.buttonshape);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean trollear = true;

                // si hay botones invisibles, tiro una dado de 3 caras para ver si los devuelvo
                Random r = new Random();
                int r1 = r.nextInt(3 - 0 + 1) + 0;
                if(botones_invisibles.size() != 0 && r1 == 1){
                    for(int i = 0; i < botones_invisibles.size(); ++i){
                        botones_invisibles.get(i).setVisibility(View.VISIBLE);
                    }
                    botones_invisibles.clear();
                    // mira, te devuelvo los botones
                    //Toast.makeText(context, context.getResources().getString(R.string.devolver_botones), Toast.LENGTH_SHORT).show();
                    displayText(context.getResources().getString(R.string.devolver_botones));
                    trollear = false;
                }

                if (playing_sound == "") {

                    // tiro un dado de 6 caras
                    r1 = r.nextInt(6 - 0 + 1) + 0;
                    // si sale 1, no hago nada y bardeo
                    if(trollear && r1 == 1 || r1 == 2){
                        //Toast.makeText(context, mensajes_bardo[r.nextInt(mensajes_bardo.length)], Toast.LENGTH_SHORT).show();
                        displayText(mensajes_bardo[r.nextInt(mensajes_bardo.length)]);
                        return;
                    // si sale 2, el boton se vuelve invisible
                    } else if(trollear && r1 == 3) {
                        button.setVisibility(View.INVISIBLE);
                        botones_invisibles.add(button);
                        //Toast.makeText(context, mensajes_desaparecen_botones[r.nextInt(mensajes_desaparecen_botones.length)], Toast.LENGTH_SHORT).show();
                        displayText(mensajes_desaparecen_botones[r.nextInt(mensajes_desaparecen_botones.length)]);
                        return;
                    }

                    // si no, paso el sonido
                    mp = MediaPlayer.create(context, ids_sonidos[position]);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            playing_sound = "";
                            Log.d("GridAdapter", "Se termino de pasar un sonido");
                            button.setBackgroundResource(R.drawable.buttonshape);
                            mp.release();
                        }

                    });

                    playing_sound = botones[position];
                    mp.start();
                    button.setBackgroundResource(R.drawable.pressedbuttonshape);
                    Log.d("GridAdapter", "Se apreto un boton");
                } else if (playing_sound == botones[position]) {
                    Log.d("GridAdapter", "Se interrumpio un sonido");
                    mp.stop();
                    mp.release();
                    playing_sound = "";
                    button.setBackgroundResource(R.drawable.buttonshape);
                }
            }
        });

        return gridView;
    }

    @Override
    public int getCount() {
        return botones.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}