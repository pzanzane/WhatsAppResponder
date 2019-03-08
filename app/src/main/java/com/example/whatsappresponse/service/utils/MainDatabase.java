package com.example.whatsappresponse.service.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.whatsappresponse.service.dao.MessageResponseDao;
import com.example.whatsappresponse.service.model.MessageResponse;

@Database(entities = {MessageResponse.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {
    public abstract MessageResponseDao messageResponseDao();
}
