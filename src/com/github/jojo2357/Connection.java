package com.github.jojo2357;

public class Connection {
    public final Square tile1;
    public final Square tile2;
    public final Square biggerTile;

    public Connection(Square square1, Square square2, Square compareTo) {
        tile1 = square1;
        tile2 = square2;
        biggerTile = compareTo;
    }

    public Square other(Square thing){
        if (tile1 == thing)
            return tile2;
        if (tile2 == thing)
            return tile1;
        throw new IllegalStateException("Thats wrong the input doesnt own this connection");
    }

    public boolean isObeyed(){
        return biggerTile.val != null && other(biggerTile).val != null && biggerTile.val > other(biggerTile).val;
    }
}
