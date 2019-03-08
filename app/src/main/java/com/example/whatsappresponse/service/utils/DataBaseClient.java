package com.example.whatsappresponse.service.utils;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DataBaseClient {
    private Context context;
    private MainDatabase mainDatabase;
    private static DataBaseClient clientInstance = null;

    private DataBaseClient(Context context) {
        this.context = context;
        mainDatabase = Room.databaseBuilder(context,MainDatabase.class,"MessageDatabase").build();
    }

    public static DataBaseClient getInstance(Context context){
        if (clientInstance == null){
            clientInstance = new DataBaseClient(context);
        }
        return clientInstance;
    }

    public MainDatabase getMainDatabase(){
        return mainDatabase;
    }
}
