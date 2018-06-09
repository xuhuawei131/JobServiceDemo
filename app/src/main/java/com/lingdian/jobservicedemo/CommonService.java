package com.lingdian.jobservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CommonService extends Service {
    public CommonService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("xhw","CommonService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("xhw","CommonService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("xhw","CommonService onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
