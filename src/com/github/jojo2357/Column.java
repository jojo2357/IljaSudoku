package com.github.jojo2357;

public class Column extends BaseHolder{
    public Column(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    Square getSquare(int index) {
        return squares[index / gb.height];
    }
}
