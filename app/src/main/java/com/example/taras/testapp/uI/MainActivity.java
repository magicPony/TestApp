package com.example.taras.testapp.uI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taras.testapp.R;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.uI.fragments.SettingsFragment;

import static com.example.taras.testapp.ApiConst.NECESSARY_DATA_STATUS_KEY;
import static com.example.taras.testapp.ApiConst.SETTINGS_FRAGMENT_NAME;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        String tag = "";

        switch (item.getItemId()) {
            case R.id.mi_categories :
                break;

            case R.id.mi_channels :
                break;

            case R.id.mi_faves :
                break;

            case R.id.mi_settings :
                tag = SETTINGS_FRAGMENT_NAME;
                fragment = new SettingsFragment(this);
                break;

            case R.id.mi_day_program :
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, fragment, tag)
                    .addToBackStack(tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

        return true;
    }
}
