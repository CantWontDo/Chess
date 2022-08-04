package com.mygdx.game;

public class Pawn implements ChessPiece{
    COLOR color = COLOR.BlACK;
    boolean hasMoved = false;
    boolean canAttack = false;
    int attackRight;
    int attackLeft;

    public Pawn(COLOR color) {
        this.color = color;
    }

    public Pawn(COLOR color, boolean hasMoved, boolean canAttack, int attackRight, int attackLeft) {
            this.color = color;
            this.hasMoved = hasMoved;
            this.canAttack = canAttack;
            this.attackRight = attackRight;
            this.attackLeft = attackLeft;
    }

    @Override
    public boolean isValidMove(int changeX, int changeY) {
        int change = -1;
        int value = 1;
        if(!hasMoved) {
            value = 2;
        }
        if(color == COLOR.WHITE) {
            change = 1;
        }
        if(canAttack) {
            if(color == COLOR.WHITE) {
                return ((changeX == attackLeft || changeX == attackRight) && changeY == 1) || (changeX == 0 && changeY == 1);
            }
            else if(color == COLOR.BlACK){
                return ((changeX == -attackRight || changeX == attackLeft) && changeY == -1) || (changeX == 0 && changeY == -1);
            }
        }
        else {
            if(color == COLOR.WHITE) {
                return changeX == 0 && changeY <= value * change && changeY >= 0;
            }
            else if(color == COLOR.BlACK){
                return changeX == 0 && changeY >= value * change && changeY <= 0;
            }
        }
        return true;
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
        return "Pawn";
    }

    @Override
    public COLOR getColor() {
        return color;
    }

    public void setHasMoved() {
        if(hasMoved != true) {
            hasMoved = true;
        }
    }

    public void setCanAttack(boolean x, int right, int left) {
        canAttack = x;
        attackRight = right;
        attackLeft = left;
    }

    public boolean getCanAttack() {
        return canAttack;
    }
}
