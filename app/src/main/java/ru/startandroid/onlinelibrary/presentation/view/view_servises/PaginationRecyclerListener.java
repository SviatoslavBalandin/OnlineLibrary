package ru.startandroid.onlinelibrary.presentation.view.view_servises;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class PaginationRecyclerListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

            if(isLastPage()) {
                lastLoading();
                Log.e("myLog", "last loading");
            }
            else
                loadMoreItems();
    }
    //abstract methods
    protected abstract void loadMoreItems();
    protected abstract void lastLoading();
    public abstract boolean isLastPage();

}
/*if(!isLoading() && !lastPage()) {
            if((visibleItemCount + firstVisibleItemPosition)
                    >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= getTotalPageCount()){

                loadMoreItems();
            }
        }*/
