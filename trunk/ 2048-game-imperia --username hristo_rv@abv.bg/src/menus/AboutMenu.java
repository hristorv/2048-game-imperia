package menus;

import com.example.finalproject.R;
import com.example.finalproject.R.id;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;

public class AboutMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_menu);
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		Button muteButton = (Button) findViewById(id.muteButton);
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
