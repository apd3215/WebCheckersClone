package com.webcheckers.model.Piece;

import com.webcheckers.model.Piece.Piece;

/**
 * Subclass of Piece for King pieces.
 */
public class King_Piece extends Piece {
    public PieceType type;
    public King_Piece(PieceColor color ){
        super(color);
        this.type = PieceType.KING;
    }
}
