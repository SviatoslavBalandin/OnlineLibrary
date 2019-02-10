package ru.startandroid.onlinelibrary.model.service;


import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.startandroid.onlinelibrary.model.POJOs.BoxResponse;

public interface GoogleBooksApi {
    @GET("/books/v1/volumes")
    Flowable<BoxResponse> getData(@Query("q")String q);

    @GET("/books/v1/volumes")
    Flowable<BoxResponse> paginateData(@Query("q")String q, @Query("startIndex") int startIndex);

    @GET("/books/v1/volumes")
    Flowable<BoxResponse> lastData(@Query("q")String q, @Query("startIndex") int startIndex, @Query("maxResults") int maxResults);



}
// for pagination add {page} to GET query
// and add @Path("page") int page as a  parameter in getData method