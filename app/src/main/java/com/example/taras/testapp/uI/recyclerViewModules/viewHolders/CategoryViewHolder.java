package com.example.taras.testapp.uI.recyclerViewModules.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.taras.testapp.R;

/**
 * Created by Taras on 14/02/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title_CatRL);
    }
}
