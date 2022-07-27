package com.mygdx.game;

public interface ChessPiece {
    enum COLOR {
        BlACK,
        WHITE,
        NEUTRAL
    }

    COLOR color = COLOR.BlACK;

    boolean isValidMove(int changeX, int changeY);

    String convertColor();

    String getName();

    COLOR getColor();
}

