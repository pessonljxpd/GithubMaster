package com.jess.arms.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;

import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Shelly on 2017-3-20.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static boolean isNetworkAvailable(@NonNull Context pContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            return getNetworkState(connectivityManager, allNetworks);
        } else {
            NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            return getNetworkState(connectivityManager, allNetworkInfo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static boolean getNetworkState(ConnectivityManager pConnectivityManager, @NonNull Network[] pNetworks) {
        for (Network network : pNetworks) {
            NetworkInfo networkInfo = pConnectivityManager.getNetworkInfo(network);

            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    private static boolean getNetworkState(ConnectivityManager pConnectivityManager, @NonNull NetworkInfo[]
            pNetworkInfos) {
        for (NetworkInfo networkInfo : pNetworkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED){
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return false;
        }
        ConnectivityManager mConnectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        } else {
            return false;
        }
    }

    public static boolean isWifiConnected(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return activeNetworkInfo.isAvailable();
        } else {
            return false;
        }
    }

    public static boolean isMobileConnected(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return false;
        }

        //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return networkInfo.isAvailable();
        } else {
            return false;
        }

    }

    public static int getNetType(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return -1;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return networkInfo.getType();
        } else {
            Timber.tag(TAG).d("NetworkInfo is null or unavailable");
            return -1;
        }
    }

    /**
     * 返回移动网络运营商的名字(例：中国联通、中国移动、中国电信)
     * 仅当用户已在网络注册时有效, CDMA 可能会无效
     *
     * @param pContext
     * @return
     */
    public static String getNetworkOperatorName(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    /**
     * 返回移动终端类型
     * PHONE_TYPE_NONE :0手机制式未知
     * PHONE_TYPE_GSM :1手机制式为GSM，移动和联通
     * PHONE_TYPE_CDMA :2手机制式为CDMA，电信
     * PHONE_TYPE_SIP:3
     *
     * @param pContext
     * @return
     */
    public static int getPhoneType(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return -1;
        }
        TelephonyManager telephonyManager = (TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType();
    }

    /**
     * 在中国，联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     *
     * @param pContext
     * @return 2G、3G、4G、未知四种状态
     */
    public static int getNetworkClass(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return -1;
        }
        TelephonyManager telephonyManager = (TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetworkConstants.NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetworkConstants.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetworkConstants.NETWORK_CLASS_4_G;

            default:
                return NetworkConstants.NETWORK_CLASS_UNKNOWN;
        }
    }

    /**
     * 获取当前的网络链接状态
     * 0：其他
     * 1：WIFI
     * 2：2G
     * 3：3G
     * 4：4G
     *
     * @param pContext
     * @return 没有网络，2G，3G，4G，WIFI
     */
    public static int getNetworkState(Context pContext) {
        if (pContext == null) {
            Timber.tag(TAG).d("Context is null");
            return -1;
        }
        int netWorkType = NetworkConstants.NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) pContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NetworkConstants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetworkClass(pContext);
            }
        }
        return netWorkType;
    }

    /**
     * 获取设备
     *
     * @param pContext
     * @return
     */
    public static String getIp(Context pContext) {
        WifiManager wm = (WifiManager) pContext.getSystemService(Context.WIFI_SERVICE);
        //检查Wifi状态
        if (!wm.isWifiEnabled()) {
            wm.setWifiEnabled(true);
        }
        WifiInfo wi = wm.getConnectionInfo();
        //获取32位整型IP地址
        int ipAdd = wi.getIpAddress();
        //把整型地址转换成“*.*.*.*”地址
        return intToIp(ipAdd);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }
}
