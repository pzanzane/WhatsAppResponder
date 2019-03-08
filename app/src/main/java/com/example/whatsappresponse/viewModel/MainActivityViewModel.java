package com.example.whatsappresponse.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.whatsappresponse.service.model.MessageResponse;
import com.example.whatsappresponse.service.utils.DatabaseRepository;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel implements
        InterfaceRepositories.CallBackToGetMessagList,
        InterfaceRepositories.CallBackForDeletion {

    private MutableLiveData<List<MessageResponse>> messageList;
    private MutableLiveData<Boolean> deletionStatus;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        messageList = new MutableLiveData<>();
        deletionStatus = new MutableLiveData<>();
    }

    public LiveData<List<MessageResponse>> getList(){
        new DatabaseRepository(getApplication()).getMessagesList(this);
        return messageList;
    }

    public LiveData<Boolean> deleteMessage(int messageId){
        new DatabaseRepository(getApplication()).delete(this,messageId);
        return deletionStatus;
    }

    @Override
    public void getMessageList(List<MessageResponse> list) {
        if (list == null){
            return;
        }
        messageList.postValue(list);
    }

    @Override
    public void deletionCompleted(int id) {
        if (id == 0)
            return;
        deletionStatus.postValue(true);
        new DatabaseRepository(getApplication()).getMessagesList(this);
    }
}
