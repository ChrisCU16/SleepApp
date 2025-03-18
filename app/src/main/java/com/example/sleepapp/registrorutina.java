package com.example.sleepapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class registrorutina extends AppCompatActivity {

    private Spinner spinnerAge;
    private SeekBar seekBarWorkHours, seekBarExercise, seekBarRecreationalActivities, seekBarCaffeine, seekBarScreenTime, seekBarStress;
    private TextView textViewWorkHoursValue, textViewExerciseValue, textViewRecreationalActivitiesValue, textViewCaffeineValue, textViewScreenTimeValue, textViewStressValue;
    private Button btnSubmit;
    private SqlLiteHelper sqlLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrorutina);

        // Inicializar SqlLiteHelper
        sqlLiteHelper = new SqlLiteHelper(this);

        // Conectar con los elementos del XML
        spinnerAge = findViewById(R.id.spinnerAge);
        seekBarWorkHours = findViewById(R.id.seekBarWorkHours);
        seekBarExercise = findViewById(R.id.seekBarExercise);
        seekBarRecreationalActivities = findViewById(R.id.seekBarRecreationalActivities);
        seekBarCaffeine = findViewById(R.id.seekBarCaffeine);
        seekBarScreenTime = findViewById(R.id.seekBarScreenTime);
        seekBarStress = findViewById(R.id.seekBarStress);
        textViewWorkHoursValue = findViewById(R.id.textViewWorkHoursValue);
        textViewExerciseValue = findViewById(R.id.textViewExerciseValue);
        textViewRecreationalActivitiesValue = findViewById(R.id.textViewRecreationalActivitiesValue);
        textViewCaffeineValue = findViewById(R.id.textViewCaffeineValue);
        textViewScreenTimeValue = findViewById(R.id.textViewScreenTimeValue);
        textViewStressValue = findViewById(R.id.textViewStressValue);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Rellenar el Spinner de edad
        ArrayAdapter<CharSequence> adapterAge = ArrayAdapter.createFromResource(this, R.array.age_options, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapterAge);

        // Actualizar el valor de las SeekBars dinámicamente
        seekBarWorkHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewWorkHoursValue.setText(progress + " h");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarExercise.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewExerciseValue.setText(progress + " min");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarRecreationalActivities.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewRecreationalActivitiesValue.setText(progress + " h");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarCaffeine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewCaffeineValue.setText(progress + " tazas");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarScreenTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewScreenTimeValue.setText(progress + " h");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarStress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewStressValue.setText(progress + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Manejar el botón de guardar
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de las SeekBars
                int workHours = seekBarWorkHours.getProgress();
                int exerciseTime = seekBarExercise.getProgress();
                int recreationalActivitiesTime = seekBarRecreationalActivities.getProgress();
                int caffeineConsumption = seekBarCaffeine.getProgress();
                int screenTime = seekBarScreenTime.getProgress();
                int stressLevel = seekBarStress.getProgress();

                // Definir el DNI (esto debería ser el DNI real del usuario)
                int dni = 73024009; // Cambia esto por el valor correcto

                // Guardar en la base de datos
                sqlLiteHelper.insertarRutina(
                        dni,  // Agregar el parámetro DNI aquí
                        spinnerAge.getSelectedItem().toString(),
                        workHours,
                        exerciseTime,
                        recreationalActivitiesTime,
                        caffeineConsumption,
                        screenTime,
                        stressLevel
                );

                Toast.makeText(registrorutina.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                finish(); // Cerrar la actividad
            }
        });

    }
}
