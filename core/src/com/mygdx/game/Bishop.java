package com.mygdx.game;

public class Bishop implements ChessPiece {
    COLOR color = COLOR.BlACK;

    public Bishop(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return Math.abs(changeX) == Math.abs(changeY);
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
