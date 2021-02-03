package com.github.jojo2357;

public class GameBoard {
    public final Row[] rows = new Row[]{new Row(),new Row(),new Row(),new Row(),new Row(),new Row(),};
    public final Column[] columns = new Column[]{new Column(),new Column(),new Column(),new Column(),new Column(),new Column(),};
    public final Box[] boxes = new Box[]{new Box(),new Box(),new Box(),new Box(),new Box(),new Box(),};

    public GameBoard(int[][] values, int[][] compares) {
        for (int i = 0; i < 36; i++) {
            Square sq = new Square(rows[i / 6], columns[i % 6], boxes[i / 3 - 2 * ((i % 12) / 6) - 2 * (i / 12)], values[i / 6][i % 6] - 1, i);
            rows[i / 6].squares[i % 6] = sq;
            columns[i % 6].squares[i / 6] = sq;
            boxes[i / 3 - 2 * ((i % 12) / 6) - 2 * (i / 12)].squares[i % 3 + 3 * ((i % 12) / 6)] = sq;
        }
        for (int i = 0; i < 36; i++){
            if (i / 6 != 0) {
                Square biggerOne = null;
                if (compares[2 * (i / 6) - 1][i % 6] == -1)
                    biggerOne = rows[i / 6].getSquare(i);
                if (compares[2 * (i / 6) - 1][i % 6] == 1)
                    biggerOne = rows[i / 6 - 1].getSquare(i - 6);
                rows[i / 6].getSquare(i).connections[0] = new Connection(rows[i / 6 - 1].getSquare(i - 6), rows[i / 6].getSquare(i), biggerOne);
                rows[i / 6 - 1].getSquare(i - 6).connections[2] = rows[i / 6].getSquare(i).connections[0];
            }
            if (i % 6 != 0){
                Square biggerOne = null;
                if (compares[2 * (i / 6)][i % 6 - 1] == -1)
                    biggerOne = rows[i / 6].getSquare(i);
                if (compares[2 * (i / 6)][i % 6 - 1] == 1)
                    biggerOne = rows[i / 6].getSquare(i - 1);
                rows[i / 6].getSquare(i - 1).connections[1] = new Connection(rows[i / 6].getSquare(i - 1), rows[i / 6].getSquare(i), biggerOne);
                rows[i / 6].getSquare(i).connections[3] = rows[i / 6].getSquare(i - 1).connections[1];
            }
        }
        System.out.print("");
    }

    public GameBoard solve() {
        for (Box box : boxes)
            for (Square square : box.squares)
                square.updateAcceptance();
        return this;
    }

    private boolean solved() {
        for (Row row : rows) {
            if (!row.isSolved()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String out = "";
        for (Row row : rows) {
            out += row + "\n";
        }
        return out;
    }
}
