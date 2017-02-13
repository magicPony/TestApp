package com.example.taras.testapp;

import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;

import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
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

public class UtilsApi {

    public static String getDate(int daysToAdd) {
        //      :|
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int day, month, year;
        day = today.monthDay;
        month = today.month + 1 + daysToAdd;
        year = today.year;
        Log.d("current_date", intToString(day, 2) + intToString(month, 2) + intToString(year, 4));
        return intToString(day, 2) + intToString(month, 2) + intToString(year, 4);
    }

    public static ArrayList<ChannelModel> cursorToChannelList(Cursor cursor) {
        ArrayList<ChannelModel> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                res.add(new ChannelModel(cursor));
            } while (cursor.moveToNext());
        }

        return  res;
    }

    public static ArrayList<CategoryModel> cursorToCategoryList(Cursor cursor) {
        ArrayList<CategoryModel> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                res.add(new CategoryModel(cursor));
            } while (cursor.moveToNext());
        }

        return res;
    }

    public static ArrayList<ChannelModel> jsonToChannelLst(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<ChannelModel>>(){}.getType());
    }

    public static ArrayList<CategoryModel> jsonToCategoryList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<ArrayList<CategoryModel>>(){}.getType());
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

    public static String intToString(int n, int len) {
        String s = Integer.toString(n);

        while (s.length() < len) {
            s = "0" + s;
        }

        return s;
    }
}
