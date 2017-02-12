package com.example.taras.testapp.retrofitApi.modelResponse;

import com.example.taras.testapp.models.ChannelModel;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Taras on 11/02/2017.
 */

public interface IChannelRequest {
    Call<List<ChannelModel>> getCategoryList();
}
