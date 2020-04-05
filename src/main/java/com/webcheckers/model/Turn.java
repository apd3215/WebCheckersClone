package com.webcheckers.model;

import java.util.ArrayList;

public class Turn {

    private ArrayList<Move> moves;

    public Turn(){
        this.moves = new ArrayList<>();
    }

    public void add_move(Move move){
        this.moves.add(move);
    }

    public void remove_move(){
        this.moves.remove(this.moves.size() - 1);
    }

    public Move getPrevMove(){
        return this.moves.get(this.moves.size() - 1);
    }
}
