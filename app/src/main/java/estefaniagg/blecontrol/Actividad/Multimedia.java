package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import estefaniagg.blecontrol.R;

public class Multimedia extends Principal {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_multimedia);
        getSupportActionBar().setTitle(R.string.tt_multimedia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button volup = (Button) findViewById(R.id.volup);
        Button voldown = (Button) findViewById(R.id.voldown);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void PLPA(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("1"+"13");
        }
    }
    public void masvoz(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("1"+"11");
        }
    }
    public void menosvoz(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("1"+"12");
        }
    }
    public void siguiente(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("1"+"14");
        }
    }
    public void anterior(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("1"+"15");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_multimedia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.desconexion:
                final Intent intent = new Intent(this, Principal.class);
                Multimedia.this.setResult(-4,intent);
                Multimedia.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
