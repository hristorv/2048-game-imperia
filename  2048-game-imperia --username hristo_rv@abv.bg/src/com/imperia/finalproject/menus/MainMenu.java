package com.imperia.finalproject.menus;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;
import com.example.finalproject.R;
import com.imperia.finalproject.initialization.InitializationActivity;

public class MainMenu extends Activity {
	private static final float DEFAULT_VALUE_FOR_SOUND = 0.5f;
	public static MediaPlayer mpBackgroundSound;
	public static MediaPlayer mpButtonClick;
	public static boolean isMuted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		// initializing Background Sound
		MainMenu.mpBackgroundSound = MediaPlayer.create(this,
				R.raw.fun_with_friends);
		MainMenu.mpBackgroundSound.setLooping(true);
		// Lowering Background sound
		MainMenu.mpBackgroundSound.setVolume(DEFAULT_VALUE_FOR_SOUND,
				DEFAULT_VALUE_FOR_SOUND);
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();

		// initializing Button for new game
		Button newGameButton = (Button) findViewById(R.id.new_game_button);
		newGameButton.setBackgroundResource(R.layout.border_score);
		// initializing button for about menu
		Button aboutButton = (Button) findViewById(R.id.about_button);
		aboutButton.setBackgroundResource(R.layout.border_score);
		// initializing button for exit game
		Button exitGameButton = (Button) findViewById(R.id.exit_button);
		exitGameButton.setBackgroundResource(R.layout.border_score);
		// initializing button for sound
		ToggleButton muteButton = (ToggleButton) findViewById(R.id.muteButton);

		newGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				Intent intent = new Intent(MainMenu.this,
						InitializationActivity.class);
				startActivity(intent);
			}
		});
		aboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				Intent intent = new Intent(MainMenu.this, AboutMenu.class);
				startActivity(intent);
			}
		});
		exitGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				finish();
			}
		});
		muteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				if (MainMenu.mpBackgroundSound.isPlaying()) {
					MainMenu.mpBackgroundSound.pause();
					MainMenu.isMuted = true;
				} else {
					MainMenu.mpBackgroundSound.start();
					MainMenu.isMuted = false;
				}
			}
		});

	}

	@Override
	protected void onPause() {
		MainMenu.mpBackgroundSound.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		MainMenu.mpButtonClick = MediaPlayer.create(this, R.raw.button_click);
		if (!MainMenu.mpBackgroundSound.isPlaying() && !MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		super.onResume();
	}

}
