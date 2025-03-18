package com.example.sleepapp;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.app.PendingIntent;
import android.app.AlarmManager;

public class RegistrarAlarma extends AppCompatActivity {

    private TextView tvRecommendedSleepTime, tvMaxSleepTime;
    private Button btnSelectTone, btnSetAlarm;
    private TimePicker timePickerAlarm;
    private Uri selectedToneUri; // Para almacenar el tono seleccionado
    private SqlLiteHelper sqlLiteHelper; // Declara la variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraralarma);

        // Inicializa la instancia de SqlLiteHelper
        sqlLiteHelper = new SqlLiteHelper(this);

        // Conectar los elementos del layout
        timePickerAlarm = findViewById(R.id.timePickerAlarm);
        tvRecommendedSleepTime = findViewById(R.id.tvRecommendedSleepTime);
        tvMaxSleepTime = findViewById(R.id.tvMaxSleepTime);
        btnSelectTone = findViewById(R.id.btnSelectTone);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);

        // Calcular inicialmente la hora sugerida y la hora máxima de descanso
        calcularHoras();

        // Manejar los cambios en el TimePicker
        timePickerAlarm.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Recalcular las horas cuando se cambie el TimePicker
                calcularHoras();
            }
        });

        // Manejar la selección de tono de alarma
        btnSelectTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Selecciona un tono de alarma");
                startActivityForResult(intent, 1);
            }
        });

        // Configurar la alarma al hacer clic en el botón
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePickerAlarm.getHour();
                int minute = timePickerAlarm.getMinute();

                if (selectedToneUri != null) {
                    // Guardar la alarma en la base de datos con el tono seleccionado
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Programar la alarma
                    Intent intent = new Intent(RegistrarAlarma.this, AlarmReceiver.class);
                    intent.putExtra("ALARM_TONE", selectedToneUri.toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            RegistrarAlarma.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (alarmManager != null) {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        Toast.makeText(RegistrarAlarma.this, "Alarma configurada para " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

                        // Enviar Intent a estadisticas
                        Intent updateIntent = new Intent(RegistrarAlarma.this, MenuPrincipal.class);
                        updateIntent.putExtra("UPDATE_GRAPH", true);
                        startActivity(updateIntent); // O usa un método diferente si deseas regresar a la actividad actual
                    }
                } else {
                    Toast.makeText(RegistrarAlarma.this, "Por favor, selecciona un tono de alarma", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Recibir el resultado del selector de tono
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedToneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (selectedToneUri == null) {
                selectedToneUri = Settings.System.DEFAULT_ALARM_ALERT_URI;
            }
        }
    }

    private void calcularHoras() {
        int selectedHour = timePickerAlarm.getHour(); // Obtener la hora seleccionada
        int selectedMinute = timePickerAlarm.getMinute(); // Obtener los minutos seleccionados

        // Suponemos que el usuario necesita un mínimo de 8 horas de sueño
        int baseSleepDuration = 8; // Cambiar si se obtienen datos de rutina que indiquen más tiempo

        // Obtener duración adicional de sueño de la rutina
        int dniUsuario = obtenerDniUsuario(); // Implementa este método para obtener el DNI del usuario logueado
        int additionalSleep = sqlLiteHelper.obtenerDuracionAdicionalDeRutina(dniUsuario); // Llama al nuevo método

        // Calcular la duración total de sueño
        int totalSleepDuration = baseSleepDuration + additionalSleep; // Duración total de sueño a considerar

        // Calcular la hora sugerida para acostarse
        Calendar bedTime = Calendar.getInstance();
        bedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        bedTime.set(Calendar.MINUTE, selectedMinute);

        // Restar la duración total de sueño
        bedTime.add(Calendar.HOUR_OF_DAY, -totalSleepDuration); // Resta las horas de sueño

        // Ajustar formato 12 horas para la hora sugerida para acostarse
        String amPmSuggested = (bedTime.get(Calendar.HOUR_OF_DAY) >= 12) ? "PM" : "AM";
        int suggestedSleepHour = bedTime.get(Calendar.HOUR_OF_DAY) % 12; // Convertir de 24h a 12h
        if (suggestedSleepHour == 0) suggestedSleepHour = 12; // Ajustar medianoche a 12 AM

        // Mostrar la hora sugerida para acostarse
        tvRecommendedSleepTime.setText("Hora sugerida para acostarse: " + suggestedSleepHour + ":00 " + amPmSuggested);

        // Mostrar la hora máxima de descanso
        String amPmMax = (selectedHour >= 12) ? "PM" : "AM";
        int displayHourMax = selectedHour % 12; // Convertir de 24h a 12h
        if (displayHourMax == 0) displayHourMax = 12; // Ajustar medianoche a 12 AM

        // Mostrar la hora máxima en formato de 12 horas
        tvMaxSleepTime.setText("Hora para maxima para descansar: " + displayHourMax + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute) + " " + amPmMax);
    }

    // Implementa este método para obtener el DNI del usuario logueado
    private int obtenerDniUsuario() {
        // Tu lógica para obtener el DNI del usuario actual
        return 0; // Reemplaza con el DNI real
    }
}
