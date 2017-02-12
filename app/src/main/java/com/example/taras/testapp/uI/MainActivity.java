package com.example.taras.testapp.uI;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taras.testapp.R;
import com.example.taras.testapp.SyncScheduler;
import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.retrofitApi.modelResponse.CategoryRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ICategoryRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.taras.testapp.CastUtils.cursorToCategoryList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tryScheduler();
        //tryDatabaseIO();
    }

    private void tryScheduler() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.setAlarm(this);
    }

    private void tryDatabaseIO() {
        ICategoryRequest categoryRequest = new CategoryRequestImpl();
        Call<List<CategoryModel>> requestRes = categoryRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    saveData(response.body());
                    checkData();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    private void checkData() {
        Cursor cursor = getContentResolver().query(CategoryEntry.CONTENT_URI, null, null, null, null);
        ArrayList<CategoryModel> categories = cursorToCategoryList(cursor);

        for (CategoryModel category : categories) {
            Log.d("CHECK_DATA", "id=" + category.getId() + " title=" + category.getTitle());
        }
    }

    private void saveData(List<CategoryModel> categories) {
        for (CategoryModel category : categories) {
            getContentResolver().insert(CategoryEntry.CONTENT_URI, category.toContentValues());
        }
    }
}
