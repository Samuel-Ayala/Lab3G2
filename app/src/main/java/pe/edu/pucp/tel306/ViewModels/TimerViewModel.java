package pe.edu.pucp.tel306.ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    //private int contador = 1;

    private MutableLiveData<Integer> contadorMutable = new MutableLiveData<>();
    private int limitSeconds;
    private Thread thread = null;


    public void starttimerThread(int limit) {
        limitSeconds=limit;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int contador = limitSeconds; contador >=0; contador--) {
                    contadorMutable.postValue(contador);
                    Log.d("contador",String.valueOf(contador));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                }

            }
        });
        thread.start();
    }

    public MutableLiveData<Integer> getContadorMutable() {
        return contadorMutable;
    }

    public void setContadorMutable(MutableLiveData<Integer> contadorMutable) {
        this.contadorMutable = contadorMutable;
    }


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}

