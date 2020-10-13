package pe.edu.pucp.tel306;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Información de la aplicación");

        final String texto1 = "Pomodoro PUCP es un temporizador pomodoro en bucle personalizable y fácil de usar para aumentar su eficiencia.";
        final String texto2 = "La Técnica Pomodoro es un método de gestión del tiempo desarrollado por Francesco Cirillo a finales de los 80. Esta técnica utiliza un temporizador para dividir las obras en un conjunto de intervalos separados por descansos. La técnica Pomodoro aumenta la productividad tomando descansos cortos programados con regularidad.";
        final String texto3 = " * Decida la tarea a realizar, establezca los temporizadores en 25 minutos para un Pomodoro\n" +
                "* Trabaja en la tarea hasta que se complete el temporizador\n" +
                "* Después de completar el temporizador, coloque una marca de verificación en la lista de tareas pendientes\n" +
                "* Tómate un breve descanso de 5 minutos\n" +
                "* Después de cuatro \"Pomodoro\", tómate un descanso\n" +
                "* Repita el paso 1.";

        final TextView textView = findViewById(R.id.textoInfo);
        final Spinner sobreLaApp = findViewById(R.id.spinner1);

        sobreLaApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sobreLaApp.getSelectedItem().toString().equals("Temporizador Pomodoro PUCP en línea")){
                    textView.setText(texto1);
                }else if (sobreLaApp.getSelectedItem().toString().equalsIgnoreCase("Acerca de la Técnica Pomodoro")){
                    textView.setText(texto2);
                }else {
                    textView.setText(texto3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}


