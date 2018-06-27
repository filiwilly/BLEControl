package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import estefaniagg.blecontrol.R;

public class Multimedia extends Principal {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_multimedia);
        getSupportActionBar().setTitle(R.string.tt_multimedia);
    }

    public void PLPA(View view){
        if(conectado){
            mBluetoothLeService.WriteValue("33"+"dale al pause");
        }
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
                Multimedia.this.setResult(-4,intent);
                Multimedia.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
