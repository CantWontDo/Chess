package com.mygdx.game;

public class King implements ChessPiece {
    COLOR color = COLOR.BlACK;
    boolean inCheck = false;

    public King(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return changeX >= -1 && changeX <= 1 &&
                changeY >= -1 && changeY <= 1;
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

    public void setCheck(boolean check) {
        inCheck = check;
    }

    public boolean getCheck() {
        return inCheck;
    }

    @Override
    public String getName() {
        return "King";
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
