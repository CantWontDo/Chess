package com.mygdx.game;

public class Board {
    Space[][] chessBoard = new Space[8][8];
    int turn = 1;

    public Board() {
        for(int j = 0; j < 8; j++) {
            for(int k = 0; k < 8; k++) {
                chessBoard[j][k] = new Space(j, k , new EmptyPiece());
            }
        }
        chessBoard[0][0] = new Space(0, 0, new Castle(ChessPiece.COLOR.WHITE));
        chessBoard[1][0] = new Space(0, 1, new Knight(ChessPiece.COLOR.WHITE));
        chessBoard[2][0] = new Space(0, 2, new Bishop(ChessPiece.COLOR.WHITE));
        chessBoard[3][0] = new Space(0, 3, new Queen(ChessPiece.COLOR.WHITE));
        chessBoard[4][0] = new Space(0, 4, new King(ChessPiece.COLOR.WHITE));
        chessBoard[5][0] = new Space(0, 5, new Bishop(ChessPiece.COLOR.WHITE));
        chessBoard[6][0] = new Space(0, 6, new Knight(ChessPiece.COLOR.WHITE));
        chessBoard[7][0] = new Space(0, 7, new Castle(ChessPiece.COLOR.WHITE));
        for(int i = 0; i < 8; i++) {
            chessBoard[i][1] = new Space(1, i, new Pawn(ChessPiece.COLOR.WHITE));
        }

        chessBoard[0][7] = new Space(7, 0, new Castle(ChessPiece.COLOR.BlACK));
        chessBoard[1][7] = new Space(7, 1, new Knight(ChessPiece.COLOR.BlACK));
        chessBoard[2][7] = new Space(7, 2, new Bishop(ChessPiece.COLOR.BlACK));
        chessBoard[3][7] = new Space(7, 3, new King(ChessPiece.COLOR.BlACK));
        chessBoard[4][7] = new Space(7, 4, new Queen(ChessPiece.COLOR.BlACK));
        chessBoard[5][7] = new Space(7, 5, new Bishop(ChessPiece.COLOR.BlACK));
        chessBoard[6][7] = new Space(7, 6, new Knight(ChessPiece.COLOR.BlACK));
        chessBoard[7][7] = new Space(7, 7, new Castle(ChessPiece.COLOR.BlACK));
        for(int i = 0; i < 8; i++) {
            chessBoard[i][6] = new Space(6, i, new Pawn(ChessPiece.COLOR.BlACK));
        }
    }

    public void makeMove(Space from, Space to) {
        int changeX = to.x - from.x;
        int changeY = to.y - from.y;
        if(from.getPiece() instanceof EmptyPiece) {
            printError("chose empty piece.");
        }
        else if(from.getPiece().getColor() == to.getPiece().getColor()) {
            printError("Target is same color.");
        }
        else {
            if(isCorrectTurn(from)) {
                lookDiagonally();
                if (from.getPiece().isValidMove(changeY, changeX)) {
                    if (isPathClear(from, to)) {
                        to.setPiece(from.getPiece());
                        from.setPiece(new EmptyPiece());
                        if(to.getPiece() instanceof Pawn) {
                            ((Pawn) to.getPiece()).setHasMoved();
                        }
                        switchTurn();
                    }
                }
                else {
                    printError("Not valid move");
                }
            }
            else {
                printError("Not your turn.");
            }
        }
    }

    public boolean isPathClear(Space from, Space to) {
        // gets beginning position
        int x = from.y;
        int y = from.x;

        int changeX = to.x - from.x;
        int changeY = to.y - from.y;
        if(!(from.getPiece() instanceof Knight)) {
            // if not knight, iterates towards destination
            while (x != to.x || y != to.y) {
                if (x < to.x) {
                    x++;
                } else if (x > to.x) {
                    x--;
                }
                if (y < to.y) {
                    y++;
                } else if (y > to.y) {
                    y--;
                }
                // if at destination, checks if destination is empty
                // if not, checks if they are the same color and if the piece is a pawn to prevent invalid moves
                if(x == to.x && y == to.y) {
                    if(!(chessBoard[x][y].getPiece() instanceof EmptyPiece)) {
                        if(chessBoard[x][y].getPiece().getColor() == from.getPiece().getColor()) {
                            return false;
                        }
                        else {
                            if(from.getPiece() instanceof Pawn && changeX == 0) {
                                printError("Can't kill with pawn (forward).");
                                return false;
                            }
                        }
                    }
                }
                // if not at destination, checks for a piece that obstructs the move
                else {
                    if (!(chessBoard[x][y].getPiece() instanceof EmptyPiece)) {
                        printError("Move is obstructed.");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isCorrectTurn(Space space) {
        if(space.getPiece().getColor() == ChessPiece.COLOR.BlACK) {
            return turn <= 0;
        }
        else if(space.getPiece().getColor() == ChessPiece.COLOR.WHITE) {
            return turn >= 0;
        }
        return true;
    }

    private void printError(String message) {
        System.out.println(message);
        System.out.println("Invalid");
        System.out.println("______________________________________________________________");
    }

    private void switchTurn() {
        turn = -turn;
    }

    public Space[][] getChessBoard() {
        return chessBoard;
    }

    public Space getSpace(int x, int y) {
        return chessBoard[x][y];
    }

    private void lookDiagonally() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                // finds pawn
                if (chessBoard[i][j].getPiece() instanceof Pawn) {
                    // gets a reference to the piece, casted to pawn so that it can use pawn only methods
                    Pawn pawn = (Pawn) chessBoard[i][j].getPiece();
                    // if black, the increments will be the opposite of white
                    int value = -1;
                    if(pawn.getColor() == ChessPiece.COLOR.WHITE) {
                        value = 1;
                    }
                    // if i and j are not out of bounds when added and subtracted from, keeps going
                    if(j - value > -1 && j - value < 8 && j + value < 8 && j + value > -1 && i - value > -1 && i - value < 8 && i + value < 8 && i + value > -1) {
                        // if the space to the right upwards diagonal is the opposite color or the left upwards diagonal is the opposite color, the pawn can attack.
                        // second condition is weird
                        if ((chessBoard[i + value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i + value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL) ||
                                chessBoard[i - value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i - value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL) {
                            pawn.setCanAttack(true);
                        }
                        // in other situations, it cannot
                        else {
                            pawn.setCanAttack(false);
                        }
                    }
                }
            }
        }
    }
}
