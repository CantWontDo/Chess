package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;

public class ChessDisplay {
    Board chessBoard;
    HashMap<String, Texture> pieceTextures = new HashMap<>();

    Texture blackSpace;
    Texture whiteSpace;
    Texture selected;
    Texture highlighted;
    Texture check;

    Texture blackVictory;
    Texture whiteVictory;

    int height = 2400;
    int width = 2400;
    int size = height / 8;

    public ChessDisplay(Board board) {
        this.chessBoard = board;
        pieceTextures.put("BlackBishop", new Texture(Gdx.files.internal("BlackBishop.png")));
        pieceTextures.put("WhiteBishop", new Texture(Gdx.files.internal("WhiteBishop.png")));
        pieceTextures.put("BlackCastle", new Texture(Gdx.files.internal("BlackCastle.png")));
        pieceTextures.put("WhiteCastle", new Texture(Gdx.files.internal("WhiteCastle.png")));
        pieceTextures.put("BlackPawn", new Texture(Gdx.files.internal("BlackPawn.png")));
        pieceTextures.put("WhitePawn", new Texture(Gdx.files.internal("WhitePawn.png")));
        pieceTextures.put("BlackHorse", new Texture(Gdx.files.internal("BlackHorse.png")));
        pieceTextures.put("WhiteHorse", new Texture(Gdx.files.internal("WhiteHorse.png")));
        pieceTextures.put("BlackKing", new Texture(Gdx.files.internal("BlackKing.png")));
        pieceTextures.put("WhiteKing", new Texture(Gdx.files.internal("WhiteKing.png")));
        pieceTextures.put("BlackQueen", new Texture(Gdx.files.internal("BlackQueen.png")));
        pieceTextures.put("WhiteQueen", new Texture(Gdx.files.internal("WhiteQueen.png")));
        pieceTextures.put("Debug", new Texture(Gdx.files.internal("Debug.png")));

        blackSpace = new Texture(Gdx.files.internal("BlackSpace.png"));
        whiteSpace = new Texture(Gdx.files.internal("WhiteSpace.png"));
        selected = new Texture(Gdx.files.internal("HighlightedSpace.png"));
        highlighted = new Texture(Gdx.files.internal("HighlightedSpace_2.png"));
        check = new Texture(Gdx.files.internal("CheckSpace.png"));

        blackVictory = new Texture(Gdx.files.internal("BlackVictory.png"));
        whiteVictory = new Texture(Gdx.files.internal("WhiteVictory.png"));
    }

    public void redraw(SpriteBatch batch) {
        drawBoard(batch);
        drawPieces(batch);
    }

    public void drawBoard(SpriteBatch batch) {
        batch.begin();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int rowOdd = i % 2;
                int columnOdd = j % 2;
                ChessPiece potentialKing = chessBoard.getSpace(i, j).getPiece();
                if(chessBoard.getSpace(i, j).getSelected()) {
                    batch.draw(selected, i * size, j * size, size, size);
                }
                else if(chessBoard.getSpace(i, j).getHighlighted()) {
                    batch.draw(highlighted, i * size, j * size, size, size);
                }
                else {
                    if (rowOdd == columnOdd) {
                        batch.draw(whiteSpace, i * size, j * size, size, size);
                    } else {
                        batch.draw(blackSpace, i * size, j * size, size, size);
                    }
                }
                if(potentialKing instanceof King) {
                    if(((King) potentialKing).getCheck()) {
                        batch.draw(check, i * size, j * size, size, size);
                    }
                }
            }
        }
        batch.end();
    }

    public void drawPieces(SpriteBatch batch) {
        batch.begin();
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Space space = chessBoard.getSpace(i , j);
                if(pieceTextures.containsKey(space.getPiece().convertColor() + space.getPiece().getName())) {
                    batch.draw(pieceTextures.get(space.getPiece().convertColor() + space.getPiece().getName()), i * size + (size * 3/10), j * size + (size / 10), 120, 225);
                }
            }
        }
        batch.end();
    }

    public void drawVictory(SpriteBatch batch, ChessPiece.COLOR color) {
        batch.begin();
        if(color == ChessPiece.COLOR.BlACK) {
            batch.draw(blackVictory, 0, 0, size * 8, size * 8);
        }
        else if(color == ChessPiece.COLOR.WHITE) {
            batch.draw(whiteVictory, 0, 0, size * 8, size * 8);
        }
        batch.end();
    }
}
