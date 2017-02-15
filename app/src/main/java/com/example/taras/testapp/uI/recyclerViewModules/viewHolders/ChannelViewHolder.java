package com.example.taras.testapp.uI.recyclerViewModules.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.taras.testapp.R;

/**
 * Created by Taras on 14/02/2017.
 */

public class ChannelViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;
    private int mChannelId;

    public void setChannelId(int id) {
        mChannelId = id;
    }

    public int getChannelId() {
        return mChannelId;
    }

    public ChannelViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_name_ChanRL);
    }
}
