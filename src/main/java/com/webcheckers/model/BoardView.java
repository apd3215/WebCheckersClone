package com.webcheckers.model;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable<Row> {
    private List<Row> rows = new ArrayList<Row>();

    public BoardView() {
        for(int i = 0; i < 8; i++) {
            Row row = new Row(i);
            rows.add(row);
        }
    }

    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }

}
