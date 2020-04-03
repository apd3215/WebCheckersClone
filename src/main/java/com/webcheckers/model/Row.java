package com.webcheckers.model;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webcheckers.model.Piece.Piece;
import com.webcheckers.model.Piece.Piece.PieceColor;
import com.webcheckers.model.Piece.Piece.PieceType;
import com.webcheckers.model.Piece.Single_Piece;

/**
 * Represents a Row on the game board.
 * Iterable over each space.
 * @author Joe Netti
 * @author Joshua Yoder
 */
public class Row implements Iterable<Space> {
    private int index;
    private List<Space> spaces = new ArrayList<Space>();

    /**
     * Constructor
     * Create a row of spaces, alternating between light and dark
     * colors depending on the space position on the board.
     * @param index the index of the row
     */
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

    /**
     * Fills a row with a given piece color (only on the black squares)
     * @param pieceColor the piece color to fill the row with
     */
    public void fillRow(PieceColor pieceColor) {
        for(int i = 0; i < 8; i++) {
            if(index%2 == 0) {
                if(i%2 != 0) {
                    Piece piece = new Single_Piece(pieceColor);
                    spaces.get(i).setPiece(piece);
                }
            } else {
                if(i%2 == 0) {
                    Piece piece = new Single_Piece(pieceColor);
                    spaces.get(i).setPiece(piece);
                }
            }
        }
    }

    /**
     * Returns an iterator that is the reverse of the row iterator.
     * Useful for generating the view for the white player.
     * @return reverse row iterator (by reverse spaces)
     */
    public Iterator<Space> reverseIterator() {
        List<Space> inv = new ArrayList<Space>();
        for (int i = 0; i < 8; i++){
            inv.add(this.spaces.get(7-i));
        }
        return inv.iterator();
    }

    /**
     * Return an iterator over the row (spaces).
     * @return board iterator (by spaces)
     */
    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

    /**
     * Gets the index of a specific row.
     * @return the index of a row
     */
    public int getIndex() {
        return index;
    }

    public List<Space> getSpaces(){ return spaces;}

    public Space getSpace(int i) {
        return this.spaces.get(i);
    }

}