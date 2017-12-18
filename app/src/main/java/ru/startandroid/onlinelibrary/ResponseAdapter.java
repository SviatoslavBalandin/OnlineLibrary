package ru.startandroid.onlinelibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.startandroid.onlinelibrary.model.BoxResponse;

/**
 * Created by Home on 30.04.2017.
 */

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder> {

    private BoxResponse response;

    public ResponseAdapter(BoxResponse response){
        this.response = response;
    }

    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        List<String> authors = response.getItems().get(position).getVolumeInfo().getAuthors();
        String title = response.getItems().get(position).getVolumeInfo().getTitle();
        String infoLink = response.getItems().get(position).getVolumeInfo().getInfoLink();
        String publishedDate = response.getItems().get(position).getVolumeInfo().getPublishedDate();

        StringBuilder listOfAuthors = new StringBuilder();
        if(authors != null) {
            for (String author : authors) {
                listOfAuthors.append(author);
                if (authors.indexOf(author) != authors.size() - 1)
                    listOfAuthors.append(", ");
            }
        }
        if(listOfAuthors.length() != 0)
            holder.authors.setText(listOfAuthors);
        else
            holder.authors.setText(publishedDate);

        holder.title.setText(title);
        holder.infoLink.setText(infoLink);
    }

    @Override
    public int getItemCount() {
        if(response.getItems() == null)
            return 0;

        return response.getItems().size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView authors;
        TextView infoLink;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.volume_title);
            authors = (TextView) itemView.findViewById(R.id.authors);
            infoLink = (TextView) itemView.findViewById(R.id.infoLink);

        }
    }
}
