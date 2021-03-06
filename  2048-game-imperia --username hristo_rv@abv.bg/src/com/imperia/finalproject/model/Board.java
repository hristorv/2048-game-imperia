package com.imperia.finalproject.model;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.imperia.finalproject.square.Square;

/**
 * Implements the singleton pattern.Use getBoard() method to get an instance of
 * the board.
 * 
 */
public class Board {

	private static final int DEFAULT_ROWS_COUNT = 4;
	private static final int DEFAULT_COLUM_COUNT = 4;
	private GridLayout gridLayout;
	private static Board board;

	private Board() {

	}

	public static Board getBoard() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	public GridLayout getGridLayout() {
		return gridLayout;
	}

	public void setGridLayout(GridLayout gridLayout) {
		this.gridLayout = gridLayout;
	}

	/**
	 * Checks if all the squares in the grid are default.
	 * 
	 * @return True if the grid is empty,false otherwise.
	 */
	public boolean IsEmpty() {
		for (Square square : SquaresData.squaresAll) {
			if (!(square.isDefault())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the grid is full,by calling isDefault() for every square in the
	 * grid.
	 * 
	 * @return True,if the grid is full,false otherwise.
	 */
	public boolean isFull() {
		for (Square square : SquaresData.squaresAll) {
			if (square.isDefault()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * If the grid is null,initialize it for the first time,otherwise gets the
	 * one thats stored in Data
	 * 
	 * @param relativeParent
	 *            The realative layout parent
	 */
	public void initializeBoard(RelativeLayout relativeParent, Activity activity) {
		if (gridLayout == null) {
			GridLayout newgridLayout = setNewGridLayout(relativeParent,
					activity);
			addSquaresToGrid(newgridLayout);
			gridLayout = newgridLayout;
		} else {
			// Detaches the Data.grid view from its parent
			((ViewGroup) gridLayout.getParent()).removeView(gridLayout);
			addGridToRelativeParent(relativeParent, gridLayout);
		}
	}

	/**
	 * Adds the grid view to the relative parent and sets the gravity of the
	 * relative layout to center the grid.
	 * 
	 * @param relativeParent
	 *            The relative layout
	 */
	private void addGridToRelativeParent(RelativeLayout relativeParent,
			GridLayout grid) {
		relativeParent.addView(grid);
	}

	/**
	 * Reads the square objects from the array list and adds them to the grid.
	 * 
	 * @param grid
	 *            The grid layout
	 */
	private void addSquaresToGrid(GridLayout grid) {
		for (Square square : SquaresData.squaresAll) {
			grid.addView(square);
		}
	}

	/**
	 * Initializes the grid layout with 4 rows and column.Add the grid to the
	 * relative parent layout.
	 * 
	 * @param relativeParent
	 *            The parent layout
	 * @return The initialized grid layout
	 */
	private GridLayout setNewGridLayout(RelativeLayout relativeParent,
			Activity activity) {
		GridLayout grid = new GridLayout(activity);
		grid.setColumnCount(DEFAULT_COLUM_COUNT);
		grid.setRowCount(DEFAULT_ROWS_COUNT);
		addGridToRelativeParent(relativeParent, grid);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				grid.getLayoutParams());
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		grid.setLayoutParams(params);
		return grid;
	}

}
