package project2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents the game of SuperTicTacToe based on
 * a variable board size, number of connections, and is x first
 * @author Gabe Kucinich, Zay Price, Joe Zylla
 * @version 10/17/2022
 */
public class SuperTicTacToeGame {
	/**
	 * creates a variable board of type Cell[][]
	 */
	private Cell[][] board;
	/**
	 * creates a variable called status of type GameStatus
	 */
	private GameStatus status;
	/**
	 * creates an int named numToWin
	 */
	private int boardSize;
	/**
	 * creates an int named numToWin
	 */
	private int numToWin;
	/**
	 * creates an int named turnCount
	 */
	private int turnCount;
	/**
	 * creates a boolean named isXFirst
	 */
	private boolean isXFirst;
	/**
	 * creates a boolean named isXTurn
	 */
	private boolean isXTurn;
	/**
	 * creates a boolean named xWin
	 */
	private boolean xWin;
	/**
	 * Creates a boolean named oWin
	 */
	private boolean oWin;
	/**
	 * creates an ArrayList of Points called undoList
	 */
	private ArrayList<Point> undoList;

	/**
	 * Constructs a SuperTicTacToe game given a boardSize,
	 *  numToWin, and isXFirst.
	 * @param boardSize the size of the board
	 * @param numToWin the number of connections in a row to win
	 * @param isXFirst whether or not x makes the first move
	 */
	public SuperTicTacToeGame(int boardSize, int numToWin, 
			boolean isXFirst) {
		xWin = false;
		oWin = false;
		undoList = new ArrayList<Point>();
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;
		turnCount = 1;
		this.boardSize = boardSize;
		this.numToWin = numToWin;
		this.isXFirst = isXFirst;
		isXTurn = isXFirst;

		//Initialize every cell to be empty
		for (int row = 0; row < this.boardSize; row++) {
			for (int col = 0; col < this.boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
		if (!isXTurn){
			ai();
		}
	}

	/**
	 * uses the select method given row and column based on player turn
	 * @param row the row to be selected
	 * @param col the column to be selected
	 */
	public void playersTurn(int row, int col){
		select(row,col);
	}

	/**
	 * sets cells to x or o based on isXTurn and the passed row and col
	 * checks to make sure move is valid (not an occupied spot)
	 * @param row the row to be selected
	 * @param col the column to be selected
	 */
	public void select(int row, int col) {
		Cell cellType;
		if (isXTurn) 
			cellType = Cell.X;
		else
			cellType = Cell.O;

		if (isMoveValid(row, col)) {
			board[row][col] = cellType;
			undoList.add(new Point(row, col));
			if (getXTurn()) {
				xWin = checkWin(numToWin, Cell.X);
				if (xWin) {
					setGameStatus(GameStatus.X_WON);
				}
				else if (!checkForEmptySpace()) {
					setGameStatus(GameStatus.CATS);
				}
				else{
					changeTurn(getXTurn());
					turnCount++;
				}
			}
			else {
				oWin = checkWin(numToWin, Cell.O);
				if (oWin) {
					setGameStatus(GameStatus.O_WON);
				}
				else if (!checkForEmptySpace()) {
					setGameStatus(GameStatus.CATS);
				}
				else{
					changeTurn(getXTurn());
					turnCount++;
				}
			}
		}
	}

	/**
	 * gets the size of the board as an int
	 * @return boardSize the size of the board
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * gets the num to win
	 * @return numToWin the number of connections needed to win
	 */
	public int getNumToWin() {
		return numToWin;
	}

	/**
	 * returns the two dimensional array of cells called board
	 * @return board returns the board
	 */
	public Cell[][] getBoard() {
		return board;
	}

	/**
	 * sets the game status to the provided status
	 * @param status returns the current status 
	 */
	public void setGameStatus(GameStatus status) {
		this.status = status;
	}

	/**
	 * gets the game status of type GameStatus
	 * @return status
	 */
	public GameStatus getGameStatus() {
		return status;
	}

	/**
	 * Resets the game and board to original conditions
	 */
	public void reset() {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
		setTurnCount(1);
		setXTurn(isXFirst);
		xWin = false;
		oWin = false;
		setGameStatus(GameStatus.IN_PROGRESS);
		if(!isXTurn){
			ai();
		}
	}

	/**
	 * a method to get the cell at a the specified row and col
	 * @param row the row of the cell
	 * @param col the column of the cell
	 * @return board[row][col] the board space at the row and column
	 */
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/**
	 * a method to set the turnCount to the specified count
	 * @param count the turn count to be set
	 */
	public void setTurnCount(int count) {
		turnCount = count;
	}

	/**
	 * a method to change the turn
	 * @param isXTurn if it is x's turn
	 */
	public void changeTurn(boolean isXTurn) {
		if (isXTurn == true) {
			setXTurn(false);
		} else {
			setXTurn(true);
		}
	}

	/**
	 * a method to get if it is x's turn
	 * @return isXTurn if it is x's turn
	 */
	public boolean getXTurn() {
		return isXTurn;
	}

	/**
	 * a method to set x's turn
	 * @param setTurn whether or not to set it as x's turn
	 */
	public void setXTurn(boolean setTurn) {
		isXTurn = setTurn;
	}

	/**
	 * a method to check if any win has occurred
	 * @param numToWin the number of connections to win
	 * @param cellType the type of cell as X or O
	 * @return if a win has occurred
	 */
	private boolean checkWin(int numToWin, Cell cellType){
		Point p = checkAllRows(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkAllColumns(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkAllDiagonal1s(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkAllDiagonal2s(numToWin, cellType);
		if (p.x != -1)
			return true;
		else
			return false;	
	}

	/**
	 * a method that checks rows for a win
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkAllRows(int numToWin, Cell cellType) {	
		Point p = new Point(-1,-1);
		for (int row = 0; row < boardSize; row++) {
			p = checkSingleRow(row, numToWin, cellType);
			if (p.x != -1) {
				return p;
			}
		}

		return p;//returns values of -1
	}

	/**
	 * method to check a single row for a win of type cellType
	 * @param row the row to be checked
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkSingleRow(int row, int numToWin, Cell cellType){
		int count;
		Point p = new Point(-1,-1);
		for (int col = 0; col < boardSize - numToWin + 1; col++) {
			//and columns
			count = 0;//count starts at 0
			while (board[row][col].equals(cellType)) {
				count++;

				if (count >= numToWin) {//if the count reaches a win
					p.setLocation(row, col);
					return p;
				}
				board[row][col] = board[row][col++];
			}
		}
		return p;
	}

	/**
	 * method to check all columns for a win
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */

	private Point checkAllColumns(int numToWin, Cell cellType){
		Point p = new Point(-1,-1);
		for (int col = 0; col < boardSize; col++) {//for all columns
			p = checkSingleColumn(col, numToWin, cellType);
			if (p.x != -1) {
				return p;
			}
		}
		return p;
	}

	/**
	 * a method to check single columns for a win of type cellType
	 * @param col the column to be checked
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkSingleColumn(int col, int numToWin, 
			Cell cellType) {
		int count;
		Point p = new Point(-1,-1);
		for (int row = 0; row < boardSize - numToWin + 1; row++) {
			//and rows
			count = 0;//count starts at 0
			while (board[row][col].equals(cellType)) {
				count++;

				if (count >= numToWin) {
					p.setLocation(row, col);
					return p;
				}
				board[row][col] = board[row++][col];
			}
		}
		return p;
	}

	/**
	 * check diagonally from top left to bottom right for a win
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkAllDiagonal1s(int numToWin, Cell cellType) {
		Point p = new Point(-1,-1);
		for (int col = 0; col < boardSize; col++) { 
			p = checkSingleDiagonal1(boardSize - getNumToWin(), col,
					numToWin, cellType);
			if (p.x != -1) {
				return p;
			}
		}
		return p;
	}

	/**
	 * checks single diagonal1s for a win of type cellType
	 * @param row the row being checked
	 * @param col the column being checked
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkSingleDiagonal1(int row, int col, int numToWin,
			Cell cellType) {
		int count = 0;
		Point p = new Point(-1,-1);

		//find the top-left square of the diagonal
		if (row <= col) {
			col = col - row;
			row = 0;
		}
		else { 
			row = row - col;
			col = 0;
		}

		for (int i = row, j = col; i < boardSize; i++, j++) {

			count = 0;
			while (i < boardSize && j < boardSize && 
					board[i][j].equals(cellType)) {

				count++;

				if (count >= numToWin) {
					p.setLocation(i, j);
					return p;
				}
				//go to next square in diagonal
				board[i][j] = board[i++][j++]; 
			}
		}

		return p;
	}

	/**
	 * check for win diagonally from top right to bottom left 
	 * @param numToWin the number of moves
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkAllDiagonal2s(int numToWin, Cell cellType) {
		Point p = new Point(-1,-1);
		for (int col = boardSize - 1; col > 0; col--) {
			p = checkSingleDiagonal2(boardSize - getNumToWin(), col,
									 numToWin, cellType);
			if (p.x != -1) {
				return p;
			}
		}
		return p;
	}

	/**
	 * a method that checks single diagonal2s for a win of type cellType
	 * @param row the row to be checked
	 * @param col the column to be checked
	 * @param numToWin the number of connections needed to win
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting a win or -1's denoting not
	 */
	private Point checkSingleDiagonal2(int row, int col, int numToWin,
								       Cell cellType) {
		int count = 0;
		Point p = new Point(-1,-1);

		//find the top-right square of the diagonal
		if (row + col >= boardSize-1) {
			row = row + col - (boardSize-1);
			col = boardSize-1;
		}
		else {
			col = row+col;
			row = 0;
		}

		for (int i = row, j = col; i < boardSize; i++, j--) {

			count = 0;
			while (i < boardSize && j >= 0 && 
				   board[i][j].equals(cellType)) {

				count++;

				if (count >= numToWin) {
					p.setLocation(i, j);
					return p;
				}
				board[i][j] = board[i++][j--];
			}
		}

		return p;
	}


	/**
	 * a method that undoes a point if there is a point to undo
	 */
	public void undo() {
		//if there are valid moves to undo
		if(undoList.size() > 0 && isXTurn) {
			turnCount -= 1;
			Point p = undoList.remove(undoList.size()-1);//a new point
			//equals the last point in undoList and removes it
			board[p.x][p.y] = Cell.EMPTY;//set the cell back to empty
		}
		else if (undoList.size() > 1 && !isXTurn){
			turnCount -= 1;
			Point p = undoList.remove(undoList.size()-1);//a new point
			//equals the last point in undoList and removes it
			board[p.x][p.y] = Cell.EMPTY;//set the cell back to empty
		}
		//else if prevents undo past ai's first move; 
		//without it the turn order changes.
	}

	/**
	 * Controls the logic for the computer player.
	 */
	public void ai() {
		if (turnCount <= 2) {
			firstMove();
		}
		else {
			Point lastHumanMove = new Point(-1,-1);
			Point lastAIMove = new Point(-1,-1);
			Point p = new Point(-1,-1);

			lastHumanMove = undoList.get(undoList.size()-1);
			lastAIMove = undoList.get(undoList.size()-2);

			//try to get numToWin in a row
			p = findSquare(lastAIMove, numToWin-1, Cell.O); 
			
			//if p.x == -1, then the previous call to findSquare 
			//did not find a move
			if (p.x == -1) {
				//defend against winning move
				p = findSquare(lastHumanMove, numToWin-1, Cell.X); 
			}
				
			//If human plays first, we check for defensive moves first
			if (isXFirst) {
				if (p.x == -1) {
					//defend against unblocked string
					p = findSquare(lastHumanMove, numToWin-2, Cell.X); 
				
				if (p.x == -1)
					//try to create unblocked string
					p = findSquare(lastAIMove, numToWin-2, Cell.O); 
				}
					

				
			} //If AI plays first, we check for offensive moves first
			else { 
				if (p.x == -1)
					p = findSquare(lastAIMove, numToWin-2, Cell.O); 
				if (p.x == -1)
					p = findSquare(lastHumanMove, numToWin-2, Cell.X); 
			}

			
			if (p.x == -1) {
				//Try to find a square connected to the last move
				p = findNear(lastAIMove); 
			}

			if (p.x == -1) {
				//pick a random square
				p = rand(p);
			}
				 
			if (p.x != -1) {
				select(p.x, p.y);
			}
		}
	}
	
	/**
	 * Finds a square based on whether a suitable choice exists in the
	 * row, column, diagonal1, or diagonal2 of the move passed to it.
	 * If no square is found, returns Point with values of (-1,-1)
	 * @param move the initial move to be found
	 * @param numTarget the target number
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point findSquare(Point move, int numTarget, Cell cellType){
		Point p = new Point(-1,-1);
		p = scanRow(move, numTarget, cellType);

		if (p.x == -1)
			p = scanColumn(move, numTarget, cellType);

		if (p.x == -1)
			p = scanDiagonal1(move, numTarget, cellType);

		if (p.x == -1)
			p = scanDiagonal2(move, numTarget, cellType);

		return p;
	}
	
	/**
	 * Finds a square next to the move passed to it
	 * If no empty square exists, returns Point with values of (-1,-1)
	 * @param move the point trying to be connected to
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point findNear(Point move) {
		Point p = new Point(-1,-1);//new point p for reference at -1,-1
		for (int i = -1; i < 2; i++) {//for int within 1 of move x
			for (int j = -1; j < 2; j++) {//for int within 1 of move y
				if(isMoveValid(move.x+i,move.y+j)){//if move valid
					p.setLocation(move.x+i, move.y+j);//set the point
				}
			}
		}
		return p;
	}
	
	/**
	 * Selects a random square on the board as a last resort.
	 * @param p a point to be checked and set random
	 * @return p a random, valid point
	 */
	private Point rand(Point p) {
		Random randomGen = new Random();
		int randRow;
		int randCol;
		do{
			randRow = randomGen.nextInt(boardSize);
			randCol = randomGen.nextInt(boardSize);
			p.setLocation(randRow, randCol);
		}
		while (!isMoveValid(randRow,randCol));

		return p;
	}

	/**
	 * If the parameter move created a string of numTarget cells in
	 *  a row, try to find an empty square to either block or add to it
	 * @param move a point of a move
	 * @param numTarget the target number
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point scanRow(Point move, int numTarget, Cell cellType){
		Point p = checkSingleRow(move.x, numTarget, cellType);

		if (p.x != -1) {
			if (numTarget == numToWin-1) {
				if (isMoveValid(p.x, p.y+1)) {
					p.setLocation(p.x, p.y+1);
				}
				else if (isMoveValid(p.x, p.y-(numTarget))) {
					p.setLocation(p.x, p.y-(numTarget));
				}
				else { 
					p.setLocation(-1,-1);
				}
			}
			else if (numTarget == numToWin-2) {
				if (isMoveValid(p.x, p.y+1) &&
					isMoveValid(p.x, p.y-numTarget)) { 
					p.setLocation(p.x, p.y-numTarget);
				}
				else { 
					p.setLocation(-1,-1);
				}
			}		
		}
		return p;
	}

	/**
	 * If the parameter move created a string of numTarget cells in a
	 * column, find an empty square to either block or add to it
	 * @param move a point of a move
	 * @param numTarget the target number
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point scanColumn(Point move, int numTarget, Cell cellType){
		Point p = checkSingleColumn(move.y, numTarget, cellType);

		if (p.x != -1) {
			if (numTarget == numToWin-1) {
				if (isMoveValid(p.x+1, p.y)) {
					p.setLocation(p.x+1, p.y);
				}
				else if (isMoveValid(p.x-(numTarget), p.y)) {
					p.setLocation(p.x-(numTarget), p.y);
				}
				else { 
					p.setLocation(-1,-1);
				}
			}
			else if (numTarget == numToWin-2) {
				if (isMoveValid(p.x+1, p.y) &&
					isMoveValid(p.x-(numTarget), p.y)) { 
					p.setLocation(p.x-(numTarget), p.y);
				}
				else { 
					p.setLocation(-1,-1);
				}
			}		
		}
		return p;
	}
	
	/**
	 * If the parameter move created a string of numTarget cells in a
	 * diagonal1, find an empty square to either block or add to it
	 * @param move a point of a move
	 * @param numTarget the target number
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point scanDiagonal1(Point move, int numTarget,
								Cell cellType) {
		Point p = checkSingleDiagonal1(move.x, move.y, numTarget, 
									   cellType);

		if (p.x != -1) {
			if (numTarget == numToWin-1) {
				if (isMoveValid(p.x+1, p.y+1)) {
					p.setLocation(p.x+1, p.y+1);
				}
				else if (isMoveValid(p.x-(numTarget), 
									 p.y-(numTarget))) {
					p.setLocation(p.x-(numTarget), p.y-(numTarget));
				}
				else { 
					p.setLocation(-1,-1);
				}
			}
			else if (numTarget == numToWin-2) {
				if (isMoveValid(p.x+1, p.y+1) &&
					isMoveValid(p.x-(numTarget), p.y-(numTarget))) { 
					p.setLocation(p.x-(numTarget), p.y-(numTarget));
				}
				else { 
					p.setLocation(-1,-1);
				}
			}		
		}

		return p;
	}

	/**
	 * If the parameter move created a string of numTarget cells in a
	 * diagonal1, find an empty square to either block or add to it
	 * @param move a point of a move
	 * @param numTarget the target number
	 * @param cellType the type of cell as X, O, or EMPTY
	 * @return p a positive point denoting valid or -1's denoting not
	 */
	private Point scanDiagonal2(Point move, int numTarget, 
							    Cell cellType) {
		Point p = checkSingleDiagonal2(move.x, move.y, numTarget, 
									   cellType);

		if (p.x != -1) {
			if (numTarget == numToWin-1) {
				if (isMoveValid(p.x+1, p.y-1)) {
					p.setLocation(p.x+1, p.y-1);
				}
				else if (isMoveValid(p.x-numTarget, p.y+numTarget)) {
					p.setLocation(p.x-numTarget, p.y+numTarget);
				}
				else { 
					p.setLocation(-1,-1);
				}
			}
			else if (numTarget == numToWin-2) {
				if (isMoveValid(p.x+1, p.y-1) &&
					isMoveValid(p.x-numTarget, p.y+numTarget)) { 
					p.setLocation(p.x-numTarget, p.y+numTarget);
				}
				else { 
					p.setLocation(-1,-1);
				}
			}		
		}

		return p;
	}
	
	/**
	 * Places the AI's first move in the center of the board
	 */
	public void firstMove(){
		Point p = new Point(boardSize/2,boardSize/2);
		p = findNear(p);
		select(p.x, p.y);
	}
	
	/**
	 * Check if there is an empty space in the board
	 * @return emptySpace a boolean denoting if the space is empty
	 */
	public boolean checkForEmptySpace(){
		boolean emptySpace = false;

		for (int row = 0; row < boardSize; row++){
			for (int col = 0; col < boardSize; col++){
				if (board[row][col]==Cell.EMPTY){
					emptySpace = true;
				}
			}
		}
		return emptySpace;

	}
	
	/**
	 * Check if the row and column parameters are within the board
	 * and if the square at the space is empty
	 * @param row the row to be checked
	 * @param col the column to be checked
	 * @return the validity of the move as true or false
	 */
	public boolean isMoveValid(int row, int col){
		if (0 <= row && row < boardSize && 
				0 <= col && col < boardSize && 
				getCell(row, col).equals(Cell.EMPTY)) {
			return true;
		}
		else
			return false;
	}
}