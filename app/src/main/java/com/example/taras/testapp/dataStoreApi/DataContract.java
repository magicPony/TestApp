package com.example.taras.testapp.dataStoreApi;

import android.net.Uri;

/**
 * Created by Taras on 11/02/2017.
 */

public class DataContract {
    public static final String AUTHORITY = "my_authority";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_CATEGORIES = "categories";
    public static final String PATH_CHANNELS = "channels";
    public static final String PATH_PROGRAMS = "programs";
}
