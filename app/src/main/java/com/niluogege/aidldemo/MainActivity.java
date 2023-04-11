package com.niluogege.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.niluogege.service.TestAidlInterface;

/**
 * 客户端，用于调用服务
 */
public class MainActivity extends AppCompatActivity {
    //实际是 TestAidlInterface.Stub.Proxy 对象
    TestAidlInterface service;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//这里的service就是 服务端Service onBind 方法中返回的 Stub对象。
            Log.d("MainActivity", "onServiceConnected name:" + name + " IBinder= " + service);
            MainActivity.this.service = TestAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity", "onServiceDisconnected name:" + name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.niluogege.aidl.TestAidlService");
                //设置服务端 Service包名
//                intent.setPackage("com.niluogege.service");
                //同一个进程下 使用aidl测试
                intent.setPackage("com.niluogege.aidldemo");
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.tv_do).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int add = service.calculation(1, 3);
                    Log.d("MainActivity", "add:" + add);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.tv_test_any).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long start = SystemClock.currentThreadTimeMillis();
                    service.testANR();
                    long end = SystemClock.currentThreadTimeMillis();
                    Log.d("MainActivity", "tv_test_any use time" + (end-start)+" ms");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.tv_unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });


    }
}
