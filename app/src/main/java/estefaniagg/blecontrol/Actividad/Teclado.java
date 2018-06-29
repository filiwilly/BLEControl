package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import estefaniagg.blecontrol.R;

public class Teclado extends Principal {

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
                Teclado.this.setResult(-5, intent);
                Teclado.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
