package com.webcheckers.model;

/**
 * Represents a piece that lies in a space in the webcheckers game.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class Piece {
  /**
   * Enum, either a single piece or a king piece.
   */
  public enum PieceType {SINGLE, KING}

  /**
   * Enum, either a white or red piece.
   */
  public enum PieceColor {WHITE, RED}

  public PieceType type;
  public PieceColor color;

  /**
   * Constructor for a piece object.
   * @param type the type of the piece
   * @param color the color of the piece
   */
  public Piece(PieceType type, PieceColor color) {
    this.type = type; 
    this.color = color; 
  }

  /**
   * Accessor
   * @return the type of the piece
   */
  public PieceType getType() {
    return this.type;
  }

  /**
   * Accessor
   * @return the color of the piece
   */
  public PieceColor getColor() {
    return this.color;
  }

}
