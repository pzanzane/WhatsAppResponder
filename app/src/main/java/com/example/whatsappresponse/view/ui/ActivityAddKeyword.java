package com.example.whatsappresponse.view.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.service.utils.InterfaceRepositories;
import com.example.whatsappresponse.view.adapter.AddMessageAdapter;
import com.example.whatsappresponse.view.adapter.UtilItemDecorators;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        multiViewsList = new ArrayList<MultiView>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterAddMessage = new AddMessageAdapter(this, multiViewsList);
        adapterAddMessage.addReceivedItem();
        adapterAddMessage.addDefaultReplyItem();

        recyclerView.setAdapter(adapterAddMessage);
        recyclerView.addItemDecoration(UtilItemDecorators.getSpaceDecorator(this,R.drawable.shape_drawable));
        viewModelAddKeyword = ViewModelProviders.of(this).get(AddKeywordViewModel.class);
        buttonAddKey.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        List<MultiView> listMessages = adapterAddMessage.getList();

        String message = listMessages.get(0).getMessage();

        if(TextUtils.isEmpty(message)){
            Toast.makeText(this,"Recieved text cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        for(MultiView multiView: listMessages){
            if(TextUtils.isEmpty(multiView.getMessage())){
                Toast.makeText(this,"Reply cannot be empty",Toast.LENGTH_SHORT).show();
                return;
            }
        }

        listMessages.remove(0);
        for(MultiView multiView: listMessages){

            String receive = message;
            String sent = multiView.getMessage();

            viewModelAddKeyword.insertMessages(receive.toLowerCase(),sent).observe(ActivityAddKeyword.this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean success) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            });

        }

    }
}
