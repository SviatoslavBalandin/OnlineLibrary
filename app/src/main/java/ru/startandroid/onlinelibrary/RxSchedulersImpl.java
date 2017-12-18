package ru.startandroid.onlinelibrary;

import javax.inject.Singleton;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 29/07/2017.
 */
@Singleton
public class RxSchedulersImpl implements RxSchedulers {

    @Override
    public Scheduler getIO() {
        return Schedulers.io();
    }

    @Override
    public Scheduler getComputation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler getUI() {
        return AndroidSchedulers.mainThread();
    }
}
