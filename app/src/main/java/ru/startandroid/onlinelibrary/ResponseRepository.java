package ru.startandroid.onlinelibrary;

import java.util.List;

import ru.startandroid.onlinelibrary.model.POJOs.Item;

public interface ResponseRepository {

    public void save(Item item);

    public List<Item> getAll();

    public Item findById(String id);

}
