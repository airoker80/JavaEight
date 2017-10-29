package com.airoker80.javaeight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    TextView hellotext ;
    Observable<Integer>  observable ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        observable =Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onNext(4);
            e.onComplete();
        });
        hellotext = (TextView) findViewById(R.id.hellotext);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Toast.makeText(MainActivity.this, "Hello Java 8", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Integer integer) {

                try {
                    Thread.sleep(500);
                    Log.d("OnNexat", String.valueOf(integer));
                    hellotext.setText(String.valueOf(integer));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };


        hellotext.setOnClickListener(view -> {
//            Toast.makeText(this, "Hello Java 8", Toast.LENGTH_SHORT).show();
            observable.subscribe(observer);
        });

    }
}
