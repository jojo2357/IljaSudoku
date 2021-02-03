package com.github.jojo2357;

public class Row extends BaseHolder{
    @Override
    public String toString(){
        String out = "";
        for (Square square : squares)
            out += (square.val == null ? "?" : square.val + 1) + " ";
        return out;
    }

    @Override
    Square getSquare(int index) {
        return squares[index % 6];
    }
}
