package com.example.taras.testapp.uI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.uI.recyclerViewModules.adapters.ChannelsAdapter;

/**
 * Created by Taras on 15/02/2017.
 */

public class ChannelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_AChan);
        ChannelsAdapter adapter = new ChannelsAdapter(TmpDataController.getChannels());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
