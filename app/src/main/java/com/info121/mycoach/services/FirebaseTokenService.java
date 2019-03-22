package com.info121.mycoach.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.info121.mycoach.App;

/**
 * Created by KZHTUN on 7/28/2017.
 */

public class FirebaseTokenService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseTokenService";



    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        App.FCM_TOKEN = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "FCN Token" +  App.FCM_TOKEN);

    }
}
