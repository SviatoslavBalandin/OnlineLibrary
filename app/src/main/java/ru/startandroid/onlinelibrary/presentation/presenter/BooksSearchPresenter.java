package ru.startandroid.onlinelibrary.presentation.presenter;

public interface BooksSearchPresenter {

   void searchBooks(String searchQuery);
   void paginateBooks(String query, int page);
   void lastRequest(String searchQuery, int page, int maxResult);
}
