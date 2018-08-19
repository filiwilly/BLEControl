package estefaniagg.blecontrol.Actividad;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import estefaniagg.blecontrol.Data.Dispositivo;
import estefaniagg.blecontrol.Data.Util;
import estefaniagg.blecontrol.R;
import estefaniagg.blecontrol.Servicio.BluetoothLeService;
import estefaniagg.blecontrol.UI.Escanear;

import static android.content.ContentValues.TAG;

public class Principal extends AppCompatActivity {
    public static BluetoothAdapter mBluetoothAdapter;
    private static Dispositivo dispositivo_conectado;
    private BroadcastReceiver localBroadcastReceiver;

    protected static Boolean conectado = false;
    protected TextView tvDispo;
    protected ImageView iconoble;
    private ImageView im_cargando;
    private Animation animacion;
    protected String nombreact;
    private static boolean destruir = false;

    protected BluetoothLeService mBluetoothLeService;
    protected static String mNombreDispo = "Ningún dispositivo conectado";
    protected static String mDirecDispo;

    protected final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Incapaz de inicializar Bluetooth");
                finish();
            }
            // Para conectar automaticamente al dispositivo sería:
            //mBluetoothLeService.connect(mDirecDispo);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // EVENTOS GATT
    protected final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "Pasa por mGattUpdate");
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(TAG, "Solo Gatt");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                if (!nombreact.equals("Principal")) {
                    destruir=true;
                    finish();
                }
                conectado = false;
                Log.d(TAG, "Desconectado");
                Toast.makeText(getApplicationContext(), "DESCONECTADO", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                conectado = true;
                Log.d(TAG, "Conectado");
                im_cargando.clearAnimation();
                im_cargando.setVisibility(View.INVISIBLE);
                tvDispo.setText(mNombreDispo);
                Toast.makeText(getApplicationContext(), "CONECTADO", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //En el que caso que leyéramos de la placa Arduino...
                //Log.d(TAG, "Disponible");
                //String data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case 1: //Resultado de petición de activación BT
                if (resultCode == RESULT_CANCELED) { //No se autoriza la activación
                    Toast t = Toast.makeText(this,"Active el Bluetooth para utilizar" +
                            "esta aplicación",Toast.LENGTH_LONG);
                    t.show();
                    return;
                }
                return;
            case 2:
                if (resultCode == -2) {
                    conectado=false;
                    Toast.makeText(getApplicationContext(), "DESCONECTADO", Toast.LENGTH_SHORT).show();
                    mBluetoothLeService.disconnect();
                    invalidateOptionsMenu();
                }
                return;
            case 3:
                if (resultCode == -3) {
                    conectado=false;
                    Toast.makeText(getApplicationContext(), "DESCONECTADO", Toast.LENGTH_SHORT).show();
                    mBluetoothLeService.disconnect();
                    invalidateOptionsMenu();
                }
            case 4:
                if (resultCode == -4) {
                    conectado=false;
                    Toast.makeText(getApplicationContext(), "DESCONECTADO", Toast.LENGTH_SHORT).show();
                    mBluetoothLeService.disconnect();
                    invalidateOptionsMenu();
                }
            case 5:
                if (resultCode == -5) {
                    conectado=false;
                    Toast.makeText(getApplicationContext(), "DESCONECTADO", Toast.LENGTH_SHORT).show();
                    mBluetoothLeService.disconnect();
                    invalidateOptionsMenu();
                }
            default:
                return;
        }
    }

    public void multimedia(View view) {
        if (conectado) {
            Intent intent = new Intent(this, Multimedia.class);
            startActivityForResult(intent, 4);
        } else {
            Snackbar.make(view, R.string.noconectado, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void gamepad(View view) {
        if (conectado) {
            Intent intent = new Intent(this, Gamepad.class);
            startActivityForResult(intent, 3);
        } else {
            Snackbar.make(view, R.string.noconectado, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void voz(View view) {
        if (conectado) {
            Intent intent = new Intent(this, Voz.class);
            intent.putExtra("direccion", mDirecDispo);
            startActivityForResult(intent, 2);
        } else {
            Snackbar.make(view, R.string.noconectado, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void teclado(View view) {
        if (conectado) {
            Intent intent = new Intent(this, Teclado.class);
            intent.putExtra("direccion", mDirecDispo);
            startActivityForResult(intent, 5);
        } else {
            Snackbar.make(view, R.string.noconectado, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        ImageButton iconolupa = (ImageButton) findViewById(R.id.ic_lupa);
        iconolupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                Escanear fragmento = new Escanear();
                fragmento.show(fragmentManager,"Dialogo Escanear");
            }
        });

        tvDispo = (TextView) findViewById(R.id.tvDispo);
        iconoble = (ImageView) findViewById(R.id.iconoble);
        im_cargando = (ImageView) findViewById(R.id.im_cargando);
        animacion = AnimationUtils.loadAnimation(this, R.anim.anim_rotacion);
        animacion.setInterpolator(new LinearInterpolator());
        localBroadcastReceiver = new LocalBroadcastReceiver();

        //Inicializar los Adaptadores
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //Comprobaciones
        Util.ComprobarBLEon(this);
        Util.ComprobarBLE(this);
        Util.ComprobarVersion(this);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    public static void RecibeDispo (Dispositivo dispositivo){
        dispositivo_conectado=dispositivo;
        mNombreDispo=dispositivo_conectado.getName();
        mDirecDispo=dispositivo_conectado.getAddress();
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equals("Registrar Dispositivo")) {
                invalidateOptionsMenu();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        if (!mNombreDispo.equals("Ningún dispositivo conectado")) {
            if (conectado) {
                //Desconectar -- Con dispositivo seleccionado y conectado
                menu.findItem(R.id.conexion).setVisible(false);
                menu.findItem(R.id.desconexion).setVisible(true);
                iconoble.setImageResource(R.drawable.im_logorojo);
            } else {
                //Conectar -- Con dispositivo seleccionado
                menu.findItem(R.id.conexion).setVisible(true);
                menu.findItem(R.id.desconexion).setVisible(false);
                iconoble.setImageResource(R.drawable.im_logogris);
                tvDispo.setText(mNombreDispo);
                destruir=false;
            }
        } else {
            //Escanear -- Desconectado y sin dispositivo
            menu.findItem(R.id.desconexion).setVisible(false);
            menu.findItem(R.id.conexion).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.desconexion:
                mBluetoothLeService.disconnect();
                invalidateOptionsMenu();
                return true;
            case R.id.conexion:
                tvDispo.setText("Conectando...");
                im_cargando.startAnimation(animacion);
                im_cargando.setVisibility(View.VISIBLE);
                Log.d(TAG,mDirecDispo+mBluetoothLeService);
                mBluetoothLeService.connect(mDirecDispo);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ON XXXXX
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume");
        registerReceiver(mGattUpdateReceiver, Util.makeGattUpdateIntentFilter());
        nombreact = this.getClass().getSimpleName();
        invalidateOptionsMenu();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                localBroadcastReceiver,
                new IntentFilter("Registrar Dispositivo"));
        //if (mBluetoothLeService != null) {
        //final boolean result = mBluetoothLeService.connect(mDirecDispo);
        //Log.d(TAG, "Resultado de solicitud de conexion=" + result);
        //}
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"OnPause");
        unregisterReceiver(mGattUpdateReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                localBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"OnStop");
        if(destruir){
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"OnDestroy");
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
}
