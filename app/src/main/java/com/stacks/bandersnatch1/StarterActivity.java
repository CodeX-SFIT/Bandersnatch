package com.stacks.bandersnatch1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

public class StarterActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starter);
//		Crashlytics.getInstance().crash();

		Button button = findViewById(R.id.submit);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(((EditText)findViewById(R.id.password)).getText().toString().equals("las")){
					Intent intent = new Intent(StarterActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});
	}
}
