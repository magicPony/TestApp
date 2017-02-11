package com.example.taras.testapp;

import android.database.Cursor;

import com.example.taras.testapp.models.Category;
import com.example.taras.testapp.models.Channel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Taras on 09/02/2017.
 */

public class CastUtils {

    public static ArrayList<Channel> cursorToChannelList(Cursor cursor) {
        ArrayList<Channel> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                res.add(new Channel(cursor));
            } while (cursor.moveToNext());
        }

        return  res;
    }

    public static ArrayList<Category> cursorToCategoryList(Cursor cursor) {
        ArrayList<Category> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                res.add(new Category(cursor));
            } while (cursor.moveToNext());
        }

        return res;
    }

    public static ArrayList<Channel> jsonToChannelLst(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Channel>>(){}.getType());
    }

    public static ArrayList<Category> jsonToCategoryList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<Category>>(){}.getType());
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }

        return builder.toString();
    }
}
