package com.example.whatsappresponse.view.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;
import com.example.whatsappresponse.view.adapter.AddMessageAdapter;
import com.example.whatsappresponse.viewModel.AddKeywordViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityAddKeyword extends AppCompatActivity implements View.OnClickListener {
    private Button buttonAddKey;
    private AddKeywordViewModel viewModelAddKeyword;
    private RecyclerView recyclerView;
    private AddMessageAdapter adapterAddMessage;
    private List<MultiView> multiViewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        buttonAddKey = findViewById(R.id.addKey);
        recyclerView = findViewById(R.id.addMessageRecycler);

        multiViewsList = new ArrayList<MultiView>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterAddMessage = new AddMessageAdapter(this, multiViewsList);
        adapterAddMessage.addReceivedItem();
        adapterAddMessage.addDefaultReplyItem();

        recyclerView.setAdapter(adapterAddMessage);
        viewModelAddKeyword = ViewModelProviders.of(this).get(AddKeywordViewModel.class);
        buttonAddKey.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        List<MultiView> listMessages = adapterAddMessage.getList();

        String message = listMessages.get(0).getMessage();
        listMessages.remove(0);

        for(MultiView multiView: listMessages){

            String receive = message;
            String sent = multiView.getMessage();

            viewModelAddKeyword.insertMessages(receive,sent).observe(ActivityAddKeyword.this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean success) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });

        }

    }
}
