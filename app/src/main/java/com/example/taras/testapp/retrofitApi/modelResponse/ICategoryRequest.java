package com.example.taras.testapp.retrofitApi.modelResponse;

import com.example.taras.testapp.models.CategoryModel;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Taras on 11/02/2017.
 */

public interface ICategoryRequest {
    Call<List<CategoryModel>> getCategoryList();
}
