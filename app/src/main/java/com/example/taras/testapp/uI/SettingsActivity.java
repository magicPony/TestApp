package com.example.taras.testapp.uI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.TmpDataController;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCurrentValue;
    private boolean isValueChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
        isValueChanged = false;
    }

    private void initUI() {
        Button btnUpdate, btnOne, btnSeven, btnThirty;
        btnUpdate = (Button) findViewById(R.id.btn_update_AS);
        btnOne = (Button) findViewById(R.id.btn_one_AS);
        btnSeven = (Button) findViewById(R.id.btn_seven_AS);
        btnThirty = (Button) findViewById(R.id.btn_thirty_AS);

        tvCurrentValue = (TextView) findViewById(R.id.tv_current_val_AS);
        int currentValue = PrefsApi.getInt(this, DAYS_TO_LOAD_KEY, 1);
        tvCurrentValue.setText(Integer.toString(currentValue));

        btnUpdate.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnThirty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_AS :
                TmpDataController.updateData();
                isValueChanged = false;
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

    private void updateDaysToLoad(int newVal) {
        isValueChanged = true;
        PrefsApi.putInt(this, DAYS_TO_LOAD_KEY, newVal);
        tvCurrentValue.setText(Integer.toString(newVal));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (isValueChanged) {
            TmpDataController.updateData();
        }
    }
}
