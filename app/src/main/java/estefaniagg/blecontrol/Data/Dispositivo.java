package estefaniagg.blecontrol.Data;

import android.bluetooth.BluetoothDevice;

public class Dispositivo {

    private BluetoothDevice dispositivo;
    private int rssi;

    public Dispositivo(BluetoothDevice dispositivo, int rssi) {
        this.dispositivo = dispositivo;
        this.rssi = rssi;
    }

    public String getAddress() {
        return dispositivo.getAddress();
    }

    public String getName() {
        return dispositivo.getName();
    }

    public void setRSSI(int rssi) {
        this.rssi = rssi;
    }

    public int getRSSI() {
        return rssi;
    }
}
