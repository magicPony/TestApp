package com.example.taras.testapp;

import android.net.Uri;

/**
 * Created by Taras on 10/02/2017.
 */

public class ApiConst {
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String URL_KEY = "url";
    public static final String PICTURE_URL_KEY = "picture";
    public static final String CATEGORY_ID_KEY = "category";
    public static final String TITLE_KEY = "title";
    public static final String CATEGORY_CLASS = "category";
    public static final String CHANNEL_CLASS = "channel";
    public static final String AUTHORITY = "my_authority";
    public static final String CATEGORIES_PATH = "categories_path";
    public static final String CHANNELS_PATH = "channels_path";
    public static final String CATEGORIES_TABLE = "category_list";
    public static final String CHANNELS_TABLE = "channel_list";
    public static final String CATEGORIES_LINK = "http://52.50.138.211:8080/ChanelAPI/categories";
    public static final String CHANNELS_LINK = "http://52.50.138.211:8080/ChanelAPI/chanels";

    public static final Uri CATEGORIES_URI = Uri.parse("content://" + AUTHORITY + "/" + CATEGORIES_PATH);
    public static final Uri CHANNELS_URI   = Uri.parse("content://" + AUTHORITY + "/" + CHANNELS_PATH);
}
