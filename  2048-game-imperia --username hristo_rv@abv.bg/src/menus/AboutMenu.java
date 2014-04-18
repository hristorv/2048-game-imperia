package menus;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.finalproject.R;

public class AboutMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_menu);
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		ToggleButton muteButton = (ToggleButton) findViewById(R.id.muteButton);
		Button backButton = (Button) findViewById(R.id.backButton);
		
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
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				finish();
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
