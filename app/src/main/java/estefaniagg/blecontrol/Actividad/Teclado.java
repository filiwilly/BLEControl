package estefaniagg.blecontrol.Actividad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import estefaniagg.blecontrol.Data.Teclas;
import estefaniagg.blecontrol.R;

public class Teclado extends Principal {
    private static final String DEBUG_TAG = "Velocidad";
    private VelocityTracker mVelocityTracker = null;
    boolean teclado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_teclado);
        getSupportActionBar().setTitle(R.string.tt_teclado);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividades, menu);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("key pressed", String.valueOf(event.getKeyCode()));
        int codigo= Teclas.getTeclaid(event.getKeyCode());
        if (conectado) {
            mBluetoothLeService.WriteValue("4" + String.valueOf(codigo));
        }
        return super.onKeyUp(keyCode, event);
    }

    public void abrirteclado (View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (teclado == false) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
            teclado=true;
        } else {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            teclado=false;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.desconexion:
                final Intent intent = new Intent(this, Principal.class);
                Teclado.this.setResult(-5, intent);
                Teclado.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
