package pe.edu.pucp.tel306;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Editar preferencias");

        Button button = findViewById(R.id.buttonGuardarPreferencias);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                TextView textView1 = findViewById(R.id.editTextIntTrabajo);
                TextView textView2 = findViewById(R.id.editTextIntervDescanso);
                TextView textView3 = findViewById(R.id.editTextCiclosPomodoros);

                Boolean flag1 = true;
                String textoIntTrabajo = textView1.getText().toString();
                Log.d("msg", "Este es un debug "+textoIntTrabajo);
                String[] spliteoA = textoIntTrabajo.split(":");
                String textoIntTrabajoPart1 = spliteoA[0];
                String textoIntTrabajoPart2 = spliteoA[1];



                if(textoIntTrabajoPart1 == "" || textoIntTrabajoPart2 == ""){
                    textView1.setError("Ingrese un tiempo válido, formato mm:ss");
                }else{
                    if (Integer.parseInt(textoIntTrabajoPart1) >= 0 && Integer.parseInt(textoIntTrabajoPart1) <= 60 && Integer.parseInt(textoIntTrabajoPart2) >= 0 && Integer.parseInt(textoIntTrabajoPart2) <= 60) {
                        flag1 = false;
                    } else {
                        textView1.setError("Ingrese un tiempo válido, formato mm:ss");
                    }
                }



                Boolean flag2 = true;
                String textoIntervDescanso = textView2.getText().toString();
                String[] spliteoB = textoIntervDescanso.split(":");
                String textoIntervDescansoPart1 = spliteoB[0];
                String textoIntervDescansoPart2 = spliteoB[1];




                if(textoIntervDescansoPart1 == "" || textoIntervDescansoPart2 ==""){
                    textView2.setError("Ingrese un tiempo válido, formato mm:ss");
                } else {
                    if (Integer.parseInt(textoIntervDescansoPart1) >= 0 && Integer.parseInt(textoIntervDescansoPart1) <= 60 && Integer.parseInt(textoIntervDescansoPart2) >= 0 && Integer.parseInt(textoIntervDescansoPart2) <= 60) {
                        flag2 = false;
                    } else {
                        textView2.setError("Ingrese un tiempo válido, formato mm:ss");

                    }
                }


                Boolean flag3 = true;
                int ciclosPromodoros = 0;
                try {
                    ciclosPromodoros = Integer.parseInt(textView3.getText().toString());
                    flag3 = false;
                } catch (NumberFormatException e) {
                    textView3.setError("Ingrese un número.");
                }


                if (flag1 == false && flag2 == false && flag3 == false) {
                    intent.putExtra("value1", textoIntTrabajo);
                    intent.putExtra("value2", textoIntervDescanso);
                    intent.putExtra("value3", ciclosPromodoros);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}