package com.stacks.bandersnatch1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stacks.bandersnatch1.adapter.ChatAdapter;
import com.stacks.bandersnatch1.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	RecyclerView chatView;
	CardView sendButton;
	EditText txtInput;

	String strStories = "{\n" +
			"  \"story\": [\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Welcome to this game.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Here are your instructions.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"ar\",\n" +
			"          \"name\": \"stones\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"goto\",\n" +
			"          \"index\": 1\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Great you found the stones\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Choose wisely...\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"chooser\",\n" +
			"          \"prompt\": \"Bad boys, bad boys whatcha gonna do? Whacha gonna do when they come for you?\",\n" +
			"          \"options\": [\n" +
			"            {\n" +
			"              \"text\": \"RUN\",\n" +
			"              \"index\": \"0\"\n" +
			"            },\n" +
			"            {\n" +
			"              \"text\": \"HIDE\",\n" +
			"              \"index\": \"2\"\n" +
			"            }\n" +
			"          ]\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"You chose wisely, son.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Goodbye.\"\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  ]\n" +
			"}";

	String strStory2 = "{\n" +
			"  \"story\": [\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Welcome to this game.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Here are your instructions.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Jumping to a new story.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"jump\",\n" +
			"          \"index\": \"1\"\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Welcome to this game.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Here are your instructions.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Stories end here\"\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  ]\n" +
			"}";

	String strStory3 = "{\n" +
			"  \"story\": [\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Welcome to this game.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Here are your instructions.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Jumping to a new story.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"jump\",\n" +
			"          \"index\": \"1\"\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Welcome to this game.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Here are your instructions.\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"chooser\",\n" +
			"          \"prompt\": \"Bad boys, bad boys whatcha gonna do? Whatcha gonna do when they come for you?\",\n" +
			"          \"options\": [\n" +
			"            {\n" +
			"              \"text\": \"RUN\",\n" +
			"              \"index\": \"2\"\n" +
			"            },\n" +
			"            {\n" +
			"              \"text\": \"HIDE\",\n" +
			"              \"index\": \"3\"\n" +
			"            }\n" +
			"          ]\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"You chose option 1\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Stories end here\"\n" +
			"        }\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"operations\": [\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"You chose option 2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"wait\",\n" +
			"          \"duration\": \"2\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"type\": \"bot\",\n" +
			"          \"bot\": \"Stories end here\"\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  ]\n" +
			"}";

	private static JSONArray stories;

	private static int story_index;
	private static int operation_index;
	private static JSONArray current_operations;

	ChatAdapter adapter;
	private List<Message> messageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		chatView = findViewById(R.id.chat_view);
		txtInput = findViewById(R.id.input_message);

		try {
			stories = new JSONObject(strStory3).getJSONArray("story");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		messageList = new ArrayList<>();
//		for (int i = 0; i < 5; i++) {
//			Message message = new Message();
//			message.setBot(i%2==0);
//			message.setMessage("Thkljdlaksjkl;asj d ladsjd jlhcl\n dlasjdlkas dlkasj dlkasj lkdas jldkjla lkasjd lksaj dlkasj laksdlk");
//			messageList.add(message);
//		}

		chatView.setLayoutManager(new LinearLayoutManager(this));

		adapter = new ChatAdapter(messageList, this);
		chatView.setAdapter(adapter);

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

		startStory();
	}


	void startStory(){
//		ChatAdapter adapter = new ChatAdapter()
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

//				nextStory();
				break;
			}
		}
//		if(operation_index != current_operations.length()-1){
//
//		}
	}

	private void nextStory(){
//		story_index++;
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
//								nextStory();
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
			case "ar":

				return false;
			case "chooser":

				Bundle bundle = new Bundle();
				bundle.putString("prompt", operation.optString("prompt"));
				JSONArray options = operation.optJSONArray("options");
				bundle.putString("option0", options.optJSONObject(0).optString("text"));
				bundle.putString("option1", options.optJSONObject(1).optString("text"));
				Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
				intent.putExtras(bundle);
				startActivityForResult(intent, 100);

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
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
