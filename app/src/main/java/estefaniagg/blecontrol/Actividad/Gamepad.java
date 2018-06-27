package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import estefaniagg.blecontrol.R;

public class Gamepad extends Principal {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_gamepad);
        getSupportActionBar().setTitle(R.string.tt_gamepad);
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
