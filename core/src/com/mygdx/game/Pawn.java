package com.mygdx.game;

public class Pawn implements ChessPiece{
    COLOR color = COLOR.BlACK;

    public Pawn(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return changeX == 0 && changeY == 1;
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
