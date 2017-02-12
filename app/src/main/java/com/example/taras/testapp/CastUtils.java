package com.example.taras.testapp;

import android.database.Cursor;

import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 09/02/2017.
 */

public class CastUtils {

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

    public static JSONObject programListToJson(List<ProgramItemModel> programs) {
        // TODO
        return null;
    }
}
