
package ru.startandroid.onlinelibrary.model.POJOs;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoxResponse {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("totalItems")
    @Expose
    private Long totalItems;
    @SerializedName("items")
    @Expose
    private List<Item> items;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
