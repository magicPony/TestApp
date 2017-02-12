package com.example.taras.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.dataStoreApi.ChannelEntry;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.retrofitApi.modelResponse.CategoryRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ChannelRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ICategoryRequest;
import com.example.taras.testapp.retrofitApi.modelResponse.IChannelRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.taras.testapp.ApiConst.COMMAND_CANCEL_ALARM;
import static com.example.taras.testapp.ApiConst.COMMAND_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_NO_ACTION;
import static com.example.taras.testapp.ApiConst.COMMAND_RESET_ALARM_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CATEGORIES;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CHANNELS;

/**
 * Created by Taras on 11/02/2017.
 */

public class DataHandleService extends IntentService {

    private Context mContext;

    public DataHandleService() {
        super("my_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplication();
        resetAlarm();
    }

    private void resetAlarm() {
        cancelAlarm();
        startAlarm();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SERVICE_DEBUG", "hooray!!!");
        int command = intent.getIntExtra(COMMAND_KEY, COMMAND_NO_ACTION);
        //updateCategories(); // only debug string

        switch (command) {
            case COMMAND_RESET_ALARM_KEY :
                resetAlarm();
                break;

            case COMMAND_CANCEL_ALARM :
                cancelAlarm();
                break;

            case COMMAND_UPDATE_CATEGORIES :
                updateCategories();
                break;

            case COMMAND_UPDATE_CHANNELS :
                updateChannels();
                break;
        }
    }

    private void updateChannels() {
        IChannelRequest channelRequest = new ChannelRequestImpl();
        Call<List<ChannelModel>> requestRes = channelRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<ChannelModel>>() {
            @Override
            public void onResponse(Call<List<ChannelModel>> call, Response<List<ChannelModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    getContentResolver().delete(ChannelEntry.CONTENT_URI, null, null);

                    for (ChannelModel channel : response.body()) {
                        Log.d("update_data", "id=" + channel.getId() + " name=" + channel.getName());
                        getContentResolver().insert(ChannelEntry.CONTENT_URI, channel.toContentValues());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ChannelModel>> call, Throwable t) {

            }
        });
    }

    private void startAlarm() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.setAlarm(mContext);
    }

    private void cancelAlarm() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.cancelAlarm(mContext);
    }

    private void updateCategories() {
        ICategoryRequest categoryRequest = new CategoryRequestImpl();
        Call<List<CategoryModel>> requestRes = categoryRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    getContentResolver().delete(CategoryEntry.CONTENT_URI, null, null);

                    for (CategoryModel category : response.body()) {
                        Log.d("update_data", "id=" + category.getId() + " title=" + category.getTitle());
                        getContentResolver().insert(CategoryEntry.CONTENT_URI, category.toContentValues());
                    }

                    Log.d("update_date", "data updated");
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }
}
