package ru.startandroid.onlinelibrary.di;

import dagger.Module;
import dagger.Provides;
import ru.startandroid.onlinelibrary.GoogleBooksApi;
import ru.startandroid.onlinelibrary.RxSchedulers;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenterImpl;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;

/**
 * Created by user on 23/07/2017.
 */
@Module
public class BooksSearchModule {
    private final BooksSearchView view;

    public BooksSearchModule(BooksSearchView view) {
        this.view = view;
    }
    @Provides
    @PerScreen
    public BooksSearchPresenter providesBooksSearchPresenter(GoogleBooksApi api, RxSchedulers schedulers){
        return new BooksSearchPresenterImpl(api, view, schedulers);
    }
}
