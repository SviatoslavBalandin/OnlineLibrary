package ru.startandroid.onlinelibrary.di;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.onlinelibrary.model.service.GoogleBooksApi;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenterImpl;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;

@Module
public class BooksSearchModule {

    private final BooksSearchView view;

    public BooksSearchModule(BooksSearchView view) {
        this.view = view;
    }

    @PerScreen
    @Provides
    BooksSearchPresenter providesBooksSearchPresenter(GoogleBooksApi api){
        return new BooksSearchPresenterImpl(api, view);
    }

}
