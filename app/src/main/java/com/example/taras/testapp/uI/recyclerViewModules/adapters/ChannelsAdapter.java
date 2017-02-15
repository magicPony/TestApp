package com.example.taras.testapp.uI.recyclerViewModules.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.testapp.R;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.uI.recyclerViewModules.viewHolders.ChannelViewHolder;

import java.util.ArrayList;

/**
 * Created by Taras on 15/02/2017.
 */

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private ArrayList<ChannelModel> mChannels;

    public ChannelsAdapter(ArrayList<ChannelModel> channels) {
        mChannels = channels;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_row_layout, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        ChannelModel channel = mChannels.get(position);
        holder.tvName.setText(channel.getName());
    }

    @Override
    public int getItemCount() {
        return mChannels.size();
    }
}
