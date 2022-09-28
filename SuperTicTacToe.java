package project2; 

import javax.swing.JFrame;

public class SuperTicTacToe {
	//private static SuperTicTacToePanel panel; wrong class?
	//private static int boardSize;
	
	public static void main(String[] args ) {
		
		int boardSize = 3;
		int numToWin = 3;
		boolean isXFirst = true;
		
		SuperTicTacToeGame game = new SuperTicTacToeGame(boardSize, numToWin, isXFirst);
		
		JFrame frame = new JFrame("Super Tic-Tac-Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SuperTicTacToePanel panel = new SuperTicTacToePanel(game); //alt: pass boardSize, numToWin, isXFirst
		//better to not pass game because it separates parts of MVC more.
		
		frame.getContentPane().add(panel);
		frame.setSize(1100,450);
		frame.setVisible(true); 
		
	}
}
