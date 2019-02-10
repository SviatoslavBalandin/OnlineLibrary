package ru.startandroid.onlinelibrary.presentation.view;

public interface BooksSearchView {

    int getMainListSize();

    void showBooksSearchError(String error);

    void showInProgress();

    void showNothingFound();
}
