package ru.startandroid.onlinelibrary.presentation.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.startandroid.onlinelibrary.GoogleBooksApi;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;


public class BooksSearchPresenterImpl implements BooksSearchPresenter{

    private final GoogleBooksApi booksApi;
    private final BooksSearchView view;

    public BooksSearchPresenterImpl(GoogleBooksApi booksApi, BooksSearchView view) {
        this.booksApi = booksApi;
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void searchBooks(String searchQuery) {
        Log.d("myLog", "Start search");
        if (!searchQuery.isEmpty()) {
            view.showInProgress();
            booksApi.getData(searchQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::showBooksSearchResults,
                            error -> {
                        view.showBooksSearchError("An error occur during networking");
                        Log.e("myLog", error.getMessage(), error);
                    });

        }
    }
    @SuppressLint("CheckResult")
    @Override
    public void paginateBooks(String searchQuery, int page) {
        if (!searchQuery.isEmpty()) {
            booksApi.paginateData(searchQuery, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::showBooksSearchResults,
                            error -> Log.e("myLog", error.getMessage(), error));

        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void lastRequest(String searchQuery, int page, int maxResult) {
        if (!searchQuery.isEmpty()) {
            Log.e("myLog", "last request started");
            booksApi.lastData(searchQuery, page, maxResult)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(view::showBooksSearchResults,
                            error -> Log.e("myLog", error.getMessage(), error));

        }
    }

}
