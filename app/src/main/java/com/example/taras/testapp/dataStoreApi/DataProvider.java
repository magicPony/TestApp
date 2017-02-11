package com.example.taras.testapp.dataStoreApi;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Taras on 11/02/2017.
 */

public class DataProvider extends ContentProvider {
    private static final int CATEGORY = 1;
    private static final int CATEGORY_ID = 2;
    private static final int CHANNEL = 3;
    private static final int CHANNEL_ID = 4;

    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private DBHelper mDBHelper;

    public static UriMatcher buildUriMatcher() {
        String content = DataContract.AUTHORITY;
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(content, DataContract.PATH_CATEGORIES, CATEGORY);
        matcher.addURI(content, DataContract.PATH_CATEGORIES + "/#", CATEGORY_ID);
        matcher.addURI(content, DataContract.PATH_CHANNELS, CHANNEL);
        matcher.addURI(content, DataContract.PATH_CHANNELS + "/#", CHANNEL_ID);

        return matcher;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case CATEGORY :
                return CategoryEntry.CONTENT_TYPE;

            case CATEGORY_ID :
                return CategoryEntry.CONTENT_ITEM_TYPE;

            case CHANNEL :
                return CategoryEntry.CONTENT_TYPE;

            case CHANNEL_ID :
                return CategoryEntry.CONTENT_ITEM_TYPE;

            default :
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = null;

        switch (mUriMatcher.match(uri)) {
            case CATEGORY :
                cursor = db.query(
                        CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;

            case CATEGORY_ID :
                long _id = ContentUris.parseId(uri);
                cursor = db.query(
                        CategoryEntry.TABLE_NAME,
                        projection,
                        CategoryEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            case CHANNEL :
                cursor = db.query(
                        ChannelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case CHANNEL_ID :
                _id = ContentUris.parseId(uri);
                cursor = db.query(
                        ChannelEntry.TABLE_NAME,
                        projection,
                        ChannelEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            default :
                throw new UnsupportedOperationException("Unknown uro: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long _id;
        Uri resultUri = null;

        switch (mUriMatcher.match(uri)) {
            case CATEGORY :
                _id = db.insert(CategoryEntry.TABLE_NAME, null, contentValues);

                if (_id > 0) {
                    resultUri = CategoryEntry.buildCategoryUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }

                break;

            case CHANNEL :
                _id = db.insert(ChannelEntry.TABLE_NAME, null, contentValues);

                if (_id > 0) {
                    resultUri = ChannelEntry.buildCategoryUri(_id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }

                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rows = 0;

        switch (mUriMatcher.match(uri)) {
            case CATEGORY :
                rows = db.delete(CategoryEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case CHANNEL :
                rows = db.delete(ChannelEntry.TABLE_NAME, selection, selectionArgs);
                break;
        }

        if (selection == null || rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rows = 0;

        switch (mUriMatcher.match(uri)) {
            case CATEGORY :
                rows = db.update(CategoryEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;

            case CHANNEL :
                rows = db.update(ChannelEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
        }

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);;
        }

        return rows;
    }
}
