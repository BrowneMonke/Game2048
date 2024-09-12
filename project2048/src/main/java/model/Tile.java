package model;

import java.util.*;

public class Tile {
    private int value;
    private int row;
    private int column;
    //Resets after each turn
    private boolean merged;


    public Tile(int row, int column) {
        this.setValue(0);
        this.setRow(row);
        this.setColumn(column);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
    public boolean isNotMerged() {
        return !this.merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    @Override
    public String toString() {
        return String.format("%d", this.value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Tile tile)) return false;
        return this.value == tile.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
