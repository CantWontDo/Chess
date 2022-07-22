package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Space {
    int x;
    int y;
    private boolean isEmpty;


    private ChessPiece piece;


    public Space(int x, int y, ChessPiece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public boolean getEmpty() {
        return isEmpty;
    }

    public Color convertColor(ChessPiece.COLOR color) {
        if(color == ChessPiece.COLOR.BlACK) {
            return Color.DARK_GRAY;
        }
        else if (color == ChessPiece.COLOR.WHITE) {
            return Color.SLATE;
        }
        return null;
    }

    public void draw(SpriteBatch spriteBatch, int size, Sprite sprite) {
        spriteBatch.draw(sprite, x * 100, y * 100, size, size);
    }

}
