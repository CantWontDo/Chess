package com.mygdx.game;

public class InputMaster {
    int trueX;
    int trueY;

    int boardX;
    int boardY;

    int oldBoardX;
    int oldBoardY;

    int size;

    Board chessBoard;

    public InputMaster(int size, Board board) {
        this.size = size;
        chessBoard = board;
    }

    public void setTrueInput(int x, int y) {
        trueX = x;
        trueY = y;
    }

    public void setBoardX(boolean old) {
        for(int i = 0; i < 8; i++) {
            if(check(trueX, i * size, (i + 1) * size)) {
                if(old) {
                    oldBoardX = i;
                    break;
                }
                else {
                    boardX = i;
                    break;
                }
            }
        }
    }

    public void setBoardY(boolean old) {
        for(int i = 0; i < 8; i++) {
            if(check(trueY, i * size, (i + 1) * size)) {
                if(old) {
                    oldBoardY = i;
                    break;
                }
                else {
                    boardY = i;
                    break;
                }
            }
        }
    }

    public void putInput() {
        //System.out.println(chessBoard.getSpace(oldBoardX, oldBoardY).getPiece().getClass().getName() + " " + chessBoard.getSpace(boardX, boardY).getPiece().getClass().getName());
        chessBoard.makeMove(chessBoard.getSpace(oldBoardX, oldBoardY), chessBoard.getSpace(boardX, boardY));
    }

    public boolean check(int num, int bound1, int bound2) {
        return num >= bound1 && num < bound2;
    }
}
