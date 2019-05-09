package ru.startandroid.onlinelibrary.presentation.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ru.startandroid.onlinelibrary.presentation.presenter.BooksSearchPresenter;
import ru.startandroid.onlinelibrary.presentation.view.search_view_services.ResponseAdapter;
import ru.startandroid.onlinelibrary.presentation.view.search_view_services.SearchDiffUtilCallback;
import ru.startandroid.onlinelibrary.presentation.view.search_view_services.SearchViewModel;

public class BooksSearchActivity extends AppCompatActivity implements BooksSearchView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lookingButton)
    Button btnSearch;
    @BindView(R.id.barSearch)
    EditText searchView;

    @Inject
    BooksSearchPresenter presenter;

    private ResponseAdapter adapter;
    private static final String QUERY_KEY = "q11846";
    private static final String KEY_RECYCLER_STATE = "recycler_state_1";
    private String query;
    private static Bundle recyclerViewState;
    private SearchViewModel model;

    void resolveDependencies() {
        MyApp.getAppComponent().createBooksSearchComponent(new BooksSearchModule(this))
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_fragment_layout);
        ButterKnife.bind(this);
        if (savedInstanceState != null) query = (String) savedInstanceState.get(QUERY_KEY);
        resolveDependencies();
        model = ViewModelProviders.of(this).get(SearchViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResponseAdapter(new SearchDiffUtilCallback());
        if (model.listWasSet()) {
            adapter.submitList(model.getSavedList());
            recyclerView.setAdapter(adapter);
        }
        setOnKeyListener(searchView);
    }

    @OnClick(R.id.lookingButton)
    void onSearchClick() {
        query = searchView.getText().toString().trim();

        if (!query.isEmpty()) {
            Bundle pair = new Bundle();
            pair.putString(QUERY_KEY, query);
            this.onSaveInstanceState(pair);
            model.setList(presenter.request(query));
            adapter.submitList(model.getSavedList());
            recyclerView.setAdapter(adapter);
            hideKeyboard();
        }
    }

    @Override
    public int getMainListSize() {
        return (adapter != null && adapter.getCurrentList() != null) ? adapter.getCurrentList().size() : 0;
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

    private void hideKeyboard() {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager inMan = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inMan != null) {
                inMan.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }

    private void setOnKeyListener(EditText searchView) {
        searchView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)
                    btnSearch.performClick();
            }
            return false;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        recyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerViewState != null) {
            Parcelable listState = recyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}

//API key:
//AIzaSyA7K0WHGSX9iyEnMEP5U1q7v8Qre-wH4xI
//uid:
//112872464509056536949
