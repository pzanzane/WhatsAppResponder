package com.example.whatsappresponse.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.EnumViewTypes;
import com.example.whatsappresponse.service.model.MultiView;
import com.example.whatsappresponse.view.ui.ActivityAddKeyword;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener,
        CallBackTextWatcher {

    private Context context;
    private List<MultiView> multiViewsList;
    private int itemCount = 0;
    private MyOnFocused myOnFocused = null;
    private MyTextWatcher myTextWatcher = null;

    public AddMessageAdapter(Context context, List<MultiView> multiViewsList) {
        this.context = context;
        this.multiViewsList = multiViewsList;
        this.myTextWatcher = new MyTextWatcher(this);
        this.myOnFocused = new MyOnFocused(myTextWatcher);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        switch (EnumViewTypes.values()[viewType]){
            case RECEIVED:
                view = LayoutInflater.from(context).inflate(R.layout.received,viewGroup,false);
                return new ViewHolderReceived(view, this);

            case REPLYADD:
                view = LayoutInflater.from(context).inflate(R.layout.reply_add, viewGroup,false);
                return new ViewHolderReplyAdd(view);

            case REPLYDELETE:
                view = LayoutInflater.from(context).inflate(R.layout.reply_delete, viewGroup, false);
                return new ViewHolderReplyDelete(view, this);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
       switch (multiViewsList.get(position).getEnumViewTypes()){
           case RECEIVED:
                   return EnumViewTypes.RECEIVED.ordinal();
           case REPLYADD:
                   return EnumViewTypes.REPLYADD.ordinal();
           case REPLYDELETE:
                   return EnumViewTypes.REPLYDELETE.ordinal();
               default:
                   return -1;
           }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        MultiView multiView = multiViewsList.get(position);
        EnumViewTypes enumView = EnumViewTypes.values()[viewHolder.getItemViewType()];
        switch (enumView){
            case RECEIVED:
                ViewHolderReceived viewHolderReceived = (ViewHolderReceived) viewHolder;
                viewHolderReceived.imageViewReplyAdd.setTag(position);
                viewHolderReceived.imageViewReplyAdd.setOnClickListener(this);
                viewHolderReceived.editText.setTag(position);
                viewHolderReceived.editText.setOnFocusChangeListener(myOnFocused);
                viewHolderReceived.editText.setText(multiView.getMessage());

                break;
            case REPLYADD:

                ViewHolderReplyAdd viewHolderReplyAdd = (ViewHolderReplyAdd) viewHolder;
                viewHolderReplyAdd.editText.setTag(position);
                viewHolderReplyAdd.editText.setOnFocusChangeListener(myOnFocused);
                viewHolderReplyAdd.editText.setText(multiView.getMessage());

                break;
            case REPLYDELETE:
                ViewHolderReplyDelete viewHolderReplyDelete = (ViewHolderReplyDelete) viewHolder;
                viewHolderReplyDelete.imageViewReplyDelete.setTag(position);
                viewHolderReplyDelete.imageViewReplyDelete.setOnClickListener(this);
                viewHolderReplyDelete.editText.setTag(position);
                viewHolderReplyDelete.editText.setOnFocusChangeListener(myOnFocused);
                viewHolderReplyDelete.editText.setText(multiView.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return multiViewsList.size();
    }

    @Override
    public void onClick(View v) {

        int position = Integer.parseInt(String.valueOf(v.getTag()));
        switch (v.getId()){
            case R.id.addReplyIcon:
                itemCount++;
                multiViewsList.add(new MultiView("",itemCount,EnumViewTypes.REPLYDELETE));
                Collections.sort(multiViewsList, new Comparator<MultiView>() {
                    @Override
                    public int compare(MultiView left, MultiView right) {
                        if(left.getId() > right.getId()){
                            return -1;
                        }else if(left.getId() < right.getId()){
                            return 1;
                        }
                        return 0;
                    }
                });
                notifyDataSetChanged();
                break;

            case R.id.deleteIcon:
                multiViewsList.remove(position);
                notifyDataSetChanged();
                break;
        }



    }

    public void addReceivedItem(){
        multiViewsList.add(new MultiView("",10000, EnumViewTypes.RECEIVED));
    }

    public void addDefaultReplyItem(){
        multiViewsList.add(new MultiView("", itemCount,EnumViewTypes.REPLYADD));
    }

    @Override
    public void callback(int position, String message) {
        MultiView multiView = multiViewsList.get(position);
        multiView.setMessage(message);
    }

    public List<MultiView> getList(){
        return multiViewsList;
    }
    public static class ViewHolderReceived extends RecyclerView.ViewHolder {

        private ImageView imageViewReplyAdd;
        private EditText editText;

        public ViewHolderReceived(@NonNull View itemView,
                                  View.OnClickListener onClickListener) {
            super(itemView);
            imageViewReplyAdd = itemView.findViewById(R.id.addReplyIcon);
            imageViewReplyAdd.setOnClickListener(onClickListener);
            editText = itemView.findViewById(R.id.receiveEditText);
        }

    }

    public static class ViewHolderReplyAdd extends RecyclerView.ViewHolder {


        private EditText editText;

        public ViewHolderReplyAdd(@NonNull View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.replyEditText);
        }
    }

    public static class ViewHolderReplyDelete extends RecyclerView.ViewHolder {
        private ImageView imageViewReplyDelete;
        private EditText editText;

        public ViewHolderReplyDelete(@NonNull View itemView,
                                     View.OnClickListener onClickListener) {
            super(itemView);
            imageViewReplyDelete = itemView.findViewById(R.id.deleteIcon);
            imageViewReplyDelete.setOnClickListener(onClickListener);
            editText = itemView.findViewById(R.id.replyEditText);
        }
    }

    private static class MyOnFocused implements View.OnFocusChangeListener{

        private MyTextWatcher myTextWatcher = null;
        public MyOnFocused(MyTextWatcher myTextWatcher){
            this.myTextWatcher = myTextWatcher;
        }
        @Override
        public void onFocusChange(View view, boolean isFocused) {

            if(view instanceof EditText){
                EditText editText = ((EditText)view);

                if(isFocused){
                    int position = Integer.parseInt(String.valueOf(view.getTag()));
                    Log.d("WASTE","Focused: "+position);

                    myTextWatcher.setPosition(position);
                    editText.addTextChangedListener(myTextWatcher);


                }else{
                    editText.removeTextChangedListener(myTextWatcher);
                }
            }

        }
    }
    public static class MyTextWatcher implements TextWatcher{

        private int position;
        private CallBackTextWatcher callBack;

        public MyTextWatcher(CallBackTextWatcher callBack){
            this.callBack = callBack;
        }
        public void setPosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.d("WASTE","Position: "+position+ " Text: "+charSequence.toString());
            callBack.callback(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
