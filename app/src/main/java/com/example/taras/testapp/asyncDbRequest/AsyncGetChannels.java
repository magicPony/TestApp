package com.example.taras.testapp.asyncDbRequest;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.ChannelEntry;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.uI.IOnDataLoadedCallback;

import java.util.ArrayList;

import static com.example.taras.testapp.UtilsApi.cursorToChannelList;

/**
 * Created by Taras on 13/02/2017.
 */

public class AsyncGetChannels extends AsyncTask<Void, Void, ArrayList<ChannelModel>> {

    private IOnDataLoadedCallback mCallback;
    private Context mContext;

    public AsyncGetChannels(Context context, IOnDataLoadedCallback callback) {
        super();
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected ArrayList<ChannelModel> doInBackground(Void... voids) {
        Cursor cursor = mContext.getContentResolver().query(ChannelEntry.CONTENT_URI, null, null, null, null);
        return cursorToChannelList(cursor);
    }

    @Override
    protected void onPostExecute(ArrayList<ChannelModel> channels) {
        Log.d("AsyncGet", "onPostExecute  channels");
        super.onPostExecute(channels);
        TmpDataController.updateChannels(channels);

        if (mCallback != null) {
            mCallback.onFinish();
        }
    }
}
