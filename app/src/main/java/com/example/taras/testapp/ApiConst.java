package com.example.taras.testapp;

import android.net.Uri;

/**
 * Created by Taras on 10/02/2017.
 */

public class ApiConst {
    // parse keys
    public static final String ID_KEY = "id";
    public static final String NAME_KEY = "name";
    public static final String URL_KEY = "url";
    public static final String PICTURE_URL_KEY = "picture";
    public static final String CATEGORY_ID_KEY = "category";
    public static final String TITLE_KEY = "title";
    public static final String DATE_KEY = "date";
    public static final String TIME_KEY = "time";
    public static final String DESCRIPTION_KEY = "description";
    public static final String CHANNEL_ID_KEY = "channel_id";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String JSON_PROGRAM_KEY = "json_program";

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

    // service constants
    public static final int COMMAND_RESET_ALARM_KEY = 102;
    public static final int COMMAND_CANCEL_ALARM = 101;
    public static final int COMMAND_NO_ACTION = 103;
    public static final int COMMAND_UPDATE_CATEGORIES = 104;
    public static final int COMMAND_UPDATE_CHANNELS = 105;
    public static final int COMMAND_UPDATE_PROGRAM = 106;
    public static final String COMMAND_KEY = "command";
}
