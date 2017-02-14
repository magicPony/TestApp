package com.example.taras.testapp.uI;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.taras.testapp.PrefsApi;
import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.ProgramsEntry;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.ProgramItemModel;

import org.json.JSONException;

import static com.example.taras.testapp.ApiConst.NECESSARY_DATA_STATUS_KEY;
import static com.example.taras.testapp.UtilsApi.cursorToProgram;
import static com.example.taras.testapp.UtilsApi.getDate;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TmpDataController.createInstance(this);
        TmpDataController.setCallback(new IOnDataLoadedCallback() {
            @Override
            public void onFinish() {
                initUI();
            }
        });

        if (PrefsApi.getInt(this, NECESSARY_DATA_STATUS_KEY, 0) == 1) {
            TmpDataController.initData();
        }
        //TmpDataController.updateData();
        /*try {
            checkProgram();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    private void checkProgram() throws JSONException {
        Uri contentUri = ProgramsEntry.CONTENT_URI.buildUpon().appendPath(getDate(0)).build();
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);

        for (ProgramItemModel programItem : cursorToProgram(cursor)) {
            Log.d("ama_hasla", "time=" + programItem.getTime() + " title=" + programItem.getTitle());
        }
    }

    private void initUI() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
