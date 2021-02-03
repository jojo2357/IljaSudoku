package com.github.jojo2357;

public abstract class BaseHolder {
    public final Square[] squares = new Square[6];

    public void updateAll(){
        for (Square square : squares)
            square.updateAcceptance();
    }

    public boolean hasValue(int val) {
        for (Square square : squares) {
            if (square != null && square.val == val) return true;
        }
        return false;
    }

    public boolean isSolved() {
        return hasValue(0) && hasValue(1) && hasValue(2) && hasValue(3) && hasValue(4) && hasValue(5);
    }

    /*public boolean solveYourself(){
        for (Square square : squares)
            square.updateAcceptance();
        boolean has6 = false;
        for (Square square : squares)
            if ()
    }*/

    abstract Square getSquare(int index);
}
