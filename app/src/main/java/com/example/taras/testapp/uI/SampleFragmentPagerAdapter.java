package com.example.taras.testapp.uI;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.taras.testapp.dataStoreApi.ChannelEntry;
import com.example.taras.testapp.models.ChannelModel;

import java.util.ArrayList;

import static com.example.taras.testapp.UtilsApi.cursorToChannelList;

/**
 * Created by Taras on 12/02/2017.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> tabTitles;
    private Context mContext;
    private ArrayList<Integer> channelIds;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initData();
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(channelIds.get(position));
    }

    private void initData() {
        Cursor cursor = mContext.getContentResolver().query(ChannelEntry.CONTENT_URI, null, null, null, null);
        ArrayList<ChannelModel> channels = cursorToChannelList(cursor);
        tabTitles = new ArrayList<>();
        channelIds = new ArrayList<>();

        for (ChannelModel channel : channels) {
            tabTitles.add(channel.getName());
            channelIds.add(channel.getId());
        }
    }
}
