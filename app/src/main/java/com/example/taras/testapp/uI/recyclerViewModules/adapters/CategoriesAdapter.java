package com.example.taras.testapp.uI.recyclerViewModules.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.testapp.R;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.uI.activities.ChannelsActivity;
import com.example.taras.testapp.uI.recyclerViewModules.viewHolders.CategoryViewHolder;

import java.util.ArrayList;

import static com.example.taras.testapp.ApiConst.CATEGORY_ID_KEY;

/**
 * Created by Taras on 15/02/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private ArrayList<CategoryModel> mCategories;
    private Context mContext;

    public CategoriesAdapter(Context context, ArrayList<CategoryModel> categories) {
        mContext = context;
        mCategories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_layout, parent, false);
        final CategoryViewHolder holder = new CategoryViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChannelsActivity.class);
                intent.putExtra(CATEGORY_ID_KEY, holder.getCategoryId());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel category = mCategories.get(position);
        holder.tvTitle.setText(category.getTitle());
        holder.setCategoryId(mCategories.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
