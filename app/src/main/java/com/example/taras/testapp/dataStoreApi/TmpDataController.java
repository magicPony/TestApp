package com.example.taras.testapp.dataStoreApi;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.taras.testapp.R;
import com.example.taras.testapp.asyncDbRequest.AsyncGetCategories;
import com.example.taras.testapp.asyncDbRequest.AsyncGetChannels;
import com.example.taras.testapp.asyncDbRequest.AsyncGetProgram;
import com.example.taras.testapp.models.CategoryModel;
import com.example.taras.testapp.models.ChannelModel;
import com.example.taras.testapp.models.ProgramItemModel;
import com.example.taras.testapp.serviceModules.DataHandleService;
import com.example.taras.testapp.uI.IOnDataLoadedCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.taras.testapp.ApiConst.NECESSARY_DATA_STATUS_KEY;

/**
 * Created by Taras on 13/02/2017.
 */

public class TmpDataController {

    private static boolean isChannelsLoaded, isProgramLoaded, isCategoriesLoaded;
    private static ArrayList<ChannelModel> mChannels;
    private static ArrayList<CategoryModel> mCategories;
    private static Map<String, ArrayList<ProgramItemModel>> mPrograms;
    private static IOnDataLoadedCallback mCallback;
    private static NotificationManager mNotificationManager;

    public static void resetDataLoadStatus() {
        isCategoriesLoaded = isChannelsLoaded = isProgramLoaded = false;
    }

    public static boolean isDataLoaded() {
        return isCategoriesLoaded && isChannelsLoaded && isProgramLoaded;
    }

    public static void notifyChannelsLoaded() {
        isChannelsLoaded = true;
        performUpdate();
    }

    public static void notifyCategoriesLoaded() {
        isCategoriesLoaded = true;
        performUpdate();
    }

    public static void notifyProgramLoaded() {
        isProgramLoaded = true;
        performUpdate();
    }

    private static void performUpdate() {
        if (isDataLoaded()) {
            if (mCallback == null || mContext == null) {
                return;
            }

            mCallback.onFinish();
            sendDataLoadedNotification();
            resetDataLoadStatus();
            PrefsApi.putInt(mContext, NECESSARY_DATA_STATUS_KEY, 1);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void createInstance(Context context) {
        mContext = context;
        mChannels = new ArrayList<>();
        mCategories = new ArrayList<>();
        mPrograms = new HashMap<>();
    }

    public static void updateChannels(List<ChannelModel> channels) {
        if (mContext == null) {
            return;
        }

        mChannels = new ArrayList<>();
        ArrayList<ChannelModel> fave, unfave;
        fave = new ArrayList<>();
        unfave = new ArrayList<>();

        for (ChannelModel channel : channels) {
            int channelId = channel.getId();

            if (PrefsApi.getInt(mContext, Integer.toString(channelId), 0) == 0) {
                unfave.add(channel);
            } else {
                fave.add(channel);
            }
        }

        for (ChannelModel channel : fave) {
            mChannels.add(channel);
        }

        for (ChannelModel channel : unfave) {
            mChannels.add(channel);
        }
    }

    public static void updateCategories(List<CategoryModel> categories) {
        if (mContext == null) {
            return;
        }

        mCategories = new ArrayList<>();

        for (CategoryModel category : categories) {
            mCategories.add(category);
        }
    }

    public static void updateProgram(List<ProgramItemModel> program, String date) {
        if (mContext == null) {
            return;
        }

        Collections.sort(program, new Comparator<ProgramItemModel>() {
            @Override
            public int compare(ProgramItemModel lhs, ProgramItemModel rhs) {
                return lhs.compareTo(rhs);
            }
        });

        ArrayList<ProgramItemModel> tmp = new ArrayList<>();

        for (ProgramItemModel programItem : program) {
            tmp.add(programItem);
        }

        mPrograms.put(date, tmp);
    }

    public static ArrayList<CategoryModel> getCategories() {
        return mCategories;
    }

    public static ArrayList<ChannelModel> getChannels() {
        return mChannels;
    }

    public static ArrayList<ProgramItemModel> getProgram(String date) {
        return mPrograms.get(date);
    }

    public static void setCallback(IOnDataLoadedCallback callback) {
        mCallback = callback;
    }

    public static void initData() {
        resetDataLoadStatus();

        AsyncGetCategories categoriesRequest = new AsyncGetCategories(mContext, new IOnDataLoadedCallback() {
            @Override
            public void onFinish() {
                notifyCategoriesLoaded();
            }
        });

        AsyncGetProgram programRequest = new AsyncGetProgram(mContext, new IOnDataLoadedCallback() {
            @Override
            public void onFinish() {
                notifyProgramLoaded();
            }
        });

        AsyncGetChannels channelsRequest = new AsyncGetChannels(mContext, new IOnDataLoadedCallback() {
            @Override
            public void onFinish() {
                notifyChannelsLoaded();
            }
        });

        categoriesRequest.execute();
        programRequest.execute();
        channelsRequest.execute();
    }

    public static void updateData() {
        resetDataLoadStatus();
        Intent intent = new Intent(mContext, DataHandleService.class);
        mContext.startService(intent);
    }

    public static ArrayList<ChannelModel> getFaveChannels() {
        ArrayList<ChannelModel> res = new ArrayList<>();

        for (ChannelModel channel : mChannels) {
            int channelId = channel.getId();

            if (PrefsApi.getInt(mContext, Integer.toString(channelId), 0) == 1) {
                res.add(channel);
            }
        }

        return res;
    }

    public static void setNotificationManager(NotificationManager notificationManager) {
        TmpDataController.mNotificationManager = notificationManager;
    }

    public static void sendDataLoadedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Test App")
                .setContentText("data is loaded successfully");

        mNotificationManager.notify(0, builder.build());
    }
}
