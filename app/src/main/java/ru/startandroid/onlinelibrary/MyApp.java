package ru.startandroid.onlinelibrary;

import android.app.Application;

import ru.startandroid.onlinelibrary.di.AppComponent;
import ru.startandroid.onlinelibrary.di.NetWorkModule;
import ru.startandroid.onlinelibrary.di.DaggerAppComponent;

public class MyApp extends Application {
    private static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }
    public static AppComponent getAppComponent(){
        return appComponent;
    }

    protected AppComponent buildAppComponent(){
        return DaggerAppComponent.builder()
                .netWorkModule(new NetWorkModule())
                .build();
    }
}
