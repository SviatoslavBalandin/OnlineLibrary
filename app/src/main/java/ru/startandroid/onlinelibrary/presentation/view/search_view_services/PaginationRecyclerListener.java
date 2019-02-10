package ru.startandroid.onlinelibrary.presentation.view.search_view_services;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class PaginationRecyclerListener extends RecyclerView.OnScrollListener {

    private int pageSize;
    private LinearLayoutManager manager;

    protected PaginationRecyclerListener(LinearLayoutManager manager, int pageSize) {
        super();
        this.manager = manager;
        this.pageSize = pageSize;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();

        if(!isLoading() && !isLastPage()) {
            if((visibleItemCount + firstVisibleItemPosition)
                    >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= pageSize){
                loadMoreItems();

            }
        }
    }
    //abstract methods
    protected abstract void loadMoreItems();
    public abstract boolean isLoading();
    public abstract boolean isLastPage();

}