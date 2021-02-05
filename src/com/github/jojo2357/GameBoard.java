package com.github.jojo2357;

import com.github.jojo2357.rendering.ScreenManager;
import com.github.jojo2357.rendering.typeface.Colors;
import com.github.jojo2357.rendering.typeface.JojoFont;
import com.github.jojo2357.rendering.typeface.TextRenderer;
import com.github.jojo2357.util.Point;
import com.github.jojo2357.util.Texture;

public class GameBoard {
    public final Row[] rows = new Row[]{new Row(), new Row(), new Row(), new Row(), new Row(), new Row(),};
    public final Column[] columns = new Column[]{new Column(), new Column(), new Column(), new Column(), new Column(), new Column(),};
    public final Box[] boxes = new Box[]{new Box(), new Box(), new Box(), new Box(), new Box(), new Box(),};

    private final int[][] comparators;
    private final int[][] startingValues;

    public GameBoard(int[][] values, int[][] compares) {
        comparators = compares;
        startingValues = values;
        for (int i = 0; i < 36; i++) {
            Square sq = new Square(rows[i / 6], columns[i % 6], boxes[i / 3 - 2 * ((i % 12) / 6) - 2 * (i / 12)], values[i / 6][i % 6] - 1, i);
            rows[i / 6].squares[i % 6] = sq;
            columns[i % 6].squares[i / 6] = sq;
            boxes[i / 3 - 2 * ((i % 12) / 6) - 2 * (i / 12)].squares[i % 3 + 3 * ((i % 12) / 6)] = sq;
        }
        for (int i = 0; i < 36; i++) {
            if (i / 6 != 0) {
                Square biggerOne = null;
                if (compares[2 * (i / 6) - 1][i % 6] == -1)
                    biggerOne = rows[i / 6].getSquare(i);
                if (compares[2 * (i / 6) - 1][i % 6] == 1)
                    biggerOne = rows[i / 6 - 1].getSquare(i - 6);
                rows[i / 6].getSquare(i).connections[0] = new Connection(rows[i / 6 - 1].getSquare(i - 6), rows[i / 6].getSquare(i), biggerOne);
                rows[i / 6 - 1].getSquare(i - 6).connections[2] = rows[i / 6].getSquare(i).connections[0];
            }
            if (i % 6 != 0) {
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

    public boolean solved() {
        for (Row row : rows) {
            if (!row.isSolved()) return false;
        }
        for (Column column : columns)
            if (!column.isSolved()) return false;
        for (Box box : boxes)
            if (!box.isSolved()) return false;
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

    public void outputBoard(String outfileName, boolean solved) {
        ScreenManager.init();
        JojoFont.init();
        ScreenManager.tick();
        Texture square = new Texture("EmptyTile");
        Texture comparator = new Texture("Comparator");
        Texture box = new Texture("Box");
        //ScreenManager.drawBoxFilled(new Point(0,0), new Point(2 * ScreenManager.windowSize.getWidth(), 2 * ScreenManager.windowSize.getHeight()), 255, 255, 255);
        for (int i = 0; i < 36; i++) {
            ScreenManager.renderTexture(square, new Point(32 * (i % 6) + 16, 32 * (i / 6) + 16).multiply(2), 2);
        }
        for (int i = 0; i < 6; i++)
            ScreenManager.renderTexture(box, new Point(96 * (i % 2) + 96 / 2, 64 * (i / 2) + 64 / 2).multiply(2), 2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (comparators[2 * i + 1][j] != 0)
                    ScreenManager.renderTexture(comparator, new Point(32 * (j % 6) + 16, 32 * i + 32).multiply(2), 2, -90 * comparators[2 * i + 1][j]);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (comparators[2 * i][j] != 0)
                    ScreenManager.renderTexture(comparator, new Point(32 * (j % 6) + 32, 32 * i + 16).multiply(2), 2, -90 * comparators[2 * i][j] + 90);
            }
        }
        if (solved) {
            for (int i = 0; i < 36; i++)
                TextRenderer.render((rows[i / 6].squares[i % 6].val != null ? ("" + (rows[i / 6].squares[i % 6].val + 1)) : ""), new Point(32 * (i % 6) + 16, 32 * (i / 6) + 16).multiply(2), Colors.BLUE);
        } else {
            for (int i = 0; i < 36; i++)
                TextRenderer.render((startingValues[i / 6][i % 6] != 0 ? ("" + (startingValues[i / 6][i % 6] + 1)) : ""), new Point(32 * (i % 6) + 16, 32 * (i / 6) + 16).multiply(2), Colors.GREEN);
        }
        ScreenManager.finishRender();
        if (solved)
            ScreenManager.SaveImage("generatedSolutions\\" + outfileName);
        else
            ScreenManager.SaveImage("generatedPuzzles\\" + outfileName);
    }
}
