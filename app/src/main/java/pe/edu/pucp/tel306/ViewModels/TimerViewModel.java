package pe.edu.pucp.tel306.ViewModels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    //private int contador = 1;

    private MutableLiveData<Long> contadorMutable = new MutableLiveData<>();
    private long limitMiliseconds;
    private Thread thread = null;


    public void starttimerThread(long limit) {
        limitMiliseconds = limit;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (long contador = limitMiliseconds; contador >= 0; contador--) {
                    contadorMutable.postValue(contador);
                    try{
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        break;
                    }
                }
            }
        });
        thread.start();
    }

    public void stoptimerThread() {
        thread.interrupt();
    }

    public MutableLiveData<Long> getContadorMutable() {
        return contadorMutable;
    }

    public void setContadorMutable(MutableLiveData<Long> contadorMutable) {
        this.contadorMutable = contadorMutable;
    }


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}

