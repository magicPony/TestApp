package com.example.taras.testapp.retrofitApi.modelResponse;

import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.retrofitApi.ApiModule;
import com.example.taras.testapp.retrofitApi.IApi;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Taras on 11/02/2017.
 */

public class ChannelRequestImpl implements IChannelRequest {

    private IApi api = ApiModule.getApiInterface();

    @Override
    public Call<List<ChannelModel>> getCategoryList() {
        return api.getChannelList();
    }
}
