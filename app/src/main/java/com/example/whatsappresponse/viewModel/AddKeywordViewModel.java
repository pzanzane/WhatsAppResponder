package com.example.whatsappresponse.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.whatsappresponse.service.utils.DatabaseRepository;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;

public class AddKeywordViewModel extends AndroidViewModel implements InterfaceRepositories.CallBackForInsertion {
    private MutableLiveData<Boolean> insertionStatus;


    public AddKeywordViewModel(@NonNull Application application) {
        super(application);
        insertionStatus = new MutableLiveData<>();

    }

    public LiveData<Boolean> insertMessages(String receivedMessage, String sentMessage){
        new DatabaseRepository(getApplication()).insert(this,receivedMessage,sentMessage);
        return insertionStatus;
    }


    @Override
    public void insertionCompleted(long id) {
        if (id == 0)
            return;

        insertionStatus.postValue(true);
    }


}
