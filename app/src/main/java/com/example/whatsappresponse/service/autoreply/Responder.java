package com.example.whatsappresponse.service.autoreply;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.whatsappresponse.service.model.MessageResponse;
import com.example.whatsappresponse.service.utils.AppExecutor;

import java.util.ArrayList;
import java.util.List;

public class Responder {

    private long delayMillis = 100;
    private Notification.Action actions[];
    private List<MessageResponse> listMessages;
    private Context context;
    private String textKey;

    public Responder(Context context,
                     Notification.Action actions[],
                     List<MessageResponse> listMessages,
                     String textMessage){

        this.context = context;
        this.actions = actions;
        this.listMessages = listMessages;
        this.textKey = textMessage;
    }


    public void sendMessageAsync(){

        AppExecutor.getInstance().getDiskIO().execute(()->{

            List<String> listMessage = new ArrayList<>();
            for(MessageResponse modelReply: listMessages){

                if(modelReply.getReceivedMessage().equalsIgnoreCase(textKey)){
                    Log.d("WASTE","Text Message matched: "+ textKey);
                    listMessage.add(modelReply.getReplyMessage());
                }
            }

            for(Notification.Action action: actions){

                if(action.getRemoteInputs() == null){
                    continue;
                }

                //Return if
                if(!action.title.toString().equalsIgnoreCase("reply")){
                    continue;
                }

                for (android.app.RemoteInput remoteInput : action.getRemoteInputs()) {

                    long millis = delayMillis;
                    for(String message: listMessage){


                        AppExecutor.getInstance().getMainThread().execute(()->{
                            send(message,
                                    remoteInput.getResultKey(),
                                    action,
                                    context);
                        });

                    }
                }
            }

        });

    }

    private void send(String message,
                      String resultKey,
                      Notification.Action action,
                      Context context){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(resultKey, message);
        android.app.RemoteInput.addResultsToIntent(action.getRemoteInputs(), intent, bundle);
        try {
            action.actionIntent.send(context, 0, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
