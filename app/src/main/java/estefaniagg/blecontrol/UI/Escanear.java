package estefaniagg.blecontrol.UI;

import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.view.WindowManager.LayoutParams;

import estefaniagg.blecontrol.Actividad.Principal;
import estefaniagg.blecontrol.Data.Dispositivo;
import estefaniagg.blecontrol.R;

import static android.content.ContentValues.TAG;

public class Escanear extends DialogFragment {

    private Button b_escaneo;
    private boolean escaneando;
    private Handler mHandler;
    Adaptador_dispositivo adaptador;
    Dispositivo dispositivo_conectado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                    .TRANSPARENT));
        }
        return dialog;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_escaneo, container);
        ListView lv = (ListView) v.findViewById(R.id.lista);
        adaptador = new Adaptador_dispositivo(v.getContext());
        lv.setAdapter(adaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cuando se pulsa un elemento de la lista de dispositivos:
                dispositivo_conectado = adaptador.getItem(position);
                if (dispositivo_conectado == null) return;
                Principal.RecibeDispo(dispositivo_conectado);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                        new Intent("Registrar Dispositivo"));
                if (escaneando) {
                    Principal.mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    escaneando = false;
                }
                dismiss();
            }
        });
        b_escaneo = (Button) v.findViewById(R.id.b_escaneo);
        b_escaneo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b_escaneo.getText().equals(getString(R.string.escanear))) { //Si damos a "Escanear"
                    adaptador.clear();
                    escanear(true);
                } else if (b_escaneo.getText().equals(getString(R.string.noescanear))) {  //Si damos a "Parar"
                    escanear(false);
                }
            }
        });
        escanear(true);
        return v;
    }

    private void onclick() {

    }

    private void escanear(final boolean enable) {  //Acción Escanear
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    escaneando = false;
                    Principal.mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    b_escaneo.setText(R.string.escanear); //Cambia texto del botón a "Escanear"
                }
            }, 10000); //Duración de escaneo: 10 seg

            escaneando = true;
            Principal.mBluetoothAdapter.startLeScan(mLeScanCallback);
            b_escaneo.setText(R.string.noescanear); //Cambia texto del botón a "Parar"
        } else {
            escaneando = false;
            Principal.mBluetoothAdapter.stopLeScan(mLeScanCallback);
            b_escaneo.setText(R.string.escanear); //Cambia texto del botón a "Escanear"
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    Log.d(TAG, "Escanear" + rssi);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adaptador.addDispo(device, rssi);
                            adaptador.notifyDataSetChanged();
                        }
                    });
                }
            };

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }
}
