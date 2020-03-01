package com.webcheckers.model;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webcheckers.model.Piece.PieceColor;
import com.webcheckers.model.Piece.PieceType;

public class Row implements Iterable<Space> {
    private int index;
    private List<Space> spaces = new ArrayList<Space>();

    public Row(int index) {
        this.index = index;
        for(int i = 0; i < 8; i++) {
            Space space = new Space(i);
            if (index % 2 == 0){
                if (i % 2 == 0){
                    space.setColor(Space.Color.LIGHT);
                }
                else{
                    space.setColor(Space.Color.DARK);
                }
            }
            else{
                if (i % 2 == 0) {
                    space.setColor(Space.Color.DARK);
                }
                else{
                    space.setColor(Space.Color.LIGHT);
                }
            }
            spaces.add(space);
        }
    }

    public void fillRow(PieceColor pieceColor) {
        for(int i = 0; i < 8; i++) {
            if(index%2 == 0) {
                if(i%2 != 0) {
                    Piece piece = new Piece(PieceType.SINGLE, pieceColor);
                    spaces.get(i).setPiece(piece);
                }
            } else {
                if(i%2 == 0) {
                    Piece piece = new Piece(PieceType.SINGLE, pieceColor);
                    spaces.get(i).setPiece(piece);
                }
            }
        }
    }

    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

    public int getIndex() {
        return index;
    }
}