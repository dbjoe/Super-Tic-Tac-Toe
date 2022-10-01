package project2;

import javax.swing.JOptionPane;

public class SuperTicTacToeGame {
    private Cell[][] board;
    private GameStatus status;
    private int boardSize;
    private int numToWin;
    private int turnCount;
    private boolean isXTurn;

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
        }
        else {
            board[row][col] = Cell.O;
        }
        
        setGameStatus(checkWin(numToWin));
        
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
    //pass in cell type so you don't have to rewrite above loops for o
    public GameStatus checkWin(int numToWin) {
 
    	//check each row across
    	int count = 0;
    	for (int row = 0; row < boardSize; row++) {
        	for (int col = 0; col < boardSize - 1; col++) {
        		
        			if (board[row][col].equals(Cell.X) 
        					&& board[row][col+1].equals(Cell.X)) {
        				count++;
        			
        			if (count >= numToWin-1)
        				return GameStatus.X_WON;	
        			}
        		} 

        	}
    	
    	//check down each column
    	count = 0;
    	for (int col = 0; col < boardSize; col++) {
        	for (int row = 0; row < boardSize - 1; row++) {
        		
        			if (board[row][col].equals(Cell.X) 
        					&& board[row+1][col].equals(Cell.X)) {
        				count++;
        			
        			if (count >= numToWin-1)
        				return GameStatus.X_WON;	
        			}
        		} 

        	}
    	    	
    	//check diagonally from top left to bottom right
    	
//    	lowest diagonal starting square given by board[boardSize - numToWin][0]
//    	then we move up one diagonal at a time until we reach the diagonal of 
//		length boardSize
    	//TODO: count seems to not reset correctly?
    	for (int i = numToWin; i < boardSize; i++) {
    		count = 0;
    		for (int j = 0; j < i - 1; j++) {
    			if (board[boardSize-i+j][j] == Cell.X 
    					&& board[boardSize-i+j+1][j+1] == Cell.X) {
    				count++;
    			}
    			if (count >= numToWin - 1)
    				return GameStatus.X_WON;	
    		}
    	}
//		TODO: After fixing above loops, mirror to check middle diagonal and above

    	//Write similar code to check from bottom left to top right

    	return GameStatus.IN_PROGRESS; 
    	
    }
}
