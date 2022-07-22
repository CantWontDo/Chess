package com.mygdx.game;

public class Board {
    Space[][] chessBoard = new Space[8][8];

    public Board() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int rowOdd = i % 2;
                int columnOdd = j % 2;
                if(rowOdd == columnOdd) {
                    chessBoard[i][j] = new Space(i, j, new Bishop(ChessPiece.COLOR.WHITE));
                }
                else {
                    chessBoard[i][j] = new Space(i, j, new Bishop(ChessPiece.COLOR.BlACK));
                }
            }
        }
    }

    public void makeMove(Space from, Space to) {
        int changeX = to.x - from.x;
        int changeY = to.y - from.y;
        if(from.getPiece() instanceof EmptyPiece) {
            System.out.println("Invalid");
            System.out.println("______________________________________________________________");
        }
        else if(from.getPiece().getColor() == to.getPiece().getColor()) {
            System.out.println("Invalid");
            System.out.println("______________________________________________________________");
        }
        else {
            if(from.getPiece().isValidMove(changeX, changeY)) {
                iterateMove(from, to);
            }
            else {
                System.out.println("Invalid");
                System.out.println("______________________________________________________________");
            }
        }
    }

    public void iterateMove(Space from, Space to) {
        boolean doMove = true;
        int x = from.x;
        int y = from.y;
        while(x != to.x || y != to.y) {
            if(x < to.x) {
                x++;
            }
            else if(x > to.x) {
                x--;
            }
            if(y < to.y) {
                y++;
            }
            else if(y > to.y) {
                y--;
            }
            if(!(chessBoard[x][y].getPiece() instanceof EmptyPiece)) {
                doMove = false;
                System.out.println("Invalid");
                System.out.println("______________________________________________________________");
            }
        }
        if(doMove) {
            to.setPiece(from.getPiece());
            from.setPiece(new EmptyPiece());
        }
    }

    public Space[][] getChessBoard() {
        return chessBoard;
    }

    public Space getSpace(int x, int y) {
        return chessBoard[x][y];
    }
}
