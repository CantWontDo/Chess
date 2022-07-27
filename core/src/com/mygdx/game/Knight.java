package com.mygdx.game;

public class Knight implements ChessPiece {
    COLOR color = COLOR.BlACK;

    public Knight(COLOR color) {
        this.color = color;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        return (Math.abs(changeX) == 2 && Math.abs(changeY) == 1) ||
                (Math.abs(changeX) == 1 && Math.abs(changeY) == 2);
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
        return "Horse";
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
