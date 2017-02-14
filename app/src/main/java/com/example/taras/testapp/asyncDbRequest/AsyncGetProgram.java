package com.example.taras.testapp.asyncDbRequest;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.taras.testapp.PrefsApi;
import com.example.taras.testapp.dataStoreApi.ProgramsEntry;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.uI.IOnDataLoadedCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;
import static com.example.taras.testapp.UtilsApi.cursorToProgram;
import static com.example.taras.testapp.UtilsApi.getDate;

/**
 * Created by Taras on 13/02/2017.
 */

public class AsyncGetProgram extends AsyncTask<Void, Void, Map<String, ArrayList<ProgramItemModel>>> {

    private Context mContext;
    private IOnDataLoadedCallback mCallback;

    public AsyncGetProgram(Context context, IOnDataLoadedCallback callback) {
        super();
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected Map<String, ArrayList<ProgramItemModel>> doInBackground(Void... voids) {
        int daysCount = PrefsApi.getInt(mContext, DAYS_TO_LOAD_KEY, 1);
        Map<String, ArrayList<ProgramItemModel>> programs = new HashMap<>();

        for (int i = 0; i < daysCount; i++) {
            String date = getDate(i);
            Uri contentUri = ProgramsEntry.CONTENT_URI.buildUpon().appendPath(date).build();
            Log.d("debug_uri", contentUri.toString());
            Cursor cursor = mContext.getContentResolver().query(contentUri, null, null, null, null);
            try {
                programs.put(date, cursorToProgram(cursor));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return programs;
    }

    @Override
    protected void onPostExecute(Map<String, ArrayList<ProgramItemModel>> programs) {
        super.onPostExecute(programs);

        for (Map.Entry<String, ArrayList<ProgramItemModel>> program : programs.entrySet()) {
            TmpDataController.updateProgram(program.getValue(), program.getKey());
        }

        if (mCallback != null) {
            mCallback.onFinish();
        }
    }
}
