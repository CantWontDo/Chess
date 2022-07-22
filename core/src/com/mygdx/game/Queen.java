package com.mygdx.game;

public class Queen implements ChessPiece {
    COLOR color = COLOR.BlACK;

    public Queen(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return Math.abs(changeX) == Math.abs(changeY) || changeY == 0 || changeX == 0;
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
