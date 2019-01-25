package ru.startandroid.onlinelibrary.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import ru.startandroid.onlinelibrary.ResponseAdapter;
import ru.startandroid.onlinelibrary.di.BooksSearchModule;
import ru.startandroid.onlinelibrary.model.BoxResponse;
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;
import ru.startandroid.onlinelibrary.presentation.view.view_servises.PaginationRecyclerListener;

public class BooksSearchActivity extends Activity implements BooksSearchView{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lookingButton)
    Button btnSearch;
    @BindView(R.id.barSearch)
    EditText searchView;
    @Inject
    BooksSearchPresenter presenter;

    private ResponseAdapter adapter;
    private LinearLayoutManager manager;
    private final int TEN = 10;
    private String previousQuery = "";

    void resolveDependencies(){
        MyApp.getAppComponent().createBooksSearchComponent(new BooksSearchModule(this))
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment_layout);
        ButterKnife.bind(this);
        resolveDependencies();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        setOnKeyListener(searchView);
    }
    @OnClick(R.id.lookingButton)
    void onSearchClick() {
        String query = searchView.getText().toString().trim();
        Log.e("myLog", "previousQuery = " + previousQuery);
        if(!query.equalsIgnoreCase(previousQuery)) {
            previousQuery = query;
            setPaginationListener(recyclerView);
            adapter = new ResponseAdapter();
            presenter.searchBooks(query);
            resetPageNumber();
        }
        hideKeyboard();
        Log.e("myLog", "page = " + page);

    }

    @Override
    public void showBooksSearchResults(BoxResponse searchResult) {
        if(!adapter.isInitiated() && searchResult.getItems() != null)
            adapter.init(searchResult);
        else if(adapter.isInitiated()) {
            manager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
            adapter.paginate(searchResult);
        }
        recyclerView.setAdapter(adapter);
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
    private int page = TEN;

    private void resetPageNumber(){
        page = TEN;
    }

    private void    setPaginationListener(RecyclerView view) {
        view.addOnScrollListener(new PaginationRecyclerListener() {


            @Override
            protected void loadMoreItems() {
                Log.e("myLog", "total adapter item count = " + adapter.getTotalItemCount());
                Log.e("myLog", "adapter item count = " + adapter.getItemCount());
                presenter.paginateBooks(searchView.getText().toString().trim(), page);
                page += TEN;
                Log.e("myLog", "PAGE = " + page);
            }

            @Override
            protected void lastLoading() {
                int amount = (int) (adapter.getTotalItemCount() - manager.getItemCount());
                Log.e("myLog", "last amount = " + amount);
                Log.e("myLog", " last PAGE = " + page);
                Log.e("myLog", "total adapter item count2 = " + adapter.getTotalItemCount());
                Log.e("myLog", "adapter item count2 = " + adapter.getItemCount());
                presenter.lastRequest(searchView.getText().toString().trim(), page, amount);
                //view.removeOnScrollListener(this);
            }

            @Override
            protected boolean itsTimeToLoadMore() {
                return (manager.findLastVisibleItemPosition() == (adapter.getItemCount() - 3));
            }

            @Override
            public boolean isLastPage() {
                return adapter.getTotalItemCount() - manager.getItemCount() <= TEN;
            }


        });
    }
}

//API key:
//AIzaSyA7K0WHGSX9iyEnMEP5U1q7v8Qre-wH4xI
//uid:
//112872464509056536949
