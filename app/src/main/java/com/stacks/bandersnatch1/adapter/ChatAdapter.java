package com.stacks.bandersnatch1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stacks.bandersnatch1.R;
import com.stacks.bandersnatch1.model.Message;

import java.util.List;

import believe.cht.fadeintextview.TextView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<Message> messages;
	private Context context;
	private Boolean preload;

	public ChatAdapter(List<Message> messages, Context context) {
		this.messages = messages;
		this.context = context;
		preload = true;
	}

	@Override
	public int getItemViewType(int position) {
		if(messages.get(position).getBot())
			return 0;
		return 1;
//		return super.getItemViewType(position);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder;
		if(viewType == 0){
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.bot_says,
					parent,
					false
			);
			viewHolder = new BotMessage(view);
		}else{
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.user_says,
					parent,
					false
			);
			viewHolder = new UserMessage(view);
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		if(holder.getItemViewType() == 0) {
			((TextView) holder.itemView.findViewById(R.id.message)).setText(
					messages.get(position).getMessage()
			);
			if(position != messages.size()-1){
				((TextView) holder.itemView.findViewById(R.id.message)).setLetterDuration(
						10
				);
			}
		}else{
			((android.widget.TextView) holder.itemView.findViewById(R.id.message)).setText(
					messages.get(position).getMessage()
			);
		}
	}

	@Override
	public int getItemCount() {
		return messages.size();
	}

	public class BotMessage extends RecyclerView.ViewHolder{
		public BotMessage(@NonNull View itemView) {
			super(itemView);
		}
	}

	public class UserMessage extends RecyclerView.ViewHolder{
		public UserMessage(@NonNull View itemView) {
			super(itemView);
		}
	}
}
