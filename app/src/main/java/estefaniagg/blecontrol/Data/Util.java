package estefaniagg.blecontrol.Data;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import estefaniagg.blecontrol.Actividad.Principal;
import estefaniagg.blecontrol.R;
import estefaniagg.blecontrol.Servicio.BluetoothLeService;

public class Util {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MY_PERMISSION_RESPONSE = 42;

    public static void ComprobarBLEon(Activity activity) {
        if (Principal.mBluetoothAdapter == null || !Principal.mBluetoothAdapter.isEnabled()) { //Relacionado con permiso BT Manifest
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    public static void ComprobarBLE(Activity activity) {
        if (Principal.mBluetoothAdapter == null) {
            Toast.makeText(activity.getBaseContext(), R.string.noble, Toast.LENGTH_SHORT).show();
            activity.finish();
            return;
        }
    }

    public static void ComprobarVersion(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.w("BleActivity", "Acceso no garantizado");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_RESPONSE);
        }
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

}
