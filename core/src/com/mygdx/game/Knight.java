package com.mygdx.game;

public class Knight implements ChessPiece {
    COLOR color = COLOR.BlACK;

    public Knight(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return (Math.abs(changeX) == 2 && Math.abs(changeY) == 1) ||
                (Math.abs(changeY) == 2 && Math.abs(changeX) == 1);
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
