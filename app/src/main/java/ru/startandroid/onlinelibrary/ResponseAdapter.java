package ru.startandroid.onlinelibrary;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.startandroid.onlinelibrary.model.BoxResponse;
import ru.startandroid.onlinelibrary.model.Item;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {

    private BoxResponse firstResponse;
    private boolean initiated = false;
    private List<Item> items;

    public void init(BoxResponse response) {
        firstResponse = response;
        items = response.getItems();
        initiated = true;
    }
    public boolean isInitiated(){
        return initiated;
    }

    public void paginate(BoxResponse response) {
        if(response.getItems() != null) {
            items.addAll(response.getItems());
            //this.notifyDataSetChanged();

        }
    }
    public long getTotalItemCount(){
        long res = 0;
        try{
            res = firstResponse.getTotalItems();
        }catch (NullPointerException e) {
            e.fillInStackTrace();
        }
        return res;
    }

    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (initiated) {
            List<String> authors = items.get(position).getVolumeInfo().getAuthors();
            String title = items.get(position).getVolumeInfo().getTitle();
            String infoLink = items.get(position).getVolumeInfo().getInfoLink();
            String publishedDate = items.get(position).getVolumeInfo().getPublishedDate();

            StringBuilder listOfAuthors = new StringBuilder();
            if (authors != null) {
                for (String author : authors) {
                    listOfAuthors.append(author);
                    if (authors.indexOf(author) != authors.size() - 1)
                        listOfAuthors.append(", ");
                }
            }
            if (listOfAuthors.length() != 0)
                holder.authors.setText(listOfAuthors);
            else
                holder.authors.setText(publishedDate);

            holder.title.setText(title);
            holder.infoLink.setText(infoLink);
        }
    }

    @Override
    public int getItemCount() {
        return (items != null && initiated) ? items.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.volume_title)
        TextView title;
        @BindView(R.id.authors)
        TextView authors;
        @BindView(R.id.infoLink)
        TextView infoLink;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
