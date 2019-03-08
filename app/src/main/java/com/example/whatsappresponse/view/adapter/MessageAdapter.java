package com.example.whatsappresponse.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsappresponse.R;
import com.example.whatsappresponse.service.model.MessageResponse;
import com.example.whatsappresponse.view.ui.MainActivity;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<MessageResponse> list;

    public MessageAdapter(Context context, List<MessageResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        MessageResponse messageResponse = list.get(position);

        final int id = messageResponse.getId();
        viewHolder.receiveText.setText(messageResponse.getReceivedMessage());
        viewHolder.sentText.setText(messageResponse.getReplyMessage());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).deleteSelectedMessage(id);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<MessageResponse> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView receiveText, sentText;
        private ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveText = itemView.findViewById(R.id.receiveTextViewOutput);
            sentText = itemView.findViewById(R.id.replyTextViewOutput);
            delete = itemView.findViewById(R.id.deleteIcon);
        }
    }
}
