package com.example.whatsappresponse.service.utils;

import android.content.Context;

import com.example.whatsappresponse.service.model.MessageResponse;

import java.util.List;

public class DatabaseRepository {
    private Context context;

    public DatabaseRepository(Context context) {
        this.context = context;
    }

    public void getMessagesForKey(String key,
                                  final InterfaceRepositories.CallBackToGetMessagList callBackToGetMessagList){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<MessageResponse> list = DataBaseClient.getInstance(context)
                        .getMainDatabase().messageResponseDao().getMessagesForKey(key);
                AppExecutor.getInstance().getMainThread().execute(()->{
                    callBackToGetMessagList.getMessageList(list);
                });
            }
        });
    }

    public void getMessagesList(final InterfaceRepositories.CallBackToGetMessagList callBackToGetMessagList){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<MessageResponse> list = DataBaseClient.getInstance(context)
                        .getMainDatabase().messageResponseDao().getAllMessages();
                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackToGetMessagList.getMessageList(list);
                    }
                });
            }
        });
    }

    public void insert(final InterfaceRepositories.CallBackForInsertion callBackForInsertion, final String receiveMessage,
                       final String sentMessage){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setReceivedMessage(receiveMessage);
                messageResponse.setReplyMessage(sentMessage);

                final long id = DataBaseClient.getInstance(context).getMainDatabase().messageResponseDao()
                        .insertMessages(messageResponse);
                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackForInsertion.insertionCompleted(id);
                    }
                });
            }
        });
    }

    public void delete(final InterfaceRepositories.CallBackForDeletion callBackForDeletion, final int messageId){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final int id = DataBaseClient.getInstance(context)
                        .getMainDatabase().messageResponseDao().deleteMessage(messageId);
                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBackForDeletion.deletionCompleted(id);
                    }
                });
            }
        });
    }
}
