package com.example.taras.testapp.retrofitApi;

import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Taras on 11/02/2017.
 */

public interface IApi {

    @GET("categories")
    Call<List<CategoryModel>> getCategoryList();

    @GET("chanels")
    Call<List<ChannelModel>> getChannelList();

    @GET("programs/{DATE}")
    Call<List<ProgramItemModel>> getProgram(@Path("DATE") String date);

}
