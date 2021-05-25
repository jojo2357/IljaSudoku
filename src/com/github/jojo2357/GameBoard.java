package com.github.jojo2357;

import com.github.jojo2357.rendering.ScreenManager;
import com.github.jojo2357.rendering.typeface.Colors;
import com.github.jojo2357.rendering.typeface.JojoFont;
import com.github.jojo2357.rendering.typeface.TextRenderer;
import com.github.jojo2357.util.Point;
import com.github.jojo2357.util.Texture;

import java.util.Arrays;

public class GameBoard {
    public final Row[] rows;
    public final Column[] columns;
    public final Box[] boxes;
    public final int width, height, area, boxhite, boxwidth;
    private final int[][] comparators;
    private final int[][] startingValues;
    public int difficultyIndex = 0;

    public int[][] getMandatoryCompares(){
        int[][] out = new int[height * 2 - 1][width];
        for (int i = 0; i < area; i++) {
            if (rows[i / width].squares[i % width].val == null) {
                if (i % width != width - 1) {
                    if (rows[i / width].squares[i % width].maxPossible() < rows[i / width].squares[(i % width) + 1].minPossible()) {
                        out[2 * (i / width)][i % width] = Integer.compare(rows[i / width].squares[i % width].maxPossible(), rows[i / width].squares[(i % width) + 1].minPossible());
                    } else if (rows[i / width].squares[i % width].minPossible() > rows[i / width].squares[(i % width) + 1].maxPossible()){
                        out[2 * (i / width)][i % width] = Integer.compare(rows[i / width].squares[i % width].minPossible(), rows[i / width].squares[(i % width) + 1].maxPossible());
                    }
                }
                if (i / width != width - 1) {
                    if (rows[i / width].squares[i % width].maxPossible() < rows[(i / width) + 1].squares[i % width].minPossible()) {
                        out[2 * (i / width) + 1][i % width] = Integer.compare(rows[i / width].squares[i % width].maxPossible(), rows[(i / width) + 1].squares[i % width].minPossible());
                    } else if (rows[i / width].squares[i % width].minPossible() > rows[(i / width) + 1].squares[i % width].maxPossible()){
                        out[2 * (i / width) + 1][i % width] = Integer.compare(rows[i / width].squares[i % width].minPossible(), rows[(i / width) + 1].squares[i % width].maxPossible());
                    }
                }
            } else {
                if (i % width != width - 1) {
                        if (rows[i / width].squares[(i % width) + 1].val != null)
                            out[2 * (i / width)][i % width] = Integer.compare(rows[i / width].squares[i % width].val, rows[i / width].squares[(i % width) + 1].val);
                }
                if (i / width != width - 1) {
                    if (rows[(i / width) + 1].squares[i % width].val != null)
                        out[2 * (i / width) + 1][i % width] = Integer.compare(rows[i / width].squares[i % width].val, rows[(i / width) + 1].squares[i % width].val);
                }
            }
        }
        for (int i = 0; i < height * 2 - 1; i++)
            System.arraycopy(comparators[i], 0, out[i], 0, comparators[i].length);
        return out;
    }

    public int[][] getBoardVals(){
        int[][] out = new int[height][width];
        for (int i = 0; i < area; i++)
            out[i / width][i % width] = rows[i / width].squares[i % width].val == null ? 0 : rows[i / width].squares[i % width].val + 1;
        return out;
    }

    public boolean[][][] getAllowals(){
        boolean [][][] out = new boolean[height][width][height];
        for (int i = 0; i < area; i++)
            for (int j = 0; j < width; j++)
                out[i / width][i % width][j] = rows[i / width].squares[i % width].acceptedNumbers[j];
        return out;
    }

    public GameBoard(int[][] values, int[][] compares) {
        comparators = compares;
        startingValues = values;
        height = values.length;
        width = values[0].length;
        area = width * height;
        boxhite = getBoxHite(width);
        boxwidth = height / boxhite;
        rows = new Row[width];
        columns = new Column[height];
        boxes = new Box[width];
        for (int i = 0; i < width; i++) {
            rows[i] = new Row(this);
            boxes[i] = new Box(this);
        }
        for (int i = 0; i < height; i++) {
            columns[i] = new Column(this);
        }
        for (int i = 0; i < area; i++) {
            Square sq = new Square(rows[i / height], columns[i % width], boxes[((i / boxwidth) % (width / boxwidth)) + ((i / width) / boxhite) * (width / boxwidth)], values[i / height][i % width] - 1, i, this);
            rows[i / height].squares[i % width] = sq;
            columns[i % width].squares[i / height] = sq;
            boxes[((i / boxwidth) % (width / boxwidth)) + ((i / width) / boxhite) * (width / boxwidth)].squares[i % boxwidth + boxwidth * ((i % (width * boxhite)) / height)] = sq;
        }
        for (int i = 0; i < area; i++) {
            if (i / height != 0) {
                Square biggerOne = null;
                if (compares[2 * (i / height) - 1][i % width] == -1)
                    biggerOne = rows[i / height].getSquare(i);
                if (compares[2 * (i / height) - 1][i % width] == 1)
                    biggerOne = rows[i / height - 1].getSquare(i - width);
                rows[i / height].getSquare(i).connections[0] = new Connection(rows[i / height - 1].getSquare(i - width), rows[i / height].getSquare(i), biggerOne);
                rows[i / height - 1].getSquare(i - width).connections[2] = rows[i / height].getSquare(i).connections[0];
            }
            if (i % width != 0) {
                Square biggerOne = null;
                if (compares[2 * (i / height)][i % width - 1] == -1)
                    biggerOne = rows[i / height].getSquare(i);
                if (compares[2 * (i / height)][i % width - 1] == 1)
                    biggerOne = rows[i / height].getSquare(i - 1);
                rows[i / height].getSquare(i - 1).connections[1] = new Connection(rows[i / height].getSquare(i - 1), rows[i / height].getSquare(i), biggerOne);
                rows[i / height].getSquare(i).connections[3] = rows[i / height].getSquare(i - 1).connections[1];
            }
        }
        System.out.print("");
    }

    private static int getBoxHite(int area) {
        for (int i = (int)Math.floor(Math.sqrt(area)); i >= 1; i--)
            if (area % i == 0)
                return Math.min(i, area / i);
        return -1;
    }

    public int solveNumber() {
        int out = 0;
        for (Row row : rows)
            for (Square square : row.squares)
                if (square.val != null)
                    out++;
        return out;
    }

    public GameBoard solve(boolean trace, int limit) {
        try {
            for (Row row : rows)
                for (Square sq : row.squares)
                    sq.updateObviously(trace);
            for (Box box : boxes)
                for (Square square : box.squares)
                    square.updateAcceptance(trace, limit);
            while (true) {
                boolean shouldContinue = false;
                for (Box box : boxes)
                    if (box.isDirty)
                        shouldContinue = true;
                for (Column box : columns)
                    if (box.isDirty)
                        shouldContinue = true;
                for (Row box : rows)
                    if (box.isDirty)
                        shouldContinue = true;
                if (!shouldContinue)
                    break;
                else {
                    for (Box box : boxes)
                        if (box.isDirty)
                            box.updateAll(trace, limit);
                    for (Column box : columns)
                        if (box.isDirty)
                            box.updateAll(trace, limit);
                    for (Row box : rows)
                        if (box.isDirty)
                            box.updateAll(trace, limit);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored){}
        return this;
    }

    public GameBoard solve(boolean stackTrace) {
        return solve(stackTrace, 0);
    }

    public GameBoard solve() {
        return solve(false);
    }

    public int countUnsolved(){
        int out = 0;
        for (Row row : rows)
            for (Square sq : row.squares)
                out += sq.val == null ? 1 : 0;
        return out;
    }

    public boolean illegallySolved(){
        for (Row row : rows)
            if (row.isIllegallySolved()) return true;
        for (Column column : columns)
            if (column.isIllegallySolved()) return true;
        for (Box box : boxes)
            if (box.isIllegallySolved()) return true;
        for (Row rw : this.rows)
            for (Square sq : rw.squares) {
                for (Connection cn : sq.connections)
                    if (cn != null && cn.biggerTile != null && cn.biggerTile.val != null && cn.other(cn.biggerTile).val != null && !cn.isObeyed())
                        return true;
                if (sq.maxPossible() == -1)
                    return true;
            }
        return false;
    }

    public boolean solved() {
        for (Row row : rows)
            if (!row.isSolved()) return false;
        for (Column column : columns)
            if (!column.isSolved()) return false;
        for (Box box : boxes)
            if (!box.isSolved()) return false;
        for (Row rw : this.rows)
            for (Square sq : rw.squares)
                for (Connection cn : sq.connections)
                    if (cn != null && cn.biggerTile != null && !cn.isObeyed())
                        return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Row row : rows) {
            out.append(row).append("\n");
        }
        return out.toString();
    }

    public void outputBoard(String outfileName, boolean solved) {
        ScreenManager.init();
        JojoFont.init();
        ScreenManager.tick();
        Texture boxEdge = new Texture("TileEdge");
        Texture boxCorner = new Texture("TileCorner");
        Texture boxNone = new Texture("TileNone");
        Texture comparator = new Texture("Comparator");
        //ScreenManager.drawBoxFilled(new Point(0,0), new Point(2 * ScreenManager.windowSize.getWidth(), 2 * ScreenManager.windowSize.getHeight()), 255, 255, 255);
        for (int i = 0; i < height; i++) {
            //System.out.println(new Point(32 * (i % (width / boxwidth)) * boxwidth + 16, 32 * (i / (width / boxwidth)) * boxhite + 16).multiply(2));
            ScreenManager.renderTexture(boxCorner, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16, 32 * (i / (width / boxwidth)) * boxhite + 16).multiply(2), 2);
            ScreenManager.renderTexture(boxCorner, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * (boxwidth - 1), 32 * (i / (width / boxwidth)) * boxhite + 16).multiply(2), 2, 270);
            ScreenManager.renderTexture(boxCorner, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16, 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * (boxhite - 1)).multiply(2), 2, 90);
            ScreenManager.renderTexture(boxCorner, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * (boxwidth - 1), 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * (boxhite - 1)).multiply(2), 2, 180);
            for (int j = 0; j < boxwidth - 2; j++){
                ScreenManager.renderTexture(boxEdge, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * (j + 1), 32 * (i / (width / boxwidth)) * boxhite + 16).multiply(2), 2);
                ScreenManager.renderTexture(boxEdge, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * (j + 1), 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * (boxhite - 1)).multiply(2), 2, 180);
            }
            for (int j = 0; j < boxhite - 2; j++){
                ScreenManager.renderTexture(boxEdge, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16, 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * (j + 1)).multiply(2), 2, 90);
                ScreenManager.renderTexture(boxEdge, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * (boxwidth - 1), 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * (j + 1)).multiply(2), 2, -90);
            }
        }
        for (int i = 0; i < boxes.length; i++)
        for (int j = 0; j < (boxwidth - 2) * (boxhite - 2); j++){
            ScreenManager.renderTexture(boxNone, new Point(32 * (i % (width / boxwidth)) * boxwidth + 16 + 32 * ((j % (boxwidth - 2)) + 1), 32 * (i / (width / boxwidth)) * boxhite + 16 + 32 * ((j / (boxwidth - 2)) + 1)).multiply(2),2);
        }
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                if (comparators[2 * i + 1][j] != 0)
                    ScreenManager.renderTexture(comparator, new Point(32 * (j % width) + 16, 32 * i + 32).multiply(2), 2, -90 * comparators[2 * i + 1][j]);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                if (comparators[2 * i][j] != 0)
                    ScreenManager.renderTexture(comparator, new Point(32 * (j % width) + 32, 32 * i + 16).multiply(2), 2, -90 * comparators[2 * i][j] + 90);
            }
        }
        if (solved) {
            solve();
            for (int i = 0; i < area; i++)
                TextRenderer.render((rows[i / height].squares[i % width].val != null ? (rows[i / height].squares[i % width].val + 1) : -1), new Point(32 * (i % width) + 16, 32 * (i / height) + 16).multiply(2), Colors.BLUE);
        } else {
            for (int i = 0; i < area; i++)
                TextRenderer.render((startingValues[i / height][i % width] != 0 ? (startingValues[i / height][i % width]) : -1), new Point(32 * (i % width) + 16, 32 * (i / height) + 16).multiply(2), Colors.BLACK);
        }
        ScreenManager.finishRender();
        if (solved)
            ScreenManager.SaveImage("generatedSolutions\\" + outfileName);
        else
            ScreenManager.SaveImage("generatedPuzzles\\" + outfileName);
    }

    public String locateIllegal() {
        for (int i = 0; i < rows.length; i++)
            if (rows[i].isIllegallySolved()) return "Row " + i + " is illegal";
        for (int i = 0; i < boxes.length; i++)
            if (boxes[i].isIllegallySolved()) return "Box " + i + " is illegal";
        for (int i = 0; i < columns.length; i++)
            if (columns[i].isIllegallySolved()) return "Col " + i + " is illegal";
        for (Row rw : this.rows)
            for (Square sq : rw.squares)
                for (Connection cn : sq.connections)
                    if (cn != null && cn.biggerTile != null && cn.biggerTile.val != null && cn.other(cn.biggerTile).val != null && !cn.isObeyed())
                        return "A connection is wrong";
        return "Everything is ok";
    }

    public void softReset() {
        for (Row row : rows){
            for (Square square : row.squares){
                Arrays.fill(square.acceptedNumbers, true);
            }
        }
    }

    public int countSolved() {
        return area - countUnsolved();
    }
}
