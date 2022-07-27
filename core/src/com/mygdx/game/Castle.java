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
        return "Castle";
    }

    @Override
    public COLOR getColor() {
        return color;
    }
}
