package com.imperia.finalproject.menus;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.finalproject.R;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.imperia.finalproject.model.Scores;
import com.imperia.finalproject.square.Square;

public class AboutMenu extends Activity {
	private static final int VALUE_FOR_HARD = 2048;
	private static final int VALUE_FOR_NORMAL = 1024;
	private static final int VALUE_FOR_EASY = 512;
	private UiLifecycleHelper uiHelper;
	public String difficulty;
	public int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_menu);
		// initializing uiHelper
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		// initializing spinner for difficulty
		Spinner spinnerForDifficulty = (Spinner) findViewById(R.id.spinner_difficulty);
		// adding list with options for difficulty
		List<String> list = new ArrayList<String>();
		list.add("Easy");
		list.add("Normal");
		list.add("Hard");
		// initializing adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerForDifficulty.setAdapter(adapter);

		final SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",
				Context.MODE_PRIVATE);
		position = prefs.getInt("difficult", 0);
		spinnerForDifficulty.setSelection(position);
		spinnerForDifficulty
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position_real, long id) {
						position = position_real;
						difficulty = parent.getItemAtPosition(position)
								.toString();
						if (difficulty == "Easy") {
							Square.setMAX_SQUARE_NUMBER(VALUE_FOR_EASY);
						}
						if (difficulty == "Normal") {
							Square.setMAX_SQUARE_NUMBER(VALUE_FOR_NORMAL);
						}
						if (difficulty == "Hard") {
							Square.setMAX_SQUARE_NUMBER(VALUE_FOR_HARD);
						}
						Editor editor = prefs.edit();
						editor.putInt("difficult", position_real);
						editor.commit();
						parent.setSelection(position_real);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		// initializing button for share to facebook
		Button shareFacebook = (Button) findViewById(R.id.shareFacebook);
		// initializing button for back
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setBackgroundResource(R.layout.border_score);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				finish();
			}
		});
		shareFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				int highscore = Scores.getScores().getHighScore();
				// Facebook dialog that will be shared in facebook
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
						AboutMenu.this)
						.setPlace("Sofia")
						.setPicture(
								"http://blog.datumbox.com/wp-content/uploads/2014/04/game-2048-java.png")
						.setDescription(String.valueOf(highscore))
						.setCaption("new high score")
						.setLink("https://developers.facebook.com/android")
						.build();
				uiHelper.trackPendingDialogCall(shareDialog.present());
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MainMenu.mpBackgroundSound.pause();
		uiHelper.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MainMenu.mpButtonClick = MediaPlayer.create(this, R.raw.button_click);
		if (!MainMenu.mpBackgroundSound.isPlaying() && !MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		uiHelper.onResume();
	}
}
