package com.mygdx.game;

public class Move {
    private int changeX;
    private int changeY;

    public Move(int changeX, int changeY) {
        this.changeX = changeX;
        this.changeY = changeY;
    }

    public int getChangeX() {
        return changeX;
    }

    public int getChangeY() {
        return changeY;
    }
}
