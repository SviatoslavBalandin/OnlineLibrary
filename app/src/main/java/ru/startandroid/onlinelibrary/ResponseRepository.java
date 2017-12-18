package ru.startandroid.onlinelibrary;

import java.util.List;

import ru.startandroid.onlinelibrary.model.Item;

/**
 * Created by Home on 18.05.2017.
 */

public interface ResponseRepository {

    public void save(Item item);

    public List<Item> getAll();

    public Item findById(String id);

}
