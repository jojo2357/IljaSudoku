package com.github.jojo2357;

import java.util.Arrays;

public class Square {
    public final boolean[] acceptedNumbers;
    public final boolean[] superNo;
    public final Connection[] connections = new Connection[4];
    public final Row row;
    public final Column column;
    public final Box box;
    public Integer val;
    private final int loc;

    private final GameBoard gbjustcuz;

    public Square(Row ro, Column colum, Box boks, Integer vahl, int loc, GameBoard lol) {
        gbjustcuz = lol;
        if (vahl < 0)
            vahl = null;
        row = ro;
        column = colum;
        box = boks;
        val = vahl;
        this.loc = loc;
        acceptedNumbers = new boolean[boks.squares.length];
        superNo = new boolean[boks.squares.length];
        Arrays.fill(acceptedNumbers, true);
    }

    public Square (Row ro, Column colum, Box boks, GameBoard lol){
        this(ro, colum, boks, null, -1, lol);
    }

    public boolean updateAcceptance(boolean stackTrace, int limit){
        boolean startedUnsolved = val == null;
        gbjustcuz.difficultyIndex++;
        if (limit != 0 && gbjustcuz.difficultyIndex > limit)
            return false;
        boolean[] previousVals = new boolean[acceptedNumbers.length];
        System.arraycopy(acceptedNumbers, 0, previousVals, 0, acceptedNumbers.length);
        updateObviously(stackTrace);
        if (val == null)
            for (Connection connection : connections)
                if (connection != null && connection.biggerTile != null){
                    if (connection.other(this).val != null){
                        if (connection.biggerTile == this){
                            for (int i = 0; i <= connection.other(this).val; i++){
                                acceptedNumbers[i] = false;
                            }
                        }else{
                            for (int i = connection.other(this).val + 1; i < acceptedNumbers.length; i++){
                                acceptedNumbers[i] = false;
                            }
                        }
                    }else{
                        if (connection.biggerTile == this){
                            for (int i = 0; i < connection.other(this).minPossible() + 1; i++){
                                acceptedNumbers[i] = false;
                            }
                        }else{
                            for (int i = connection.other(this).maxPossible(); i < acceptedNumbers.length; i++){
                                acceptedNumbers[i] = false;
                            }
                        }
                    }
                    //todo cheque here
                }
        for (int i = 0; i < acceptedNumbers.length && val == null; i++){
            if (!acceptedNumbers[i])
                continue;
            boolean foundElsewhere = false;
            for (Square square : row.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                if (gbjustcuz.illegallySolved()){
                    //System.out.println("Im da prolem");
                    /*superNo[val] = true;
                    val = null;
                    Arrays.fill(acceptedNumbers, true);
                    break;*/
                }
                if (stackTrace)
                System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ") by unavailibility in row");
                for (int j = 0; j < acceptedNumbers.length; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
            foundElsewhere = false;
            for (Square square : column.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                if (gbjustcuz.illegallySolved()){
                    //System.out.println("Im da prolem");
                    /*superNo[val] = true;
                    val = null;
                    Arrays.fill(acceptedNumbers, true);
                    break;*/
                }
                if (stackTrace)
                System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ") by unavailibility in column");
                for (int j = 0; j < acceptedNumbers.length; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
            foundElsewhere = false;
            for (Square square : box.squares) {
                foundElsewhere |= square.acceptedNumbers[i] && square != this;
            }
            if (!foundElsewhere){
                val = i;
                if (gbjustcuz.illegallySolved()){
                    //System.out.println("Im da prolem");
                    /*superNo[val] = true;
                    val = null;
                    Arrays.fill(acceptedNumbers, true);
                    break;*/
                }
                if (stackTrace)
                System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ")  by unavailibility in box");
                for (int j = 0; j < acceptedNumbers.length; j++)
                    acceptedNumbers[j] = val == j;
                break;
            }
        }
        if (val == null){
            int myRowdex = box.indexOf(this) / gbjustcuz.boxwidth;
            for (int i = 0; i < acceptedNumbers.length; i++){
                if (!acceptedNumbers[i])
                    continue;
                int therowdex = -1;
                boolean aRowCan = false;
                for (int j = 0; j < gbjustcuz.boxhite; j++) {
                    boolean thisRowCan = false;
                    for (int k = 0; k < gbjustcuz.boxwidth && !thisRowCan; k++) {
                        for (int l = 0; l < row.squares.length / gbjustcuz.boxwidth && !thisRowCan; l++) {
                            if (row.squares[l * gbjustcuz.boxwidth].box != box)
                                thisRowCan = row.squares[l * gbjustcuz.boxwidth].box.squares[j * gbjustcuz.boxwidth + k].val == null && row.squares[l * gbjustcuz.boxwidth].box.squares[j * gbjustcuz.boxwidth + k].acceptedNumbers[i];
                        }
                    }
                    if (thisRowCan){
                        therowdex = j;
                        if (aRowCan) {
                            aRowCan = false;
                            break;
                        }
                        aRowCan = true;
                    }
                }
                if (aRowCan){
                    acceptedNumbers[i] = (therowdex != myRowdex);
                }
            }
            int myColdex = box.indexOf(this) % gbjustcuz.boxwidth;
            for (int i = 0; i < acceptedNumbers.length; i++){
                if (!acceptedNumbers[i])
                    continue;
                int thecoldex = -1;
                boolean aColCan = false;
                for (int j = 0; j < gbjustcuz.boxwidth; j++) {
                    boolean thisColCan = false;
                    for (int k = 0; k < gbjustcuz.boxhite && !thisColCan; k++) {
                        for (int l = 0; l < column.squares.length / gbjustcuz.boxhite && !thisColCan; l++) {
                            if (column.squares[l * gbjustcuz.boxhite].box != box)
                                thisColCan = column.squares[l * gbjustcuz.boxhite].box.squares[j + gbjustcuz.boxwidth * k].val == null && column.squares[l * gbjustcuz.boxhite].box.squares[j + gbjustcuz.boxwidth * k].acceptedNumbers[i];
                        }
                    }
                    if (thisColCan){
                        thecoldex = j;
                        if (aColCan) {
                            aColCan = false;
                            break;
                        }
                        aColCan = true;
                    }
                }
                if (aColCan){
                    acceptedNumbers[i] = (thecoldex != myColdex);
                }
            }
        }
        if (val == null && lastNumber() != null) {
            val = lastNumber();
            if (gbjustcuz.illegallySolved()){
                //System.out.println("I am the problem");
                /*superNo[val] = true;
                Arrays.fill(acceptedNumbers, true);
                val = null;*/
            }
            if (stackTrace)
            System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (1 + (loc / acceptedNumbers.length))) + ") by POE");
        }
        if (!Arrays.equals(previousVals, acceptedNumbers) || (startedUnsolved ^ val == null)){
            markAllDirty();
            /*for (Square connection : box.squares){
                connection.updateAcceptance(stackTrace, limit);
            }
            for (Square connection : row.squares){
                connection.updateAcceptance(stackTrace, limit);
            }
            for (Square connection : column.squares){
                connection.updateAcceptance(stackTrace, limit);
            }*/
            return true;
        }
        return false;
    }

    public void updateObviously(boolean stackTrace) {
        if (val != null)
            for (int i = 0; i < acceptedNumbers.length; i++)
                acceptedNumbers[i] = val == i;
        if (val == null) {
            for (Square square : row.squares) {
                if (val == null && square != this && square.val != null && acceptedNumbers[square.val]) {
                    acceptedNumbers[square.val] = false;
                    if (lastNumber() != null) {
                        val = lastNumber();
                        if (gbjustcuz.illegallySolved()){
                            //System.out.println("Im da prolem");
                            /*superNo[val] = true;
                            val = null;
                            Arrays.fill(acceptedNumbers, true);
                            break;*/
                        }
                        if (stackTrace)
                            System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ") through elimination in row");
                    }
                }
            }
            for (Square square : box.squares) {
                if (val == null && square != this && square.val != null && acceptedNumbers[square.val]) {
                    acceptedNumbers[square.val] = false;
                    if (lastNumber() != null) {
                        val = lastNumber();
                        if (gbjustcuz.illegallySolved()){
                            //System.out.println("Im da prolem");
                            /*superNo[val] = true;
                            val = null;
                            Arrays.fill(acceptedNumbers, true);
                            break;*/
                        }
                        if (stackTrace)
                            System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ") thorugh elimination in box");
                    }
                }
            }
            for (Square square : column.squares) {
                if (val == null && square != this && square.val != null && acceptedNumbers[square.val]) {
                    acceptedNumbers[square.val] = false;
                    if (lastNumber() != null) {
                        val = lastNumber();
                        if (gbjustcuz.illegallySolved()){
                            //System.out.println("Im da prolem");
                            /*superNo[val] = true;
                            val = null;
                            Arrays.fill(acceptedNumbers, true);
                            break;*/
                        }
                        if (stackTrace)
                            System.out.println("#" + gbjustcuz.solveNumber() + " is " + (val + 1) + " at " + "(" + (1 + (loc % acceptedNumbers.length)) + ", " + (1 + (loc / acceptedNumbers.length)) + ") through elimination in column");
                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return val != null ? "" + (val + 1) : "?";
    }

    public Integer lastNumber(){
        Integer out = null;
        for (int i = 0; i < acceptedNumbers.length; i++){
            if (acceptedNumbers[i] && !superNo[i])
                if (out == null)
                    out = i;
                else
                    return null;
        }
        return out;
    }

    public int minPossible(){
        for (int i = 0; i < acceptedNumbers.length; i++)
            if (acceptedNumbers[i])
                return i;
        return -1;
    }

    public int maxPossible(){
        for (int i = acceptedNumbers.length - 1; i >= 0; i--)
            if (acceptedNumbers[i])
                return i;
        return -1;
    }

    public int countPossibilities(){
        int out = 0;
        for (boolean can : acceptedNumbers)
            if (can)
                out++;
        return out;
    }

    public void markAllDirty() {
        row.isDirty = column.isDirty = box.isDirty = true;
    }
}
