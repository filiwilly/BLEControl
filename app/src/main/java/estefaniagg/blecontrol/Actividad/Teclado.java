package estefaniagg.blecontrol.Actividad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import estefaniagg.blecontrol.Data.Teclas;
import estefaniagg.blecontrol.R;

import static android.content.ContentValues.TAG;

public class Teclado extends Principal {
    boolean teclado = false;
    RelativeLayout Area;
    float x1, x2;
    float y1, y2;
    float distx = 0, disty = 0;
    boolean primermove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_teclado);
        getSupportActionBar().setTitle(R.string.tt_teclado);
        Area = (RelativeLayout) findViewById(R.id.l2);
        //TOUCHPAD
        Area.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int accion = event.getActionMasked();
                switch (accion) {
                    case MotionEvent.ACTION_DOWN:
                        primermove = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        x1 = event.getX();
                        y1 = event.getY();
                        if (primermove) {
                            primermove = false;
                        } else {
                            distx = x1 - x2;
                            disty = y1 - y2;
                        }
                        x2 = x1;
                        y2 = y1;
                        if (conectado) {
                            Log.d(TAG, distx+" "+disty);
                            mBluetoothLeService.WriteValue("5" + Math.round(distx) + ";" + Math.round(disty));
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void clickizq(View view){
        if (conectado){
            mBluetoothLeService.WriteValue("6" + "LEFT");
        }
    }
    public void clickder(View view){
        if (conectado){
            mBluetoothLeService.WriteValue("6" + "RIGHT");
        }
    }
    public void arriba(View view){
        if (conectado){
            mBluetoothLeService.WriteValue("7" + "1");
        }
    }
    public void abajo(View view){
        if (conectado){
            mBluetoothLeService.WriteValue("7" + "-1");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividades, menu);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("key pressed", String.valueOf(event.getKeyCode()));
        int codigo = Teclas.getTeclaid(event.getKeyCode());
        if (conectado) {
            mBluetoothLeService.WriteValue("4" + String.valueOf(codigo));
        }
        return super.onKeyUp(keyCode, event);
    }

    public void abrirteclado(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (teclado == false) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            teclado = true;
        } else {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            teclado = false;
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
