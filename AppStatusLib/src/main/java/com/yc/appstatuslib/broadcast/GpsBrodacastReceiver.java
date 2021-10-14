package com.yc.appstatuslib.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.System;
import android.text.TextUtils;

import com.yc.appstatuslib.ResourceManager;

public final class GpsBrodacastReceiver extends BroadcastReceiver {
    private static final String TAG = "GpsBrodacastReceiver";
    private ResourceManager mManager;

    public GpsBrodacastReceiver(ResourceManager mManager) {
        this.mManager = mManager;
    }

    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction.equals("android.location.PROVIDERS_CHANGED")) {
            this.notifyGpsSwitchState(isGpsEnabled(context));
        }

    }

    public static boolean isGpsEnabled(Context context) {
        String gps = System.getString(context.getContentResolver(), "location_providers_allowed");
        return !TextUtils.isEmpty(gps) && gps.contains("gps");
    }

    private void notifyGpsSwitchState(boolean state) {
        if (this.mManager != null) {
            if (state) {
                this.mManager.dispatcherGpsOn();
            } else {
                this.mManager.dispatcherGpsOff();
            }

        }
    }
}

