package com.example.taras.testapp;

import android.database.Cursor;
import android.net.Uri;
import android.text.format.Time;
import android.util.Log;

import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.taras.testapp.ApiConst.JSON_PROGRAM_KEY;

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

    public static ArrayList<ProgramItemModel> cursorToProgram(Cursor cursor) throws JSONException {
        cursor.moveToFirst();
        int jsonCol;
        jsonCol = cursor.getColumnIndex(JSON_PROGRAM_KEY);
        String json;
        json = cursor.getString(jsonCol);
        return jsonToProgram(json);
    }

    public static ArrayList<ProgramItemModel> jsonToProgram(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<ProgramItemModel> res = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            res.add(new ProgramItemModel(jsonObject));
        }

        return res;
    }

    public static String intToString(int n, int len) {
        String s = Integer.toString(n);

        while (s.length() < len) {
            s = "0" + s;
        }

        return s;
    }

    public static String parseDate(Uri contentUri) {
        String s = contentUri.toString(), res = "";
        int i = s.length();

        while (s.charAt(i - 1) != '/') {
            i--;
        }

        for (; i < s.length(); i++) {
            res += s.charAt(i);
        }

        return res;
    }
}
