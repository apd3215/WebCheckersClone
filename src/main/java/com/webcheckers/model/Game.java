package com.webcheckers.model;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.King_Piece;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Piece.PieceColor;
import com.webcheckers.model.Piece.Single_Piece;

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
            int redpieces = 0;
            int whitepieces = 0;
            for (int i = 0; i < 8; i++){
                for (int k = 0; k < 8; k++){
                    Space space = this.boardView.getSpace(i, k);
                    if (space != null){
                        if (space.getPiece() != null){
                            if (space.getPiece().color == PieceColor.RED){
                                redpieces++;
                            }
                            else{
                                whitepieces++;
                            }
                        }
                    }
                }
            }
            if (redpieces == 0 || whitepieces == 0){
                this.isGameOver = true;
                return true;
            }
            else{
                return false;
            }
        }
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

    public boolean check_board(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (boardView.getSpace(i,j).getPiece() == null) {
                    continue;
                } else if (boardView.getSpace(i, j).getPiece() instanceof King_Piece){
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
        if (move == null){
            return false;
        }
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
            PieceColor color;
            if (activeColor == PieceColor.RED){
                color = PieceColor.WHITE;
            } else {
                color = PieceColor.RED;
            }
            this.boardView.getSpace(row, col).setPiece(new Single_Piece(color));
        } else {
            this.boardView.getSpace(currRow,currCell).setPiece(end);
            this.boardView.getSpace(endRow, endCell).setPiece(null);
        }
        return true;
    }


    public boolean makeMove(Move move){
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
                        captured.setPiece(null);
                    }
                    else if ((endCell - currCell) == -2){ // coming from right to left
                        int capturedCell = endCell +1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        captured.setPiece(null);
                    }
            } else { // if White player makes the jump move
                    int capturedRow =  endRow - 1;
                    if ((endCell - currCell) == 2){ //coming from left to right but from white's perspective
                        int capturedCell = endCell - 1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        captured.setPiece(null);
                    }
                    else if ((endCell - currCell) == -2){
                        int capturedCell = endCell + 1;
                        Space captured = this.boardView.getSpace(capturedRow,capturedCell);
                        captured.setPiece(null);
                    }
            }
        }
        curr.setPiece(null);
        end_space.setPiece(moved);
        System.out.println("Start Row : " + currRow + " Start Col: " + currCell);
        System.out.println("End Row: " + endRow + " End Col: " + endCell);
        return true;
    }

    private Boolean isValidNormalMoveSingle(Move move) throws Exception {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Piece startPiece = startSpace.getPiece();
        PieceColor pieceColor = startPiece.color;
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece endPiece = endSpace.getPiece();
        if (endPiece != null) {
            throw new Exception("Piece already present at endPiece.");
        }
        boolean validRow;
        boolean validCell;
        if (pieceColor == PieceColor.RED) {
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
        if (Math.abs(turn.getPrevMove().getStart().getRow() - turn.getPrevMove().getEnd().getRow()) != 2){
            Move prev = this.turn.getPrevMove();
            this.backUp();
            if (!check_board()){
                return false;
            }
            this.makeMove(prev);
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
                if (pStart instanceof King_Piece || pStart.getColor() == PieceColor.RED) {
                    if (startCol - endCol > 0) {
                        return boardView.getSpace(startRow - 1, startCol - 1).getPiece().getColor() != curr;
                    } else {
                        return boardView.getSpace(startRow - 1, startCol + 1).getPiece().getColor() != curr;
                    }
                } else {
                    return false;
                }
            } else {
                if (pStart instanceof King_Piece || pStart.getColor() == PieceColor.WHITE) {
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
        Space startSpace = boardView.getSpace(start.getRow(), start.getCell());
        Space endSpace = boardView.getSpace(end.getRow(), end.getCell());
        Piece startPiece = startSpace.getPiece();
        Piece endPiece = endSpace.getPiece();

        // beginning and end must be on black square
        if (startSpace.getColor() == Space.Color.LIGHT || endSpace.getColor() == Space.Color.LIGHT) {
            throw new Exception("Pieces only on black squares.");
        }
        else if (startPiece == null) {
            throw new Exception("No piece on start square.");
        }
        else if (endPiece != null) {
            throw new Exception("Already a piece present on end square.");
        }

        if (startPiece.getType() == Piece.PieceType.SINGLE) {
            if (turn.getNum() > 1){
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
                return b;
            }
        }
        else {
            //TODO KING PIECE
            throw new Exception("King move not implemented yet");

        }
    }
}