package com.example.whatsappresponse.service.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.whatsappresponse.service.model.MessageResponse;

import java.util.List;

@Dao
public interface MessageResponseDao {
    @Insert
    long insertMessages(MessageResponse messageResponse);

    @Query("select * from MessageResponse")
    List<MessageResponse> getAllMessages();

    @Query("select * from MessageResponse where receivedMessage=:key")
    List<MessageResponse> getMessagesForKey(String key);

    @Query("delete from MessageResponse where id=:messageId")
    int deleteMessage(int messageId);
}
