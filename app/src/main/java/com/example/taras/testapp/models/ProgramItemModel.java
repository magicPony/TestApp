package com.example.taras.testapp.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.taras.testapp.ApiConst.CHANNEL_ID_KEY;
import static com.example.taras.testapp.ApiConst.DATE_KEY;
import static com.example.taras.testapp.ApiConst.DESCRIPTION_KEY;
import static com.example.taras.testapp.ApiConst.TIME_KEY;
import static com.example.taras.testapp.ApiConst.TITLE_KEY;

/**
 * Created by Taras on 12/02/2017.
 */

public class ProgramItemModel {
    @SerializedName("channel_id")
    private int channelId;
    private String date;
    private String time;
    private String title;
    private String description;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CHANNEL_ID_KEY, channelId);
        contentValues.put(DATE_KEY, date);
        contentValues.put(TIME_KEY, time);
        contentValues.put(TITLE_KEY, title);
        contentValues.put(DESCRIPTION_KEY, description);

        return contentValues;
    }

    public ProgramItemModel(Cursor cursor) {
        int chanIdCol, dateCol, timeCol, titleCol, descCol;
        chanIdCol = cursor.getColumnIndex(CHANNEL_ID_KEY);
        dateCol = cursor.getColumnIndex(DATE_KEY);
        timeCol = cursor.getColumnIndex(TIME_KEY);
        titleCol = cursor.getColumnIndex(DESCRIPTION_KEY);
        descCol = cursor.getColumnIndex(DESCRIPTION_KEY);

        channelId = cursor.getInt(chanIdCol);
        date = cursor.getString(dateCol);
        time = cursor.getString(timeCol);
        title = cursor.getString(titleCol);
        description = cursor.getString(descCol);
    }

    public ProgramItemModel() {

    }

    public String getTitle() {
        return title;
    }

    public int getChannelId() {
        return channelId;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(CHANNEL_ID_KEY, channelId);
        jsonObject.put(TITLE_KEY, title);
        jsonObject.put(DATE_KEY, date);
        jsonObject.put(DESCRIPTION_KEY, description);
        jsonObject.put(TIME_KEY, time);

        return jsonObject;
    }

    public int compareTo(ProgramItemModel programItem) {
        return time.compareTo(programItem.getTime());
    }
}
