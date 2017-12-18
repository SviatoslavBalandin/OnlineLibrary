package ru.startandroid.onlinelibrary.presentation.view;

import ru.startandroid.onlinelibrary.model.BoxResponse;

/**
 * Created by user on 23/07/2017.
 */

public interface BooksSearchView {

    void showBooksSearchResults(BoxResponse searchResult);

    void showBooksSearchError(String error);

    void showInProgress();
}
