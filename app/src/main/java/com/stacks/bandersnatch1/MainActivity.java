package com.stacks.bandersnatch1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
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
//	EditText txtInput;
	TextView btnContinue;

	public static final String TAG = "MainActivity";

	private static JSONArray stories;

	private static int story_index;
	private static int operation_index;
	private static JSONArray current_operations;

	private Boolean DEBUG = true;

	private TextView header;
	private ImageView bitmoji;
	ChatAdapter adapter;
	private List<Message> messageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Fabric.with(this, new Crashlytics());
		chatView = findViewById(R.id.chat_view);
//		txtInput = findViewById(R.id.input_message);
		btnContinue = findViewById(R.id.btnContinue);
		header = findViewById(R.id.name);
		bitmoji = findViewById(R.id.bitmoji);

		try {
			stories = new JSONObject(AssetJSONFile("scripts/draft3.json")).getJSONArray("story");
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
//		sendButton.setOnClickListener(sendMessage);

//		sendButton.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				startActivity(new Intent(MainActivity.this, ChooserActivity.class));
//				return true;
//			}
//		});

		sendButton.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(DEBUG){
					DEBUG = false;
					Toast.makeText(MainActivity.this, "DEBUG MODE DISABLE", Toast.LENGTH_SHORT).show();
				}else
				{
					DEBUG = true;
					Toast.makeText(MainActivity.this, "DEBUG MODE ENABLED", Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});

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

		logFBevent("start");

		messageList.clear();
		adapter.notifyDataSetChanged();
		story_index = 0;
		executeStory();
	}

	private synchronized void executeStory(){
		Log.d(TAG, "executeStory: " + story_index);

		logFBevent("executeStory: " + story_index);

		current_operations = stories.optJSONObject(story_index).optJSONArray("operations");
		operation_index = 0;
		execOp();
	}

	void execOp(){
		while(executeOperation()){
			operation_index++;
			if(operation_index == current_operations.length()) {
//				Toast.makeText(MainActivity.this, "Story completed", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}
	private void nextStory(){
		Log.d(TAG, "nextStory: " + story_index);
		logFBevent("nextStory: " + story_index);
		if(story_index == stories.length()){
//			Toast.makeText(this, "All stories completed", Toast.LENGTH_SHORT).show();
		}else{
			Message message = new Message();
			message.setSeparator(true);
			addMessageAndScroll(message);
			executeStory();
		}
	}

	void logFBevent(String temp){

		Bundle bundle = new Bundle();
		bundle.putString("event", temp);
		FirebaseAnalytics.getInstance(MainActivity.this).logEvent("event", bundle);
	}

	private boolean executeOperation(){
		JSONObject operation = current_operations.optJSONObject(operation_index);
		String type = operation.optString("type");
		String temp = String.format("executeOperation: %d %d %s", story_index, operation_index, type);
		Log.d(TAG, temp);
		logFBevent(temp);

		switch (type){
			case "bot":
				Message message = new Message();
				message.setMessage(operation.optString("bot"));
				message.setBot(true);
				String character = operation.optString("character");
				if(character != null){
					message.setCharacter_name(character);
					switch (character){
						case "Front Office Personnel":
							message.setCharacter_pic(R.mipmap.front_office_personnel);
							break;
						case "Bank Manager":
							message.setCharacter_pic(R.mipmap.bank_manager);
							break;
						case "Brazilian Officer":
							message.setCharacter_pic(R.mipmap.brazilian_officer);
							break;
						case "Dominov":
							message.setCharacter_pic(R.mipmap.dominov);
							break;
						case "Egypt intelligence Officer":
							message.setCharacter_pic(R.mipmap.egypt_intelligence_officer);
							break;
						case "MIB":
							message.setCharacter_pic(R.mipmap.mib);
							break;
						case "Mafia Thug":
							message.setCharacter_pic(R.mipmap.mafia_thug);
							break;
						case "Nirav":
							message.setCharacter_pic(R.mipmap.nirav_modi);
							break;
						case "RAW":
							message.setCharacter_pic(R.mipmap.raw_incharge);
							break;
						case "Supreme Leader":
							message.setCharacter_pic(R.mipmap.supreme_leader);
							break;
						case "annie":
							message.setCharacter_pic(R.mipmap.annie);
							break;
						default:
							message.setCharacter_pic(R.mipmap.ic_launcher);
							message.setCharacter_name("The Bot");
							break;
					}
				}else {
					message.setCharacter_pic(R.mipmap.ic_launcher);
					message.setCharacter_name("The Bot");
				}

				if(message.getCharacter_name() != null){
					header.setText(message.getCharacter_name());
					Glide.with(MainActivity.this)
							.load(message.getCharacter_pic())
							.into(bitmoji);
				}
				addMessageAndScroll(message);

				if(operation.has("wait")){
					Integer wait = DEBUG?1000:(1000*operation.optInt("wait"));
					CountDownTimer timer = new CountDownTimer(wait, 1000) {
						@Override
						public void onTick(long millisUntilFinished) {}

						@Override
						public void onFinish() {
							incrementOpAndNext();
						}
					};
					timer.start();
					return false;
				}
				return true;
			case "wait":
				Integer wait = DEBUG?1000:(1000*operation.optInt("duration"));
				CountDownTimer timer = new CountDownTimer(wait, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {}

					@Override
					public void onFinish() {
						incrementOpAndNext();
					}
				};
				timer.start();

				return false;
			case "jump":
				story_index = operation.optInt("index");
//				Toast.makeText(this, "Jumping", Toast.LENGTH_SHORT).show();
				nextStory();
				return false;

			case "user_wait":
//				txtInput.setVisibility(View.GONE);
				btnContinue.setVisibility(View.VISIBLE);
				sendButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
//						txtInput.setVisibility(View.VISIBLE);
						btnContinue.setVisibility(View.GONE);
						sendButton.setOnClickListener(null);
						incrementOpAndNext();
					}
				});
				return false;
			case "ar":
				cameraButton.setVisibility(View.VISIBLE);
//				txtInput.setVisibility(View.GONE);
				sendButton.setVisibility(View.GONE);

				return false;

			case "user":
				Message message1 = new Message();
				message1.setMessage(operation.optString("user"));
				message1.setBot(false);
				addMessageAndScroll(message1);
				return true;

			case "separator":
				Message message2 = new Message();
				message2.setSeparator(true);
				message2.setBot(false);
				addMessageAndScroll(message2);
				return true;

			case "chooser":
				Bundle bundle1 = new Bundle();
				bundle1.putString("prompt", operation.optString("prompt"));
				JSONArray options = operation.optJSONArray("options");
				bundle1.putString("option0", options.optJSONObject(0).optString("text"));
				bundle1.putString("option1", options.optJSONObject(1).optString("text"));
				Intent intent1 = new Intent(MainActivity.this, ChooserActivity.class);
				intent1.putExtras(bundle1);
				startActivityForResult(intent1, 100);

				return false;

			case "image":
				Message message3 = new Message();
//				switch (operation.optString("image")){
//					// TODO: 27-08-2019 add image
//				}
				message3.setImage(R.mipmap.ic_launcher);
				addMessageAndScroll(message3);
				return true;
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
//			txtInput.setVisibility(View.VISIBLE);

			incrementOpAndNext();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	void incrementOpAndNext(){
		operation_index++;
		execOp();
	}

	public String AssetJSONFile (String filename) throws IOException {
		AssetManager manager = getAssets();
		InputStream file = manager.open(filename);
		byte[] formArray = new byte[file.available()];
		file.read(formArray);
		file.close();

		return new String(formArray);
	}

	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}

	void addMessageAndScroll(Message message){
		messageList.add(message);
		adapter.notifyItemInserted(messageList.size()-1);
		chatView.scrollToPosition(messageList.size()-1);
	}

	private void save(){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
	}
}
