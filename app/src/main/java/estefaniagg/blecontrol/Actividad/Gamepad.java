package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import estefaniagg.blecontrol.R;

public class Gamepad extends Principal {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_gamepad);
        getSupportActionBar().setTitle(R.string.tt_gamepad);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //Botones de Dirección:
        Button b1 = (Button) this.findViewById(R.id.bup);
        Button b2 = (Button) this.findViewById(R.id.bup2);
        Button b3 = (Button) this.findViewById(R.id.bup3);
        Button b4 = (Button) this.findViewById(R.id.bup4);
        Button b5 = (Button) this.findViewById(R.id.bup5);
        Button b6 = (Button) this.findViewById(R.id.bup6);
        Button m1 = (Button) this.findViewById(R.id.bmid1);
        Button m2 = (Button) this.findViewById(R.id.bmid2);
        Button m3 = (Button) this.findViewById(R.id.bmid3);
        Button m4 = (Button) this.findViewById(R.id.bmid4);
        //Acciones de los botones de dirección:
        //TODO: Cambiar códigos numericos por teclas
        m1.setOnTouchListener(new View.OnTouchListener() { //Arriba-Izquierda
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"14");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"15");
                }
                return false;
            }
        });

        m2.setOnTouchListener(new View.OnTouchListener() { //Arriba-Derecha
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"16");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"17");
                }
                return false;
            }
        });
        m3.setOnTouchListener(new View.OnTouchListener() { //Abajo-Izquierda
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"18");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"19");
                }
                return false;
            }
        });
        m4.setOnTouchListener(new View.OnTouchListener() { //Abajo-Derecha
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"20");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"21");
                }
                return false;
            }
        });

        b4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"3");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"4");
                }
                return false;
            }
        });

        b3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"7");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"8");
                }
                return false;
            }
        });
        b2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"5");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"6");
                }
                return false;
            }
        });
        b1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"1");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"2");
                }
                return false;
            }
        });
        b5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"10");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"12");
                }
                return false;
            }
        });
        b6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"11");
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"13");
                }
                return false;
            }
        });
    }

    public void stpressed (View v){
        mBluetoothLeService.WriteValue("9");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.desconexion:
                final Intent intent = new Intent(this, Principal.class);
                Gamepad.this.setResult(-3,intent);
                Gamepad.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
