package com.puma.nextwhere.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.puma.nextwhere.R;
import com.puma.nextwhere.model.ChatModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rajesh on 2/6/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int USERMESSAGE = 1, SENDERMESSAGE = 2;
    private ArrayList<ChatModel> chatData;

    public ChatAdapter(ArrayList<ChatModel> chatData) {
        this.chatData = chatData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == USERMESSAGE)
            return new CommonLayout(layoutInflater.inflate(R.layout.adapter_usermsg, parent, false));
        else if (viewType == SENDERMESSAGE)
            return new CommonLayout(layoutInflater.inflate(R.layout.adapter_sendermsg, parent, false));
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setMessage((CommonLayout) holder, position);
    }

    private void setMessage(CommonLayout holder, int position) {
        ChatModel chatModel = chatData.get(position);
        holder.message.setText(chatModel.getMessage());
        holder.displayTime.setText(chatModel.getTime());
    }

    @Override
    public int getItemViewType(int position) {
        return chatData.get(position).getMessageType();
    }

    @Override
    public int getItemCount() {
        return chatData == null ? 0 : chatData.size();
    }

    class CommonLayout extends RecyclerView.ViewHolder {
        @BindView(R.id.userImage)
        ImageView userImage;
        @BindView(R.id.message)
        TextView message;
        @BindView(R.id.displayTime)
        TextView displayTime;

        CommonLayout(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
