package com.example.taras.testapp.dataStoreApi;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.taras.testapp.dataStoreApi.DataContract.BASE_CONTENT_URI;
import static com.example.taras.testapp.dataStoreApi.DataContract.PATH_CHANNELS;

/**
 * Created by Taras on 11/02/2017.
 */

public class ChannelEntry implements BaseColumns {
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHANNELS).build();

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_CHANNELS;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_CHANNELS;

    public static final String TABLE_NAME = "channels";


    public static Uri buildCategoryUri(Long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}

