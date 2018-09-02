package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import estefaniagg.blecontrol.R;
import static android.content.ContentValues.TAG;

public class Multimedia extends Principal implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    int codigo_app;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_multimedia);
        getSupportActionBar().setTitle(R.string.tt_multimedia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        ImageButton volup = (ImageButton) findViewById(R.id.volup);
        ImageButton voldown = (ImageButton) findViewById(R.id.voldown);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.apps_array, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.d(TAG, Integer.toString(pos));
        //0 - Spotify 1 - Youtube 2 - Netflix 3 - VLC 4 - Windows Media 5 - PowerPoint
        codigo_app = pos;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void PLPA(View view) {
        if (conectado) {
            if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "16");
            } else {
                mBluetoothLeService.WriteValue("1" + "13");
            }
        }
    }

    public void masvoz(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "11");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "17");
            } else if (codigo_app == 5) {
                mBluetoothLeService.WriteValue("1" + "33");
            }else {
                mBluetoothLeService.WriteValue("1" + "19");
            }
        }
    }

    public void menosvoz(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "12");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "18");
            } else if (codigo_app == 5) {
                mBluetoothLeService.WriteValue("1" + "34");
            }else {
                mBluetoothLeService.WriteValue("1" + "20");
            }
        }
    }

    public void siguiente(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "25");
            } else if (codigo_app == 1) {
                mBluetoothLeService.WriteValue("1" + "23");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "28");
            } else {
                mBluetoothLeService.WriteValue("1" + "21");
            }
        }
    }

    public void anterior(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "26");
            } else if (codigo_app == 1) {
                mBluetoothLeService.WriteValue("1" + "24");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "27");
            } else {
                mBluetoothLeService.WriteValue("1" + "22");
            }
        }
    }

    public void primera(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "15");
            } else if (codigo_app == 1) {
                mBluetoothLeService.WriteValue("1" + "30");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "27");
            } else if (codigo_app == 2) {
                mBluetoothLeService.WriteValue("1" + "22");
            } else {
                mBluetoothLeService.WriteValue("1" + "22");
            }
        }
    }

    public void ultima(View view) {
        if (conectado) {
            if (codigo_app == 0) {
                mBluetoothLeService.WriteValue("1" + "14");
            } else if (codigo_app == 1) {
                mBluetoothLeService.WriteValue("1" + "29");
            } else if (codigo_app == 4) {
                mBluetoothLeService.WriteValue("1" + "28");
            } else if (codigo_app == 3) {
                mBluetoothLeService.WriteValue("1" + "31");
            } else {
                mBluetoothLeService.WriteValue("1" + "21");
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
                Multimedia.this.setResult(-4, intent);
                Multimedia.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
