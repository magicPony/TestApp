package com.example.taras.testapp.uI.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.dataStoreApi.TmpDataController;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;
import static com.example.taras.testapp.ApiConst.ONLY_FAVE_KEY;

/**
 * Created by Taras on 15/02/2017.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    
    private TextView tvCurrent;
    private CheckBox cbOnlyFave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnUpdate ,btnOne, btnSeven, btnThirty;

        btnUpdate = (Button) findViewById(R.id.btn_update_AS);
        btnOne = (Button) findViewById(R.id.btn_one_AS);
        btnSeven = (Button) findViewById(R.id.btn_seven_AS);
        btnThirty = (Button) findViewById(R.id.btn_thirty_AS);
        tvCurrent = (TextView) findViewById(R.id.tv_current_val_AS);
        cbOnlyFave = (CheckBox) findViewById(R.id.cb_only_fave_AS);

        btnUpdate.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnThirty.setOnClickListener(this);
        updateDaysToLoad(PrefsApi.getInt(this, DAYS_TO_LOAD_KEY, 1));
        cbOnlyFave.setOnClickListener(this);
        cbOnlyFave.setChecked(PrefsApi.getInt(this, ONLY_FAVE_KEY, 0) == 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_only_fave_AS :
                int state = PrefsApi.getInt(this, ONLY_FAVE_KEY, 0);
                state ^= 1;
                PrefsApi.putInt(this, ONLY_FAVE_KEY, state);
                cbOnlyFave.setChecked(state == 1);
                break;

            case R.id.btn_update_AS :
                TmpDataController.updateData();
                onBackPressed();
                break;

            case R.id.btn_one_AS :
                updateDaysToLoad(1);
                break;

            case R.id.btn_seven_AS :
                updateDaysToLoad(7);
                break;

            case R.id.btn_thirty_AS :
                updateDaysToLoad(30);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateDaysToLoad(int newVal) {
        PrefsApi.putInt(this, DAYS_TO_LOAD_KEY, newVal);
        tvCurrent.setText(Integer.toString(newVal));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
}
