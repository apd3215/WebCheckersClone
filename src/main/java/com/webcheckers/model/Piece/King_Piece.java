package com.webcheckers.model.Piece;

/**
 * Subclass of Piece for King pieces.
 * @author Jonathan Baxley
 */
public class King_Piece extends Piece {
    public PieceType type;
    public King_Piece(PieceColor color ){
        super(color);
        this.type = PieceType.KING;
    }
}
