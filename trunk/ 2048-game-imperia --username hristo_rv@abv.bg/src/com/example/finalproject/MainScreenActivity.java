package com.example.finalproject;

import utils.Utilities;
import initialization.InitializationActivity;
import model.Board;
import model.GameState;
import model.SquaresData;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainScreenActivity extends Activity {
	private GestureDetectorCompat mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		RelativeLayout relativeParent = (RelativeLayout) findViewById(R.id.relativeParent_main_screen);

		Board.getBoard().initializeBoard(relativeParent, this);

		if (Board.getBoard().IsEmpty()) {
			SquaresData.generateRandom();
			SquaresData.generateRandom();
		}
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		// Only for testing.
		setUpButtons();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * check gestures and moves the squares depending on the direction of the
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
	 * This is for testing only.
	 */
	private void setUpButtons() {
		Button buttonUp = (Button) findViewById(R.id.button_up);
		buttonUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				moveSquaresUp();

			}
		});
		Button buttonDown = (Button) findViewById(R.id.button_down);
		buttonDown.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				moveSquaresDown();

			}
		});
		Button buttonLeft = (Button) findViewById(R.id.button_left);
		buttonLeft.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				moveSquaresLeft();

			}
		});
		Button buttonRight = (Button) findViewById(R.id.button_right);
		buttonRight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				moveSquaresRight();

			}
		});
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
					.setTitle("You win !")
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
							}).setIcon(R.drawable.ic_launcher).show();
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
							}).setIcon(R.drawable.ic_launcher).show();
		}
	}

}
