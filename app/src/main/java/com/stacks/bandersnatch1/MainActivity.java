package com.stacks.bandersnatch1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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

	public static final String TAG = "MainActivity";

	private static JSONArray stories;

	private static int story_index;
	private static int operation_index;
	private static JSONArray current_operations;

	private Boolean DEBUG = true;

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
			stories = new JSONObject(AssetJSONFile("scripts/draft1.json")).getJSONArray("story");
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
		Log.d(TAG, "startStory: ");
		messageList.clear();
		adapter.notifyDataSetChanged();
		story_index = 0;
		executeStory();
	}

	private synchronized void executeStory(){
		Log.d(TAG, "executeStory: " + story_index);
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
		Log.d(TAG, "nextStory: " + story_index);
		if(story_index == stories.length()){
			Toast.makeText(this, "All stories completed", Toast.LENGTH_SHORT).show();
		}else{
			executeStory();
		}
	}

	private boolean executeOperation(){
		JSONObject operation = current_operations.optJSONObject(operation_index);
		String type = operation.optString("type");
		Log.d(TAG, "executeOperation: " + story_index + " " + operation_index + " " + type);
		switch (type){
			case "bot":
				Message message = new Message();
				message.setMessage(operation.optString("bot"));
				message.setBot(true);
				String character = operation.optString("character");
				if(character != null){
					message.setCharacter_name(character);
					switch (character){
						case "fop":
							// TODO: 26-08-2019 set header here
							message.setCharacter_pic(R.mipmap.fop);
							break;
//						case "anita":
//							message.setCharacter_pic(R.mipmap.anita);
					}
				}
				messageList.add(message);
				adapter.notifyItemInserted(messageList.size()-1);
				chatView.scrollToPosition(messageList.size()-1);

				if(operation.has("wait")){
					CountDownTimer timer = new CountDownTimer(operation.optInt("wait")*1000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {
							
						}

						@Override
						public void onFinish() {
							operation_index++;
							while (executeOperation()) {
								operation_index++;
								if (operation_index == current_operations.length()) {
									Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
									break;
								}
							}
						}
					};
					timer.start();
					return false;
				}
				return true;
			case "wait":
				Integer wait = operation.optInt("duration");
				if(!DEBUG) {
					CountDownTimer timer = new CountDownTimer(wait * 1000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {

						}

						@Override
						public void onFinish() {
							operation_index++;
							while (executeOperation()) {
								operation_index++;
								if (operation_index == current_operations.length()) {
									Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
									break;
								}
							}

						}
					};
					timer.start();
				}else{
					CountDownTimer timer = new CountDownTimer(1000, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {

						}

						@Override
						public void onFinish() {
							operation_index++;
							while (executeOperation()) {
								operation_index++;
								if (operation_index == current_operations.length()) {
									Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
									break;
								}
							}

						}
					};
					timer.start();
				}
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

			case "user":
				Message message1 = new Message();
				message1.setMessage(operation.optString("user"));
				message1.setBot(false);
				messageList.add(message1);
				adapter.notifyItemInserted(messageList.size()-1);
				chatView.scrollToPosition(messageList.size()-1);
				return true;

			case "separator":
				Message message2 = new Message();
				message2.setSeparator(true);
				message2.setBot(false);
				messageList.add(message2);
				adapter.notifyItemInserted(messageList.size()-1);
				chatView.scrollToPosition(messageList.size()-1);
				return true;

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

			case "image":
				Message message3 = new Message();
//				switch (operation.optString("image")){
//					// TODO: 27-08-2019
//				}
				message3.setImage(R.mipmap.ic_launcher);
				messageList.add(message3);
				adapter.notifyItemInserted(messageList.size()-1);
				chatView.scrollToPosition(messageList.size()-1);
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
