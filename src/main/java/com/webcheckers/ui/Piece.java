package com.webcheckers.ui;

public class Piece {

  public enum PieceType {NORMAL, KING}

  public enum Color {BLACK, RED}

  public PieceType type;
  public PieceColor color;

  public Piece(PieceType type, PieceColor color) {
    this.type = type; 
    this.color = color; 
  }

  public PieceType getType() {
    return this.type;
  }

  public PieceType getColor() {
    return this.color;
  }

}
