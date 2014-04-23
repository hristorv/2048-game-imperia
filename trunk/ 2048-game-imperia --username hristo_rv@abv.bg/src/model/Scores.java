package model;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Scores {
	private static Scores scores;
	private int currentScore;
	private int highScore;
	private TableLayout scoreBoard;
	private TextView currentScoreView;
	private TextView highScoreView;

	private Scores() {
	}

	public static Scores getScores() {
		if (scores == null)
			scores = new Scores();
		return scores;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public int getHighScore() {
		return highScore;
	}

	public TableLayout getScoreBoard() {
		return scoreBoard;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
		if (currentScoreView != null)
			currentScoreView.setText("" + currentScore);
		if (currentScore > highScore) {
			setHighScore(currentScore);
			highScoreView.setText("" + highScore);
		}

	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public void setScoreBoard(TableLayout tableLayout) {
		this.scoreBoard = tableLayout;
	}

	/**
	 * Initializes the score board.If the score board is null,detaches the old
	 * one from its parent and adds it again to the relative parent.If its
	 * null,initializes it and sets its rows.
	 * 
	 * @param activity
	 *            The calling activity.
	 * @param relativeParent
	 *            The relative parent layout.
	 */
	public void initializeScoreBoard(Activity activity,
			RelativeLayout relativeParent) {
		if (scoreBoard == null) {
			scoreBoard = setNewTableLayout(relativeParent, activity);
			setCurrentScoreRow(activity);

			setHighScoreRow(activity);
		} else {
			((ViewGroup) scoreBoard.getParent()).removeView(scoreBoard);
			addScoreBoardToRelativeParent(relativeParent, scoreBoard);
		}

	}

	/**
	 * Initializes the high score row.Set up the text views and add them to the
	 * row.
	 * 
	 * @param activity
	 *            The calling activity.
	 */
	private void setHighScoreRow(Activity activity) {
		TableRow highScoreRow = new TableRow(activity);
		TextView highScore = new TextView(activity);
		highScore.setText("HighScore:");
		highScoreRow.addView(highScore);
		highScoreView = new TextView(activity);
		highScoreView.setText("" + this.highScore);
		highScoreRow.addView(highScoreView);
		scoreBoard.addView(highScoreRow);
	}

	/**
	 * Initializes the current score row.Set up the text views and add them to
	 * the row.
	 * 
	 * @param activity
	 *            The calling activity.
	 */
	private void setCurrentScoreRow(Activity activity) {
		TableRow currentScoreRow = new TableRow(activity);
		TextView curScore = new TextView(activity);
		curScore.setText("CurrentScore:");
		currentScoreRow.addView(curScore);
		currentScoreView = new TextView(activity);
		currentScoreRow.addView(currentScoreView);
		scoreBoard.addView(currentScoreRow);
	}

	/**
	 * Adds the the table layout to the relative parent layout.
	 * 
	 * @param relativeParent
	 *            The relative parent layout.
	 * @param table
	 *            The table layout.
	 */
	private void addScoreBoardToRelativeParent(RelativeLayout relativeParent,
			TableLayout table) {
		relativeParent.addView(table);

	}

	/**
	 * Initializes a new Table layout.Adjust its parameters and add it to the
	 * relative layout parent.
	 * 
	 * @param relativeParent
	 *            The relative parent layout.
	 * @param activity
	 *            The calling activity.
	 * @return The initialized table layout.
	 */
	private TableLayout setNewTableLayout(RelativeLayout relativeParent,
			Activity activity) {
		TableLayout curTableLayout = new TableLayout(activity);
		addScoreBoardToRelativeParent(relativeParent, curTableLayout);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				curTableLayout.getLayoutParams());
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		curTableLayout.setLayoutParams(params);
		curTableLayout.setBackgroundColor(Color.CYAN);
		return curTableLayout;
	}

}
