package ru.startandroid.onlinelibrary.presentation.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.startandroid.onlinelibrary.MyApp;
import ru.startandroid.onlinelibrary.R;
import ru.startandroid.onlinelibrary.ResponseAdapter;
import ru.startandroid.onlinelibrary.di.BooksSearchModule;
import ru.startandroid.onlinelibrary.model.BoxResponse;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;

/**
 * Created by user on 23/07/2017.
 */

public class BooksSearchActivity extends Activity implements BooksSearchView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lookingButton)
    Button search;
    @BindView(R.id.barSearch)
    SearchView searchView;

    @Inject
    BooksSearchPresenter presenter;

    void resolveDependencies(){
        MyApp.getAppComponent().createBooksSearchComponent(new BooksSearchModule(this))
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        resolveDependencies();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Log.d("myLog", "Was created!");
    }

    @OnClick(R.id.lookingButton)
    void onSearchClick() {
        String query = searchView.getQuery().toString();
        presenter.searchBooks(query);
    }

    @Override
    public void showBooksSearchResults(BoxResponse searchResult) {
        ResponseAdapter adapter = new ResponseAdapter(searchResult);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showBooksSearchError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInProgress() {
        Toast.makeText(this, "In process...", Toast.LENGTH_LONG).show();
    }
}

//API key:
//AIzaSyA7K0WHGSX9iyEnMEP5U1q7v8Qre-wH4xI
//uid:
//112872464509056536949
