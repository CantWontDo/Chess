package com.mygdx.game;

public class EmptyPiece implements ChessPiece {
        public COLOR color = COLOR.NEUTRAL;

        @Override
        public boolean isValidMove(int changeX, int changeY) {
            return false;
        }

        @Override
        public COLOR getColor() {
            return color;
        }
}

