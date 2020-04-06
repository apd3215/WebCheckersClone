package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.PieceColor;

import java.util.logging.Logger;

/**
 * Represents a single game in webcheckers. Holds some game logic
 * and is instantiated whenever a new game begins.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class Game {
    private static final Logger LOG = Logger.getLogger(Game.class.getName());
    private Player redPlayer;
    private Player whitePlayer;
    private PieceColor activeColor;
    private BoardView boardView;
    private boolean isGameOver;
    private Player isResigned;
    private Turn turn;
    private Player winner;
    private int whitepieces;
    private int redpieces;

    /**
     * Enum representing the view mode (player, spectator, replay)
     */
    public enum ViewMode {PLAY, SPECTATOR, REPLAY}

    /**
     * Instantiate a game with a given red and white player. Sets the board.
     * @param redPlayer the red player in the game
     * @param whitePlayer the white player in the game
     */
    public Game(Player redPlayer, Player whitePlayer) {
        LOG.fine("Game instantiated between" + redPlayer.getName() + " and " + whitePlayer.getName());
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.activeColor = PieceColor.RED;
        this.boardView = new BoardView();
        this.turn = new Turn();
        boardView.setBoard();
        this.isGameOver = false;
        this.isResigned = null;
        this.winner = null;
        this.whitepieces = 12;
        this.redpieces = 12;
    }

    public Turn getTurn(){
        return this.turn;
    }

    /**
     * Return the red player.
     * @return the player object corresponding to the red player
     */
    public Player getRedPlayer() {
        return this.redPlayer;
    }

    /**
     * Return the white player.
     * @return the player object corresponding to the white player
     */
    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public void gameOver(){
        this.isGameOver = true;
    }

    public boolean isGameOver(){
        if (this.isGameOver){
            return true;
        }
        else{
            if (redpieces == 0){
                this.winner = this.whitePlayer;
                this.isGameOver = true;
                return true;
            }
            else if (whitepieces == 0){
                this.winner = this.redPlayer;
                this.isGameOver = true;
                return true;
            }
            else{
                return false;
            }
        }
    }

    public Player getWinner(){
        return this.winner;
    }

    public void setIsResigned(Player player){
        this.isResigned = player;
    }

    public Player getIsResigned(){
        return this.isResigned;
    }
    /**
     * Gets the active PieceColor of the game (red or white)
     * @return the active PieceColor
     */
    public PieceColor getActiveColor() {
        return this.activeColor;
    }

    /**
     * Returns the board view of the game.
     * @author the BoardView
     */
    public BoardView getBoardView() {
        return this.boardView;
    }


    /**
     * Returns weather a player object is in the game.
     * @return weather the player object is in this game.
     */
    public Boolean isPlayerInGame(Player player) {
        return redPlayer == player || whitePlayer == player;
    }

    private void updatePiece(PieceColor Color){
        if (Color == PieceColor.WHITE){
            this.whitepieces--;
        } else {
            this.redpieces--;
        }
    }

    private void revertPiece(PieceColor Color){
        if (Color == PieceColor.WHITE){
            this.whitepieces++;
        } else {
            this.redpieces++;
        }
    }

    private boolean check_UpRight(int i, int j){
        if (i > 5 || j > 5){
            return true;
        } else {
            Piece p1 = boardView.getSpace(i+1, j+1).getPiece();
            Piece p2 = boardView.getSpace(i+2, j+2).getPiece();
            return !(p2 == null && p1 != null && p1.getColor() != this.activeColor);
        }
    }

    private boolean check_UpLeft(int i, int j){
        if (i > 5 || j < 2){
            return true;
        } else {
            Piece p1 = boardView.getSpace(i+1, j-1).getPiece();
            Piece p2 = boardView.getSpace(i+2, j-2).getPiece();
            return !(p2 == null && p1 != null && p1.getColor() != this.activeColor);
        }
    }

    private boolean check_DownRight(int i, int j){
        if (i < 2 || j > 5){
            return true;
        } else {
            Piece p1 = boardView.getSpace(i-1, j+1).getPiece();
            Piece p2 = boardView.getSpace(i-2, j+2).getPiece();
            return !(p2 == null && p1 != null && p1.getColor() != this.activeColor);
        }
    }

    private boolean check_DownLeft(int i, int j){
        if (i < 2 || j < 2){
            return true;
        } else {
            Piece p1 = boardView.getSpace(i-1, j-1).getPiece();
            Piece p2 = boardView.getSpace(i-2, j-2).getPiece();
            return !(p2 == null && p1 != null && p1.getColor() != this.activeColor);
        }
    }

    private boolean checkBoardDoubleJump(int i, int j){
        if (boardView.getSpace(i, j).getPiece().getType() == Piece.PieceType.KING){
            boolean temp = check_DownLeft(i,j) && check_DownRight(i,j) && check_UpLeft(i,j)
                    && check_UpRight(i,j);
            if (!temp){
                return false;
            }
        } else if (boardView.getSpace(i,j).getPiece().getColor() == this.activeColor &&
                this.activeColor == PieceColor.WHITE){
            boolean temp = check_UpLeft(i,j) && check_UpRight(i,j);
            if (!temp){
                return false;
            }
        } else if (boardView.getSpace(i,j).getPiece().getColor() == this.activeColor &&
                this.activeColor == PieceColor.RED){
            boolean temp = check_DownLeft(i,j) && check_DownRight(i,j);
            if (!temp){
                return false;
            }
        }
        return true;
    }

    public boolean check_board(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (boardView.getSpace(i,j).getPiece() == null) {
                    continue;
                } else if (boardView.getSpace(i, j).getPiece().getType() == Piece.PieceType.KING){
                    boolean temp = check_DownLeft(i,j) && check_DownRight(i,j) && check_UpLeft(i,j)
                            && check_UpRight(i,j);
                    if (!temp){
                        return false;
                    }
                } else if (boardView.getSpace(i,j).getPiece().getColor() == this.activeColor &&
                                                this.activeColor == PieceColor.WHITE){
                    boolean temp = check_UpLeft(i,j) && check_UpRight(i,j);
                    if (!temp){
                        return false;
                    }
                } else if (boardView.getSpace(i,j).getPiece().getColor() == this.activeColor &&
                                                this.activeColor == PieceColor.RED){
                    boolean temp = check_DownLeft(i,j) && check_DownRight(i,j);
                    if (!temp){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean backUp(){
        Move move = this.turn.getPrevMove();
        this.turn.remove_move();
        int currRow = move.getStart().getRow();
        int currCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();
        setTurnAttr(currRow, currCell);
        Piece end = this.boardView.getSpace(endRow, endCell).getPiece();
        if (Math.abs(currRow - endRow) == 2){
            this.boardView.getSpace(currRow, currCell).setPiece(end);
            this.boardView.getSpace(endRow, endCell).setPiece(null);
            int row = ( currRow + endRow ) / 2;
            int col = ( currCell + endCell) / 2;
            Piece capturedPiece = this.turn.rem_capture();
            this.boardView.getSpace(row,col).setPiece(capturedPiece);
            revertPiece(capturedPiece.color);

        } else {
            this.boardView.getSpace(currRow,currCell).setPiece(end);
            this.boardView.getSpace(endRow, endCell).setPiece(null);
        }
        return true;
    }


    public void makeMove(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();
        int currRow = start.getRow();
        int currCell = start.getCell();
        int endRow = end.getRow();
        int endCell = end.getCell();
        Space curr = this.boardView.getSpace(currRow, currCell);
        Space end_space = this.boardView.getSpace(endRow, endCell);
        Piece moved = curr.getPiece();
        if (Math.abs(currRow - endRow) != 1){ // check if it's a jump move
            if (endRow < currRow){ // if Red player makes the jump Move
                    int capturedRow = endRow +1;
                    if ( (endCell - currCell) == 2){ // coming from left to right
                        int capturedCell = endCell -1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        this.turn.add_capture(captured.getPiece());
                        updatePiece(captured.getPiece().color);
                        captured.setPiece(null);
                    }
                    else if ((endCell - currCell) == -2){ // coming from right to left
                        int capturedCell = endCell +1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        this.turn.add_capture(captured.getPiece());
                        updatePiece(captured.getPiece().color);
                        captured.setPiece(null);
                    }
            } else { // if White player makes the jump move
                    int capturedRow =  endRow - 1;
                    if ((endCell - currCell) == 2){ //coming from left to right but from white's perspective
                        int capturedCell = endCell - 1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        this.turn.add_capture(captured.getPiece());
                        updatePiece(captured.getPiece().color);
                        captured.setPiece(null);
                    }
                    else if ((endCell - currCell) == -2){
                        int capturedCell = endCell + 1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        this.turn.add_capture(captured.getPiece());
                        updatePiece(captured.getPiece().color);
                        captured.setPiece(null);
                    }
            }
        }
        curr.setPiece(null);
//        Piece redKing = new King_Piece(PieceColor.RED);
//        Piece whiteKing = new King_Piece(PieceColor.WHITE);
        Piece redKing = new Piece(Piece.PieceColor.RED, Piece.PieceType.KING);
        Piece whiteKing = new Piece(PieceColor.WHITE, Piece.PieceType.KING);

        PieceColor color = moved.getColor();
        if (color == PieceColor.RED && endRow == 0){
            end_space.setPiece(redKing);
        }
        else if (color == PieceColor.WHITE && endRow == 7){
            end_space.setPiece(whiteKing);
        }
        else{
            end_space.setPiece(moved);
        }
        boolean isKing = this.boardView.getSpace(endRow,endCell).getPiece().type == Piece.PieceType.KING;
        System.out.println("Start Row : " + currRow + " Start Col: " + currCell + " isKing: " + isKing);
        System.out.println("End Row: " + endRow + " End Col: " + endCell + " isKing: " + isKing );
    }

    private Boolean isValidNormalMoveSingle(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Piece startPiece = startSpace.getPiece();
        PieceColor pieceColor = startPiece.color;
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece endPiece = endSpace.getPiece();
        boolean isKing = startPiece.getType() == Piece.PieceType.KING;
        if (endPiece != null) {
            throw new Exception("Piece already present at endPiece.");
        }
        boolean validRow;
        boolean validCell;
        if (isKing){
            validRow = Math.abs(start.getRow() - end.getRow()) == 1;
            validCell = Math.abs(start.getCell() - end.getCell()) ==1;
        }
        else if (pieceColor == PieceColor.RED ) {
            validRow = start.getRow() - end.getRow() == 1;
            validCell = start.getCell() - end.getCell() == -1 || start.getCell() - end.getCell() == 1;
        }
        else {
            validRow = end.getRow() - start.getRow() == 1;
            validCell = start.getCell() - end.getCell() == -1 || start.getCell() - end.getCell() == 1;
        }

        if (!validRow) {
            throw new Exception("Non-jump must be a difference of 1 row.");
        }
        else if (!validCell){
            throw new Exception("Non-jump must be a difference of 1 col.");
        }
        else {
            return true;
        }
    }

    public boolean endTurn(){
        Move prev = this.turn.getPrevMove();
        if (Math.abs(turn.getPrevMove().getStart().getRow() - turn.getPrevMove().getEnd().getRow()) != 2){
            this.backUp();
            if (!check_board()){
                this.makeMove(prev);
                this.getTurn().add_move(prev);
                return false;
            }
            this.makeMove(prev);
            this.getTurn().add_move(prev);
        } else {
            if (!checkBoardDoubleJump(prev.getEnd().getRow(), prev.getEnd().getCell())){
                return false;
            }
        }
        if (this.activeColor == PieceColor.RED){
            this.activeColor = PieceColor.WHITE;
        } else {
            this.activeColor = PieceColor.RED;
        }
        this.turn = new Turn();
        return true;
    }

    private void setTurnAttr(int row, int col){
        this.getTurn().setCol(col);
        this.getTurn().setRow(row);
    }

    public Boolean isValidJump(Move move){
        int startRow = move.getStart().getRow();
        int startCol = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCol = move.getEnd().getCell();
        if (this.getTurn().getNum() > 1){
            if (startRow != this.turn.getRow() || startCol != this.turn.getCol()){
                return false;
            }
        }
        if (Math.abs(startRow - endRow) != 2 || Math.abs(startCol - endCol) != 2){
            return false;
        } else {
            Piece pStart = boardView.getSpace(startRow, startCol).getPiece();
            Piece pEnd = boardView.getSpace(endRow, endCol).getPiece();
            PieceColor curr = pStart.getColor();
            if (pEnd != null){
                return false;
            }
            if (startRow - endRow > 0){
                if (pStart.getType() == Piece.PieceType.KING || pStart.getColor() == PieceColor.RED) {
                    if (startCol - endCol > 0) {
                        return boardView.getSpace(startRow - 1, startCol - 1).getPiece().getColor() != curr;
                    } else {
                        return boardView.getSpace(startRow - 1, startCol + 1).getPiece().getColor() != curr;
                    }
                } else {
                    return false;
                }
            } else {
                if (pStart.getType() == Piece.PieceType.KING || pStart.getColor() == PieceColor.WHITE) {
                    if (startCol - endCol > 0) {
                        return boardView.getSpace(startRow + 1, startCol - 1).getPiece().getColor() != curr;
                    } else {
                        return boardView.getSpace(startRow + 1, startCol + 1).getPiece().getColor() != curr;
                    }
                } else {
                    return false;
                }
            }
        }
    }


    public Boolean isMoveValid(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();

        System.out.println(start);

        if (start.getRow() > 7 || start.getCell() > 7 || start.getRow() < 0 || start.getCell() < 0) {
            throw new Exception("Index request out of bounds");
        } 

        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece startPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();

        if (startSpace.getColor() == Space.Color.LIGHT || endSpace.getColor() == Space.Color.LIGHT) {
            throw new Exception("Pieces only on black squares.");
        }
        else if (startPiece == null) {
            throw new Exception("No piece on start square.");
        }
        else if (endPiece != null) {
            throw new Exception("Already a piece present on end square.");
        }

        if (turn.getNum() == 1 && this.turn.getCapturedLen() == 0){
            throw new Exception("Invalid Move.");
        }
        if (Math.abs(start.getRow() - end.getRow()) == 1) {
            return isValidNormalMoveSingle(move);
        }
        else {
            // handles king as well as normal jump moves
            boolean b = isValidJump(move);
            if (!b){
                throw new Exception("Not a Valid Move.");
            } else {
                setTurnAttr(move.getStart().getRow(), move.getStart().getCell());
            }
            return true;
        }
    }
}