package estefaniagg.blecontrol.Servicio;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdaptador;
    private String mBluetoothDispoDir;
    public BluetoothGatt mBluetoothGatt;
    private int mEstadoConexion = ESTADO_DESCONECTADO;
    public int paquetetam;

    private static final int ESTADO_DESCONECTADO = 0;
    private static final int ESTADO_CONECTANDO = 1;
    private static final int ESTADO_CONECTADO = 2;

    public final static String ACTION_GATT_CONNECTED =
            "estefaniagg.blecontrol.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "estefaniagg.blecontrol.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "estefaniagg.blecontrol.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "estefaniagg.blecontrol.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "estefaniagg.blecontrol.EXTRA_DATA";

    public final static UUID UUID_NOTIFY =
            UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public final static UUID UUID_SERVICE =
            UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");

    private BluetoothGattCharacteristic mNotifyCharacteristic;

    //Implementa metodos a los que se llama desde otras clases
    public void WriteValue(String strValue) //Enviar por BT (string)
    {
        if (mBluetoothGatt != null) {
            mNotifyCharacteristic.setValue(strValue.getBytes());
            mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);
        }
    }

    public void WriteValue(byte[] data)  //Enviar por BT (array de bytes)
    {
        if (mBluetoothGatt != null) {
            mNotifyCharacteristic.setValue(data);
            mBluetoothGatt.writeCharacteristic(mNotifyCharacteristic);
        }
    }

    public void findService(List<BluetoothGattService> gattServices)
    {
        Log.i(TAG, "Count is:" + gattServices.size());
        for (BluetoothGattService gattService : gattServices)
        {
            Log.i(TAG, gattService.getUuid().toString());
            Log.i(TAG, UUID_SERVICE.toString());
            if(gattService.getUuid().toString().equalsIgnoreCase(UUID_SERVICE.toString()))
            {
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                Log.i(TAG, "Count is:" + gattCharacteristics.size());
                for (BluetoothGattCharacteristic gattCharacteristic :
                        gattCharacteristics)
                {
                    if(gattCharacteristic.getUuid().toString().equalsIgnoreCase(UUID_NOTIFY.toString()))
                    {
                        Log.i(TAG, gattCharacteristic.getUuid().toString());
                        Log.i(TAG, UUID_NOTIFY.toString());
                        mNotifyCharacteristic = gattCharacteristic;
                        setCharacteristicNotification(gattCharacteristic, true);
                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                        return;
                    }
                }
            }
        }
    }

    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mEstadoConexion = ESTADO_CONECTADO;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Conectado a Servidor GATT.");
                //Intenta descubrir servicios después de conectarse correctamente.
                Log.i(TAG, "Intentando comenzar servicio de descubrimiento:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mEstadoConexion = ESTADO_DESCONECTADO;
                Log.i(TAG, "Desconectado de Servidor GATT.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                findService(gatt.getServices());
            } else {
                Log.w(TAG, "onServicesDiscovered recibido: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //Después de usar un dispositivo se debe llamar a BluetoothGatt.close() para que los
        //recursos se limpien.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Inicializa una referencia al Adaptador Bluetooth local.
     *
     * @return Devuelve true si se inicializa correctamente.
     */
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Imposible inicializar BluetoothManager.");
                return false;
            }
        }
        mBluetoothAdaptador = mBluetoothManager.getAdapter();
        if (mBluetoothAdaptador == null) {
            Log.e(TAG, "Incapaz de obtener un BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Se conecta al servidor GATT alojado en el dispositivo BLE
     * @param address Dirección de dispositivo destino.
     *
     * @return true si conexión correcta. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdaptador == null || address == null) {
            Log.w(TAG, "BluetoothAdapter no inicializado o sin direccion.");
            return false;
        }

        // Dispositivo anteriormente conectado.  Intenta reconectar.
        if (mBluetoothDispoDir != null && address.equals(mBluetoothDispoDir)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Intentando usar un mBluetoothGatt previo para la conexion.");
            if (mBluetoothGatt.connect()) {
                mEstadoConexion = ESTADO_CONECTANDO;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdaptador.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Dispositivo no encontrado.  Imposible conectar.");
            return false;
        }

        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Intentando crear una nueva conexion.");
        if (mBluetoothGatt!=null){
            Log.d(TAG, "mBluetoothGatt ON");
        }
        mBluetoothDispoDir = address;
        mEstadoConexion = ESTADO_CONECTANDO;
        return true;
    }

    /**
     * Desconecta o cancela una conexion en proceso. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdaptador == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter no inicializado");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * Después de usar el dispositivo BLE se debe llamar a esta funcion para limpiar recursos
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.disconnect();
        mBluetoothGatt.close();
        mBluetoothGatt = null;
        mNotifyCharacteristic = null;
    }

    /**
     * Lectura de datos.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdaptador == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter no inicializado");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Habilita o inhabilita las notificaciones
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdaptador == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter no inicializado");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }
}

