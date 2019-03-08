package com.example.whatsappresponse.service.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor executorInstance;
    private final Executor diskIO;
    private final Executor mainThread;

    private AppExecutor(Executor diskIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public static class MainThreadCreate implements Executor{
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }

    public static AppExecutor getInstance(){
        if (executorInstance == null){
            executorInstance = new AppExecutor(Executors.newFixedThreadPool(1), new MainThreadCreate());
        }
        return executorInstance;
    }

    public Executor getDiskIO(){
        return diskIO;
    }

    public Executor getMainThread(){
        return mainThread;
    }
}
