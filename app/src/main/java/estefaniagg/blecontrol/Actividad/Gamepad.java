package estefaniagg.blecontrol.Actividad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import estefaniagg.blecontrol.R;

import static android.content.ContentValues.TAG;

public class Gamepad extends Principal {
    @SuppressLint("ClickableViewAccessibility")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_gamepad);
        getSupportActionBar().setTitle(R.string.tt_gamepad);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //Sensor giroscopio
        SensorManager sensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // Create a listener
        SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 0.5f) { // anticlockwise

                } else if(sensorEvent.values[2] < -0.5f) { // clockwise

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

// Register the listener
        sensorManager.registerListener(gyroscopeSensorListener,
                gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

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
        m1.setOnTouchListener(new View.OnTouchListener() { //Arriba-Izquierda
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"15"+"LU");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"16"+"LU");
                }
                return false;
            }
        });

        m2.setOnTouchListener(new View.OnTouchListener() { //Arriba-Derecha
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"17"+"RU");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"18"+"RU");
                }
                return false;
            }
        });
        m3.setOnTouchListener(new View.OnTouchListener() { //Abajo-Izquierda
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"19"+"LD");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"20"+"LD");
                }
                return false;
            }
        });
        m4.setOnTouchListener(new View.OnTouchListener() { //Abajo-Derecha
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"21"+"RD");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"22"+"RD");
                }
                return false;
            }
        });

        b4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"13"+"KEY_DOWN_ARROW");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"14"+"KEY_DOWN_ARROW");
                }
                return false;
            }
        });

        b3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"13"+"KEY_RIGHT_ARROW");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"14"+"KEY_RIGHT_ARROW");
                }
                return false;
            }
        });
        b2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"13"+"KEY_LEFT_ARROW");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"14"+"KEY_LEFT_ARROW");
                }
                return false;
            }
        });
        b1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"13"+"KEY_UP_ARROW");
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"14"+"KEY_UP_ARROW");
                }
                return false;
            }
        });
        b5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"11"+"k");
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                    mBluetoothLeService.WriteValue("2"+"12"+"k");
                }
                return false;
            }
        });
        b6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"Action"+event.getAction());
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mBluetoothLeService.WriteValue("2"+"11"+"l");
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL
                        || event.getAction()==MotionEvent.ACTION_POINTER_UP){
                    mBluetoothLeService.WriteValue("2"+"12"+"l");
                }
                return false;
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void stpressed (View v){
        mBluetoothLeService.WriteValue("2"+"13"+"KEY_RETURN");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mBluetoothLeService.WriteValue("2"+"14"+"KEY_RETURN");
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
