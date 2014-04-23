package initialization;

import java.util.ArrayList;

import menus.MainMenu;
import model.Board;
import model.Scores;
import model.SquaresData;
import square.Square;
import utils.Utilities;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.finalproject.MainScreenActivity;
import com.example.finalproject.R;

public class InitializationActivity extends Activity {
	private static final int NUM_OF_TOTAL_SQUARES = 16;
	private static final int NUM_OF_SQUARES = 16;
	private static final int FOURTH_ROW_STARTING_INDEX = 12;
	private static final int THIRD_ROW_STARTING_INDEX = 8;
	private static final int SECOND_ROW_STARTING_INDEX = 4;
	private static final int NUM_OF_SQUARES_IN_ROW_AND_COLUMN = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initialization_activity);
		int squareWidthHeight = calculateSquareDimensions();
		initializeSquares(squareWidthHeight);
		// Reset the grid
		Board.getBoard().setGridLayout(null);
		// Reset the score board
		Scores.getScores().setScoreBoard(null);
		// Gets the high score and resets the current score
		Scores.getScores().setCurrentScore(0);
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",
				Context.MODE_PRIVATE);
		Scores.getScores().setHighScore(prefs.getInt("key", 0)); // 0 is the
																	// default
																	// value
		// Starts the Main screen activity
		Intent intent = new Intent(InitializationActivity.this,
				MainScreenActivity.class);
		startActivity(intent);

		// Closes the activity
		this.finish();

	}

	/**
	 * 
	 * Calculates the square dimensions.First gets the screen dimensions.Next
	 * initialize the grid row/column dimensions based on the smaller screen
	 * width/height.Trims 40 dp,so the grid would fit in the screen.Last
	 * calculates the square dimensions based on the grid row/column size.
	 * 
	 * @return Square width/height dimension
	 * 
	 */
	private int calculateSquareDimensions() {
		// Gets the screen size
		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int screenWidth = (size.x);
		int screenHeight = (size.y);
		// Checks which side,width or height is smaller and initialize the grid
		// side with the same dimension
		int gridSideDimension = 0;
		if (screenWidth < screenHeight)
			gridSideDimension = screenWidth;

		else
			gridSideDimension = screenHeight;
		// Trims the excessive size to fit into the visible screen
		gridSideDimension -= Utilities.dpToPx(40, this);
		// Initialize the square dimension
		int squareWidthHeight = gridSideDimension / 4;
		return squareWidthHeight;
	}

	/**
	 * 
	 * Initializes the squares in the grid in array list.Indexes from 0 to
	 * 15.And initialize separate lists for every row and column
	 * 
	 */
	private void initializeSquares(int widthAndHeight) {
		initializeSquareLists();
		// Initializes the squares in the grid
		for (int i = 0; i < NUM_OF_SQUARES; i++) {
			SquaresData.squaresAll.add(new Square(this, i, widthAndHeight));
		}
		initializeTheRowLists();

		initializeTheColumnLists();
		// Initialize the row and column array lists in the squares
		initializeTheListsInSquares();

		// Sets up the first and last indexes in the rows and columns
		for (Square square : SquaresData.squaresAll)
			square.setIndexes();
	}

	/**
	 * Initialize the row and column lists in every square.The lists are the
	 * same as the one in Data.
	 */
	private void initializeTheListsInSquares() {
		for (int i = 0; i < NUM_OF_SQUARES_IN_ROW_AND_COLUMN; i++) {
			SquaresData.squaresInFirstColumn.get(i).setColumn(
					SquaresData.squaresInFirstColumn);
			SquaresData.squaresInSecondColumn.get(i).setColumn(
					SquaresData.squaresInSecondColumn);
			SquaresData.squaresInThirdColumn.get(i).setColumn(
					SquaresData.squaresInThirdColumn);
			SquaresData.squaresInFourthColumn.get(i).setColumn(
					SquaresData.squaresInFourthColumn);
			SquaresData.squaresInFirstRow.get(i).setRow(
					SquaresData.squaresInFirstRow);
			SquaresData.squaresInSecondRow.get(i).setRow(
					SquaresData.squaresInSecondRow);
			SquaresData.squaresInThirdRow.get(i).setRow(
					SquaresData.squaresInThirdRow);
			SquaresData.squaresInFourthRow.get(i).setRow(
					SquaresData.squaresInFourthRow);
		}
	}

	/**
	 * Initialize the column lists.The next element in a column is calculated by
	 * the sum of the previous one and the number of squares in a row.In every
	 * other column the index of the square is the sum of the column index and
	 * the first column square index.
	 */
	private void initializeTheColumnLists() {
		for (int i = 0; i < SquaresData.squaresAll.size(); i += NUM_OF_SQUARES_IN_ROW_AND_COLUMN) {
			SquaresData.squaresInFirstColumn.add(SquaresData.squaresAll.get(i));
			SquaresData.squaresInSecondColumn.add(SquaresData.squaresAll
					.get(i + 1));
			SquaresData.squaresInThirdColumn.add(SquaresData.squaresAll
					.get(i + 2));
			SquaresData.squaresInFourthColumn.add(SquaresData.squaresAll
					.get(i + 3));
		}
	}

	/**
	 * Initialize the row lists.The indexes of the squares are calculated by
	 * incrementing by one,and the sum of the square index and the row index.
	 */
	private void initializeTheRowLists() {
		for (int i = 0; i < NUM_OF_SQUARES_IN_ROW_AND_COLUMN; i++) {
			SquaresData.squaresInFirstRow.add(SquaresData.squaresAll.get(i));
			SquaresData.squaresInSecondRow.add(SquaresData.squaresAll.get(i
					+ SECOND_ROW_STARTING_INDEX));
			SquaresData.squaresInThirdRow.add(SquaresData.squaresAll.get(i
					+ THIRD_ROW_STARTING_INDEX));
			SquaresData.squaresInFourthRow.add(SquaresData.squaresAll.get(i
					+ FOURTH_ROW_STARTING_INDEX));
		}
	}

	/**
	 * Initialize the square lists in Data with empty array lists.
	 */
	private void initializeSquareLists() {
		SquaresData.squaresAll = new ArrayList<Square>(NUM_OF_TOTAL_SQUARES);
		SquaresData.squaresInFirstColumn = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInSecondColumn = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInThirdColumn = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInFourthColumn = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInFirstRow = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInSecondRow = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInThirdRow = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
		SquaresData.squaresInFourthRow = new ArrayList<Square>(
				NUM_OF_SQUARES_IN_ROW_AND_COLUMN);
	}

	protected void OnPause() {
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
