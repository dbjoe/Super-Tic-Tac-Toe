package project2;

import javax.swing.JOptionPane;

public class SuperTicTacToeGame {
	private Cell[][] board;
	private GameStatus status;
	private int boardSize;
	private int numToWin;
	private int turnCount;
	private boolean isXTurn;
	private boolean xWin = false;
	private boolean oWin = false;

	public SuperTicTacToeGame(int boardSize, int numToWin, boolean isXFirst){
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;
		turnCount = 0;
		this.boardSize = boardSize;
		this.numToWin = numToWin;
		isXTurn = isXFirst;

		for (int row = 0; row < this.boardSize; row++){
			for (int col = 0; col < this.boardSize; col++){
				board[row][col] = Cell.EMPTY;
			}
		}
	}
	public void select(int row, int col){
		if (!getCell(row, col).equals(Cell.EMPTY)){
			throw new IllegalArgumentException("Cannot place here");
		}
		if (isXTurn) {
			board[row][col] = Cell.X;
			xWin = checkWin(numToWin, Cell.X);
		}
		else {
			board[row][col] = Cell.O;
			oWin = checkWin(numToWin, Cell.O);
		}		
		
		if (xWin)
			setGameStatus(GameStatus.X_WON);
		else if (oWin)
			setGameStatus(GameStatus.O_WON);
		else if (turnCount == boardSize * boardSize -1)
			setGameStatus(GameStatus.CATS);

		increaseTurnCount(1);
		changeTurn(getXTurn());
	}

	public int getBoardSize(){
		return boardSize;
	}

	public Cell[][] getBoard(){
		return board;
	}

	public void setGameStatus(GameStatus status) {
		this.status = status;
	}

	public GameStatus getGameStatus(){
		return status;
	}
	public void reset(){
		for (int row = 0; row < boardSize; row++){
			for (int col = 0; col < boardSize; col++){
				board[row][col] = Cell.EMPTY;
			}
		}
		setTurnCount(0);
	}

	//TODO: Should this return the Cell type? e.g., X, O, or EMPTY
	public Cell getCell(int row, int col){
		return board[row][col];
	}
	public void setCell(int row, int col, Cell cellType) {
		board[row][col] = cellType;
	}
	public int getTurnCount(){
		return turnCount;
	}
	public void setTurnCount(int count){
		turnCount = count;
	}
	public void increaseTurnCount(int count){
		turnCount += count;
	}
	public void changeTurn(boolean isXTurn){
		if (isXTurn == true){
			setXTurn(false);
		}
		else{
			setXTurn(true);
		}
	}
	public boolean getXTurn(){
		return isXTurn;
	}
	public void setXTurn(boolean setTurn){
		isXTurn = setTurn;
	}

	//TODO: consider changing back to private later
	//write comments explaining each section of the method
	public 	boolean checkWin(int numToWin, Cell cellType) {
		int count;

		//check across each row
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

		//check down each column
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

		//check diagonally from top left to bottom right
		for (int i = numToWin - boardSize; i < boardSize - numToWin; i++) {
			int row;
			if (i < 0)
				row = Math.abs(i);
			else
				row = 0;

			for (int j = 0; j < numToWin-1; j++) {
				count = 0;
				while (board[row][j].equals(cellType)) {
					count++;
					board[row][j] = board[row++][j++];
					if (count >= numToWin)
						return true;
				}
			}
		}

		//check diagonally from top right to bottom left
		for (int i = numToWin - boardSize; i < boardSize - numToWin; i++) {
			int row;
			if (i < 0)
				row = Math.abs(i);
			else
				row = 0;

			for (int j = boardSize-1; j > numToWin-1; j--) {
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
}
