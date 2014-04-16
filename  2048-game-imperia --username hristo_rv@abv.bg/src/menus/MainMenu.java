package menus;

import initialization.InitializationActivity;
import com.example.finalproject.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		Button newGameButton = (Button) findViewById(R.id.new_game_button);
		Button aboutButton = (Button) findViewById(R.id.about_button);

		newGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this,
						InitializationActivity.class);
				startActivity(intent);
			}
		});
		aboutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this, AboutMenu.class);
				startActivity(intent);

			}
		});

		
	}

}
