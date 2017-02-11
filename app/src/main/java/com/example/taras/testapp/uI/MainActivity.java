package com.example.taras.testapp.uI;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taras.testapp.CastUtils;
import com.example.taras.testapp.R;
import com.example.taras.testapp.models.Category;
import com.example.taras.testapp.serverRequestApi.AsyncRequest;
import com.example.taras.testapp.serverRequestApi.IOnPostExecute;

import java.util.ArrayList;

import static com.example.taras.testapp.ApiConst.CATEGORIES_LINK;
import static com.example.taras.testapp.ApiConst.CATEGORIES_URI;
import static com.example.taras.testapp.ApiConst.ID_KEY;
import static com.example.taras.testapp.ApiConst.PICTURE_URL_KEY;
import static com.example.taras.testapp.ApiConst.TITLE_KEY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // debug
        tryRequest();
        tryGetRequest();
    }

    private void tryGetRequest() {
        Cursor cursor = getContentResolver().query(CATEGORIES_URI, new String[]{ID_KEY + " = 23"}, null, null, null);
        Category category = new Category(cursor);
        Log.d("name_505", "id=" + category.getId() + " title=" + category.getTitle() + " pic=" + category.getPictureUrl());
        /*
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAME, KEY_NUMBER}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
         */
    }

    private void tryRequest() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_KEY, "23");
        contentValues.put(TITLE_KEY, "title_taras");
        contentValues.put(PICTURE_URL_KEY, "http://taras");
        getContentResolver().insert(CATEGORIES_URI, contentValues);
    }

    private void tryAsyncRequest() {
        AsyncRequest asyncRequest = new AsyncRequest(new IOnPostExecute() {
            @Override
            public void onSuccess(String response) {
                ArrayList<Category> categories = CastUtils.jsonToCategoryList(response);

                for (Category category : categories) {
                    Log.d("TARAS", "id=" + category.getId() + " title=" + category.getTitle() + " pic=" + category.getPictureUrl());
                    Log.d("taras_debug", getContentResolver().insert(CATEGORIES_URI, category.toContentValues()).toString());
                }

                Cursor cursor = getContentResolver().query(CATEGORIES_URI, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idCol, titleCol, picCol;
                    idCol = cursor.getColumnIndex(ID_KEY);
                    titleCol = cursor.getColumnIndex(TITLE_KEY);
                    picCol = cursor.getColumnIndex(PICTURE_URL_KEY);

                    do {
                        int id;
                        String title, pictureUrl;
                        id = cursor.getInt(idCol);
                        Log.d("taras_debug", "id=" + Integer.toString(id));
                        title = cursor.getString(titleCol);
                        Log.d("taras_debug", "title=" + title);
                        pictureUrl = cursor.getString(picCol);
                        Log.d("taras_debug", "picture==" + pictureUrl);
                    } while (cursor.moveToNext());
                }

                /*categories = cursorToCategoryList(cursor);

                for (Category category : categories) {
                    Log.d("TARAS", "id=" + category.getId() + " title=" + category.getTitle());
                }*/
            }
        });

        asyncRequest.execute(CATEGORIES_LINK);
    }
}
