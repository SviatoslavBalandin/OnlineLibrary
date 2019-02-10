package ru.startandroid.onlinelibrary.di;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = { NetWorkModule.class})
@Singleton
public interface AppComponent {
    BooksSearchComponent createBooksSearchComponent(BooksSearchModule module);
}
