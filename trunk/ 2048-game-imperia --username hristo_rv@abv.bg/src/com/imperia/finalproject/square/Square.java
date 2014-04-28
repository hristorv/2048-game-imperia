package com.imperia.finalproject.square;

import java.util.List;
import com.imperia.finalproject.model.Animations;
import com.imperia.finalproject.model.Borders;
import com.imperia.finalproject.model.GameState;
import com.imperia.finalproject.model.Scores;
import com.imperia.finalproject.model.SquaresData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.TextView;

@SuppressLint("ViewConstructor")
public class Square extends TextView {

	private static final int SQUARES_IN_A_COLUMN = 4;
	private static final double TEXTSIZE_PERCENTAGE = 0.3;
	private static final int DEFAULT_SQUARE_NUMBER = 1;
	private static final int SQUARES_IN_A_ROW = 4;
	private static final int MAX_SQUARE_NUMBER = 2048;
	private static final int LAST_NUM_WITH_BLACK_COLOR_TEXT = 4;
	private static final int MAX_SQUARE_INDEX = 15;
	private static final int MIN_SQUARE_INDEX = 0;
	int indexInGrid;
	int number = 1;
	int widthAndHeight;
	boolean isMaxNumber = false;
	private List<Square> row;
	private List<Square> column;
	public int lastIndexInRow;
	private int firstIndexInRow;
	private int lastIndexInColumn;
	private int firstIndexInColumn;

	public Square(Context context, int index, int widthAndHeight) {
		super(context);
		setIndexInGrid(index);
		setDimensions(widthAndHeight);
		reset();
		this.widthAndHeight = widthAndHeight;
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX,
				(float) (widthAndHeight * TEXTSIZE_PERCENTAGE));
		this.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
		this.setGravity(Gravity.CENTER);
	}

	public List<Square> getRow() {
		return row;
	}

	public List<Square> getColumn() {
		return column;
	}

	public void setRow(List<Square> row) {
		this.row = row;
	}

	public void setColumn(List<Square> column) {
		this.column = column;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setIndexInGrid(int index) {
		if (index >= MIN_SQUARE_INDEX && index <= MAX_SQUARE_INDEX) {
			this.indexInGrid = index;
		}
	}

	public int getIndexInGrid() {
		return this.indexInGrid;
	}

	/**
	 * Initialize the width and height of the square
	 * 
	 * @param widthAndHeight
	 *            int number representing width and height dimensions of the
	 *            square
	 */
	private void setDimensions(int widthAndHeight) {
		GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
		gridParams.width = widthAndHeight;
		gridParams.height = widthAndHeight;
		this.setLayoutParams(gridParams);
	}

	/**
	 * Sets up the first and last indexes in the row and column.
	 */
	public void setIndexes() {

		lastIndexInRow = row.get(row.size() - 1).getIndexInGrid();
		firstIndexInRow = row.get(0).getIndexInGrid();
		lastIndexInColumn = column.get(column.size() - 1).getIndexInGrid();
		firstIndexInColumn = column.get(0).getIndexInGrid();

	}

	/**
	 * Resets the square view.Sets the background to default,sets empty text and
	 * sets the number to one;
	 */
	public void reset() {
		setNumber(1);
		setText("");
		setTextColor(Color.BLACK);
		setBackgroundResource(Borders.borders.get(1));
	}

	/**
	 * Upgrades the square to the next number.
	 */
	public void upgrade() {
		if (getNumber() != MAX_SQUARE_NUMBER) {
			setNumber(getNumber() * 2);
			setText("" + getNumber());
			setTextColorDependingOnNumber();
			setBackgroundResource(Borders.borders.get(getNumber()));
		}
		if (getNumber() == MAX_SQUARE_NUMBER) {
			this.isMaxNumber = true;
			GameState.isTheGameWon = true;
		}
	}

	/**
	 * Set the appropriate color for the current number
	 */
	private void setTextColorDependingOnNumber() {
		if (getNumber() > LAST_NUM_WITH_BLACK_COLOR_TEXT) {
			setTextColor(Color.WHITE);
		} else {
			setTextColor(Color.BLACK);
		}
	}

	/**
	 * Checks if the square is a default one by its number.If the square number
	 * is 1,it is a default square.
	 * 
	 * @return True if the square is default
	 */
	public boolean isDefault() {
		if (getNumber() == DEFAULT_SQUARE_NUMBER) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a right move for this square is possible.
	 * 
	 * @return True if the right move is possible,false otherwise.
	 */
	private boolean isRightMovePossible() {
		if (indexInGrid == lastIndexInRow
				|| SquaresData.squaresAll.get(indexInGrid + 1).getNumber() != getNumber()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a left move for this square is possible.
	 * 
	 * @return True if the left move is possible,false otherwise.
	 */
	private boolean isLeftMovePossible() {
		if (indexInGrid == firstIndexInRow
				|| SquaresData.squaresAll.get(indexInGrid - 1).getNumber() != getNumber()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a up move for this square is possible.
	 * 
	 * @return True if the up move is possible,false otherwise.
	 */
	private boolean isUpMovePossible() {
		if (indexInGrid == firstIndexInColumn
				|| SquaresData.squaresAll.get(indexInGrid - 4).getNumber() != getNumber()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a down move for this square is possible.
	 * 
	 * @return True if the down move is possible,false otherwise.
	 */
	private boolean isDownMovePossible() {
		if (indexInGrid == lastIndexInColumn
				|| SquaresData.squaresAll.get(indexInGrid + 4).getNumber() != getNumber()) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if there is any move possible for this square.
	 * 
	 * @return True if there is a move possible for this square,false otherwise.
	 */
	public boolean isMovePossible() {
		if (isRightMovePossible() || isLeftMovePossible() || isUpMovePossible()
				|| isDownMovePossible()) {
			return true;
		}
		return false;
	}

	/**
	 * Move the square at the right position.First checks if the square is not a
	 * default one.Then for every square after this one in the row checks if it
	 * has the same number,if it does then they "collide".If the next square is
	 * a different number,then this square moves next to it.If all squares are
	 * default/empty this square moves to the last square in the row.
	 */
	public boolean moveRight() {
		if (!(isDefault())) {
			for (int i = indexInGrid + 1; i <= lastIndexInRow; i++) {
				Square curSquare = SquaresData.squaresAll.get(i);
				if (curSquare.getNumber() == getNumber()) {
					curSquare.upgrade();
					curSquare
							.startAnimation(Animations.getAnimations().collisionAnimation);
					updateScore(i);
					this.reset();
					return true;
				}
				if ((!curSquare.isDefault())
						&& curSquare.getNumber() != getNumber()) {
					if (indexInGrid != (i - 1)) {
						SquaresData.squaresAll.get(i - 1).copy(this);
						this.reset();
						return true;
					}
					return false;
				}
				if (i == lastIndexInRow && curSquare.isDefault()) {
					curSquare.copy(this);
					this.reset();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Move the square at the left position.First checks if the square is not a
	 * default one.Then for every square before this one in the row checks if it
	 * has the same number,if it does then they "collide".If the previous square
	 * is a different number,then this square moves next to it.If all squares
	 * are default/empty this square moves to the first square in the row.
	 */
	public boolean moveLeft() {
		if (!(isDefault())) {
			for (int i = indexInGrid - 1; i >= firstIndexInRow; i--) {
				Square curSquare = SquaresData.squaresAll.get(i);
				if (curSquare.getNumber() == getNumber()) {
					curSquare.upgrade();
					curSquare
							.startAnimation(Animations.getAnimations().collisionAnimation);
					updateScore(i);
					reset();
					return true;
				}
				if ((!curSquare.isDefault())
						&& curSquare.getNumber() != getNumber()) {
					if (indexInGrid != (i + 1)) {
						SquaresData.squaresAll.get(i + 1).copy(this);
						this.reset();
						return true;
					}
					return false;
				}
				if (i == firstIndexInRow && curSquare.isDefault()) {
					curSquare.copy(this);
					this.reset();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Move the square at the lower position.First checks if the square is not a
	 * default one.Then for every square below this one in the column checks if
	 * it has the same number,if it does then they "collide".If the next square
	 * is a different number,then this square moves next to it.If all squares
	 * are default/empty this square moves to the last square in the column.
	 */
	public boolean moveDown() {
		if (!(isDefault())) {
			for (int i = indexInGrid + SQUARES_IN_A_ROW; i <= lastIndexInColumn; i += SQUARES_IN_A_ROW) {
				Square curSquare = SquaresData.squaresAll.get(i);
				if (curSquare.getNumber() == getNumber()) {
					curSquare.upgrade();
					curSquare
							.startAnimation(Animations.getAnimations().collisionAnimation);
					updateScore(i);
					reset();
					return true;
				}
				if ((!curSquare.isDefault())
						&& curSquare.getNumber() != getNumber()) {
					if (indexInGrid != (i - 4)) {
						SquaresData.squaresAll.get(i - 4).copy(this);
						this.reset();
						return true;
					}
					return false;
				}
				if (i == lastIndexInColumn && curSquare.isDefault()) {
					curSquare.copy(this);
					this.reset();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Move the square at the upper position.First checks if the square is not a
	 * default one.Then for every square over this one in the column checks if
	 * it has the same number,if it does then they "collide".If the previous
	 * square is a different number,then this square moves next to it.If all
	 * squares are default/empty this square moves to the first square in the
	 * column.
	 */
	public boolean moveUp() {
		if (!(isDefault())) {
			for (int i = indexInGrid - SQUARES_IN_A_ROW; i >= firstIndexInColumn; i -= SQUARES_IN_A_ROW) {
				Square curSquare = SquaresData.squaresAll.get(i);
				if (curSquare.getNumber() == getNumber()) {
					curSquare.upgrade();
					curSquare
							.startAnimation(Animations.getAnimations().collisionAnimation);
					updateScore(i);
					reset();
					return true;
				}
				if ((!curSquare.isDefault())
						&& curSquare.getNumber() != getNumber()) {
					if (indexInGrid != (i + SQUARES_IN_A_COLUMN)) {
						SquaresData.squaresAll.get(i + SQUARES_IN_A_COLUMN).copy(this);
						this.reset();
						return true;
					}
					return false;
				}
				if (i == firstIndexInColumn && curSquare.isDefault()) {
					curSquare.copy(this);
					this.reset();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Copies the parameters of the target square to the current square.
	 * 
	 * @param square
	 *            The square from which we are copying.
	 */
	private void copy(Square square) {
		setNumber(square.getNumber());
		setText("" + getNumber());
		setTextColorDependingOnNumber();
		setBackgroundResource(Borders.borders.get(getNumber()));

	}

	/**
	 * Updates the current score.
	 * 
	 * @param i
	 *            The index of the square,from which the number is taken.
	 */
	private void updateScore(int i) {
		int scoreNumber = SquaresData.squaresAll.get(i).getNumber();
		if (scoreNumber != MAX_SQUARE_NUMBER) {
			Scores.getScores().setCurrentScore(
					Scores.getScores().getCurrentScore() + scoreNumber);
		}
	}

}