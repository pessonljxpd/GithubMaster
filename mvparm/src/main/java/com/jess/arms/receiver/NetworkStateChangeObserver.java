package com.jess.arms.receiver;

/**
 * Created by Shelly on 2017-3-21.
 */

public interface NetworkStateChangeObserver {

    void onNetConnected(int pNetworkType);

    void onNetDisconnect();
}
