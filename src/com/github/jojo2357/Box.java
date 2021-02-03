package com.github.jojo2357;

public class Box extends BaseHolder{

    @Override
    Square getSquare(int index) {
        return squares[index % 3 + 3 * ((index % 12) / 6)];
    }
}
