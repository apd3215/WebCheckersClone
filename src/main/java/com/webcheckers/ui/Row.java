package com.webcheckers.ui;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable {
    private int index;
    private List<Space> spaces = new ArrayList<Space>();

    public Row(int index) {
        this.index = index;
        for(int i = 0; i < 8; i++) {
            Space space = new Space(i);
            spaces.add(space);
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