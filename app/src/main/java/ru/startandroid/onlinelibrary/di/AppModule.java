package ru.startandroid.onlinelibrary.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.onlinelibrary.RxSchedulers;
import ru.startandroid.onlinelibrary.RxSchedulersImpl;

/**
 * Created by user on 29/07/2017.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    RxSchedulers provideRxSchedulers(){
        return new RxSchedulersImpl();
    }
}
