package com.webcheckers.model;

import static org.mockito.Mockito.mock;

import com.webcheckers.appl.Player;
import com.webcheckers.model.Piece.PieceColor;

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
     * (any other char) leaves a space unchanged
     * @param board board representation as a 2d char array
     * @param boardView the boardView to set
     */
    private void setCustomBoard(char[][] board, BoardView boardView) {
        for(int i = 0; i <= 7; i++) {
            for(int j = 0; j <= 7; j++) {
                if (board[i][j] == '_') {
                    boardView.getSpace(i, j).setPiece(null);
                } else if (board[i][j] == 'w') {
                    boardView.getSpace(i, j).setPiece(new Piece(PieceColor.WHITE));
                } else if (board[i][j] == 'W') {
                    boardView.getSpace(i, j).setPiece(new Piece(PieceColor.WHITE, Piece.PieceType.KING));
                } else if (board[i][j] == 'r') {
                    boardView.getSpace(i, j).setPiece(new Piece(PieceColor.RED));
                } else if (board[i][j] == 'R') {
                    boardView.getSpace(i, j).setPiece(new Piece(PieceColor.RED, Piece.PieceType.KING));
                }
            }
        }
    }

    /**
     * Create a new move quickly with origin and destination integers.
     * @param origRow the origin row
     * @param origCol the origin column
     * @param destRow the destination row
     * @param destCol the destination column
     * @return the created move
     */
    private Move createMove(int origRow, int origCol, int destRow, int destCol) {
        Position start = new Position(origRow, origCol);
        Position end = new Position(destRow, destCol);
        return new Move(start, end);
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

    @Test
    public void test_invalidMoveOntoSelf() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','w',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,4);
    }

    @Test
    public void test_invalidMoveOntoOpponent() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,4);
    }

    @Test
    public void test_invalidSelfJumpOver() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','w',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }

    @Test
    public void test_validSimpleMove() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','_',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,4);
    }

    @Test
    public void test_invalidBackwardsMove() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','_',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,5,2);
    }

    @Test
    public void test_validSimpleJump() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }

    @Test
    public void test_validMultiJump() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','r',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,0,7);
    }

    @Test
    public void test_ignoreForcedSimpleJump() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,2);
    }

    @Test
    public void test_ignoreForcedMultiJump() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','r',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,2);
    }

    @Test
    public void test_partialIgnoreForcedMultiJump() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','r',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }

    @Test
    public void test_validMultiRouteForcedSimpleJumpA() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','r',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }

    public void test_validMultiRouteForcedSimpleJumpB() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','r',' ','r',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,1);
    }

    @Test
    public void test_kingedBackwards() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','_',' ','_',' '}, //3
                          {' ','_',' ','W',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,5,2);
    }

    @Test
    public void test_kingedFourOptionJumpA() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','r',' ','r',' ','_',' '}, //3
                          {' ','_',' ','W',' ','_',' ','_'}, //4
                          {'_',' ','r',' ','r',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }
    
    @Test
    public void test_kingedFourOptionJumpB() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','R',' ','r',' ','_'}, //2
                          {'_',' ','_',' ','W',' ','_',' '}, //3
                          {' ','_',' ','r',' ','r',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','R',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(3,4,7,0);
    }

    @Test
    public void test_validSimpleJumpOverKing() {
        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','_',' ','R',' ','_',' '}, //3
                          {' ','_',' ','w',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','_',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,2,5);
    }

    @Test
    public void test_kingedIgnoreJumps() {
                        //  0 . 1 . 2 . 3 . 4 . 5 . 6 . 7
        char[][] board = {{' ','_',' ','_',' ','_',' ','_'}, //0
                          {'_',' ','_',' ','_',' ','_',' '}, //1
                          {' ','_',' ','_',' ','_',' ','_'}, //2
                          {'_',' ','r',' ','_',' ','_',' '}, //3
                          {' ','_',' ','W',' ','_',' ','_'}, //4
                          {'_',' ','_',' ','r',' ','_',' '}, //5
                          {' ','_',' ','_',' ','_',' ','_'}, //6
                          {'_',' ','_',' ','_',' ','_',' '}}; //7
        setCustomBoard(board, CuT.getBoardView());
        Move move = createMove(4,3,3,4); 
    }
}