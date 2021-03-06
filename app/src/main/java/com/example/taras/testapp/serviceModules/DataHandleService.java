package com.example.taras.testapp.serviceModules;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taras.testapp.UtilsApi;
import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.dataStoreApi.ChannelEntry;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.dataStoreApi.ProgramsEntry;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.retrofitApi.modelResponse.CategoryRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ChannelRequestImpl;
import com.example.taras.testapp.retrofitApi.modelResponse.ICategoryRequest;
import com.example.taras.testapp.retrofitApi.modelResponse.IChannelRequest;
import com.example.taras.testapp.retrofitApi.modelResponse.IProgramRequest;
import com.example.taras.testapp.retrofitApi.modelResponse.ProgramRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;
import static com.example.taras.testapp.ApiConst.JSON_PROGRAM_KEY;
import static com.example.taras.testapp.ApiConst.TIMESTAMP_KEY;

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

    private ContentValues[] contentValues;

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!intent.hasExtra("taras")) {
            return;
        }

        Log.d("service_debug", "taras");
        updateCategories();
        updateChannels();
        int daysCount = PrefsApi.getInt(mContext, DAYS_TO_LOAD_KEY, 1);
        contentValues = new ContentValues[daysCount];

        for (int i = 0; i < daysCount; i++) {
            updateProgram(UtilsApi.getDate(i), i == daysCount - 1, i);
        }
    }

    private void updateProgram(final String date, final boolean isLastDay, final int position) {
        IProgramRequest programRequest = new ProgramRequestImpl();
        Call<List<ProgramItemModel>> requestRes = programRequest.getProgramList(date);

        requestRes.enqueue(new Callback<List<ProgramItemModel>>() {
            @Override
            public void onResponse(Call<List<ProgramItemModel>> call, Response<List<ProgramItemModel>> response) {
                TmpDataController.updateProgram(response.body(), date);

                JSONArray jsonArray = new JSONArray();

                for (ProgramItemModel programItem : response.body())
                    try {
                        jsonArray.put(programItem.toJson());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                ContentValues contentValuesProgram = new ContentValues();
                contentValuesProgram.put(JSON_PROGRAM_KEY, jsonArray.toString());
                contentValuesProgram.put(TIMESTAMP_KEY, date);
                contentValues[position] = contentValuesProgram;

                if (isLastDay) {
                    getContentResolver().bulkInsert(ProgramsEntry.CONTENT_URI, contentValues);
                    TmpDataController.notifyProgramLoaded();
                }
                Log.d("update_data", "updateProgram day=" + Integer.toString(position));
            }

            @Override
            public void onFailure(Call<List<ProgramItemModel>> call, Throwable t) {

            }
        });
    }

    private void updateChannels() {
        Log.d("update_data", "updateChannels");
        IChannelRequest channelRequest = new ChannelRequestImpl();
        Call<List<ChannelModel>> requestRes = channelRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<ChannelModel>>() {
            @Override
            public void onResponse(Call<List<ChannelModel>> call, Response<List<ChannelModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    TmpDataController.updateChannels(response.body());
                    TmpDataController.notifyChannelsLoaded();
                    getContentResolver().delete(ChannelEntry.CONTENT_URI, null, null);

                    for (ChannelModel channel : response.body()) {
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
        Log.d("update_data", "updateCategories");
        ICategoryRequest categoryRequest = new CategoryRequestImpl();
        Call<List<CategoryModel>> requestRes = categoryRequest.getCategoryList();

        requestRes.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.code() == 200 && response.body() != null) {
                    TmpDataController.updateCategories(response.body());
                    TmpDataController.notifyCategoriesLoaded();
                    getContentResolver().delete(CategoryEntry.CONTENT_URI, null, null);


                    for (CategoryModel category : response.body()) {
                        getContentResolver().insert(CategoryEntry.CONTENT_URI, category.toContentValues());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

}
