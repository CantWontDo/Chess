package com.mygdx.game;

public class EmptyPiece implements ChessPiece {
        public COLOR color = COLOR.NEUTRAL;

        @Override
        public boolean isValidMove(int changeX, int changeY) {
            return false;
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
        return "Null";
    }

        @Override
        public COLOR getColor() {
            return COLOR.NEUTRAL;
        }
}

