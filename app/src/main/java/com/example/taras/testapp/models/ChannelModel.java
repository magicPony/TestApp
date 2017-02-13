package com.example.taras.testapp.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.taras.testapp.ApiConst;
import com.google.gson.annotations.SerializedName;

import static com.example.taras.testapp.ApiConst.CATEGORY_ID_KEY;
import static com.example.taras.testapp.ApiConst.ID_KEY;
import static com.example.taras.testapp.ApiConst.NAME_KEY;
import static com.example.taras.testapp.ApiConst.PICTURE_URL_KEY;
import static com.example.taras.testapp.ApiConst.URL_KEY;

/**
 * Created by Taras on 09/02/2017.
 */

public class ChannelModel {
    private int id;
    private String name;
    private String url;
    @SerializedName("picture")
    private String pictureUrl;
    @SerializedName("category_id")
    private int categoryId;
    private int isFave;

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_KEY, id);
        contentValues.put(NAME_KEY, name);
        contentValues.put(URL_KEY, url);
        contentValues.put(PICTURE_URL_KEY, pictureUrl);
        contentValues.put(CATEGORY_ID_KEY, categoryId);

        return contentValues;
    }

    public ChannelModel(Cursor cursor) {
        int idCol, nameCol, urlCol, picCol, catCol;
        idCol = cursor.getColumnIndex(ID_KEY);
        nameCol = cursor.getColumnIndex(ApiConst.NAME_KEY);
        urlCol = cursor.getColumnIndex(ApiConst.URL_KEY);
        picCol = cursor.getColumnIndex(ApiConst.PICTURE_URL_KEY);
        catCol = cursor.getColumnIndex(ApiConst.CATEGORY_ID_KEY);

        id = cursor.getInt(idCol);
        name = cursor.getString(nameCol);
        url = cursor.getString(urlCol);
        pictureUrl = cursor.getString(picCol);
        categoryId = cursor.getInt(catCol);
    }

    public ChannelModel() {
    }

    public int getId() {
        return id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getUrl() {
        return url;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
