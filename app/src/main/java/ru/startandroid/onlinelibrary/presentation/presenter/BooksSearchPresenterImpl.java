package ru.startandroid.onlinelibrary.presentation.presenter;

import android.annotation.SuppressLint;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;
import android.arch.paging.PositionalDataSource;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.startandroid.onlinelibrary.model.POJOs.BoxResponse;
import ru.startandroid.onlinelibrary.model.POJOs.Item;
import ru.startandroid.onlinelibrary.model.service.GoogleBooksApi;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;


public class BooksSearchPresenterImpl implements BooksSearchPresenter{

    private final GoogleBooksApi booksApi;
    private final BooksSearchView view;
    private final BoxResponse stub;

    private static final int PAGE_SIZE = 10;

    public BooksSearchPresenterImpl(GoogleBooksApi booksApi, BooksSearchView view) {
        this.booksApi = booksApi;
        this.view = view;
        stub = new BoxResponse();
        stub.setItems(Collections.emptyList());
    }

    @Override
    public PagedList request (String searchQuery){

        return getPageList(new SearchPageKeyedDataSource(searchQuery), getListConfigurations());
    }

    private PagedList.Config getListConfigurations() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();
    }

    private PagedList getPageList(DataSource dataSource, PagedList.Config config) {
        return new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setNotifyExecutor(new MainThreadExecutor())
                .build();
    }

    private class SearchPageKeyedDataSource extends PositionalDataSource<Item> {

        private String searchQuery;
        private int startPage = 0;

        SearchPageKeyedDataSource(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @SuppressLint("CheckResult")
        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Item> callback) {

            view.showInProgress();

            booksApi.getData(searchQuery)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturnItem(stub)
                    .subscribe(
                            boxResponse -> {
                                if(boxResponse.getTotalItems() == 0) {
                                    view.showNothingFound();
                                    callback.onResult(stub.getItems(), startPage);
                                }
                                else
                                    callback.onResult(boxResponse.getItems(), startPage);},
                            error -> view.showBooksSearchError("An error occur during networking"));

        }
        @SuppressLint("CheckResult")
        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Item> callback) {

            startPage += PAGE_SIZE;

            booksApi.paginateData(searchQuery, startPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturnItem(stub)
                    .subscribe(
                            boxResponse -> {
                                long itemsCount = boxResponse.getTotalItems();

                                if(itemsCount != 0 && itemsCount > view.getMainListSize()) {
                                    callback.onResult(boxResponse.getItems());
                                }
                                else
                                    callback.onResult(stub.getItems());
                                },
                            error ->
                                    view.showBooksSearchError("An error occur during networking"));
        }
    }

    private class MainThreadExecutor implements Executor {
        private final Handler h = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            h.post(command);
        }
    }

}
