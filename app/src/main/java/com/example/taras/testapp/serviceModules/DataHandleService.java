package com.example.taras.testapp.serviceModules;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.Time;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.dataStoreApi.ChannelEntry;
import com.example.taras.testapp.dataStoreApi.ProgramsEntry;
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

import static com.example.taras.testapp.ApiConst.COMMAND_CANCEL_ALARM;
import static com.example.taras.testapp.ApiConst.COMMAND_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_NO_ACTION;
import static com.example.taras.testapp.ApiConst.COMMAND_RESET_ALARM_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CATEGORIES;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_CHANNELS;
import static com.example.taras.testapp.ApiConst.COMMAND_UPDATE_PROGRAM;
import static com.example.taras.testapp.ApiConst.DATE_KEY;
import static com.example.taras.testapp.ApiConst.JSON_PROGRAM_KEY;
import static com.example.taras.testapp.ApiConst.TIMESTAMP_KEY;
import static com.example.taras.testapp.CastUtils.intToString;

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
        if (!intent.hasExtra(COMMAND_KEY)) {
            intent.putExtra(COMMAND_KEY, COMMAND_UPDATE_PROGRAM);
            intent.putExtra(DATE_KEY, getCurrentDate());
        }

        int command = intent.getIntExtra(COMMAND_KEY, COMMAND_NO_ACTION);

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

            case COMMAND_UPDATE_PROGRAM :
                String date = "29022017";

                if (intent.hasExtra(DATE_KEY)) {
                    intent.getStringExtra(DATE_KEY);
                }

                updateProgram(date);
                break;
        }
    }

    private void updateProgram(final String date) {
        IProgramRequest programRequest = new ProgramRequestImpl();
        Call<List<ProgramItemModel>> requestRes = programRequest.getProgramList(date);

        requestRes.enqueue(new Callback<List<ProgramItemModel>>() {
            @Override
            public void onResponse(Call<List<ProgramItemModel>> call, Response<List<ProgramItemModel>> response) {
                Log.d("update_data", "onResponse");
                Cursor cursor = getContentResolver().query(ProgramsEntry.CONTENT_URI, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int timestampCol = cursor.getColumnIndex(TIMESTAMP_KEY);

                    do {
                        if (date.equals(cursor.getString(timestampCol))) {
                            Log.d("update_data", "added");
                            return;
                        }
                    } while (cursor.moveToNext());
                }

                JSONArray jsonArray = new JSONArray();

                for (ProgramItemModel programItem : response.body())
                    try {
                        jsonArray.put(programItem.toJson());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                ContentValues contentValues = new ContentValues();
                contentValues.put(JSON_PROGRAM_KEY, jsonArray.toString());
                contentValues.put(TIMESTAMP_KEY, date);
                Uri addedCount = getContentResolver().insert(ProgramsEntry.CONTENT_URI, contentValues);
                Log.d("update_data", addedCount.toString());
            }

            @Override
            public void onFailure(Call<List<ProgramItemModel>> call, Throwable t) {

            }
        });
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

    public String getCurrentDate() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int day, month, year;
        day = today.monthDay;
        month = today.month + 1;
        year = today.year;
        Log.d("current_date", intToString(day, 2) + intToString(month, 2) + intToString(year, 4));
        return intToString(day, 2) + intToString(month, 2) + intToString(year, 4);
    }
}
