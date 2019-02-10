package ru.startandroid.onlinelibrary.presentation.presenter;

import android.arch.paging.PagedList;

public interface BooksSearchPresenter {

   PagedList request (String searchQuery);
}
