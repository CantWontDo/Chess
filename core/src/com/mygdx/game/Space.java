package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Space {
    int x;
    int y;
    private boolean selected = false;
    private boolean highlighted = false;


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


    public void draw(SpriteBatch spriteBatch, int pos, int width, int height, Texture img) {
        spriteBatch.begin();
        spriteBatch.draw(img, x * pos, y * pos, width, height);
        spriteBatch.end();
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean change) {
        selected = change;
    }

    public boolean getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean change) {
        highlighted = change;
    }
}
