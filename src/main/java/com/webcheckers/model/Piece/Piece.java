package com.webcheckers.model.Piece;

/**
 * Abstract class that represents a piece that lies in a space in the webcheckers game.
 * @author Joe Netti
 * @author Joshua Yoder
 * @author Jonathan Baxley
 */
public abstract class Piece {
  /**
   * Enum, either a white or red piece.
   */
  public enum PieceColor {WHITE, RED}

  /**
   * Enum, either a single or king piece.
   */
  public enum PieceType {SINGLE, KING}

  public PieceColor color;
  public PieceType type;

  /**
   * Constructor for a piece object.
   * @param color the color of the piece
   */
  public Piece(PieceColor color) {
    this.color = color;
    this.type = PieceType.SINGLE;
  }


  /**
   * Accessor
   * @return the color of the piece
   */
  public PieceColor getColor() {
    return this.color;
  }

  /**
   * Accessor
   * @return the type of the piece
   */
  public PieceType getType(){
    return this.type;
  }
}
