package com.airoker80.javaeight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObserveOnActivity extends AppCompatActivity {
    Disposable mDisposable =null;
    TextView rx_test_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe_on);
        rx_test_test = (TextView) findViewById(R.id.rx_test_test);

        Observable<String> observable = Observable.create(dataSource())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> Log.d("ObserveOnActivity", "Complete"));

        mDisposable = observable.subscribe(s -> {
            Log.d("ObserveOnActivity", "received " + s + " on thread " + Thread.currentThread().getName());
            rx_test_test.setText(s);
        });
    }

    private ObservableOnSubscribe<String> dataSource() {
        return (emitter -> {
            Thread.sleep(800);
            emitter.onNext("Value");
            Log.d("ObserveOnActivity", "dataSource() on thread " + Thread.currentThread().getName());
            emitter.onComplete();
        });
    }
}
