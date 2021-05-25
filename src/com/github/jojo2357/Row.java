package com.github.jojo2357;

public class Row extends BaseHolder{
    public Row(GameBoard gameBoard) {
        super(gameBoard);
    }

    @Override
    public String toString(){
        StringBuilder out = new StringBuilder();
        for (Square square : squares)
            out.append(square.val == null ? "?" : square.val + 1).append(square.connections[1] != null && square.connections[1].biggerTile != null ? ((square.connections[1].biggerTile == square) ? ">" : "<") : " ");
        out.append('\n');
        for (Square square : squares)
            out.append(square.connections[2] != null && square.connections[2].biggerTile != null ? ((square.connections[2].biggerTile == square) ? "v" : "^") : " ").append(" ");
        return out.toString();
    }

    @Override
    Square getSquare(int index) {
        return squares[index % gb.height];
    }
}
