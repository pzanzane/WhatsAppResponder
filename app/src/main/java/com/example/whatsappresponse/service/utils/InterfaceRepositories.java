package com.example.whatsappresponse.service.utils;

import com.example.whatsappresponse.service.model.MessageResponse;

import java.util.List;

public class InterfaceRepositories {
    public interface CallBackToGetMessagList{
        void getMessageList(List<MessageResponse> list);
    }

    public interface CallBackForInsertion{
        void insertionCompleted(long id);
    }

    public interface CallBackForDeletion{
        void deletionCompleted(int id);
    }

    public interface OnClickDelete{
        void deleteSelectedMessage(int id);
    }
}
