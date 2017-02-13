package com.example.taras.testapp.uI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.retrofitApi.modelResponse.CategoryRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ICategoryRequest;
import com.example.taras.testapp.serviceModules.DataHandleService;
import com.example.taras.testapp.serviceModules.SyncScheduler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.taras.testapp.ApiConst.COMMAND_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CATEGORIES;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CHANNELS;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_PROGRAM;
import static com.example.taras.testapp.UtilsApi.cursorToCategoryList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tryRetrofitAndService();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        updateNecessaryData(); // TODO : replace this method
    }

    private void updateNecessaryData() {
        updateProgram();
        updateCategories();
        updateChannels();
    }

    private void updateChannels() {
        Intent intent = new Intent(this, DataHandleService.class);
        intent.putExtra(COMMAND_KEY, COMMAND_UPDATE_CHANNELS);
        startService(intent);
    }

    private void updateCategories() {
        Intent intent = new Intent(this, DataHandleService.class);
        intent.putExtra(COMMAND_KEY, COMMAND_UPDATE_CATEGORIES);
        startService(intent);
    }

    private void updateProgram() {
        Intent intent = new Intent(this, DataHandleService.class);
        intent.putExtra(COMMAND_KEY, COMMAND_UPDATE_CATEGORIES);
        startService(intent);
    }

    private void tryRetrofitAndService() {
        Intent intent = new Intent(this, DataHandleService.class);
        intent.putExtra(COMMAND_KEY, COMMAND_UPDATE_PROGRAM);
        startService(intent);
    }

    private void cancelAlarm() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.cancelAlarm(this);
    }

    private void tryService() {
        Intent intent = new Intent(this, DataHandleService.class);
        startService(intent);
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
