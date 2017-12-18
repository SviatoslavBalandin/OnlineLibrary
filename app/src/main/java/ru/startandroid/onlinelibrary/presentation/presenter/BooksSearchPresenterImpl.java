package ru.startandroid.onlinelibrary.presentation.presenter;

import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.startandroid.onlinelibrary.GoogleBooksApi;
import ru.startandroid.onlinelibrary.ResponseAdapter;
import ru.startandroid.onlinelibrary.RxSchedulers;
import ru.startandroid.onlinelibrary.model.BoxResponse;
import ru.startandroid.onlinelibrary.presentation.view.BooksSearchView;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by user on 23/07/2017.
 */

public class BooksSearchPresenterImpl implements BooksSearchPresenter{

    private final GoogleBooksApi booksApi;
    private final BooksSearchView view;
    private final RxSchedulers schedulers;

    public BooksSearchPresenterImpl(GoogleBooksApi booksApi, BooksSearchView view, RxSchedulers schedulers) {
        this.booksApi = booksApi;
        this.view = view;
        this.schedulers = schedulers;
    }

    @Override
    public void searchBooks(String searchQuery) {
        Log.d("myLog", "Start search");
        if (!searchQuery.isEmpty()) {
            view.showInProgress();
            booksApi.getData(searchQuery)
                    .subscribeOn(schedulers.getIO())
                    .observeOn(schedulers.getUI())
                    .subscribe(boxResponse -> {
                        view.showBooksSearchResults(boxResponse);
                        Log.d("myLog", "About " + boxResponse.getTotalItems() + " results");
                    }, error -> {
                        view.showBooksSearchError("An error occur during networking");
                        Log.e("myLog", "An error occur during networking", error);
                    });
        }
    }
}
