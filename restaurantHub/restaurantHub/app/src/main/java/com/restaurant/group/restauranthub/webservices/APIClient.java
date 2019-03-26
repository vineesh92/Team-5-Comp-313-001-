package com.restaurant.group.restauranthub.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restaurant.group.restauranthub.AppController;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    public static Retrofit restaurantApp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppController.WEB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }
}