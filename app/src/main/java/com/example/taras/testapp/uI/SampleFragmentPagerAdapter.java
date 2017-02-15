package com.example.taras.testapp.uI;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.taras.testapp.UtilsApi;
import com.example.taras.testapp.dataStoreApi.PrefsApi;
import com.example.taras.testapp.dataStoreApi.TmpDataController;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.taras.testapp.ApiConst.DAYS_TO_LOAD_KEY;
import static com.example.taras.testapp.ApiConst.ONLY_FAVE_KEY;

/**
 * Created by Taras on 12/02/2017.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mTabTitles = null;
    private Context mContext = null;
    private ArrayList<Integer> mChannelIds = null;
    private ArrayList<ArrayList<ProgramItemModel>> mPrograms = null;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mTabTitles == null) {
            initData();
        }

        return mTabTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (mPrograms == null) {
            initData();
        }

        return new PageFragment(mContext, mPrograms.get(position), mChannelIds.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabTitles == null) {
            initData();
        }

        return mTabTitles.get(position);
    }

    private void initData() {
        mTabTitles = new ArrayList<>();
        mChannelIds = new ArrayList<>();
        mPrograms = new ArrayList<>();
        Map<Integer, Integer> channelEntryById = new HashMap<>();
        boolean onlyFave = PrefsApi.getInt(mContext, ONLY_FAVE_KEY, 0) == 1;

        for (ChannelModel channel : TmpDataController.getChannels()) {
            int channelId = channel.getId();

            if (onlyFave && PrefsApi.getInt(mContext, Integer.toString(channelId), 0) == 0) {
                continue;
            }

            mTabTitles.add(channel.getName());
            mChannelIds.add(channel.getId());
            mPrograms.add(new ArrayList<ProgramItemModel>());
        }

        for (int i = 0; i < mChannelIds.size(); i++) {
            channelEntryById.put(mChannelIds.get(i), i);
        }

        int daysCount = PrefsApi.getInt(mContext, DAYS_TO_LOAD_KEY, 1);

        for (int i = 0; i < daysCount; i++)
            for (ProgramItemModel programItem : TmpDataController.getProgram(UtilsApi.getDate(i))) {
                int channelId;
                channelId = programItem.getChannelId();

                if (channelEntryById.containsKey(channelId)) {
                    int channelEntry = channelEntryById.get(channelId);
                    mPrograms.get(channelEntry).add(programItem);
                }
            }
    }
}
