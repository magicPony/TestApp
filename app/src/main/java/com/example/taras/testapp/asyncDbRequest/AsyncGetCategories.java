package com.example.taras.testapp.asyncDbRequest;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.taras.testapp.dataStoreApi.CategoryEntry;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.uI.IOnDataLoadedCallback;

import java.util.ArrayList;

import static com.example.taras.testapp.UtilsApi.cursorToCategoryList;

/**
 * Created by Taras on 13/02/2017.
 */

public class AsyncGetCategories extends AsyncTask<Void, Void, ArrayList<CategoryModel>> {

    private Context mContext;
    private IOnDataLoadedCallback mCallback;

    public AsyncGetCategories(Context context, IOnDataLoadedCallback callback) {
        super();
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected ArrayList<CategoryModel> doInBackground(Void... voids) {
        Cursor cursor = mContext.getContentResolver().query(CategoryEntry.CONTENT_URI, null, null, null, null);
        return cursorToCategoryList(cursor);
    }

    @Override
    protected void onPostExecute(ArrayList<CategoryModel> categories) {
        Log.d("AsyncGet", "onPostExecute  categories");
        super.onPostExecute(categories);
        TmpDataController.updateCategories(categories);

        if (mCallback != null) {
            mCallback.onFinish();
        }
    }
}
