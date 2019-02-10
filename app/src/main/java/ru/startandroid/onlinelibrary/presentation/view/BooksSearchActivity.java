package ru.startandroid.onlinelibrary.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.startandroid.onlinelibrary.MyApp;
import ru.startandroid.onlinelibrary.R;
import ru.startandroid.onlinelibrary.di.BooksSearchModule;
import ru.startandroid.onlinelibrary.model.POJOs.BoxResponse;
import ru.startandroid.onlinelibrary.presentation.view.search_view_services.ResponseAdapter;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;
import ru.startandroid.onlinelibrary.presentation.view.search_view_services.SearchDiffUtilCallback;

public class BooksSearchActivity extends Activity implements BooksSearchView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swipeListLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lookingButton)
    Button btnSearch;
    @BindView(R.id.barSearch)
    EditText searchView;

    @Inject
    BooksSearchPresenter presenter;

    private ResponseAdapter adapter;

    void resolveDependencies(){
        MyApp.getAppComponent().createBooksSearchComponent(new BooksSearchModule(this))
                .inject(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment_layout);
        ButterKnife.bind(this);
        swipeLayout.setOnRefreshListener(this);
        resolveDependencies();
        adapter = new ResponseAdapter(new SearchDiffUtilCallback());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setOnKeyListener(searchView);
    }

    @OnClick(R.id.lookingButton)
    void onSearchClick() {
        String query = searchView.getText().toString().trim();
        if(!query.isEmpty()) {
            adapter.submitList(presenter.request(query));
            recyclerView.setAdapter(adapter);
            hideKeyboard();
        }
    }

    @Override
    public int getMainListSize() {
        return adapter.getCurrentList().size();
    }

    @Override
    public void showBooksSearchError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInProgress() {
        Toast.makeText(this, "In process...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNothingFound() {
        Toast.makeText(this, "Nothing found", Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard(){
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager inMan = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                inMan.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }catch (NullPointerException e) {
                e.fillInStackTrace();
            }
        }
    }
    private void setOnKeyListener(EditText searchView) {
        searchView.setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)
                    btnSearch.performClick();
            }
            return false;
        });
    }

    @Override
    public void onRefresh() {
        btnSearch.performClick();
        swipeLayout.setRefreshing(false);
    }
}

//API key:
//AIzaSyA7K0WHGSX9iyEnMEP5U1q7v8Qre-wH4xI
//uid:
//112872464509056536949
