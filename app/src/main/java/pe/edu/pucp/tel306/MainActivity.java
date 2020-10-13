package pe.edu.pucp.tel306;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.pucp.tel306.ViewModels.TimerViewModel;

public class MainActivity extends AppCompatActivity {
    private static boolean pause=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (pause){
            findViewById(R.id.buttonPause).setVisibility(View.GONE);
            findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.buttonPause).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonPlay).setVisibility(View.GONE);
        }


        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause=false;
                findViewById(R.id.buttonPause).setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "PLAY SELECTED", Toast.LENGTH_SHORT).show();
                ViewModelProvider viewModelProvider=new ViewModelProvider(MainActivity.this);
                TimerViewModel timerViewModel=viewModelProvider.get(TimerViewModel.class);
                timerViewModel.starttimerThread(60*25);
                timerViewModel.getContadorMutable().observe(MainActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        int minutes=integer/60;
                        int seconds=integer%60;
                        String splitstr=":";
                        if (seconds<10){
                            splitstr=":0";
                        }
                        TextView timer = findViewById(R.id.textViewTimer);
                        timer.setText(String.valueOf(minutes)+splitstr+String.valueOf(seconds));
                    }
                });

            }
        });


        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause=true;
                view.setVisibility(View.GONE);
                findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);

            }
        });

        findViewById(R.id.buttonRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ViewModelProvider viewModelProvider=new ViewModelProvider(this);
        TimerViewModel timerViewModel=viewModelProvider.get(TimerViewModel.class);
        timerViewModel.getContadorMutable().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int minutes=integer/60;
                int seconds=integer%60;
                TextView timer = findViewById(R.id.textViewTimer);
                timer.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void gethelp(MenuItem menuitem) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    public void startTimer(View view) {


    }

}