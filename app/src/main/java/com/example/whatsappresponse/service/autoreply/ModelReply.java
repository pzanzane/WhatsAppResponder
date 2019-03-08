package com.example.whatsappresponse.service.autoreply;

import java.util.List;

public class ModelReply {

    private String key;
    private List<String> listMessages;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getMessage() {
        return listMessages;
    }

    public void setMessage(List<String> listMessages) {
        this.listMessages = listMessages;
    }
}
