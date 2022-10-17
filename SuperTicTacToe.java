package project2;

import javax.swing.*;
import java.awt.*;

public class SuperTicTacToe {

	public static void main(String[] args) {
		int boardSize = 4;
		int numToWin = 4;
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
				JOptionPane.showMessageDialog(null, "Please enter an integer between 3 and 15");
			}

			if (boardSize < 3 || boardSize > 15) {
				boardLoopAgain = true;
				JOptionPane.showMessageDialog(null, "Please enter an integer between 3 and 15");
			}
				
		} while (boardLoopAgain);

		if (boardSize == 3)
			numToWin = 3;
		else if (boardSize == 4)
			numToWin = 4;
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
					JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and " + boardSize);
				}

				if (numToWin < 4 || numToWin > boardSize) {
					numLoopAgain = true;
					JOptionPane.showMessageDialog(null, "Please enter an integer between 4 and " + boardSize);
				}
					
			} while (numLoopAgain);
		}

		isXFirstInput = JOptionPane.showInputDialog(null, "Who starts first? X or O");
		if (isXFirstInput == null) {
			System.exit(0);
		}
		else {
			if (isXFirstInput.equalsIgnoreCase("o"))
				isXFirst = false;
			else if (isXFirstInput.equalsIgnoreCase("x"))
				isXFirst = true;
			else {
				JOptionPane.showMessageDialog(null, "Unexpected input. X will start first.");
				isXFirst = true;
			}
		}

		SuperTicTacToeGame game = new SuperTicTacToeGame(boardSize, numToWin, isXFirst);

		JFrame gui = new JFrame("Super Tic-Tac-Toe");
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SuperTicTacToePanel panel = new SuperTicTacToePanel(game);
		gui.getContentPane().add(panel);
		
		//TODO: resize frame depending on board size?
		gui.setSize(1100, 700);
		gui.setPreferredSize(new Dimension(1100, 700));
		gui.setVisible(true);
	}
}