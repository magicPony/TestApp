package com.example.taras.testapp.uI.recyclerViewModules.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.taras.testapp.R;

/**
 * Created by Taras on 12/02/2017.
 */

public class ProgramViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTime, tvTitle;

    public ProgramViewHolder(View itemView) {
        super(itemView);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time_PRL);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title_PRL);
    }
}
