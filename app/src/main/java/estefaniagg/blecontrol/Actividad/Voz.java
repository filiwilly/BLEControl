package estefaniagg.blecontrol.Actividad;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import estefaniagg.blecontrol.Data.Util;
import estefaniagg.blecontrol.R;
import estefaniagg.blecontrol.Servicio.BluetoothLeService;

import static android.content.ContentValues.TAG;

public class Voz extends Principal {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView tv_texto;
    private Button enviar;
    private String mDirecDispo;
    private ImageButton microfono;
    private ArrayList<String> lectura = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_voz);
        getSupportActionBar().setTitle(R.string.tt_voz);

        tv_texto = findViewById(R.id.tv_texto);
        enviar = findViewById(R.id.enviar);
        microfono = findViewById(R.id.ic_mic);

        Intent intent = getIntent();
        mDirecDispo = intent.getStringExtra("direccion");
        registerReceiver(mGattUpdateReceiver, Util.makeGattUpdateIntentFilter());
        microfono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reconocimiento();
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lectura != null && conectado) {
                    int tam = lectura.get(0).length();
                    byte[] bytes = lectura.get(0).getBytes();
                    int paquetetam = (int) Math.ceil( bytes.length / (double)19);
                    for (int i=0; i<paquetetam; i++){
                        int x = i*19;
                        int y = (i+1)*19;
                        if(y>bytes.length){ y=bytes.length;}
                        byte [] dividido = Arrays.copyOfRange(bytes,x,y);
                        String valor = new String(dividido);
                        mBluetoothLeService.WriteValue("3"+valor);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void reconocimiento() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Log.e(TAG, "Error en reconocimiento");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    lectura = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_texto.setText(lectura.get(0));
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.desconexion:
                final Intent intent = new Intent(this, Principal.class);
                Voz.this.setResult(-2, intent);
                Voz.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
