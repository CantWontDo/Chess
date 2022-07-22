package com.mygdx.game;

public class Castle implements ChessPiece {
    COLOR color = COLOR.BlACK;

    public Castle(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return changeY == 0 || changeX == 0;
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
