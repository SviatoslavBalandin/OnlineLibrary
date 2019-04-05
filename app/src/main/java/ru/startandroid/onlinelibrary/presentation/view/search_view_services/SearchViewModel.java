package ru.startandroid.onlinelibrary.presentation.view.search_view_services;

import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import ru.startandroid.onlinelibrary.model.POJOs.Item;

public class SearchViewModel extends ViewModel {

    private PagedList<Item> list;

    public PagedList<Item>  getSavedList() {
      return list;
    }
    public void setList(PagedList<Item>  list) {
        this.list = list;
    }

    public boolean listWasSet() {
        return list != null;
    }
}
