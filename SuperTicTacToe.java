package project2;

import javax.swing.*;
import java.awt.*;

public class SuperTicTacToe {

	public static void main(String[] args) {
		int boardSize = 3;
		int numToWin = 3;
		boolean isXFirst = true;
		String boardInput;
		String numInput;
		String isXFirstInput;

		boolean boardLoopAgain;
		do {
			// set loop condition to false to prevent infinite loop
			boardLoopAgain = false;
			boardInput = JOptionPane.showInputDialog(null, "Enter a board size between 3 and 15:");

			if (boardInput == null)
				System.exit(0);

			try {
				boardSize = Integer.parseInt(boardInput);
			} catch (NumberFormatException e) {
				boardLoopAgain = true;
			}

			if (boardSize < 3 || boardSize > 15)
				boardLoopAgain = true;
		} while (boardLoopAgain);

		if (boardSize == 3)
			numToWin = 3;
		else {
			boolean numLoopAgain;
			do {
				numLoopAgain = false;
				numInput = JOptionPane.showInputDialog(null, "Enter a number of connections to win:");

				if (numInput == null)
					System.exit(0);

				try {
					numToWin = Integer.parseInt(numInput);
				} catch (NumberFormatException e) {
					numLoopAgain = true;
				}

				if (numToWin < 4 || numToWin > boardSize)
					numLoopAgain = true;
			} while (numLoopAgain);
		}

		// TODO: fix so that hitting cancel does not throw NullPointerException
		isXFirstInput = JOptionPane.showInputDialog(null, "Who starts first? X or O");
		if (isXFirstInput.equalsIgnoreCase("o"))
			isXFirst = false;
		else if (isXFirstInput.equalsIgnoreCase("x"))
			isXFirst = true;
		else {
			JOptionPane.showMessageDialog(null, "Unexpected input. " + "X will start first");
			isXFirst = true;
		}

		SuperTicTacToeGame game = new SuperTicTacToeGame(boardSize, numToWin, isXFirst);

		JFrame gui = new JFrame("Super Tic-Tac-Toe");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SuperTicTacToePanel panel = new SuperTicTacToePanel(game);
		gui.getContentPane().add(panel);
		
		//TODO: resize frame depending on board size?
		gui.setSize(1000, 500);
		gui.setPreferredSize(new Dimension(1000, 500));
		gui.setVisible(true);
	}

	public static void validInput() {

	}
}
