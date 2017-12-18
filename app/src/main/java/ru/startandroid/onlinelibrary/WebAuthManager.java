package ru.startandroid.onlinelibrary;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by user on 13/07/2017.
 */

public class WebAuthManager {

    private OkHttpClient okHttpClient;

   public WebAuthManager(OkHttpClient client){
        okHttpClient = client;
    }
    public OkHttpClient createApiClient() {
        OkHttpClient.Builder apiClient = okHttpClient.newBuilder();

        apiClient.addInterceptor(chain -> {

            Request request = chain.request();
            HttpUrl httpUrl = request.url();
            httpUrl.newBuilder()
                    .addQueryParameter("api_key", "AIzaSyA7K0WHGSX9iyEnMEP5U1q7v8Qre-wH4xI")
                    .build();

            return chain.proceed(request);
        });
        return apiClient.build();
    }
}
