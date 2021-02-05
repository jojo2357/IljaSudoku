package com.github.jojo2357;

public class Row extends BaseHolder{
    @Override
    public String toString(){
        String out = "";
        for (Square square : squares)
            out += (square.val == null ? "?" : square.val + 1) + (square.connections[1] != null && square.connections[1].biggerTile != null ? ((square.connections[1].biggerTile == square) ? ">" : "<") : " ");
        out += '\n';
        for (Square square : squares)
            out += (square.connections[2] != null && square.connections[2].biggerTile != null ? ((square.connections[2].biggerTile == square) ? "v" : "^") : " ") + " ";
        return out;
    }

    @Override
    Square getSquare(int index) {
        return squares[index % 6];
    }
}
