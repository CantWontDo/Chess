package com.mygdx.game;

public class Pawn implements ChessPiece{
    COLOR color = COLOR.BlACK;
    boolean hasMoved = false;

    public Pawn(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        int change = -1;
        int value = 1;
        if(!hasMoved) {
            value = 2;
        }
        if(color == COLOR.WHITE) {
            change = 1;
        }
        return changeX == 0 && changeY == value * change;
    }

    @Override
    public String convertColor() {
        if(this.color == COLOR.BlACK) {
            return "Black";
        }
        else if(this.color == COLOR.WHITE) {
            return "White";
        }
        return "Null";
    }

    @Override
    public String getName() {
        return "Pawn";
    }

    @Override
    public COLOR getColor() {
        return color;
    }

    public void setHasMoved() {
        if(hasMoved != true) {
            hasMoved = true;
        }
    }
}
