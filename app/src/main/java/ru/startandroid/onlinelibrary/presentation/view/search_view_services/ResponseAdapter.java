package ru.startandroid.onlinelibrary.presentation.view.search_view_services;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.startandroid.onlinelibrary.R;
import ru.startandroid.onlinelibrary.model.POJOs.Item;

public class ResponseAdapter extends PagedListAdapter<Item, ResponseAdapter.ViewHolder> {


    public ResponseAdapter(DiffUtil.ItemCallback<Item> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseAdapter.ViewHolder holder, int position) {
       holder.bindItem(getItem(position), position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.volume_title)
        TextView titleView;
        @BindView(R.id.authors)
        TextView authorsView;
        @BindView(R.id.infoLink)
        TextView infoLinkView;
        @BindView(R.id.countNumber)
        TextView countNumber;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(Item item, int position){
            if (item.getVolumeInfo() != null) {
                List<String> authors = item.getVolumeInfo().getAuthors();
                String title = item.getVolumeInfo().getTitle();
                String infoLink = item.getVolumeInfo().getInfoLink();
                String publishedDate = item.getVolumeInfo().getPublishedDate();

                StringBuilder listOfAuthors = new StringBuilder();
                if (authors != null) {
                    for (String author : authors) {
                        listOfAuthors.append(author);
                        if (authors.indexOf(author) != authors.size() - 1)
                            listOfAuthors.append(", ");
                    }
                }
                if (listOfAuthors.length() != 0)
                    authorsView.setText(listOfAuthors);
                else
                    authorsView.setText(publishedDate);

                titleView.setText(title);
                infoLinkView.setText(infoLink);
                countNumber.setText(String.valueOf(position + 1));
            }
        }
    }
}
