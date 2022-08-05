package com.mygdx.game;

import java.util.HashMap;

public class Board {
    Space[][] chessBoard = new Space[8][8];

    boolean isCheckmate = false;
    ChessPiece.COLOR winner = null;
    int turn = 1;

    HashMap<Integer, Space> kingSpaces = new HashMap<>();
    HashMap<Integer, King> kings = new HashMap<>();

    public Board() {
        for(int j = 0; j < 8; j++) {
            for(int k = 0; k < 8; k++) {
                chessBoard[j][k] = new Space(j, k , new EmptyPiece());
            }
        }
        chessBoard[0][0] = new Space(0, 0, new Castle(ChessPiece.COLOR.WHITE));
        chessBoard[1][0] = new Space(1, 0, new Knight(ChessPiece.COLOR.WHITE));
        chessBoard[2][0] = new Space(2, 0, new Bishop(ChessPiece.COLOR.WHITE));
        chessBoard[3][0] = new Space(3, 0, new Queen(ChessPiece.COLOR.WHITE));
        chessBoard[4][0] = new Space(4, 0, new King(ChessPiece.COLOR.WHITE));
        chessBoard[5][0] = new Space(5, 0, new Bishop(ChessPiece.COLOR.WHITE));
        chessBoard[6][0] = new Space(6, 0, new Knight(ChessPiece.COLOR.WHITE));
        chessBoard[7][0] = new Space(7, 0, new Castle(ChessPiece.COLOR.WHITE));
        for(int i = 0; i < 8; i++) {
            chessBoard[i][1] = new Space(i, 1, new Pawn(ChessPiece.COLOR.WHITE));
        }
        chessBoard[0][7] = new Space(0, 7, new Castle(ChessPiece.COLOR.BlACK));
        chessBoard[1][7] = new Space(1, 7, new Knight(ChessPiece.COLOR.BlACK));
        chessBoard[2][7] = new Space(2, 7, new Bishop(ChessPiece.COLOR.BlACK));
        chessBoard[3][7] = new Space(3, 7, new Queen(ChessPiece.COLOR.BlACK));
        chessBoard[4][7] = new Space(4, 7, new King(ChessPiece.COLOR.BlACK));
        chessBoard[5][7] = new Space(5, 7, new Bishop(ChessPiece.COLOR.BlACK));
        chessBoard[6][7] = new Space(6, 7, new Knight(ChessPiece.COLOR.BlACK));
        chessBoard[7][7] = new Space(7, 7, new Castle(ChessPiece.COLOR.BlACK));
        for(int i = 0; i < 8; i++) {
            chessBoard[i][6] = new Space(i, 6, new Pawn(ChessPiece.COLOR.BlACK));
        }

        updateKings();
    }

    public Board(Space[][] board, int turn) {
        this.turn = turn;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Space space = board[i][j];
                ChessPiece piece = space.getPiece();
                if(piece instanceof Pawn) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new Pawn(piece.getColor(), ((Pawn) piece).hasMoved, ((Pawn) piece).getCanAttack(),
                    ((Pawn) piece).attackLeft, ((Pawn) piece).attackRight));
                }
                else if(piece instanceof Castle) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new Castle(piece.getColor()));
                }
                else if(piece instanceof Knight) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new Knight(piece.getColor()));
                }
                else if(piece instanceof Bishop) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new Bishop(piece.getColor()));
                }
                else if(piece instanceof Queen) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new Queen(piece.getColor()));
                }
                else if(piece instanceof King) {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new King(piece.getColor(), ((King) piece).inCheck));
                }
                else {
                    this.chessBoard[i][j] = new Space(space.x, space.y, new EmptyPiece());
                }
            }
        }
        updateKings();
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
                if (from.getPiece().isValidMove(changeX, changeY)) {
                    if (isPathClear(from, to)) {
                            if(!simulateMove(to, from)) {
                                to.setPiece(from.getPiece());
                                from.setPiece(new EmptyPiece());
                                if (to.getPiece() instanceof Pawn) {
                                    ((Pawn) to.getPiece()).setHasMoved();
                                }
                                tryCheck();
                                resetHighlight();
                                setSelect(from, false);
                                switchTurn();
                                if(kings.get(turn).getCheck()) {
                                    setCheckmate();
                                }
                            }
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

    public void setSelect(Space space, boolean change) {
        if(isCorrectTurn(space)) {
            space.setSelected(change);
        }
    }

    public void setHighlight(Space space, boolean change) {
        if(isCorrectTurn(space)) {
            ChessPiece piece = space.getPiece();
            int x = space.x;
            int y = space.y;
            int changeX;
            int changeY;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (change) {
                        changeX = i - x;
                        changeY = j - y;
                        if (piece.isValidMove(changeX, changeY) && isPathClear(space, chessBoard[i][j])) {
                            chessBoard[i][j].setHighlighted(true);
                        } else {
                            chessBoard[i][j].setHighlighted(false);
                        }
                    } else {
                        chessBoard[i][j].setHighlighted(false);
                    }
                }
            }
        }
    }

    public void resetHighlight() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                chessBoard[i][j].setHighlighted(false);
            }
        }
    }

    public boolean isPathClear(Space from, Space to) {
        // gets beginning position
        int x = from.x;
        int y = from.y;

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
                        return false;
                    }
                }
            }
        }
        else  {
            if(from.getPiece().getColor() == to.getPiece().getColor() && from != to) {
                return false;
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

    private boolean simulateMove(Space to, Space from) {
        Board simulation = new Board(chessBoard, -turn);
        printSimulation(simulation);
        Space simulateTo = simulation.getSpace(to.x, to.y);
        Space simulateFrom = simulation.getSpace(from.x, from.y);
        simulateTo.setPiece(simulateFrom.getPiece());
        simulateFrom.setPiece(new EmptyPiece());
        printSimulation(simulation);
        simulation.updateKings();
        simulation.tryCheck();
        return simulation.kings.get(-simulation.turn).getCheck();
    }

    private void switchTurn() {
        turn = -turn;
        updateKings();
    }

    public Space getSpace(int x, int y) {
        return chessBoard[x][y];
    }

    public void tryCheck() {
        boolean found = false;
        ChessPiece.COLOR opposingColor = ChessPiece.COLOR.WHITE;
        if(turn == 1) {
            opposingColor = ChessPiece.COLOR.BlACK;
        }

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(chessBoard[i][j].getPiece().getColor() != opposingColor) {
                    Space space = chessBoard[i][j];
                    int changeX = kingSpaces.get(-turn).x - space.x;
                    int changeY = kingSpaces.get(-turn).y - space.y;

                    if(space.getPiece().isValidMove(changeX, changeY) && isPathClear(space, kingSpaces.get(-turn))) {
                        found = true;
                    }
                }
            }
        }
        kings.get(-turn).setCheck(found);
    }

    private void updateKings() {
        kingSpaces.clear();
        kings.clear();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(chessBoard[i][j].getPiece().getName() == "King" && chessBoard[i][j].getPiece().getColor() == ChessPiece.COLOR.BlACK) {
                    kingSpaces.put(-1, chessBoard[i][j]);
                    kings.put(-1, (King) chessBoard[i][j].getPiece());
                }
                else if(chessBoard[i][j].getPiece().getName() == "King" && chessBoard[i][j].getPiece().getColor() == ChessPiece.COLOR.WHITE) {
                    kingSpaces.put(1, chessBoard[i][j]);
                    kings.put(1, (King) chessBoard[i][j].getPiece());
                }
            }
        }
    }

    public void setCheckmate() {
        ChessPiece.COLOR color = ChessPiece.COLOR.WHITE;
        if(turn == 1) {
            color = ChessPiece.COLOR.BlACK;
        }

        boolean checkmate = true;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(chessBoard[i][j].getPiece().getColor() == color) {
                    for(int k = 0; k < 8; k++) {
                        for(int l = 0; l < 8; l++) {
                            if(chessBoard[k][l].getPiece().getName() != "King") {
                                if (!simulateMove(chessBoard[k][l], chessBoard[i][j])) {
                                    checkmate = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        if(checkmate) {
            winner = color;
        }
        isCheckmate = checkmate;
    }

    public ChessPiece.COLOR getWinner() {
        return winner;
    }

    public boolean getCheckmate() {
        return isCheckmate;
    }

    public void lookDiagonally() {
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
                        if ((chessBoard[i + value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i + value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL) &&
                                chessBoard[i - value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i - value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL) {
                            pawn.setCanAttack(true, value, -value);
                        }
                        else if(chessBoard[i + value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i + value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL
                        && !(chessBoard[i - value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i - value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL)) {
                            pawn.setCanAttack(true, value, 0);
                        }
                        else if(chessBoard[i - value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i - value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL &&
                        !(chessBoard[i + value][j + value].getPiece().getColor() != pawn.getColor() && chessBoard[i + value][j + value].getPiece().getColor() != ChessPiece.COLOR.NEUTRAL)) {
                            pawn.setCanAttack(true, 0, -value);
                        }
                        // in other situations, it cannot
                        else {
                            pawn.setCanAttack(false, 0, 0);
                        }
                    }
                }
            }
        }
    }

    private void printSimulation(Board board) {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board.getSpace(j, i).getPiece() instanceof Pawn) {
                    System.out.print("P ");
                }
                else if(board.getSpace(j, i).getPiece() instanceof Castle) {
                    System.out.print("C ");
                }
                else if(board.getSpace(j, i).getPiece() instanceof Knight) {
                    System.out.print("K ");
                }
                else if(board.getSpace(j, i).getPiece() instanceof Bishop) {
                    System.out.print("B ");
                }
                else if(board.getSpace(j, i).getPiece() instanceof Queen) {
                    System.out.print("Q ");
                }
                else if(board.getSpace(j, i).getPiece() instanceof King) {
                    System.out.print("k ");
                }
                else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println("_______________________________________________________________");
    }
}
