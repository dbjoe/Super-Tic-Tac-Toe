package project2;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SuperTicTacToePanel extends JPanel {
	//Create the following properties for the SuperTicTacToePanel (more if you feel you need them):
	private JButton[][] jButtonsBoard;   
	private Cell[][] iBoard;
	private JButton quitButton;
	private ImageIcon xIcon; 
	private ImageIcon oIcon;		
	private ImageIcon emptyIcon;

	private int IMAGE_SIZE;
	private int BOARD_SIZE;
	//one panel for buttons on the board, one panel for undo and stuff
	private SuperTicTacToeGame game;
	

	public SuperTicTacToePanel(SuperTicTacToeGame game) {
		this.game = game;
		BOARD_SIZE = 3;
		IMAGE_SIZE = 50; //TODO: 50 is a placeholder

		String projectDir = "src/project2/";

		xIcon = new ImageIcon(
				new ImageIcon(projectDir + "x.jpg").getImage().
				getScaledInstance(
						IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		oIcon = new ImageIcon(
				new ImageIcon(projectDir + "o.jpg").getImage().
				getScaledInstance(
						IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		emptyIcon = new ImageIcon(
				new ImageIcon(projectDir + "empty.jpg").getImage().
				getScaledInstance(
						IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH));
		//icon files go in package with the rest of the classes

		GridLayout layout = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(layout);
		add(gamePanel);

		jButtonsBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
		ButtonListener listener = new ButtonListener();
		for (int row = 0; row < 3; row++) 
			for (int col = 0; col < 3; col++) {
				jButtonsBoard[row][col] = new JButton ("", emptyIcon);
				jButtonsBoard[row][col].addActionListener(listener);
				gamePanel.add(jButtonsBoard[row][col]);
			}
		
		//maybe something like jButtonsBoard[0][0].setBounds(getVisibleRect());
		//would need to scale this depending on the board size?
	}
	
	

	private void displayBoard() {
		//In this method, first call the getBoard method within the game class (see step 6)
		//to get the TicTacToe board, and then use a nested loop to set the appropriate icon to the JButtons with in the GUI.

		// NOTE: The following method returns the whole board back to 
		// the panel class.  Is this a good idea?   Talk with your 
		// instructor about this.   Perhaps you could have a method that
		// returns each cell separately. Is that a better idea? 

		iBoard = game.getBoard ();  

		for (int row = 0; row < 3; row++) 
			for (int col = 0; col < 3; col++) {
				if (iBoard[row][col] == Cell.X){
					jButtonsBoard[row][col].setIcon(xIcon);
				}
			}
	}

	private class ButtonListener implements ActionListener {
		//create an actionPerformed method that calls the different methods in the
		//TicTacToeGame class using the game object.  For example, call the game.select
		//method when a user clicks a JButton on the board and change the icon to the correct icon.
		//Also, if the user clicks the JButton quitButton, then the program exits after confirmation.  

		@Override
		public void actionPerformed(ActionEvent e) {
			//The following is some of the code that will be needed within the actionPerformed method:

			// Determine which button was selected.
			for (int row = 0; row < 3; row++) 
				for (int col = 0; col < 3; col++) 
					if (jButtonsBoard[row][col] == e.getSource())
						// tell the game which button was selected.
						game.select(row,col);

			// Display the board using the private method describe above.
			displayBoard();

			// Determine if there is a winner by asking the game object. (see step 6)
			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won and X” + “lost!\n The game will reset");
			}

		}

	}

}
