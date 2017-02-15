package com.example.taras.testapp.uI.recyclerViewModules.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.uI.recyclerViewModules.viewHolders.ChannelViewHolder;

import java.util.ArrayList;

/**
 * Created by Taras on 15/02/2017.
 */

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private ArrayList<ChannelModel> mChannels;
    private Context mContext;

    public ChannelsAdapter(Context context, ArrayList<ChannelModel> channels) {
        mContext = context;
        mChannels = channels;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row_layout, parent, false);
        final ChannelViewHolder holder = new ChannelViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int channelId, isFave;
                channelId = holder.getChannelId();
                isFave = PrefsApi.getInt(mContext, Integer.toString(channelId), 0);
                isFave ^= 1;
                PrefsApi.putInt(mContext, Integer.toString(channelId), isFave);
                Log.d("channel_clicked", "id=" + Integer.toString(channelId) + " isFave=" + Integer.toString(isFave));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        ChannelModel channel = mChannels.get(position);
        holder.tvName.setText(channel.getName());
        holder.setChannelId(mChannels.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mChannels.size();
    }
}
