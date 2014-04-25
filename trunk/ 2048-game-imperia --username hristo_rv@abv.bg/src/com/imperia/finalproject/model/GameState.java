package com.imperia.finalproject.model;

import com.imperia.finalproject.square.Square;

public class GameState {
	private static final int PERCENTAGE_INCREASE_PER_NUMBER = 5;
	private static final int SQUARE_UPGRADE_NUMBER = 2;
	private static final int MINIMUM_SQUARE_NUMBER_TO_INCREASE_PERCENTAGE = 16;
	private static final int STARTING_PERCENTAGE = 10;
	private static final int LOWEST_SQUARE_NUMBER = 2;
	public static boolean isTheGameWon;

	/**
	 * Checks if there is any move possible for all the squares in the grid.If
	 * there's move move possible.
	 * 
	 */
	public static boolean IsGameLost() {
		if (Board.getBoard().isFull()) {
			for (Square square : SquaresData.squaresAll) {
				if (square.isMovePossible())
					return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * If there is a number higher than 16,number 4 can be generated instead of
	 * 2.
	 * 
	 * @return True if higher number can be generated,false otherwise.
	 */
	private static int getHighestSquareNumber() {
		int highestNumber = LOWEST_SQUARE_NUMBER;
		for (Square square : SquaresData.squaresAll) {
			if (square.getNumber() > highestNumber) {
				highestNumber = square.getNumber();
			}
		}
		return highestNumber;
	}

	/**
	 * Gets the highest number and for every number higher than 16 chance to
	 * generate 4 square is increased by 5%.if highest number is 16 its 15%,for
	 * 32,20% and so on.
	 * 
	 * @return The calculated percentage.
	 */
	public static int getCurrentPercentToGenerateFour() {
		int highestNumber = getHighestSquareNumber();
		int percentage = STARTING_PERCENTAGE;
		// starts from 16 and multiples by 2.
		for (int i = MINIMUM_SQUARE_NUMBER_TO_INCREASE_PERCENTAGE; i <= highestNumber; i *= SQUARE_UPGRADE_NUMBER) {
			percentage += PERCENTAGE_INCREASE_PER_NUMBER;
		}
		return percentage;
	}

}
