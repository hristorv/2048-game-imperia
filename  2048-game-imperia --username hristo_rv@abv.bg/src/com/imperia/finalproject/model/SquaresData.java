package com.imperia.finalproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.imperia.finalproject.square.Square;

public class SquaresData {

	public static List<Square> squaresAll, squaresInFirstColumn,
			squaresInSecondColumn, squaresInThirdColumn, squaresInFourthColumn,
			squaresInFirstRow, squaresInSecondRow, squaresInThirdRow,
			squaresInFourthRow;
	
	public static int SquaresWidthAndHeight;

	/**
	 * Moves the squares right.Iterates each column from third to first.Skips
	 * the fourth,which can't be moved.If the move is valid return
	 * true,otherwise false.
	 */
	public static boolean moveSquaresRight() {
		boolean isMoveValid = false;
		for (Square square : squaresInThirdColumn)
			if (square.moveRight())
				isMoveValid = true;
		for (Square square : squaresInSecondColumn)
			if (square.moveRight())
				isMoveValid = true;
		for (Square square : squaresInFirstColumn)
			if (square.moveRight())
				isMoveValid = true;
		return isMoveValid;
	}

	/**
	 * Move the squares left.Iterates each column from second to fourth.Skips
	 * the first, which can't be moved.If the move is valid returns
	 * true,otherwise false.
	 */
	public static boolean moveSquaresLeft() {
		boolean isMoveValid = false;
		for (Square square : squaresInSecondColumn)
			if (square.moveLeft())
				isMoveValid = true;
		for (Square square : squaresInThirdColumn)
			if (square.moveLeft())
				isMoveValid = true;
		for (Square square : squaresInFourthColumn)
			if (square.moveLeft())
				isMoveValid = true;
		return isMoveValid;
	}

	/**
	 * Move the squares up.Iterates each row from second to fourth.Skips the
	 * first ,which can't be moved.If the move is valid returns true,otherwise
	 * false.
	 */
	public static boolean moveSquaresUp() {
		boolean isMoveValid = false;
		for (Square square : squaresInSecondRow)
			if (square.moveUp())
				isMoveValid = true;
		for (Square square : squaresInThirdRow)
			if (square.moveUp())
				isMoveValid = true;
		for (Square square : squaresInFourthRow)
			if (square.moveUp())
				isMoveValid = true;
		return isMoveValid;
	}

	/**
	 * Move the squares down.Iterates each row from third to first.Skips the
	 * fourth ,which can't be moved.If the move is valid returns true,otherwise
	 * false.
	 */
	public static boolean moveSquaresDown() {
		boolean isMoveValid = false;
		for (Square square : squaresInThirdRow)
			if (square.moveDown())
				isMoveValid = true;
		for (Square square : squaresInSecondRow)
			if (square.moveDown())
				isMoveValid = true;
		for (Square square : squaresInFirstRow)
			if (square.moveDown())
				isMoveValid = true;
		return isMoveValid;
	}

	/**
	 * Collects all the current default squares in a new list.
	 * 
	 * @return List of all the current default squares.
	 */
	public static List<Square> getDefaultSquares() {
		List<Square> defaultSquares = new ArrayList<Square>();
		for (Square square : squaresAll) {
			if (square.isDefault()) {
				defaultSquares.add(square);
			}
		}
		return defaultSquares;
	}

	/**
	 * If the grid is not full,generates a random square in the grid,by getting
	 * the default squares,picking one random of them and upgrading it.
	 */
	public static void generateRandom() {
		if (!(Board.getBoard().isFull())) {
			List<Square> defaultSquares = getDefaultSquares();
			Random random = new Random();
			int randomSquareIndex = random.nextInt(defaultSquares.size());
			squaresAll.get(
					defaultSquares.get(randomSquareIndex).getIndexInGrid())
					.upgrade();
			if (getRandPercent(GameState.getCurrentPercentToGenerateFour())) {
				// Upgrades to number 4 based on certain percentage.
				squaresAll.get(
						defaultSquares.get(randomSquareIndex).getIndexInGrid())
						.upgrade();

			}
		}
	}

	/**
	 * The method returns true if the random number generated is less than the
	 * percent specified.If the parameter is 30 , then the method will return
	 * true 30% of the time.
	 * 
	 * @param percent
	 *            what's the percent chance to return true.
	 * @return True based on the percent chance parametar.
	 */
	public static boolean getRandPercent(int percent) {
		Random rand = new Random();
		return rand.nextInt(100) <= percent;
	}

}
