package com.example.taras.testapp.uI.recyclerViewModules.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taras.testapp.R;
import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.uI.recyclerViewModules.viewHolders.ProgramViewHolder;

import java.util.ArrayList;

/**
 * Created by Taras on 12/02/2017.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramViewHolder> {

    private ArrayList<ProgramItemModel> mProgram;

    public ProgramAdapter(ArrayList<ProgramItemModel> program) {
        super();
        mProgram = program;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_row_layout, parent, false);
        return new ProgramViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder holder, int position) {
        ProgramItemModel programItem = mProgram.get(position);
        holder.tvTitle.setText(programItem.getTitle());
        holder.tvTime.setText(programItem.getTime());
    }

    @Override
    public int getItemCount() {
        return mProgram.size();
    }
}
