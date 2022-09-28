package project2;

public class SuperTicTacToeGame {
	//at some point between select and getGameStatus, you need to determine if someone has won or tied.
	//either after select, or in getGameStatus
	private Cell[][] board;
	private GameStatus status;
	private int BOARD_SIZE;
	private int NUM_TO_WIN;
	private boolean IS_X_TURN;

	public SuperTicTacToeGame() {
		//TODO: example code, might not work
		status = GameStatus.IN_PROGRESS;
		board = new Cell[3][3];

		for (int row = 0; row < 3; row++){ 
			for (int col = 0; col < 3; col++){	
				board[row][col] = Cell.EMPTY; 
			}
		}
	}

	public SuperTicTacToeGame(int boardSize, int numToWin, boolean isXFirst) {
		board = new Cell[boardSize][boardSize];
		status = GameStatus.IN_PROGRESS;

		BOARD_SIZE = boardSize;
		NUM_TO_WIN = numToWin;
		IS_X_TURN = isXFirst;

		for (int row = 0; row < BOARD_SIZE; row++){ 
			for (int col = 0; col < BOARD_SIZE; col++){	
				board[row][col] = Cell.EMPTY; 
			}
		}

	}

	public void select(int row, int col) {
		//TODO: need a lot more logic
		if (IS_X_TURN) {
			board[row][col] = Cell.X;
		}
		else {
			board[row][col] = Cell.O;
		}
		
	}

	public void reset() {
		//called from SuperTicTacToePanel to reset the board to a new game
		//reset does not resize board
	}

	public GameStatus getGameStatus() {
		//returns the appropriate enum for if a player has won the game after 
		//the select method was called
		//e.g.
		return GameStatus.IN_PROGRESS;
	}

	public Cell[][] getBoard() {
		//this method returns the board to the SuperTicTacToePanel
		//so the panel can display the board to the user.   
		return board;
	}
}
