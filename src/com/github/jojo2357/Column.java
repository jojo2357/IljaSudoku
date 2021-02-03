package com.github.jojo2357;

public class Column extends BaseHolder{

    @Override
    Square getSquare(int index) {
        return squares[index / 6];
    }
}
