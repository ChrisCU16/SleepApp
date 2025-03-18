package com.example.sleepapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Recuperar el tono de alarma
        String alarmTone = intent.getStringExtra("ALARM_TONE");

        // Iniciar la actividad ApagarAlarma
        Intent alarmIntent = new Intent(context, ApagarAlarma.class);
        alarmIntent.putExtra("ALARM_TONE", alarmTone);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Requerido para iniciar actividades desde un BroadcastReceiver
        context.startActivity(alarmIntent);
    }
}
