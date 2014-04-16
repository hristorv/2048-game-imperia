package menus;

import initialization.InitializationActivity;
import com.example.finalproject.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {
	public static MediaPlayer mpBackgroundSound;
	public static MediaPlayer mpButtonClick;
	public static boolean isMuted = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		MainMenu.mpButtonClick = MediaPlayer.create(this, R.raw.button_click);
		MainMenu.mpBackgroundSound = MediaPlayer.create(this, R.raw.fun_with_friends);
		MainMenu.mpBackgroundSound.setVolume(0.5f, 0.5f);
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		MainMenu.mpBackgroundSound.setLooping(true);

		Button newGameButton = (Button) findViewById(R.id.new_game_button);
		Button aboutButton = (Button) findViewById(R.id.about_button);

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

	}

	@Override
	protected void onPause() {
		MainMenu.mpBackgroundSound.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (!MainMenu.mpBackgroundSound.isPlaying() && !MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		super.onResume();
	}

}
