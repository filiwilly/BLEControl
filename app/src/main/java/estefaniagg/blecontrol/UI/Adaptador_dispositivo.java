package estefaniagg.blecontrol.UI;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import estefaniagg.blecontrol.Data.Dispositivo;
import estefaniagg.blecontrol.R;

import static android.content.ContentValues.TAG;

public class Adaptador_dispositivo extends BaseAdapter {
    private Context context;
    private List<Dispositivo> bleLista;
    private HashMap<String, Dispositivo> hmbleLista;

    public Adaptador_dispositivo(Context context) {
        this.context = context;
        bleLista = new ArrayList<>();
        hmbleLista = new HashMap<>();
    }

    public void addDispo(BluetoothDevice device, int rssi) {
        String direccion = device.getAddress();
        if (!hmbleLista.containsKey(direccion)) {
            Dispositivo dispositivo = new Dispositivo(device, rssi); //Crea el nuevo dispositivo
            hmbleLista.put(direccion, dispositivo);
            bleLista.add(dispositivo);
        } else {
            hmbleLista.get(direccion).setRSSI(rssi);
        }
    }

    @Override
    public Dispositivo getItem(int position) {
        if (position > bleLista.size())
            return null;
        return bleLista.get(position);
    }

    public void clear() {
        bleLista.clear();
        hmbleLista.clear();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return bleLista.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.adaptador_dispositivos, null);
            holder = new ViewHolder();
            view.setTag(holder);
            holder.txt_nombre = (TextView) view.findViewById(R.id.txt_nombre);
            holder.txt_mac = (TextView) view.findViewById(R.id.txt_mac);
            holder.rssi = (ImageView) view.findViewById(R.id.rssi);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Sacar las características del dispositivo agregado a la lista:
        final Dispositivo bleDispo = getItem(position);
        final String name = bleDispo.getName();
        Log.d(TAG, "viewholder" + name);
        final String mac = bleDispo.getAddress();
        final int rssi = bleDispo.getRSSI();
        if (name != null && name.length() > 0) {
            holder.txt_nombre.setText(name);
        } else {
            holder.txt_nombre.setText(R.string.desc);
        }
        holder.txt_mac.setText(mac);
        if (rssi > -100 && rssi < -70) {
            holder.rssi.setImageResource(R.drawable.ic_bbajo);
        } else if (rssi >= -70 && rssi < -45) {
            holder.rssi.setImageResource(R.drawable.ic_bmedio);
        } else {
            holder.rssi.setImageResource(R.drawable.ic_balto);
        }
        return view;
    }

    static class ViewHolder {
        TextView txt_nombre;
        TextView txt_mac;
        ImageView rssi;
    }
}

