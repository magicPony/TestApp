package com.example.taras.testapp.uI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.R;
import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.uI.recyclerViewModules.ProgramAdapter;

import java.util.ArrayList;

/**
 * Created by Taras on 12/02/2017.
 */
@SuppressLint("ValidFragment")
public class PageFragment extends Fragment {

    private ArrayList<ProgramItemModel> mProgram;
    private Context mContext;
    private int mChannelId;
    private Drawable starOn, starOff;
    private ImageView ivFave;

    @SuppressLint("ValidFragment")
    public PageFragment(Context context, ArrayList<ProgramItemModel> program, int channelId) {
        mContext = context;
        mProgram = program;
        mChannelId = channelId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_FP);
        ProgramAdapter adapter = new ProgramAdapter(mProgram);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        starOn = getResources().getDrawable(android.R.drawable.star_on);
        starOff = getResources().getDrawable(android.R.drawable.star_off);

        ivFave = (ImageView) view.findViewById(R.id.iv_fave_FP);
        ivFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStarState(true);
            }
        });

        setStarState(false);
    }

    public void setStarState(boolean isClicked) {
        int isFave = PrefsApi.getInt(mContext, Integer.toString(mChannelId), 0);

        if (isClicked) {
            isFave ^= 1;
        }

        PrefsApi.putInt(mContext, Integer.toString(mChannelId), isFave);
        ivFave.setImageDrawable(isFave == 1 ? starOn : starOff);
    }
}
