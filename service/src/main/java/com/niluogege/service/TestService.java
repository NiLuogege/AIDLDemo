package com.niluogege.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Created by niluogege on 2020/4/13.
 *
 * 服务端，用于提供服务
 */
public class TestService extends Service {
    private void Log(String str) {
        Log.e("TestService", "----------" + str + "----------");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log("service created");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log("service onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log("service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log("service onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log("service onBind");
        return mbinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log("service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log("service onRebind");
    }

    TestAidlInterface.Stub mbinder = new TestAidlInterface.Stub() {

        @Override
        public int calculation(int a, int b) throws RemoteException {
            return a + b;
        }
    };

}
