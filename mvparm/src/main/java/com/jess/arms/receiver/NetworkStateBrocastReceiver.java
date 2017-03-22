package com.jess.arms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.jess.arms.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class NetworkStateBrocastReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkStateBrocastReceiver.class.getSimpleName();
    private static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static NetworkStateBrocastReceiver mNetworkStateBrocastReceiver;
    private static boolean mNetAvailable = false;
    private static List<NetworkStateChangeObserver> mNetworkStateChangeObservers;
    private static int mNetworkType;

    private NetworkStateBrocastReceiver() {
    }

    public static NetworkStateBrocastReceiver getInstance() {
        if (mNetworkStateBrocastReceiver == null) {
            synchronized (NetworkStateBrocastReceiver.class) {
                if (mNetworkStateBrocastReceiver == null) {
                    mNetworkStateBrocastReceiver = new NetworkStateBrocastReceiver();
                }
            }
        }
        return mNetworkStateBrocastReceiver;
    }

    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        String action = pIntent.getAction();
        if (ANDROID_NET_CHANGE_ACTION.equals(action)) {
            if (NetworkUtils.isNetworkAvailable(pContext)) {
                mNetAvailable = true;
                mNetworkType = NetworkUtils.getNetworkState(pContext);
                Timber.tag(TAG).d("Network connected");
            } else {
                mNetAvailable = false;
                Timber.tag(TAG).d("Network disconnected");
            }
            notifyObservers();
        }

    }

    public static void registerNetworkStateChangeObserver(NetworkStateChangeObserver pChangeObserver){
        if (mNetworkStateChangeObservers==null){
            mNetworkStateChangeObservers = new ArrayList<>();
        }
        mNetworkStateChangeObservers.add(pChangeObserver);
    }

    /**
     * 注册BrocastReceiver的监听动作
     *
     * @param pContext
     */
    public static void registerNetworkStateBrocastReceiver(Context pContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        pContext.registerReceiver(getInstance(), filter);
    }

    /**
     * 注销
     * @param pContext
     */
    public static void unregisterNetworkStateBrocastReceiver(Context pContext){
        if (mNetworkStateBrocastReceiver !=null){
            pContext.unregisterReceiver(mNetworkStateBrocastReceiver);
        }
    }


    private void notifyObservers() {
        if (mNetworkStateChangeObservers != null && !mNetworkStateChangeObservers.isEmpty()) {
            for (NetworkStateChangeObserver networkStateChangeObserver : mNetworkStateChangeObservers) {
                if (mNetAvailable) {
                    networkStateChangeObserver.onNetConnected(mNetworkType);
                } else {
                    networkStateChangeObserver.onNetDisconnect();
                }
            }
        }
    }
}
