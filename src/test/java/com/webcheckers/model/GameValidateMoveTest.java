package com.webcheckers.model;

import static org.mockito.Mockito.mock;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.King_Piece;
import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Single_Piece;
import com.webcheckers.model.Piece.Piece.PieceColor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Checks move validation code in Game.
 * @author Joshua Yoder
 */
public class GameValidateMoveTest {
    /**
     * Component under test
     */
    private Game CuT;

    /**
     * Mocks for players
     */
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * printBoard prints a boardView to the console
     * @param boardView the boardView to print
     */
    private void printBoard(BoardView boardView) {
        System.out.println(" 01234567");
        for(int i = 0; i <= 7; i++) {
            System.out.print(i);
            for(int j = 0; j <= 7; j++) {
                Space space = boardView.getSpace(i, j);
                Piece piece = space.getPiece();
                if (piece == null) {
                    System.out.print("_");
                } else if (piece.color == Piece.PieceColor.WHITE && piece.type == Piece.PieceType.SINGLE) {
                    System.out.print("w");
                } else if (piece.color == Piece.PieceColor.WHITE && piece.type == Piece.PieceType.KING) {
                    System.out.print("W");
                } else if (piece.color == Piece.PieceColor.RED && piece.type == Piece.PieceType.SINGLE) {
                    System.out.print("r");
                } else if (piece.color == Piece.PieceColor.RED && piece.type == Piece.PieceType.KING) {
                    System.out.print("R");
                } 
            }
            System.out.println();
        }
    }

    /**
     * setCustomBoard sets a board in a custom way
     * _ is a space
     * w is a white piece, W kinged
     * r is a red piece, R kinged
     * u (or any other char) leaves a space unchanged
     * @param board board representation as a 2d char array
     * @param boardView the boardView to set
     */
    private void setCustomBoard(char[][] board, BoardView boardView) {
        for(int i = 0; i <= 7; i++) {
            for(int j = 0; j <= 7; j++) {
                if (board[i][j] == '_') {
                    boardView.getSpace(i, j).setPiece(null);
                } else if (board[i][j] == 'w') {
                    boardView.getSpace(i, j).setPiece(new Single_Piece(PieceColor.WHITE));
                } else if (board[i][j] == 'W') {
                    boardView.getSpace(i, j).setPiece(new King_Piece(PieceColor.WHITE));
                } else if (board[i][j] == 'r') {
                    boardView.getSpace(i, j).setPiece(new Single_Piece(PieceColor.RED));
                } else if (board[i][j] == 'R') {
                    boardView.getSpace(i, j).setPiece(new King_Piece(PieceColor.RED));
                }
            }
        }
    }

    /**
     * Setup before each test execution
     */
    @BeforeEach
    public void setup() {
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        CuT = new Game(redPlayer, whitePlayer);
    }

    /*
    //Template for Board
        char[][] board = {{'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'},
                          {'_','_','_','_','_','_','_','_'}};
        setCustomBoard(board, CuT.getBoardView());
    */

    @Test
    public void test() {
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'},
                          {'_',' ','_',' ','_',' ','_',' '},
                          {' ','_',' ','_',' ','_',' ','_'},
                          {'_',' ','_',' ','_',' ','_',' '},
                          {' ','_',' ','_',' ','_',' ','_'},
                          {'_',' ','_',' ','_',' ','_',' '},
                          {' ','_',' ','_',' ','_',' ','_'},
                          {'_',' ','_',' ','_',' ','_',' '}};
        setCustomBoard(board, CuT.getBoardView());
        printBoard(CuT.getBoardView());
    }

    public void test_invalidOutOfBoundsMove() {

    }

    public void test_invalidMoveOntoWhite() {

    }

    public void test_validSimpleMove() {

    }

    public void test_invalidBackwardsMove() {

    }

    public void test_validSimpleJump() {

    }

    public void test_validMultiJump() {

    }

    public void test_ignoreForcedSimpleJump() {

    }

    public void test_ignoreForcedMultiJump() {

    }

    public void test_partialIgnoreForcedMultiJump() {

    }

    public void test_validMultiRouteForcedSimpleJumpA() {

    }

    public void test_validMultiRouteForcedSimpleJumpB() {
        
    }

    public void test_validMultiRouteForcedMultiJumpA() {

    }
    
    public void test_validMultiRouteForcedMultiJumpB() {

    }

    public void test_kingedBackwards() {

    }

    public void test_kingOutOfBounds() {

    }

    public void test_kingedFourOptionJumpA() {

    }
    
    public void test_kingedFourOptionJumpB() {

    }

    public void test_kingedFourOptionJumpC() {
        
    }

    public void test_kingedFourOptionJumpD() {

    }

    public void test_kingedIgnoreJumps() {

    }

}