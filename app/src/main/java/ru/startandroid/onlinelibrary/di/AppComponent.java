package ru.startandroid.onlinelibrary.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Home on 04.06.2017.
 */


@Component(modules = { NetWorkModule.class, AppModule.class})
@Singleton
public interface AppComponent {

    BooksSearchComponent createBooksSearchComponent(BooksSearchModule module);

}
