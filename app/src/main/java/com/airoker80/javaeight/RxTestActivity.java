package com.airoker80.javaeight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxTestActivity extends AppCompatActivity {

    String states[] = {"Bagmati","Gandaki","JanakPur","Bla !"};
    private Disposable  disposable= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_test);

        Observable<String> observable = Observable.create(dataSource())
                .subscribeOn(Schedulers.newThread())
                .doOnComplete(() -> Log.d("MainActivity", "Complete"));

        disposable = observable.subscribe(s -> {
            Log.d("MainActivity", "received " + s + " on thread " + Thread.currentThread().getName());
        });
    }

    private ObservableOnSubscribe<String> dataSource() {
        return(emitter -> {
            for(String state : states) {
                emitter.onNext(state);
                Log.d("MainActivity", "emitting " + state + " on thread " + Thread.currentThread().getName());
                Thread.sleep(600);
            }
            emitter.onComplete();
        });
    }
    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }
}
