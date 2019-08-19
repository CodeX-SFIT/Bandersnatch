package com.stacks.bandersnatch1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.stacks.bandersnatch1.adapter.ChatAdapter;
import com.stacks.bandersnatch1.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	RecyclerView chatView;
	CardView sendButton;
	EditText txtInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		chatView = findViewById(R.id.chat_view);
		txtInput = findViewById(R.id.input_message);

		final List<Message> messageList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Message message = new Message();
			message.setBot(i%2==0);
			message.setMessage("Thkljdlaksjkl;asj d ladsjd jlhcl\n dlasjdlkas dlkasj dlkasj lkdas jldkjla lkasjd lksaj dlkasj laksdlk");
			messageList.add(message);
		}

		chatView.setLayoutManager(new LinearLayoutManager(this));
		chatView.setAdapter(new ChatAdapter(messageList, this));

		sendButton = findViewById(R.id.send_container);
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				startActivity(new Intent(MainActivity.this, ChooserActivity.class));
				if(!txtInput.getText().toString().trim().equals("")){
					Message message = new Message();
					message.setMessage(txtInput.getText().toString());
					message.setBot(false);
					messageList.add(message);
//					chatView.notifyAll();
					chatView.getAdapter().notifyItemInserted(messageList.size()-1);
					chatView.scrollToPosition(messageList.size()-1);
					txtInput.setText("");
					CountDownTimer timer = new CountDownTimer(5000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {

						}

						@Override
						public void onFinish() {
							Message message1 = new Message();
							message1.setMessage("This is a response");
							message1.setBot(true);
							messageList.add(message1);
							chatView.getAdapter().notifyItemInserted(messageList.size()-1);
							chatView.scrollToPosition(messageList.size()-1);
						}
					};
					timer.start();
//					chatView.
				}
			}
		});

		sendButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(MainActivity.this, ChooserActivity.class));
				return true;
			}
		});

	}
}
