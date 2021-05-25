package com.github.jojo2357;

public class Box extends BaseHolder{
    public Box(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    Square getSquare(int index) {
        return squares[index % gb.boxwidth + gb.boxwidth * ((index % (gb.boxhite * gb.width)) / gb.height)];
    }
}
