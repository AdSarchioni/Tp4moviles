package com.example.menuconmapa.ui.broadcast;

import static android.app.ProgressDialog.show;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CambioUsb extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Boolean conectado = intent.getBooleanExtra("connected", false);
        if (conectado) {
            Toast.makeText(context, "hola te estas conectando ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "chau te estas desconectando ", Toast.LENGTH_LONG).show();
        }
    }}
