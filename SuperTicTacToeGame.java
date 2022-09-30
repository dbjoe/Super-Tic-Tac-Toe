package project2;

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
        if (!getCell(row, col).equals("EMPTY")){
            throw new IllegalArgumentException("Cannot place here");
        }
        if (isXTurn) {
            board[row][col] = Cell.X;
        }
        else {
            board[row][col] = Cell.O;
        }
        increaseTurnCount(1);
        changeTurn(getXTurn());
    }

    public GameStatus getStatus() {
        return status;
    }
    public int getBoardSize(){
        return boardSize;
    }

    public Cell[][] getBoard(){
        return board;
    }

    public GameStatus getGameStatus(){
        return GameStatus.IN_PROGRESS;
    }
    public void reset(){
        for (int row = 0; row < boardSize; row++){
            for (int col = 0; col < boardSize; col++){
                board[row][col] = Cell.EMPTY;
            }
        }
        setTurnCount(0);
    }

    public String getCell(int row, int col){
        return "" + board[row][col];
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
}
