package com.stacks.bandersnatch1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
//import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stacks.bandersnatch1.R;
import com.stacks.bandersnatch1.model.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<Message> messages;
	private Context context;
	private Boolean preload;

	public ChatAdapter(List<Message> messages, Context context) {
		this.messages = messages;
		this.context = context;
	}

	@Override
	public int getItemViewType(int position) {

		if(messages.get(position).getImage() != -1)
			return -2;
		else if(messages.get(position).getSeparator())
			return -1;
		else if(messages.get(position).getBot())
			return 0;
		else
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
		}else if(viewType == -1){
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.text_separator,
					parent,
					false
			);
			viewHolder = new RecyclerView.ViewHolder(view){};
		}else if(viewType == -2){
			View view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.bot_image,
					parent,
					false
			);
			viewHolder = new RecyclerView.ViewHolder(view) {};
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

//		if(holder.getItemViewType() == 0) {
//			((TextView) holder.itemView.findViewById(R.id.message)).setText(
//					messages.get(position).getMessage()
//			);
//			if(position != messages.size()-1){
//				((TextView) holder.itemView.findViewById(R.id.message)).setLetterDuration(
//						10
//				);
//			}
//		}else{


		if(holder.getItemViewType() == -1)
			return;

		if(holder.getItemViewType() == -2){
//			if(messages.get(position).getCharacter_pic() != -1){
//				Glide.with(context)
//						.load(messages.get(position).getCharacter_pic())
//						.into((ImageView)holder.itemView.findViewById(R.id.bot_icon));
//			}
//			if(position != 0){
//				if(messages.get(position - 1).getCharacter_name() != null &&
//						messages.get(position).getCharacter_name() != null) {
//					if (messages.get(position - 1).getCharacter_name().equals(messages.get(position).getCharacter_name())) {
//						holder.itemView.findViewById(R.id.icon_container).setVisibility(View.INVISIBLE);
//					}else{
//						holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
//					}
//				}else{
//					holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
//				}
//			}else{
//				holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
//			}
			Glide.with(context)
					.load(messages.get(position).getImage())
					.into((ImageView) holder.itemView.findViewById(R.id.bot_image));
			return;
		}

		((android.widget.TextView) holder.itemView.findViewById(R.id.message)).setText(
				messages.get(position).getMessage()
		);
		if(holder.getItemViewType() == 0){
			if(messages.get(position).getCharacter_pic() != -1){
				Glide.with(context)
						.load(messages.get(position).getCharacter_pic())
						.into((ImageView)holder.itemView.findViewById(R.id.bot_icon));
			}
			if(position != 0){
				if(messages.get(position - 1).getCharacter_name() != null &&
						messages.get(position).getCharacter_name() != null) {
					if (messages.get(position - 1).getCharacter_name().equals(messages.get(position).getCharacter_name())) {
						holder.itemView.findViewById(R.id.icon_container).setVisibility(View.INVISIBLE);
					}else{
						holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
					}
				}else{
					holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
				}
			}else{
				holder.itemView.findViewById(R.id.icon_container).setVisibility(View.VISIBLE);
			}
		}
//		}
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
