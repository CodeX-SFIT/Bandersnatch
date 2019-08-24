package com.stacks.bandersnatch1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stacks.bandersnatch1.adapter.ChatAdapter;
import com.stacks.bandersnatch1.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	RecyclerView chatView;
	CardView sendButton;
	CardView cameraButton;
	EditText txtInput;
	TextView btnContinue;

	private static JSONArray stories;

	private static int story_index;
	private static int operation_index;
	private static JSONArray current_operations;
	private View.OnClickListener sendMessage = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!txtInput.getText().toString().trim().equals("")){
				Message message = new Message();
				message.setMessage(txtInput.getText().toString());
				message.setBot(false);
				messageList.add(message);
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
			}
		}
	};

	ChatAdapter adapter;
	private List<Message> messageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		chatView = findViewById(R.id.chat_view);
		txtInput = findViewById(R.id.input_message);
		btnContinue = findViewById(R.id.btnContinue);

		try {
			stories = new JSONObject(AssetJSONFile("scripts/5.json")).getJSONArray("story");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		messageList = new ArrayList<>();


		chatView.setLayoutManager(new LinearLayoutManager(this));

		adapter = new ChatAdapter(messageList, this);
		chatView.setAdapter(adapter);

		sendButton = findViewById(R.id.send_container);
		sendButton.setOnClickListener(sendMessage);

//		sendButton.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				startActivity(new Intent(MainActivity.this, ChooserActivity.class));
//				return true;
//			}
//		});

		cameraButton = findViewById(R.id.camera_container);
		cameraButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JSONObject operation = current_operations.optJSONObject(operation_index);
				Intent intent = new Intent(MainActivity.this, ImageTargetsActivity.class);
				intent.putExtra("looking_for", operation.optString("looking_for"));
				startActivityForResult(intent, 101);
			}
		});

		startStory();
	}


	void startStory(){
		messageList.clear();
		adapter.notifyDataSetChanged();
		story_index = 0;
		executeStory();
	}

	private synchronized void executeStory(){
		current_operations = stories.optJSONObject(story_index).optJSONArray("operations");
		operation_index = 0;
		while(executeOperation()){
			operation_index++;
			if(operation_index == current_operations.length()) {
				Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	private void nextStory(){
		if(story_index == stories.length()){
			Toast.makeText(this, "All stories completed", Toast.LENGTH_SHORT).show();
		}else{
			executeStory();
		}
	}

	private boolean executeOperation(){
		JSONObject operation = current_operations.optJSONObject(operation_index);
		String type = operation.optString("type");

		switch (type){
			case "bot":
				Message message = new Message();
				message.setMessage(operation.optString("bot"));
				message.setBot(true);
				messageList.add(message);
				adapter.notifyItemInserted(messageList.size()-1);
				chatView.scrollToPosition(messageList.size()-1);
				return true;
			case "wait":
				Integer wait = operation.optInt("duration");
				CountDownTimer timer = new CountDownTimer(wait*1000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {

					}

					@Override
					public void onFinish() {
						operation_index++;
						while(executeOperation()){
							operation_index++;
							if(operation_index == current_operations.length()) {
								Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
								break;
							}
						}

					}
				};
				timer.start();
				return false;
			case "jump":
				story_index = operation.optInt("index");
				Toast.makeText(this, "Jumping", Toast.LENGTH_SHORT).show();
				nextStory();
				return false;

			case "user_wait":
				txtInput.setVisibility(View.GONE);
				btnContinue.setVisibility(View.VISIBLE);
				sendButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						txtInput.setVisibility(View.VISIBLE);
						btnContinue.setVisibility(View.GONE);
						operation_index++;
						while(executeOperation()){
							operation_index++;
							if(operation_index == current_operations.length()) {
								Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
								break;
							}
						}
					}
				});
				return false;
			case "ar":
				cameraButton.setVisibility(View.VISIBLE);
				txtInput.setVisibility(View.GONE);
				sendButton.setVisibility(View.GONE);

				return false;
			case "chooser":

				Bundle bundle = new Bundle();
				bundle.putString("prompt", operation.optString("prompt"));
				JSONArray options = operation.optJSONArray("options");
				bundle.putString("option0", options.optJSONObject(0).optString("text"));
				bundle.putString("option1", options.optJSONObject(1).optString("text"));
				Intent intent1 = new Intent(MainActivity.this, ChooserActivity.class);
				intent1.putExtras(bundle);
				startActivityForResult(intent1, 100);

				return false;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if(requestCode == 100 && resultCode == RESULT_OK){
			int selection = data.getIntExtra("selection", -1);
			if(selection != -1){
				JSONObject operation = current_operations.optJSONObject(operation_index);
				JSONArray options = operation.optJSONArray("options");
				story_index = options.optJSONObject(selection).optInt("index");
				nextStory();
			}
		}else if(requestCode == 101 && resultCode == RESULT_OK){

			cameraButton.setVisibility(View.GONE);
			sendButton.setVisibility(View.VISIBLE);
			txtInput.setVisibility(View.VISIBLE);

			operation_index++;
			while(executeOperation()){
				operation_index++;
				if(operation_index == current_operations.length()) {
					Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	public String AssetJSONFile (String filename) throws IOException {
		AssetManager manager = getAssets();
		InputStream file = manager.open(filename);
		byte[] formArray = new byte[file.available()];
		file.read(formArray);
		file.close();

		return new String(formArray);
	}
}
