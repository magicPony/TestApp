package com.example.taras.testapp.uI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taras.testapp.R;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.retrofitApi.modelResponse.CategoryRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ICategoryRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCategories();
    }


    public void getCategories() {
        ICategoryRequest categoryRequest = new CategoryRequestImpl();
        Call<List<CategoryModel>> requestRes = categoryRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    checkData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Log.d("BAD TARAS", "!!!");
            }
        });
    }

    private void checkData(List<CategoryModel> categories) {
        for (CategoryModel category : categories) {
            Log.d("OK_REQ", "id=" + category.getId() + " title=" + category.getTitle());
        }
    }
}
