package com.example.whatsappresponse.service.autoreply;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.example.whatsappresponse.service.model.MessageResponse;
import com.example.whatsappresponse.service.utils.DatabaseRepository;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;

import java.util.ArrayList;
import java.util.List;

public class NotificationListenerServiceWhatsapp extends NotificationListenerService
implements InterfaceRepositories.CallBackToGetMessagList {

    private final String PACKAGENAME_WHATSAPP = "com.whatsapp";
    private Notification.Action actions[] = null;
    private String messageKey = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Notification notification  = sbn.getNotification();
        actions = notification.actions;
        Bundle extras = sbn.getNotification().extras;

        String packageName = sbn.getPackageName();
        String title = extras.getString("android.title");
        messageKey = extras.getCharSequence("android.text").toString().toLowerCase();

         if(actions==null){
            return;
        }

        if(!PACKAGENAME_WHATSAPP.equalsIgnoreCase(sbn.getPackageName())){
            return;
        }


        //print(actions, packageName, messageKey);
        new DatabaseRepository(getApplication()).getMessagesForKey(messageKey, this);


        }


    private void print(Notification.Action actions[], String packageName,String text){


        Log.d("WASTE","PackageName: "+ packageName+"\n"+" text: "+text.toLowerCase());
        for(Notification.Action action: actions){
            Log.d("WASTE","action.title: "+action.title);
        }
        Log.d("WASTE","=============================>");
        Log.d("WASTE","=============================>");

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void getMessageList(List<MessageResponse> list) {

        if(list == null || list.size()<=0){
            return;
        }

        Responder responder = new Responder(getApplicationContext(),
                actions,
                list,
                messageKey);
        responder.sendMessageAsync();

    }
}
