package pe.edu.pucp.tel306;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pe.edu.pucp.tel306.ViewModels.TimerViewModel;

public class MainActivity extends AppCompatActivity {
    private ActionMode mactionMode;
    private static boolean pause = true;
    private static boolean isResting = false;
    private static long limit = 25 * 60 * 1000;//maximo trabajo
    private static long rest = 5 * 60 * 1000;//maximo descanso
    private static int pomodoro = 4;//maximo ciclos
    private static long time = 25 * 60 * 1000; //tiempo instantaneo
    private static int ciclo = 1;//tiempo instantaneo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        printTimer();

        if (pause) {
            findViewById(R.id.buttonPause).setVisibility(View.GONE);
            findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.buttonPause).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonPlay).setVisibility(View.GONE);
        }

        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause = false;
                findViewById(R.id.buttonPause).setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);

                ViewModelProvider viewModelProvider = new ViewModelProvider(MainActivity.this);
                TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
                timerViewModel.starttimerThread(time);
            }
        });


        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause = true;
                view.setVisibility(View.GONE);
                findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);

                ViewModelProvider viewModelProvider = new ViewModelProvider(MainActivity.this);
                TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
                timerViewModel.stoptimerThread();
            }
        });

        findViewById(R.id.buttonRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.textViewRest).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#292929"));
                pause = true;
                findViewById(R.id.buttonPause).setVisibility(View.GONE);
                findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);
                ViewModelProvider viewModelProvider = new ViewModelProvider(MainActivity.this);
                TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
                timerViewModel.stoptimerThread();
                time = limit;
                printTimer();
            }
        });

        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
        timerViewModel.getContadorMutable().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long lg) {
                if (lg == 0) {
                    if (!isResting) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                        ad.setMessage("Deten todo! Hora de descansar :)");
                        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        ad.show();

                        isResting = true;
                        ViewModelProvider viewModelProvider = new ViewModelProvider(MainActivity.this);
                        TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
                        timerViewModel.stoptimerThread();
                        timerViewModel.starttimerThread(rest);
                        time = rest;
                        ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#44B849"));
                        findViewById(R.id.textViewRest).setVisibility(View.VISIBLE);

                    } else {
                        ciclo++;
                        if (ciclo > pomodoro) {
                            findViewById(R.id.textViewRest).setVisibility(View.GONE);
                            ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#292929"));
                            time = limit;
                            pause = true;
                            findViewById(R.id.buttonPause).setVisibility(View.GONE);
                            findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);

                            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                            ad.setTitle("Felicidades!!!!");
                            String msg = "A continuación un dataso curioso:\n";

                            ad.setMessage(msg);
                            ad.setPositiveButton("WOW!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            ad.show();

                        } else {
                            findViewById(R.id.textViewRest).setVisibility(View.GONE);
                            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                            ad.setMessage("De vuelta a concentrarse!");
                            ad.setPositiveButton("SI!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            ad.show();

                            ViewModelProvider viewModelProvider = new ViewModelProvider(MainActivity.this);
                            TimerViewModel timerViewModel = viewModelProvider.get(TimerViewModel.class);
                            timerViewModel.stoptimerThread();
                            timerViewModel.starttimerThread(limit);
                            ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#292929"));

                            time = limit;

                            TextView pomo = findViewById(R.id.textViewCiclo);
                            pomo.setText("Ciclo Pomodoro " + String.valueOf(ciclo) + " de  "+String.valueOf(pomodoro));
                        }
                        isResting = false;
                    }


                } else {
                    time = lg;
                }
                printTimer();
            }
        });


        TextView timer = findViewById(R.id.textViewTimer);
        timer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (pause) {
                    if (mactionMode != null) return false;
                    mactionMode = MainActivity.this.startActionMode(new ActionModeCallback());
                    view.setSelected(true);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "Para editar los intervalos, debe pausar el temporizador", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }

        });
    }

    public void printTimer() {
        if (time % 1000 == 0) {
            long timeseconds = time / 1000;
            long minutes = timeseconds / 60;
            long seconds = timeseconds % 60;

            String splitstr = ":";
            if (seconds < 10) {
                splitstr = ":0";
            }
            TextView timer = findViewById(R.id.textViewTimer);
            String timeStr = minutes + splitstr + seconds;
            if (minutes < 10) {
                timeStr = "0" + timeStr;
            }
            timer.setText(timeStr);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            findViewById(R.id.textViewRest).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#292929"));
            pause = true;
            findViewById(R.id.buttonPause).setVisibility(View.GONE);
            findViewById(R.id.buttonPlay).setVisibility(View.VISIBLE);

            long newlimit = data.getLongExtra("limit", 0);
            long newrest = data.getLongExtra("rest", 0);
            int newpomo = data.getIntExtra("pomo", 0);
            limit = newlimit;
            rest = newrest;
            pomodoro = newpomo;
            ciclo=1;
            ((TextView) findViewById(R.id.textViewdescanso)).setText("Tiempo de descanso: " + getTimeStr(rest));
            ((TextView) findViewById(R.id.textViewCiclo)).setText("Ciclo Pomodoro 1 de " + String.valueOf(pomodoro));
            time = limit;
            printTimer();
        }
    }

    public String getTimeStr(long time) {
        long timeseconds = time / 1000;
        long minutes = timeseconds / 60;
        long seconds = timeseconds % 60;

        String splitstr = ":";
        if (seconds < 10) {
            splitstr = ":0";
        }
        String timeStr = minutes + splitstr + seconds;
        if (minutes < 10) {
            timeStr = "0" + timeStr;
        }
        return timeStr;
    }


    class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_contextbar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btnEdit:
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("limit", limit);
                    intent.putExtra("rest", rest);
                    intent.putExtra("pomo", pomodoro);
                    int requestCode = 1;
                    startActivityForResult(intent, requestCode);
                    mode.finish();
                    return true;
                case R.id.btnDefault:
                    findViewById(R.id.textViewRest).setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.textViewTimer)).setTextColor(Color.parseColor("#292929"));
                    limit = 25 * 60 * 1000;
                    time=limit;
                    rest = 5 * 60 * 1000;
                    pomodoro=4;
                    ciclo=1;
                    ((TextView) findViewById(R.id.textViewdescanso)).setText("Tiempo de descanso: 05:00");
                    ((TextView) findViewById(R.id.textViewCiclo)).setText("Ciclo Pomodoro 1 de 4");

                    printTimer();
                    mode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mactionMode = null;
        }
    }

}