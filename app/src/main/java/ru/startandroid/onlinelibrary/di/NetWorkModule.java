package ru.startandroid.onlinelibrary.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.startandroid.onlinelibrary.model.service.GoogleBooksApi;
import ru.startandroid.onlinelibrary.model.service.WebAuthManager;

@Module
public class NetWorkModule {

    @Provides
    @Singleton
    OkHttpClient provideApiClient() {
        OkHttpClient client = new OkHttpClient();
        WebAuthManager manager = new WebAuthManager(client);
        return manager.createApiClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){

        return  new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.googleapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    GoogleBooksApi provideBooksApi(Retrofit retrofit){
        return retrofit.create(GoogleBooksApi.class);
    }
}
