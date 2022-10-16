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

		for (int row = 0; row < this.boardSize; row++) {
			for (int col = 0; col < this.boardSize; col++) {
				board[row][col] = Cell.EMPTY;
			}
		}
		if (!isXTurn){
			ai();
		}
	}
	//call ai after x is done with their turn
	public void playersTurn(int row, int col){
		select(row,col);
	}
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
		setTurnCount(1);
		setXTurn(isXFirst);
		xWin = false;
		oWin = false;
		setGameStatus(GameStatus.IN_PROGRESS);
		if(!isXTurn){
			ai();
		}
	}

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void setTurnCount(int count) {
		turnCount = count;
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
		Point p = checkWinRow(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkWinColumn(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkWinDiagonal1(numToWin, cellType);
		if (p.x != -1)
			return true;

		p = checkWinDiagonal2(numToWin, cellType);
		if (p.x != -1)
			return true;
		//return a win
		return false;//otherwise don't return a win
	}

	// check across each row for a win
	private Point checkWinRow(int numToWin, Cell cellType) {
		int count;//new counter for win comparison
		Point p = new Point(-1,-1);
		for (int row = 0; row < boardSize; row++) {//for all rows
			for (int col = 0; col < boardSize - numToWin + 1; col++) {
				//and columns
				count = 0;//count starts at 0
				while (board[row][col].equals(cellType)) {
					//while the board space is the checked type
					count++;//increase the counter
					
					//go to check next column
					if (count >= numToWin) {//if the count reaches a win
						p.setLocation(row, col);
						return p;
					}
					board[row][col] = board[row][col++];
				}
			}
		}
		return p;//otherwise return false for a row win
	}

	// check down each column for win
	private Point checkWinColumn(int numToWin, Cell cellType){
		int count;//new counter for win comparison
		Point p = new Point(-1,-1);
		for (int col = 0; col < boardSize; col++) {//for all columns
			for (int row = 0; row < boardSize - numToWin + 1; row++) {
				//and rows
				count = 0;//count starts at 0
				while (board[row][col].equals(cellType)) {

					count++;//increase the counter

					if (count >= numToWin) {
						p.setLocation(row, col);
						return p;
					}
					board[row][col] = board[row++][col];
				}
			}

		}
		return p;//otherwise return false for a column win
	}

	// check diagonally from top left to bottom right
	private Point checkWinDiagonal1(int numToWin, Cell cellType) {
		int count;
		Point p = new Point(-1,-1);
		for (int i = 0; i < boardSize; i++) {

			for (int j = 0; j < boardSize; j++) {
				count = 0;
				while (i < boardSize && j < boardSize && board[i][j].equals(cellType)) {
					count++;

					if (count >= numToWin) {
						p.setLocation(i, j);
						return p;
					}
					board[i][j] = board[i++][j++];
				}
			}
		}
		return p;
	}

	// check diagonally from top right to bottom left
	private Point checkWinDiagonal2(int numToWin, Cell cellType) {
		int count;
		Point p = new Point(-1,-1);
		for (int i = 0; i < boardSize; i++) {

			for (int j = boardSize - 1; j > 0; j--) {
				count = 0;
				while (i < boardSize && j >= 0 && board[i][j].equals(cellType)) {
					count++;

					if (count >= numToWin) {
						p.setLocation(i, j);
						return p;
					}
					board[i][j] = board[i++][j--];
				}
			}
		}
		return p;
	}

	public Point undo() {
		if(undoList.size() > 0 && isXFirst) {//if there are valid moves to undo
			turnCount -= 1;
			Point p = undoList.remove(undoList.size()-1);//a new point
			//equals the last point in undoList and removes it
			board[p.x][p.y] = Cell.EMPTY;//set the cell back to empty
			return p;//return the point to undo
		}
		else if (undoList.size() > 1 && !isXFirst){
			turnCount -= 1;
			Point p = undoList.remove(undoList.size()-1);//a new point
			//equals the last point in undoList and removes it
			board[p.x][p.y] = Cell.EMPTY;//set the cell back to empty
			return p;//return the point to undo
		}
		//else if prevents undo past ai's first move; without it the turn order changes.
		else//if there are not valid moves to undo
			return null;//Don't do anything
	}

	public void ai() {
		if (turnCount == 1) {
			firstMove();
		}
		else {
			Point lastAIMove = new Point(-1,-1);
			Point p = new Point(-1,-1);

			p = findSquare(p, numToWin-1, Cell.O); //try to get numToWin in a row
			
			if (p.x == -1)
				p = findSquare(p, numToWin-1, Cell.X); //defend against winning move
			
			if (p.x == -1)
				p = findSquare(p, numToWin-2, Cell.X); //defend against unblocked string
			
			if (p.x == -1)
				p = findSquare(p, numToWin-2, Cell.O); //try to get to undefended numToWin-1
			
			if (p.x == -1 && undoList.size() > 1) {
				lastAIMove = undoList.get(undoList.size()-2);
				p = findNear(lastAIMove); 
			}

			if (p.x == -1)
				p = rand(p); 
			
			select(p.x, p.y);
		}
	}
	
	private Point findSquare(Point p, int numTarget, Cell cellType) {
		p = scanRow(numTarget, cellType);
		
		if (p.x == -1)
			p = scanColumn(numTarget, cellType);

		if (p.x == -1)
			p = scanDiagonal1(numTarget, cellType);
		
		if (p.x == -1)
			p = scanDiagonal2(numTarget, cellType);

		return p;
	}

	private Point findNear(Point move) {
		for (int i = -1; i < 1; i++) {
			for (int j = -1; j < 1; j++) {
				if (isMoveValid(move.x+i, move.y+j)) {
					move.setLocation(move.x+i, move.y+j);
					return move; 
				}
			}
		}
		
		Point p = new Point(-1,-1);
		return p;
	}
	
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
	
	private Point scanRow(int numTarget, Cell cellType) {
		Point p = checkWinRow(numTarget, cellType);
		
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

	private Point scanColumn(int numTarget, Cell cellType) {
		Point p = checkWinColumn(numTarget, cellType);
		
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

	private Point scanDiagonal1(int numTarget, Cell cellType) {
		Point p = checkWinDiagonal1(numTarget, cellType);
		
		if (p.x != -1) {
			if (numTarget == numToWin-1) {
				if (isMoveValid(p.x+1, p.y+1)) {
					p.setLocation(p.x+1, p.y+1);
				}
				else if (isMoveValid(p.x-(numTarget), p.y-(numTarget))) {
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

	private Point scanDiagonal2(int numTarget, Cell cellType) {
		Point p = checkWinDiagonal2(numTarget, cellType);

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

	public void firstMove(){
		select(boardSize/2,boardSize/2);
	}
	
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
