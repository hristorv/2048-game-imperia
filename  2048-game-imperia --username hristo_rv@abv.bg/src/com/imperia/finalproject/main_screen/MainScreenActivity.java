package com.imperia.finalproject.main_screen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.example.finalproject.R;
import com.imperia.finalproject.initialization.InitializationActivity;
import com.imperia.finalproject.menus.MainMenu;
import com.imperia.finalproject.model.Board;
import com.imperia.finalproject.model.GameState;
import com.imperia.finalproject.model.Scores;
import com.imperia.finalproject.model.SquaresData;
import com.imperia.finalproject.utils.Utilities;

public class MainScreenActivity extends Activity {
	private GestureDetectorCompat mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		RelativeLayout relativeParent = (RelativeLayout) findViewById(R.id.relativeParent_main_screen);

		Board.getBoard().initializeBoard(relativeParent, this);
		
		initializeSurrenderButton(relativeParent);
		
		Scores.getScores().initializeScoreBoard(this, relativeParent);

		if (Board.getBoard().IsEmpty()) {
			SquaresData.generateRandom();
			SquaresData.generateRandom();
		}
		if (!MainMenu.isMuted)
			MainMenu.mpBackgroundSound.start();
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

	private void initializeSurrenderButton(RelativeLayout relativeParent) {
		Button back_button = new Button(this);
		back_button.setText("Surrender");
		relativeParent.addView(back_button);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				back_button.getLayoutParams());
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		back_button.setLayoutParams(params);
		back_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!MainMenu.isMuted)
					MainMenu.mpButtonClick.start();
				finish();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * Check gestures and moves the squares depending on the direction of the
	 * gesture Minimal Distance Gesture = 50 also removed diagonal swipe
	 * 
	 */
	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		private int swipe_Min_Distance = 5;
		private int swipe_Min_Velocity = 100;

		@Override
		public boolean onDown(MotionEvent event) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			final int xDistance = Utilities.pxToDp(
					(int) Math.abs(e1.getX() - e2.getX()),
					MainScreenActivity.this);
			final int yDistance = Utilities.pxToDp(
					(int) Math.abs(e1.getY() - e2.getY()),
					MainScreenActivity.this);
			velocityX = Math.abs(velocityX);
			velocityY = Math.abs(velocityY);
			boolean result = false;
			if (xDistance > yDistance && velocityX > this.swipe_Min_Velocity
					&& xDistance > this.swipe_Min_Distance) {
				if (e1.getX() > e2.getX()) // right to left
					moveSquaresLeft();
				else
					moveSquaresRight();

				result = true;
			} else if (velocityY > this.swipe_Min_Velocity
					&& yDistance > this.swipe_Min_Distance) {
				if (e1.getY() > e2.getY()) { // bottom to up
					moveSquaresUp();
				} else
					moveSquaresDown();
				result = true;
			}
			return result;
		}
	}

	/**
	 * Moves the squares right.If the move is valid(some square has been moved)
	 * , then checks if the game is won and generates random square.
	 */
	private void moveSquaresRight() {
		if (SquaresData.moveSquaresRight()) {
			checkIsGameWon();
			SquaresData.generateRandom();
		} else {
			checkIsGameLost();
		}
	}

	/**
	 * Move the squares left.If the move is valid(some square has been moved) ,
	 * then checks if the game is won and generates random square.
	 */
	private void moveSquaresLeft() {
		if (SquaresData.moveSquaresLeft()) {
			checkIsGameWon();
			SquaresData.generateRandom();
		} else {
			checkIsGameLost();
		}
	}

	/**
	 * Move the squares left.If the move is valid(some square has been moved) ,
	 * then checks if the game is won and generates random square.
	 */
	private void moveSquaresUp() {
		if (SquaresData.moveSquaresUp()) {
			checkIsGameWon();
			SquaresData.generateRandom();
		} else {
			checkIsGameLost();
		}
	}

	/**
	 * Move the squares down.If the move is valid(some square has been moved) ,
	 * then checks if the game is won and generates random square.
	 */
	private void moveSquaresDown() {
		if (SquaresData.moveSquaresDown()) {
			checkIsGameWon();
			SquaresData.generateRandom();
		} else {
			checkIsGameLost();
		}
	}

	/**
	 * Checks if the game is won,and puts a new alert dialog on the screen.
	 */
	private void checkIsGameWon() {
		if (GameState.isTheGameWon) {
			GameState.isTheGameWon = false;
			new AlertDialog.Builder(this)
					// .setNeutralButton(text, listener)
					.setTitle("You win !")
					.setMessage("Do you want to start another game ?")
					.setCancelable(false)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if (!MainMenu.isMuted)
										MainMenu.mpButtonClick.start();
									Intent intent = new Intent(
											MainScreenActivity.this,
											InitializationActivity.class);
									startActivity(intent);
									finish();
								}
							})
					.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if (!MainMenu.isMuted)
										MainMenu.mpButtonClick.start();
									finish();
								}
							}).setIcon(R.drawable.win_picture).show();
		}
	}

	/**
	 * Checks if the game is lost,and puts a new alert dialog on the screen.
	 */
	private void checkIsGameLost() {
		if (GameState.IsGameLost()) {
			new AlertDialog.Builder(this)
					.setTitle("You lost !")
					.setMessage("Do you want to start another game ?")
					.setCancelable(false)
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											MainScreenActivity.this,
											InitializationActivity.class);
									startActivity(intent);
									finish();
								}
							})
					.setNegativeButton(android.R.string.no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setIcon(R.drawable.lose_picture).show();
		}
	}

	/**
	 * Saves the high score,with shared preferences.
	 */
	private void saveHighScore() {
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("key", Scores.getScores().getHighScore());
		editor.commit();
	}

	@Override
	protected void onPause() {
		MainMenu.mpBackgroundSound.pause();
		saveHighScore();
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
