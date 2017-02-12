package com.example.taras.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.DBHelper;

import static com.example.taras.testapp.ApiConst.COMMAND_CANCEL_ALARM;
import static com.example.taras.testapp.ApiConst.COMMAND_KEY;
import static com.example.taras.testapp.ApiConst.COMMAND_NO_ACTION;
import static com.example.taras.testapp.ApiConst.COMMAND_RESET_ALARM_KEY;

/**
 * Created by Taras on 11/02/2017.
 */

public class DataHandleService extends IntentService {

    private DBHelper mDBHelper;
    private Context mContext;

    public DataHandleService() {
        super("my_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplication();
        mDBHelper = new DBHelper(mContext);
        resetAlarm();
    }

    private void resetAlarm() {
        cancelAlarm();
        startAlarm();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO
        Log.d("SERVICE_DEBUG", "hooray!!!");
        int command = intent.getIntExtra(COMMAND_KEY, COMMAND_NO_ACTION);

        switch (command) {
            case COMMAND_RESET_ALARM_KEY :
                resetAlarm();
                break;

            case COMMAND_CANCEL_ALARM :
                cancelAlarm();
                break;
        }
    }

    private void startAlarm() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.setAlarm(mContext);
    }

    private void cancelAlarm() {
        SyncScheduler scheduler = new SyncScheduler();
        scheduler.cancelAlarm(mContext);
    }

    /*public ArrayList<CategoryModel> getCategories() {
        Cursor cursor = mContext.getContentResolver().query(CategoryEntry.CONTENT_URI, null, null, null, null);
        return cursorToCategoryList(cursor);
    }

    public ArrayList<ChannelModel> getChannels(IO) {
        Cursor cursor = mContext.getContentResolver().query(ChannelEntry.CONTENT_URI, null, null, null, null);
        return cursorToChannelList(cursor);
    }*/
}
