package com.stacks.bandersnatch1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ChooserActivity extends AppCompatActivity {

	TextView prompt;
	TextView option0;
	TextView option1;
	View layoutOr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chooser);

		prompt = findViewById(R.id.prompt);
		option0 = findViewById(R.id.option0);
		option1 = findViewById(R.id.option1);
		layoutOr = findViewById(R.id.or_layout);

		Bundle bundle = getIntent().getExtras();
		prompt.setText(bundle.getString("prompt", ""));
		option0.setText(bundle.getString("option0", ""));
		option1.setText(bundle.getString("option1", ""));
		option0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option1.setVisibility(View.GONE);
				layoutOr.setVisibility(View.GONE);
				CountDownTimer timer = new CountDownTimer(1000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {

					}

					@Override
					public void onFinish() {
						Intent intent = new Intent();
						intent.putExtra("selection", 0);
						setResult(RESULT_OK, intent);
						finish();
					}
				};
				timer.start();
			}
		});

		option1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				option0.setVisibility(View.GONE);
				layoutOr.setVisibility(View.GONE);
				CountDownTimer timer = new CountDownTimer(1000, 1000) {
					@Override
					public void onTick(long millisUntilFinished) {

					}

					@Override
					public void onFinish() {
						Intent intent = new Intent();
						intent.putExtra("selection", 1);
						setResult(RESULT_OK, intent);
						finish();
					}
				};
				timer.start();
			}
		});
		CountDownTimer timer = new CountDownTimer(5000, 1000) {
			int count = 0;
			@Override
			public void onTick(long millisUntilFinished) {
				if(count == 1){
					prompt.setVisibility(View.VISIBLE);
				}else if(count == 3){
					layoutOr.setVisibility(View.VISIBLE);
				}
				count++;
			}

			@Override
			public void onFinish() {
				option0.setVisibility(View.VISIBLE);
				option1.setVisibility(View.VISIBLE);
			}
		};
		timer.start();
	}

	@Override
	public void onBackPressed() {

	}
}
