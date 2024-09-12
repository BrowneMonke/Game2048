package model;

import java.util.*;

public class Board {
    //Board Size + Setup
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;
    private final Tile[][] tiles;

    //Input processing + Score keeping
    private boolean moved = false;
    private final Score score;


    public Board() {
        this.tiles = new Tile[HEIGHT][WIDTH];
        this.score = new Score();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                this.tiles[i][j] = new Tile(i, j);
            }
        }

        this.addTile();
        this.addTile();
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }


//=========================================SCORE KEEPING==============================================\\
    public String getHighScore() {
        return this.score.getHighScore();
    }

    public int getScore() {
        return this.score.getScore();
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

//=========================================TILE MOVEMENT==============================================\\

    public boolean isMoved() {
        return this.moved;
    }
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public void resetMerged() {
        for (int row = 0; row < HEIGHT; row++){
            for (int col = 0; col < WIDTH; col++) {
                this.tiles[row][col].setMerged(false);
            }
        }
    }

    public void slideLeft() {
        char direction = 'L';
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 1; col < WIDTH; col++) {
                if (this.tiles[row][col].getValue() != 0) {
                    int newCol = col - 1;
                    int value = this.tiles[row][col].getValue();
                    // Move the tile as far left as possible
                    newCol = moveTile(row, newCol, value, direction);
                    // Merge tiles if they have the same value
                    this.mergeTiles(row, newCol, value, direction);
                }
            }
        }
    }

    public void slideRight() {
        char direction = 'R';
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = WIDTH - 2; col >= 0; col--) {
                if (this.tiles[row][col].getValue() != 0) {
                    int value = this.tiles[row][col].getValue();
                    int newCol = col + 1;
                    // Move the tile as far right as possible
                    newCol = moveTile(row, newCol, value, direction);
                    // Merge tiles if they have the same value
                    this.mergeTiles(row, newCol, value, direction);
                }
            }
        }
    }

    public void slideUp() {
        char direction = 'U';
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 1; row < HEIGHT; row++) {
                if (this.tiles[row][col].getValue() != 0) {
                    int value = this.tiles[row][col].getValue();
                    int newRow = row - 1;
                    // Move the tile as far up as possible
                    newRow = moveTile(newRow, col, value, direction);
                    // Merge tiles if they have the same value
                    this.mergeTiles(newRow, col, value, direction);
                }
            }
        }
    }

    public void slideDown() {
        char direction = 'D';
        for (int col = 0; col < WIDTH; col++) {
            for (int row = HEIGHT-2; row >= 0; row--) { //DESC iteration to avoid multiple merges
                if (this.tiles[row][col].getValue() != 0) {
                    int newRow = row + 1;
                    int value = this.tiles[row][col].getValue();
                    // Move the tile as far down as possible
                    newRow = moveTile(newRow, col, value, direction);
                    // Merge tiles if they have the same value
                    this.mergeTiles(newRow, col, value , direction);
                }
            }
        }
    }

    private int moveTile(int row, int col, int value, char direction) {
        if (direction == 'L') {
            return moveTileLeft(row, col, value);
        }
        else if (direction == 'R') {
            return moveTileRight(row, col, value);
        }
        else if (direction == 'U') {
            return moveTileUp(row, col, value);
        }
        else if (direction == 'D') {
           return moveTileDown(row, col, value);
        }
        return -1;
    }

    private int moveTileLeft(int row, int col, int value) {
        int newColL = col;
        while (newColL >= 0 && this.tiles[row][newColL].getValue() == 0) {
            this.tiles[row][newColL].setValue(value);
            this.tiles[row][newColL + 1].setValue(0);
            newColL--;
            this.setMoved(true);
        }
        return newColL;
    }

    private int moveTileRight(int row, int col, int value) {
        int newColR = col;
        while (newColR < WIDTH && this.tiles[row][newColR].getValue() == 0) {
            this.tiles[row][newColR].setValue(value);
            this.tiles[row][newColR - 1].setValue(0);
            newColR++;
            this.setMoved(true);
        }
        return newColR;
    }

    private int moveTileUp(int row, int col, int value) {
        int newRowU = row;
        while (newRowU >= 0 && this.tiles[newRowU][col].getValue() == 0) {
            this.tiles[newRowU][col].setValue(value);
            this.tiles[newRowU + 1][col].setValue(0);
            newRowU--;
            this.setMoved(true);
        }
        return newRowU;
    }

    private int moveTileDown(int row, int col, int value) {
        int newRowD = row;
        while (newRowD < HEIGHT && this.tiles[newRowD][col].getValue() == 0) {
            this.tiles[newRowD][col].setValue(value);
            this.tiles[newRowD - 1][col].setValue(0);
            newRowD++;
            this.setMoved(true);
        }
        return newRowD;
    }


    private void mergeTiles(int row, int col, int value, char direction) {
        switch (direction) {
            case 'L':
                mergeTilesLeft(row, col, value);
                break;
            case 'R':
                mergeTilesRight(row, col, value);
                break;
            case 'U':
                mergeTilesUp(row, col, value);
                break;
            case 'D':
                mergeTilesDown(row, col, value);
                break;
        }
    }

    public void mergeTilesLeft(int row, int newColL, int value) {
        if (newColL >= 0 && this.tiles[row][newColL].getValue() == value && this.tiles[row][newColL].isNotMerged()) {
            this.tiles[row][newColL].setValue(value * 2);
            this.tiles[row][newColL + 1].setValue(0);
            updateBoard(row, newColL, value);
        }
    }

    private void mergeTilesRight(int row, int newColR, int value) {
        if (newColR < WIDTH && this.tiles[row][newColR].getValue() == value && this.tiles[row][newColR].isNotMerged()) {
            this.tiles[row][newColR].setValue(value * 2);
            this.tiles[row][newColR - 1].setValue(0);
            updateBoard(row, newColR, value);
        }
    }

    private void mergeTilesUp(int newRowU, int col, int value) {
        if (newRowU >= 0 && this.tiles[newRowU][col].getValue() == value && this.tiles[newRowU][col].isNotMerged()) {
            this.tiles[newRowU][col].setValue(value * 2);
            this.tiles[newRowU + 1][col].setValue(0);
            updateBoard(newRowU, col, value);
        }
    }

    private void mergeTilesDown(int newRowD, int col, int value) {
        if (newRowD < HEIGHT && this.tiles[newRowD][col].getValue() == value && this.tiles[newRowD][col].isNotMerged()) {
            this.tiles[newRowD][col].setValue(value * 2);
            this.tiles[newRowD - 1][col].setValue(0);
            updateBoard(newRowD, col, value);
        }
    }

    private void updateBoard(int row, int col, int value) {
        this.tiles[row][col].setMerged(true);
        this.score.addToScore(value * 2);
        this.setMoved(true);
    }


//===========================================BOARD METHODS============================================\\

    public void addTile() {
        int x, y;
        do {
            x = (int) (Math.random() * WIDTH);
            y = (int) (Math.random() * HEIGHT);
        } while (this.tiles[x][y].getValue() != 0);

        this.tiles[x][y].setValue(Math.random() < 0.9 ? 2 : 4);
    }

    public List<Tile> adjacentTiles(Tile tile) {
        List<Tile> adjacentsList = new ArrayList<>();
        if (tile.getColumn() == 0) {
            addFirstColumnAdjacentTiles(tile, adjacentsList);
        }
        else if (tile.getColumn() == WIDTH-1) {
            addLastColumnAdjacentTiles(tile, adjacentsList);
        }
        else {
            addOtherColumnAdjacentTiles(tile, adjacentsList);
        }
        return adjacentsList;
    }

    private void addFirstColumnAdjacentTiles(Tile tile, List<Tile> adjacentsList) {
        if (tile.getRow() == 0) {
            adjacentsList.add(this.tiles[0][1]);
            adjacentsList.add(this.tiles[1][0]);
        }
        else if (tile.getRow() == HEIGHT - 1) {
            adjacentsList.add(this.tiles[(HEIGHT - 1) - 1][0]);
            adjacentsList.add(this.tiles[HEIGHT - 1][1]);
        }
        else {
            adjacentsList.add(this.tiles[tile.getRow() - 1][0]);
            adjacentsList.add(this.tiles[tile.getRow() + 1][0]);
            adjacentsList.add(this.tiles[tile.getRow()][1]);
        }
    }

    private void addLastColumnAdjacentTiles(Tile tile, List<Tile> adjacentsList) {
        if (tile.getRow() == 0) {
            adjacentsList.add(this.tiles[0][(WIDTH - 1) - 1]);
            adjacentsList.add(this.tiles[1][(WIDTH - 1)]);
        }
        else if (tile.getRow() == HEIGHT - 1) {
            adjacentsList.add(this.tiles[(HEIGHT - 1) - 1][WIDTH - 1]);
            adjacentsList.add(this.tiles[HEIGHT - 1][(WIDTH - 1) - 1]);
        }
        else {
            adjacentsList.add(this.tiles[tile.getRow() - 1][WIDTH - 1]);
            adjacentsList.add(this.tiles[tile.getRow() + 1][WIDTH - 1]);
            adjacentsList.add(this.tiles[tile.getRow()][(WIDTH - 1) - 1]);
        }
    }

    private void addOtherColumnAdjacentTiles(Tile tile, List<Tile> adjacentsList) {
        if (tile.getRow() == 0) {
            adjacentsList.add(this.tiles[0][tile.getColumn() - 1]);
            adjacentsList.add(this.tiles[0][tile.getColumn() + 1]);
            adjacentsList.add(this.tiles[1][tile.getColumn()]);
        }
        else if (tile.getRow() == HEIGHT - 1) {
            adjacentsList.add(this.tiles[(HEIGHT - 1) - 1][tile.getColumn()]);
            adjacentsList.add(this.tiles[HEIGHT - 1][tile.getColumn() - 1]);
            adjacentsList.add(this.tiles[HEIGHT - 1][tile.getColumn() + 1]);
        }
        else {
            adjacentsList.add(this.tiles[tile.getRow() - 1][tile.getColumn()]);
            adjacentsList.add(this.tiles[tile.getRow() + 1][tile.getColumn()]);
            adjacentsList.add(this.tiles[tile.getRow()][tile.getColumn() - 1]);
            adjacentsList.add(this.tiles[tile.getRow()][tile.getColumn() + 1]);
        }
    }


}
