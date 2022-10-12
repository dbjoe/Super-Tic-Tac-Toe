package project2;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SuperTicTacToeGame {
	/**2D array of Cells to that represent the squares on the board*/
	private Cell[][] board;

	/**Holds the current state of the game: IN_PROGRESS, X_WON, O_WON,
	 *  or CATS_GAME (tie)*/
	private GameStatus status;

	/**Current size of the board*/
	private int boardSize;

	/**Number of squares in a row needed to win the game*/
	private int numToWin;

	/**Current number of turns*/
	private int turnCount;

	/**Boolean of whether it is currently X's turn*/
	private boolean isXTurn;

	/**Boolean of whether X won*/
	private boolean xWin = false;

	/**Boolean of whether O won*/
	private boolean oWin = false;

	private ArrayList<Point> undoList;

	public SuperTicTacToeGame(int boardSize, int numToWin, boolean isXFirst) {
		undoList = new ArrayList<Point>();
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;
		turnCount = 0;
		this.boardSize = boardSize;
		this.numToWin = numToWin;
		isXTurn = isXFirst;

		for (int row = 0; row < this.boardSize; row++) {
			for (int col = 0; col < this.boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
	}

	public void select(int row, int col) {

		if (!getCell(row, col).equals(Cell.EMPTY)) {
			//do nothing
		}

		if (isXTurn) {
			board[row][col] = Cell.X;
			undoList.add(new Point(row, col));
			xWin = checkWin(numToWin, Cell.X);
		} else {
			board[row][col] = Cell.O;
			undoList.add(new Point(row, col));
			oWin = checkWin(numToWin, Cell.O);
		}

		if (xWin)
			setGameStatus(GameStatus.X_WON);
		else if (oWin)
			setGameStatus(GameStatus.O_WON);
		else if (turnCount == boardSize * boardSize - 1)
			setGameStatus(GameStatus.CATS);

		increaseTurnCount(1);
		changeTurn(getXTurn());
	}

	public int getBoardSize() {
		return boardSize;
	}

	public Cell[][] getBoard() {
		return board;
	}

	public void setGameStatus(GameStatus status) {
		this.status = status;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
		setTurnCount(0);
		//TODO: Set turn back to the first player, currently it has to reset twice and looks funny
		setGameStatus(GameStatus.IN_PROGRESS);
		//increaseTurnCount(1);//sets turn back to the first player?
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void setCell(int row, int col, Cell cellType) {
		board[row][col] = cellType;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int count) {
		turnCount = count;
	}

	public void increaseTurnCount(int count) {
		turnCount += count;
	}

	public void changeTurn(boolean isXTurn) {
		if (isXTurn == true) {
			setXTurn(false);
		} else {
			setXTurn(true);
		}
	}

	public boolean getXTurn() {
		return isXTurn;
	}

	public void setXTurn(boolean setTurn) {
		isXTurn = setTurn;
	}

	// TODO: consider changing back to private later
	// write comments explaining each section of the method
	public boolean checkWin(int numToWin, Cell cellType) {
		if (checkWinRow(numToWin, cellType) || //if a row won
				checkWinColumn(numToWin, cellType) ||//or a column
				checkWinDRDiagonal(numToWin, cellType) || //or a downright diag
				checkWinURDiagonal(numToWin, cellType)){//or an upright diag
			return true;//return a win
		}
		return false;//otherwise don't return a win
	}

	// check across each row for a win
	public boolean checkWinRow(int numToWin, Cell cellType) {
		int count;
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize - numToWin + 1; col++) {
				count = 0;
				while (board[row][col].equals(cellType)) {
					count++;
					board[row][col] = board[row][col++];
					if (count >= numToWin)
						return true;
				}
			}

		}
		return false;
	}

	// check down each column for win
	public boolean checkWinColumn(int numToWin, Cell cellType){
		int count;
		for (int col = 0; col < boardSize; col++) {
			for (int row = 0; row < boardSize - numToWin + 1; row++) {
				count = 0;
				while (board[row][col].equals(cellType)) {
					count++;
					board[row][col] = board[row++][col];
					if (count >= numToWin)
						return true;
				}
			}

		}
		return false;
	}

	// check diagonally from top left to bottom right
	public boolean checkWinDRDiagonal(int numToWin, Cell cellType) {
		int count;
		for (int i = numToWin - boardSize; i < boardSize - numToWin; i++) {
			int row;
			if (i < 0)
				row = Math.abs(i);
			else
				row = 0;

			for (int j = 0; j < numToWin - 1; j++) {
				count = 0;
				while (board[row][j].equals(cellType)) {
					count++;
					board[row][j] = board[row++][j++];
					if (count >= numToWin)
						return true;
				}
			}
		}
		return false;
	}

	// check diagonally from top right to bottom left
	public boolean checkWinURDiagonal(int numToWin, Cell cellType) {
		int count;
		for (int i = numToWin - boardSize; i < boardSize - numToWin; i++) {
			int row;
			if (i < 0)
				row = Math.abs(i);
			else
				row = 0;

			for (int j = boardSize - 1; j > numToWin - 1; j--) {
				count = 0;
				while (board[row][j].equals(cellType)) {
					count++;
					board[row][j] = board[row++][j--];
					if (count >= numToWin)
						return true;
				}
			}
		}
		return false;
	}
	/*Might want something like:
	 * if (isXTurn)
	 * 	remove previous two moves
	 * else
	 * 	remove previous move
	 * 
	 * Can also be done in Panel by calling undo either once or twice
	 * depending on whose turn it is. Might not conform to MVC though
	 */
	public Point undo() {
		if(undoList.size() > 0) {
			Point p = undoList.remove(undoList.size()-1);//returns the last point and removes it from undoList
			board[p.x][p.y] = Cell.EMPTY;
			return p;
		}
		else
			return null;
	}

	public void ai() {
		/*if (!isXTurn) {//if it is the ai turn
			if (turncount < 3) {//if it is the ai's first turn
				//place mark in a random spot
			}
			else {
				if (ai can win)
					place mark in winning spot
				else if (player will win)
					place mark to not lose
				else
					connect mark to previous mark
			}
		}*/
	}

}
