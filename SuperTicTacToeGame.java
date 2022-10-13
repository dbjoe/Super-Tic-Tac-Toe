package project2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class SuperTicTacToeGame {
	private Cell[][] board;
	private GameStatus status;
	private int boardSize;
	private int numToWin;
	private int turnCount;
	private boolean isXFirst;
	private boolean isXTurn;
	private boolean xWin;
	private boolean oWin;
	private ArrayList<Point> undoList;

	public SuperTicTacToeGame(int boardSize, int numToWin, boolean isXFirst) {
		isXTurn = false;
		xWin = false;
		oWin = false;
		undoList = new ArrayList<Point>();
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;
		turnCount = 1;
		this.boardSize = boardSize;
		this.numToWin = numToWin;
		isXTurn = isXFirst;
		this.isXFirst = isXFirst;


		for (int row = 0; row < this.boardSize; row++) {
			for (int col = 0; col < this.boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
	}
//call ai after x is done with their turn
	public void select(int row, int col) {
		try {
			if (!getCell(row, col).equals(Cell.EMPTY)) {
				throw new IllegalArgumentException("Cannot place here");
			}
			if (isXTurn) {
				board[row][col] = Cell.X;
				undoList.add(new Point(row, col));
				xWin = checkWin(numToWin, Cell.X);
			} else {
				oWin = checkWin(numToWin, Cell.O);
			}

			if (xWin) {
				setGameStatus(GameStatus.X_WON);
			}
			else if (oWin) {
				setGameStatus(GameStatus.O_WON);
			}
			else if (turnCount == boardSize * boardSize - 1) {
				setGameStatus(GameStatus.CATS);
			}
			if (!xWin && !oWin && !(turnCount == boardSize * boardSize - 1)) {
				increaseTurnCount();
				changeTurn(getXTurn());
				ai();
				changeTurn(getXTurn());//try here
			}
		}
		catch (IllegalArgumentException e) {
			//catch where it can not be placed and do nothing. Don't place it
		}
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
		setXTurn(isXFirst);
		xWin = false;
		oWin = false;
		setGameStatus(GameStatus.IN_PROGRESS);
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

	public void increaseTurnCount() {
		turnCount += 1;
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

	private boolean checkWin(int numToWin, Cell cellType){//if a ___ won
		if (checkWinRow(numToWin, cellType) || //row
			checkWinColumn(numToWin, cellType) ||//column
			checkWinDRDiagonal(numToWin, cellType) ||//downright diag
			checkWinURDiagonal(numToWin, cellType)){//upright diag
				return true;//return a win
		}
		return false;//otherwise don't return a win
	}

	// check across each row for a win
	private boolean checkWinRow(int numToWin, Cell cellType) {
		int count;//new counter for win comparison
		for (int row = 0; row < boardSize; row++) {//for all rows
			for (int col = 0; col < boardSize - numToWin + 1; col++) {
				//and columns
				count = 0;//count starts at 0
				while (board[row][col].equals(cellType)) {
					//while the board space is the checked type
					count++;//increase the counter
					board[row][col] = board[row][col++];
					//go to check next column
					if (count >= numToWin)//if the count reaches a win
						return true;//return true for a row win
				}
			}

		}
		return false;//otherwise return false for a row win
	}

	// check down each column for win
	private boolean checkWinColumn(int numToWin, Cell cellType){
		int count;//new counter for win comparison
		for (int col = 0; col < boardSize; col++) {//for all columns
			for (int row = 0; row < boardSize - numToWin + 1; row++) {
				//and rows
				count = 0;//count starts at 0
				while (board[row][col].equals(cellType)) {
					//while the board space is the checked type
					count++;//increase the counter
					board[row][col] = board[row++][col];
					//go to check next row
					if (count >= numToWin)//if the count reaches a win
						return true;//return true for a column win
				}
			}

		}
		return false;//otherwise return false for a column win
	}

    // check diagonally from top left to bottom right
    public boolean checkWinDRDiagonal(int numToWin, Cell cellType) {
        int count;
        int row;
        for (int i = numToWin - boardSize; i <= 0; i++) {
            row = Math.abs(i);

            for (int j = 0; j < boardSize; j++) {
                count = 0;
                while (j < boardSize && board[row][j].equals(cellType)) {
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
        int row;
        for (int i = numToWin - boardSize; i <= 0; i++) {
            row = Math.abs(i);

            for (int j = boardSize - 1; j > 0; j--) {
                count = 0;
                while (j >= 0 && board[row][j].equals(cellType)) {
                    count++;
                    board[row][j] = board[row++][j--];
                    if (count >= numToWin)
                        return true;
                }
            }
        }
        return false;
    }


	public Point undo() {
		if(undoList.size() > 0) {//if there are valid moves to undo
			turnCount -= 1;
			Point p = undoList.remove(undoList.size()-1);//a new point
			//equals the last point in undoList and removes it
			board[p.x][p.y] = Cell.EMPTY;//set the cell back to empty
			return p;//return the point to undo
		}
		else//if there are not valid moves to undo
			return null;//Don't do anything
	}

	public void ai() {
		if (!isXTurn) {//if it is the ai turn
			if (turnCount < 3) {//if it is the ai's first turn
				Random randomGen = new Random();
				int upperbound = boardSize;
				int randomColumn = randomGen.nextInt(upperbound);
				int randomRow = randomGen.nextInt(upperbound);
				board[randomRow][randomColumn] = Cell.O;
				increaseTurnCount();
				undoList.add(new Point(randomRow, randomColumn));
			}
//			else {
//				if (ai can win) {
//					place mark in winning spot;
//				}
//				else if (player will win) {
//					place mark to not lose;
//				}
//				else {
//					connect mark to previous mark(s);
//				}
//			}
		}
	}

}
