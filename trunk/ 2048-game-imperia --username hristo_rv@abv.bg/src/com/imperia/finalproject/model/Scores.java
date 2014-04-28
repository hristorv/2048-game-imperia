package com.imperia.finalproject.model;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Implements the singleton pattern.Use getBoard() method to get an instance of
 * the board.
 * 
 */
public class Scores {
	private static final int DEFAULT_CURRENT_SCORE = 0;
	private static final double TEXTSIZE_PERCENTAGE = 0.2;
	private static Scores scores;
	private int currentScore;
	private int highScore;
	private TableLayout scoreBoard;
	private TextView currentScoreView;
	private TextView highScoreView;
	private TextView popUpScoreView;

	private Scores() {
	}

	public static Scores getScores() {
		if (scores == null)
			scores = new Scores();
		return scores;
	}

	public TextView getPopUpScoreView() {
		return popUpScoreView;
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
		highScore
				.setTextSize(
						TypedValue.COMPLEX_UNIT_PX,
						(float) (SquaresData.SquaresWidthAndHeight * TEXTSIZE_PERCENTAGE));
		highScoreRow.addView(highScore);
		highScoreView = new TextView(activity);
		highScoreView
				.setTextSize(
						TypedValue.COMPLEX_UNIT_PX,
						(float) (SquaresData.SquaresWidthAndHeight * TEXTSIZE_PERCENTAGE));
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
		curScore.setTextSize(
				TypedValue.COMPLEX_UNIT_PX,
				(float) (SquaresData.SquaresWidthAndHeight * TEXTSIZE_PERCENTAGE));
		curScore.setText("CurrentScore:");
		currentScoreRow.addView(curScore);
		currentScoreView = new TextView(activity);
		initializePopUpScoreView(activity, currentScoreRow);
		currentScoreView.setText("" + DEFAULT_CURRENT_SCORE);
		currentScoreView
				.setTextSize(
						TypedValue.COMPLEX_UNIT_PX,
						(float) (SquaresData.SquaresWidthAndHeight * TEXTSIZE_PERCENTAGE));
		currentScoreRow.addView(currentScoreView);
		scoreBoard.addView(currentScoreRow);
	}

	/**
	 * Initializes the textview representing the pop up text,when you add score
	 * points.
	 * 
	 * @param activity
	 *            The calling activity
	 * @param currentScoreRow
	 *            The row in which we put it.
	 */
	private void initializePopUpScoreView(Activity activity,
			TableRow currentScoreRow) {
		popUpScoreView = new TextView(activity);
		popUpScoreView.setLayoutParams(currentScoreView.getLayoutParams());
		popUpScoreView.setBackgroundColor(Color.TRANSPARENT);
		popUpScoreView.setTextColor(Color.LTGRAY);
		currentScoreRow.addView(popUpScoreView);
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
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		curTableLayout.setLayoutParams(params);
		curTableLayout.setBackgroundColor(Color.CYAN);
		return curTableLayout;
	}

}
