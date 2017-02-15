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

public class FavesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faves);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_AF);
        ChannelsAdapter adapter = new ChannelsAdapter(TmpDataController.getFaveChannels());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
