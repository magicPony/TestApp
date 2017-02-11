package com.example.taras.testapp.dataStoreApi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.taras.testapp.ApiConst.CATEGORY_ID_KEY;
import static com.example.taras.testapp.ApiConst.ID_KEY;
import static com.example.taras.testapp.ApiConst.NAME_KEY;
import static com.example.taras.testapp.ApiConst.PICTURE_URL_KEY;
import static com.example.taras.testapp.ApiConst.TITLE_KEY;
import static com.example.taras.testapp.ApiConst.URL_KEY;

/**
 * Created by Taras on 11/02/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        addCategoryTable(sqLiteDatabase);
        addChannelTable(sqLiteDatabase);
    }

    private void addChannelTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ChannelEntry.TABLE_NAME + " (" +
                ChannelEntry._ID + " integer primary key autoincrement, " +
                ID_KEY + " integer, " +
                NAME_KEY + " text, " +
                URL_KEY + " text, " +
                PICTURE_URL_KEY + " text, " +
                CATEGORY_ID_KEY + " integer);"
        );
    }

    private void addCategoryTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry._ID + " integer primary key autoincrement, " +
                ID_KEY + " integer, " +
                TITLE_KEY + " text, " +
                PICTURE_URL_KEY + " text);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

