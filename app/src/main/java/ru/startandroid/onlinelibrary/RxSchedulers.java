package ru.startandroid.onlinelibrary;

import rx.Scheduler;

/**
 * Created by user on 29/07/2017.
 */

public interface RxSchedulers {
    Scheduler getIO();
    Scheduler getComputation();
    Scheduler getUI();
}
