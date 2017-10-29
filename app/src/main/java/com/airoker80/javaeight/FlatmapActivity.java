package com.airoker80.javaeight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by airoker80 on 10/29/2017.
 */

public class FlatmapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        final String[] states = {"Lagos", "Abuja", "Imo", "Enugu"};
        Observable<String> statesObservable = Observable.fromArray(states);

        super.onCreate(savedInstanceState);

        statesObservable.flatMap(
                s -> Observable.create(getPopulation(s))
                        .subscribeOn(Schedulers.io())
        ).subscribe(pair -> Log.d("MainActivity", pair.first + " population is " + pair.second));

    }

    private ObservableOnSubscribe<Pair> getPopulation(String state) {
        return(emitter -> {
            Random r = new Random();
            Log.d("MainActivity", "getPopulation() for " + state + " called on " + Thread.currentThread().getName());
            emitter.onNext(new Pair(state, r.nextInt(300000 - 10000) + 10000));
            emitter.onComplete();
        });
    }
}
