package com.example.taras.testapp.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static com.example.taras.testapp.ApiConst.ID_KEY;
import static com.example.taras.testapp.ApiConst.PICTURE_URL_KEY;
import static com.example.taras.testapp.ApiConst.TITLE_KEY;

/**
 * Created by Taras on 09/02/2017.
 */

public class CategoryModel {
    private int id;
    private String title;
    @SerializedName("picture")
    private String pictureUrl;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_KEY, id);
        contentValues.put(TITLE_KEY, title);
        contentValues.put(PICTURE_URL_KEY, pictureUrl);

        return contentValues;
    }

    public CategoryModel(Cursor cursor) {
        int idCol, titleCol, picCol;
        idCol = cursor.getColumnIndex(ID_KEY);
        titleCol = cursor.getColumnIndex(TITLE_KEY);
        picCol = cursor.getColumnIndex(PICTURE_URL_KEY);

        id = cursor.getInt(idCol);
        title = cursor.getString(titleCol);
        title = cursor.getString(titleCol);
        pictureUrl = cursor.getString(picCol);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
