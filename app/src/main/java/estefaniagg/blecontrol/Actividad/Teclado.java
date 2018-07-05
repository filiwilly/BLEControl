package estefaniagg.blecontrol.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import estefaniagg.blecontrol.R;

public class Teclado extends Principal {

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
        char unicodeChar = (char)event.getUnicodeChar();
        event.getKeyCharacterMap();
        Log.i("unicode:", String.valueOf(unicodeChar));
        if (event.getKeyCode()!=16 && conectado){
            mBluetoothLeService.WriteValue("4"+String.valueOf(event.getKeyCode()));
        }
        return super.onKeyUp(keyCode, event);
    }

    /*public void showSoftKeyboard(View view) {
    if (view.requestFocus()) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}*/

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
