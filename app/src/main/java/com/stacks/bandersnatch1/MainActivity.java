package com.stacks.bandersnatch1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.stacks.bandersnatch1.adapter.ChatAdapter;
import com.stacks.bandersnatch1.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	RecyclerView chatView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		chatView = findViewById(R.id.chat_view);

		List<Message> messageList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Message message = new Message();
			message.setBot(i%2==0);
			message.setMessage("Thkljdlaksjkl;asj d ladsjd jlhcl\n dlasjdlkas dlkasj dlkasj lkdas jldkjla lkasjd lksaj dlkasj laksdlk");
			messageList.add(message);
		}

		chatView.setLayoutManager(new LinearLayoutManager(this));
		chatView.setAdapter(new ChatAdapter(messageList, this));

	}
}
