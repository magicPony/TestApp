package com.example.taras.testapp.retrofitApi.modelResponse;

import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.retrofitApi.ApiModule;
import com.example.taras.testapp.retrofitApi.IApi;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Taras on 12/02/2017.
 */

public class ProgramRequestImpl implements IProgramRequest {

    private IApi api = ApiModule.getApiInterface();

    @Override
    public Call<List<ProgramItemModel>> getProgramList(String date) {
        return api.getProgram(date);
    }
}
