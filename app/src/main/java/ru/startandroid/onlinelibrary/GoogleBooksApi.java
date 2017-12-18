package ru.startandroid.onlinelibrary;


import ru.startandroid.onlinelibrary.model.BoxResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GoogleBooksApi {
    @GET("/books/v1/volumes")
    Observable<BoxResponse> getData(@Query("q")String q);
}
