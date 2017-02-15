package com.example.taras.testapp.uI.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.dataStoreApi.TmpDataController;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;

/**
 * Created by Taras on 14/02/2017.
 */

@SuppressLint("ValidFragment")
public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView tvCurrent;

    @SuppressLint("ValidFragment")
    public SettingsFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnUpdate ,btnOne, btnSeven, btnThirty;

        btnUpdate = (Button) view.findViewById(R.id.btn_update_SL);
        btnOne = (Button) view.findViewById(R.id.btn_one_SL);
        btnSeven = (Button) view.findViewById(R.id.btn_seven_SL);
        btnThirty = (Button) view.findViewById(R.id.btn_thirty_SL);
        tvCurrent = (TextView) view.findViewById(R.id.tv_current_val_SL);

        btnUpdate.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnThirty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_SL :
                TmpDataController.updateData();
                onDestroy();
                break;

            case R.id.btn_one_SL :
                updateDaysToLoad(1);
                break;

            case R.id.btn_seven_SL :
                updateDaysToLoad(7);
                break;

            case R.id.btn_thirty_SL :
                updateDaysToLoad(30);
                break;
        }
    }

    private void updateDaysToLoad(int newVal) {
        PrefsApi.putInt(mContext, DAYS_TO_LOAD_KEY, newVal);
        tvCurrent.setText(Integer.toString(newVal));
    }
}
