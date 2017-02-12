package com.example.taras.testapp.retrofitApi.modelResponse;

import com.example.taras.testapp.models.ProgramItemModel;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Taras on 12/02/2017.
 */

public interface IProgramRequest {
    Call<List<ProgramItemModel>> getProgramList(String date);
}
