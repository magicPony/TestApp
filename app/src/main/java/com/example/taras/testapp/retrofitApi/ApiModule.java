package com.example.taras.testapp.retrofitApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Taras on 11/02/2017.
 */

public class ApiModule {

    public static IApi getApiInterface() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://52.50.138.211:8080/ChanelAPI/")
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build().create(IApi.class);
    }
}
