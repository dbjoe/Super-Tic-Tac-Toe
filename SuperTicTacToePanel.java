package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SuperTicTacToePanel extends JPanel {
	private JButton[][] jButtonsBoard;
	private Cell[][] iBoard;
	private JButton quitButton;
	private JButton undoButton;
	private ImageIcon xIcon;
	private ImageIcon scaledX;
	private ImageIcon oIcon;
	private ImageIcon scaledO;
	private ImageIcon emptyIcon;
	private ImageIcon scaledEmpty;
	private SuperTicTacToeGame game;
	private int IMAGE_SIZE;
	private int BOARD_SIZE;

	public SuperTicTacToePanel(SuperTicTacToeGame game) {
		this.game = game;

		BOARD_SIZE = game.getBoardSize();
		IMAGE_SIZE = 30;

		String projectDir = "src/project2/";

		xIcon = new ImageIcon(projectDir + "x.png");
		scaledX = new ImageIcon(xIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		oIcon = new ImageIcon(projectDir + "o.png");
		scaledO = new ImageIcon(oIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		emptyIcon = new ImageIcon(projectDir + "empty.png");
		scaledEmpty = new ImageIcon(emptyIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		
		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(layout);
		add(gamePanel);

		jButtonsBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
		ButtonListener listener = new ButtonListener();
		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				jButtonsBoard[row][col] = new JButton("", scaledEmpty);
				jButtonsBoard[row][col].addActionListener(listener);
				gamePanel.add(jButtonsBoard[row][col]);
			}
		
		GridLayout buttonLayout = new GridLayout(2,1);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
		add(buttonPanel);
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		buttonPanel.add(quitButton);
		buttonPanel.add(undoButton);
		
		//If the computer goes first, we call displayBoard() to show its move
		if (game.getXTurn())
			displayBoard();
	}
	
	private void displayBoard() {

		iBoard = game.getBoard();

		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (iBoard[row][col] == Cell.X) {
					jButtonsBoard[row][col].setIcon(scaledX);
				}
				if (iBoard[row][col] == Cell.O) {
					jButtonsBoard[row][col].setIcon(scaledO);
				}
				if (iBoard[row][col] == Cell.EMPTY) {
					jButtonsBoard[row][col].setIcon(scaledEmpty);
				}
			}
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Determine which button was selected.
			if (quitButton == e.getSource()) {
				System.exit(0);
			}
			else if (undoButton == e.getSource()) {
				game.undo();//undoes the AI's last move
				game.undo();//undoes the player's last move
			}
			else {
				if(game.getXTurn()){
					for (int row = 0; row < BOARD_SIZE; row++)
						for (int col = 0; col < BOARD_SIZE; col++)
							if (jButtonsBoard[row][col] == e.getSource())
								if (game.isMoveValid(row,col)){// tell the game which button was selected.
									game.playersTurn(row, col);
									if(game.getGameStatus()==GameStatus.IN_PROGRESS)
										game.ai();
								}
				}
			}
			displayBoard();

			// Determine if there is a winner by asking the game object. (see step 6)
			if (game.getGameStatus().equals(GameStatus.X_WON)) {
				JOptionPane.showMessageDialog(null, "X won and O lost! " + "\nThe game will reset");
				game.reset();
				displayBoard();
			} else if (game.getGameStatus().equals(GameStatus.O_WON)) {
				JOptionPane.showMessageDialog(null, "O won and X lost! " + "\nThe game will reset");
				game.reset();
				displayBoard();
			} else if (game.getGameStatus().equals(GameStatus.CATS)) {
				JOptionPane.showMessageDialog(null, "Tie! " + "\nThe game will reset");
				game.reset();
				displayBoard();
			}
		}
	}
}
