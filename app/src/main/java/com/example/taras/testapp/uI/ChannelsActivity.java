package com.example.taras.testapp.uI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.uI.recyclerViewModules.adapters.ChannelsAdapter;

import java.util.ArrayList;

import static com.example.taras.testapp.ApiConst.CATEGORY_ID_KEY;

/**
 * Created by Taras on 15/02/2017.
 */

public class ChannelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        int categoryId = getIntent().getIntExtra(CATEGORY_ID_KEY, -1);
        ArrayList<ChannelModel> channels = null;

        if (categoryId == -1) {
            channels = TmpDataController.getChannels();
        } else {
            channels = new ArrayList<>();

            for (ChannelModel channel : TmpDataController.getChannels())
                if (channel.getCategoryId() == categoryId) {
                    channels.add(channel);
                }
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_AChan);
        ChannelsAdapter adapter = new ChannelsAdapter(this, channels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
