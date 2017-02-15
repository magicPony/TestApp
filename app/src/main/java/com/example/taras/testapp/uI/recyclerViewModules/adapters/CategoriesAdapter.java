package com.example.taras.testapp.uI.recyclerViewModules.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.testapp.R;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.uI.recyclerViewModules.viewHolders.CategoryViewHolder;

import java.util.ArrayList;

/**
 * Created by Taras on 15/02/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private ArrayList<CategoryModel> mCategories;

    public CategoriesAdapter(ArrayList<CategoryModel> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryModel category = mCategories.get(position);
        holder.tvTitle.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}
