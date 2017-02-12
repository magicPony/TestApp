package com.example.taras.testapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.DBHelper;

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
        mDBHelper = new DBHelper(getApplication());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO
        Log.d("SERVICE_DEBUG", "hooray!!!");
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
