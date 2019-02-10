package ru.startandroid.onlinelibrary.presentation.view.search_view_services;

import android.support.v7.util.DiffUtil;

import ru.startandroid.onlinelibrary.model.POJOs.Item;

public class SearchDiffUtilCallback extends DiffUtil.ItemCallback<Item>{

    @Override
    public boolean areItemsTheSame(Item oldItem, Item newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(Item oldItem, Item newItem) {
        return oldItem.getSelfLink().equals(newItem.getSelfLink());
    }
}
