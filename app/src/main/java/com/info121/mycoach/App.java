package com.info121.mycoach;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.info121.mycoach.utils.PrefDB;
import com.info121.mycoach.utils.Util;


/**
 * Created by KZHTUN on 7/25/2017.
 */

public class App extends Application {

    // LIVE
    public static final String CONST_REST_API_URL = "http://103.7.10.139/RestApiCoach/MyLimoService.svc/";
    public static final String CONST_WEBSITE_URL = "  http://103.7.10.139/iops_portalcoach/";

    //    // DEV
//    public static final String CONST_REST_API_URL = "http://alexisinfo121.noip.me:83/restapi/MyLimoService.svc/";
//    public static final String CONST_WEBSITE_URL = "http://alexisinfo121.noip.me:83/iops_portal/";


    public static final String CONST_URL_JOB_LIST = CONST_WEBSITE_URL + "iDriverJobsList.aspx?LogInUser=%s";
    public static String CONST_USER_NAME = "USER_NAME";
    public static String CONST_ALREADY_LOGIN = "ALREADY_LOGIN";
    public static String CONST_NOTIFICATION_TONE = "NOTIFICATION_TONE";
    public static String CONST_PROMINENT_TONE = "PROMINENT_TONE";

    public static String CONST_DEVICE_ID = "DEVICE_ID";
    public static String CONST_TIMER_DELAY = "TIMER_DELAY";
    public static String CONST_REMEMBER_ME = "REMEMBER_ME";

    public static String userName = "";
    public static String deviceID = "";

    public static long timerDelay = 0;
    public static Location location;

    public static Context targetContent;

    public static Runnable mRunnable;
    public static final Handler mHandler = new Handler();


    public static Uri DEFAULT_SOUND_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    public static Uri NOTIFICATION_SOUND_URI = null;
    public static Uri PROMINENT_SOUND_URI = null;

    private FirebaseAnalytics mFirebaseAnalytics;


    // Live
    public static final String FTP_URL = "103.7.10.139";
    public static final String FTP_USER = "info121";
    public static final String FTP_PASSWORD = "ja8mt988$$";
    public static String FTP_DIR = "limopics";


    // DEV
//    public static final String FTP_URL = "http://alexisinfo121.noip.me:83";
//    public static final String FTP_USER = "Administrator";
//    public static final String FTP_PASSWORD = "Info121@sa-ja8mt988$$";
//    public static String FTP_DIR = "iops/images/limopics";

    public static PrefDB prefDB = null;
    public static String FCM_TOKEN = "";

    public static final String[] SONG_PROJECTION = new String[]{
            MediaStore.Audio.Media._ID
            , MediaStore.Audio.Media.TITLE
            , MediaStore.Audio.Media.ARTIST
            , MediaStore.Audio.Albums.ALBUM
            , MediaStore.Audio.Media.DURATION
            , MediaStore.Audio.Media.TRACK
            , MediaStore.Audio.Media.ARTIST_ID
            , MediaStore.Audio.Media.ALBUM_ID
            , MediaStore.Audio.Media.DATA
            , MediaStore.Audio.Media.ALBUM_KEY
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //   Log.e("FCM Token ", FirebaseInstanceId.getInstance().getToken());

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FCM_TOKEN = FirebaseInstanceId.getInstance().getToken();
        Log.e("FCM", "FCM Token" +  App.FCM_TOKEN);


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Util.getDeviceID(this));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Util.getDeviceName());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        prefDB = new PrefDB(getApplicationContext());


        if (prefDB.getString("OLD_CH_ID").length() == 0)
            prefDB.putString("OLD_CH_ID", "DEFAULT_OLD");

        if (prefDB.getString("NEW_CH_ID").length() == 0)
            prefDB.putString("NEW_CH_ID", "DEFAULT_NEW");

        if (prefDB.getString("OLD_CH_ID_P").length() == 0)
            prefDB.putString("OLD_CH_ID_P", "DEFAULT_OLD_P");

        if (prefDB.getString("NEW_CH_ID_P").length() == 0)
            prefDB.putString("NEW_CH_ID_P", "DEFAULT_NEW_P");

    }


    public static Uri getProminentSoundUri() {
        if (prefDB.getString(CONST_NOTIFICATION_TONE) == "")
            return App.DEFAULT_SOUND_URI;
        else
            return Uri.parse(prefDB.getString(CONST_PROMINENT_TONE));
    }

    public static Uri getNotificationSoundUri() {
        if (prefDB.getString(CONST_NOTIFICATION_TONE) == "")
            return App.DEFAULT_SOUND_URI;
        else
            return Uri.parse(prefDB.getString(CONST_NOTIFICATION_TONE));
    }


    public static String getNewChannelId() {
        return prefDB.getString("NEW_CH_ID");
    }

    public static String getOldChannelId() {
        return prefDB.getString("OLD_CH_ID");
    }

    public static String getNewChannelIdP() {
        return prefDB.getString("NEW_CH_ID_P");
    }

    public static String getOldChannelIdP() {
        return prefDB.getString("OLD_CH_ID_P");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        mHandler.removeCallbacks(App.mRunnable);
        mRunnable = null;

    }
}
