package com.pwittchen.icsl.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pwittchen.icsl.library.config.ICSLConfig;
import com.pwittchen.icsl.library.event.ConnectivityStatusChangedEvent;
import com.pwittchen.icsl.library.helper.NetworkHelper;
import com.squareup.otto.Bus;


public class InternetConnectionChangeReceiver extends BroadcastReceiver {

    private Bus eventBus;

    public InternetConnectionChangeReceiver(Bus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ICSLConfig.getIntentName())) {
            boolean connectedToInternet = intent.getBooleanExtra(ICSLConfig.getIntentNameExtra(), false);
            ConnectivityStatus connectivityStatus = (connectedToInternet) ? ConnectivityStatus.WIFI_CONNECTED_HAS_INTERNET : ConnectivityStatus.WIFI_CONNECTED_HAS_NO_INTERNET;
            eventBus.post(new ConnectivityStatusChangedEvent(connectivityStatus, NetworkHelper.getWiFiInfo(context)));
        }
    }
}
