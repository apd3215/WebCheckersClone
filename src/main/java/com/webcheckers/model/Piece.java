package com.webcheckers.model;

public class Piece {

  public enum PieceType {SINGLE, KING}

  public enum PieceColor {WHITE, RED}

  public PieceType type;
  public PieceColor color;

  public Piece(PieceType type, PieceColor color) {
    this.type = type; 
    this.color = color; 
  }

  public PieceType getType() {
    return this.type;
  }

  public PieceColor getColor() {
    return this.color;
  }

}
