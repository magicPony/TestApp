package com.example.taras.testapp.retrofitApi;

import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Taras on 11/02/2017.
 */

public interface IApi {

    @GET("categories")
    Call<List<CategoryModel>> getCategoryList();

    @GET("chanels")
    Call<List<ChannelModel>> getChannelList();

    // TODO
}
